package com.br.microservice.compras.service;


import com.br.microservice.compras.dto.OrdemCompraDTO;
import com.br.microservice.compras.exception.InformacaoNaoEncontradaException;
import com.br.microservice.compras.model.Fornecedor;
import com.br.microservice.compras.model.Insumo;
import com.br.microservice.compras.model.OrdemCompra;
import com.br.microservice.compras.producer.OrdemCompraProducer;
import com.br.microservice.compras.repository.FornecedorRepository;
import com.br.microservice.compras.repository.InsumoRepository;
import com.br.microservice.compras.repository.OrdemCompraRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdemCompraService {

    @Autowired
    private final OrdemCompraRepository ordemCompraRepository;
    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private OrdemCompraProducer producer;

    @Autowired
    private final InsumoRepository insumoRepository;

    public Optional<OrdemCompra> cadastrar(OrdemCompraDTO ordemCompra) throws InformacaoNaoEncontradaException {
        Optional<Fornecedor> fornecedorEncontrado = fornecedorRepository.findById(ordemCompra.getFornecedor());
        OrdemCompra novaOrdem = ordemCompra.toEntity();
        novaOrdem.setIdentificador(UUID.randomUUID().toString());
        OrdemCompra salva = null;

        if (fornecedorEncontrado.isEmpty()) {
            throw new InformacaoNaoEncontradaException("Fornecedor não encontrado!");
        } else {

            fornecedorEncontrado.ifPresent(novaOrdem::setFornecedor);
            novaOrdem.setDataCriacao(LocalDateTime.now());
            List<Insumo> insumoList = ordemCompra.getListaInsumos();
            int quantidadeInsumos =
                    insumoList.stream().reduce(0,
                            (subtotal, insumo) -> subtotal + insumo.getQuantidade(), Integer::sum);


            novaOrdem.setQuantidadeTotal(quantidadeInsumos);

            double valorTotal = 0;
            List<Insumo> novoInsumoList = new ArrayList<>();

            for (Insumo insumo : insumoList) {
                double valorTotalInsumo = insumo.getValor() * insumo.getQuantidade();
                valorTotal = valorTotal + valorTotalInsumo;
                assert false;

                insumoRepository.save(insumo);
                novoInsumoList.add(insumo);
            }
            novaOrdem.setListaInsumos(novoInsumoList);
            novaOrdem.setValorTotal(valorTotal);
            salva = ordemCompraRepository.save(novaOrdem);
            producer.enviarOrdemCompraParaFinanceiro(salva);
        }
        return Optional.of(salva);
    }

    public List<OrdemCompra> ordemCompraList() {
        return (List<OrdemCompra>) ordemCompraRepository.findAll();
    }

    public Optional<OrdemCompra> encontrarPorId(Long id) {
        return ordemCompraRepository.findById(id);
    }

    public Optional<OrdemCompra> atualizarOrdemCompra(OrdemCompraDTO ordemCompra, Long id) throws InformacaoNaoEncontradaException {
        Optional<OrdemCompra> ordemEncontrada = ordemCompraRepository.findById(id);

        if(ordemEncontrada.isPresent()) {

            if(ordemCompra.getDataVencimento() != null) {
                ordemEncontrada.get().setDataVencimento(ordemCompra.getDataVencimento());
            }

            if(ordemCompra.getFornecedor() != 0) {
                Optional<Fornecedor> fornecedorEncontrado = fornecedorRepository.findById(ordemCompra.getFornecedor());
                if (fornecedorEncontrado.isEmpty()) {
                    throw new InformacaoNaoEncontradaException("Fornecedor não encontrado!");
                } else {
                    ordemEncontrada.get().setFornecedor(fornecedorEncontrado.get());
                }
            }

            ordemCompraRepository.save(ordemEncontrada.get());
        }

        return ordemEncontrada;
    }

    public Optional<OrdemCompra> deletarOrdemCompra(Long id) {
        Optional<OrdemCompra> ordemEncontrada = ordemCompraRepository.findById(id);
        ordemEncontrada.ifPresent(ordemCompraRepository::delete);

        return ordemEncontrada;
    }

    public Optional<OrdemCompra> enviarOrdemCompraPagamento(String ordemCompra) throws InformacaoNaoEncontradaException {
        Optional<OrdemCompra> ordemEncontrada = ordemCompraRepository.findByIdentificador(ordemCompra);

        if(ordemEncontrada.isPresent()) {
            producer.enviarOrdemCompraParaFinanceiroPagar(ordemCompra);
            } else {
            throw new InformacaoNaoEncontradaException("Ordem de compra não encontrada!");
            }
        return ordemEncontrada;
    }

    public void atualizarOrdemPaga(String identificador) throws InformacaoNaoEncontradaException {
        Optional<OrdemCompra> ordemEncontrada = ordemCompraRepository.findByIdentificador(identificador);

        if(ordemEncontrada.isPresent()) {
            ordemEncontrada.get().setPago(true);
            ordemEncontrada.get().setDataPagamento(LocalDateTime.now());
        } else {
            throw new InformacaoNaoEncontradaException("Ordem de compra não encontrada!");
        }

        ordemCompraRepository.save(ordemEncontrada.get());
    }
}



