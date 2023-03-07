package com.br.microservice.compras.service;

import com.br.microservice.compras.dto.FornecedorDTO;
import com.br.microservice.compras.model.Fornecedor;
import com.br.microservice.compras.producer.FornecedorProducer;
import com.br.microservice.compras.repository.FornecedorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FornecedorService {

    @Autowired
    private final FornecedorRepository fornecedorRepository;

    @Autowired
    private FornecedorProducer producer;

    public Optional<Fornecedor> cadastrar(Fornecedor fornecedor) {
        fornecedor.setIdentificador(UUID.randomUUID().toString());
        Fornecedor fornecedorSalvo = fornecedorRepository.save(fornecedor);
        producer.enviarFornecedorParaFinanceiro(fornecedor);
        return Optional.of(fornecedorSalvo);
    }

    public Optional<Fornecedor> buscarPorCpfouCnpj(String cnpj) {
        return fornecedorRepository.findByCpfOuCnpj(cnpj);
    }

    public Optional<Fornecedor> buscaPorId(Long id) {
        return fornecedorRepository.findById(id);
    }

    public List<Fornecedor> obterListaFornecedores() {
        return (List<Fornecedor>) fornecedorRepository.findAll();
    }

    public Optional<Fornecedor> atualizarFornecedor(FornecedorDTO fornecedor, Long id) {
        Optional<Fornecedor> fornecedorEncontrado = fornecedorRepository.findById(id);

        if(fornecedorEncontrado.isPresent()) {
            if(fornecedor.getNome() != null){
                fornecedorEncontrado.get().setNome(fornecedor.getNome());
            }

            if(fornecedor.getCpfOuCnpj() != null && fornecedor.getTipoFornecedor() != null) {
                fornecedorEncontrado.get().setCpfOuCnpj(fornecedor.getCpfOuCnpj());
                fornecedorEncontrado.get().setTipoFornecedor(fornecedor.getTipoFornecedor());
            }

            if(fornecedor.getSeguimento() != null) {
                fornecedorEncontrado.get().setSeguimento(fornecedor.getSeguimento());
            }

            fornecedorRepository.save(fornecedorEncontrado.get());
        }
        return fornecedorEncontrado;
    }

    public Optional<Fornecedor> deletarFornecedor(Long id) {
        Optional<Fornecedor> fornecedorEncontrado = fornecedorRepository.findById(id);
        fornecedorEncontrado.ifPresent(fornecedorRepository::delete);

        return fornecedorEncontrado;
    }
}
