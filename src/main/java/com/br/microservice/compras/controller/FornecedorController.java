package com.br.microservice.compras.controller;

import com.br.microservice.compras.model.Fornecedor;
import com.br.microservice.compras.service.FornecedorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fornecedor")
@RestControllerAdvice
@Slf4j
public class FornecedorController {

    private final FornecedorService fornecedorService;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody Fornecedor fornecedor) {
        log.info("---Recebendo informações do Fornecedor---");
        if (fornecedor == null || fornecedor.getCnpj() == null) {
            log.warn("---Fornecedor ou CNPJ null---");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fornecedor ou CNPJ nulo");
        }
        String clearCnpj = fornecedor.getCnpj().replaceAll("[\\.-]", "");
        if (clearCnpj.isBlank()) {
            log.warn("---CNPJ isblank---");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi informado um fornecedor com CNPJ válido");
        }
        fornecedor.setCnpj(clearCnpj);
        try {
            fornecedorService.execute(fornecedor);
        } catch (IllegalArgumentException e) {
            log.warn("---Não foi possível cadastro do fornecedor---");
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Não foi possível realizar o cadastro");
        }
        return ResponseEntity.created(URI.create("/fornecedor/" + fornecedor.getCnpj())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> buscarPorId(@PathVariable Long id) {
        if (id == null) {
            log.warn("---ID não informado---");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID não informado");
        }
        Optional<Fornecedor> fornecedor = fornecedorService.buscaPorId(id);
        if (!fornecedor.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fornecedor não encontrado");
        } else {
            return new ResponseEntity<>(fornecedor.get(), HttpStatus.OK);
        }
    }

    @GetMapping
    public List<Fornecedor> obterListaFornecedores() {
        return fornecedorService.obterListaFornecedores();
    }


}
