package com.br.microservice.compras.service;

import com.br.microservice.compras.model.Fornecedor;
import com.br.microservice.compras.repository.FornecedorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FornecedorService {
    private final FornecedorRepository fornecedorRepository;

    public Fornecedor execute(Fornecedor fornecedor) {
        log.info("---Salvando fornecedor no repository---");
        fornecedor.setIdentificador(UUID.randomUUID().toString());
        log.info("Fornecedor de ID {}", fornecedor.getIdentificador());

        return fornecedorRepository.findByCnpj(fornecedor.getCnpj()).orElse(fornecedorRepository.save(fornecedor));
    }

    public Optional<Fornecedor> buscarPorCnpj(String cnpj) {
        return fornecedorRepository.findByCnpj(cnpj);
    }

    public Optional<Fornecedor> buscaPorId(Long id) {
        log.info("---Buscando Fornecedor por ID informado---");
        return fornecedorRepository.findById(id);
    }

    public List<Fornecedor> obterListaFornecedores() {
        return (List<Fornecedor>) fornecedorRepository.findAll();
    }

}
