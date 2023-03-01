package com.br.microservice.compras.service;

import com.br.microservice.compras.model.Fornecedor;
import com.br.microservice.compras.repository.FornecedorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FornecedorService {
    private final FornecedorRepository fornecedorRepository;

    public void salvar(Fornecedor fornecedor) {
        Optional<Fornecedor> fornecedorEncontrado = buscarPorCnpj(fornecedor.getCnpj());
        log.info("---Cadastro de cliente - buscando se já existe CNPJ cadastrado---");
        if (fornecedorEncontrado.isPresent()) {
            log.warn("---Cadastro invalido, cnpj já existe em outro cadastro---");
            throw new IllegalArgumentException("CNPJ já está cadastrado em outro fornecedor.");
        }
        log.info("---Salvando fornecedor no repository---");
        fornecedorRepository.save(fornecedor);
    }

    public Optional<Fornecedor> buscarPorCnpj(String cnpj) {
        return fornecedorRepository.findByCnpj(cnpj);
    }

    public Optional<Fornecedor> buscaPorId(Long id) {
        log.info("---Buscando Fornecedor por ID---");
        return fornecedorRepository.findById(id);
    }

    public List<Fornecedor> obterListaFornecedores() {
        return (List<Fornecedor>) fornecedorRepository.findAll();
    }

}
