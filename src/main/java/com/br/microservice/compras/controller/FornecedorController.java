package com.br.microservice.compras.controller;

import com.br.microservice.compras.dto.FornecedorDTO;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Fornecedor> salvar(@RequestBody FornecedorDTO fornecedor) {
        Optional<Fornecedor> response;
        try {
            response = fornecedorService.cadastrar(fornecedor.toEntity());

            if(response.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Fornecedor não cadastrado.");
            }

        } catch (IllegalArgumentException e) {
            log.warn("---Não foi possível cadastro do fornecedor---");
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Não foi possível realizar o cadastro");
        }
        return new ResponseEntity<>(response.get(), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> buscarPorId(@PathVariable Long id) {
        if (id == null) {
            log.warn("---ID não informado---");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID não informado");
        }
        Optional<Fornecedor> fornecedor = fornecedorService.buscaPorId(id);
        if (fornecedor.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fornecedor não encontrado");
        } else {
            return new ResponseEntity<>(fornecedor.get(), HttpStatus.OK);
        }
    }

    @GetMapping
    public List<Fornecedor> obterListaFornecedores() {
        return fornecedorService.obterListaFornecedores();
    }


    @GetMapping("/doc")
    public ResponseEntity<Fornecedor> buscarPorCpfOuCNPJ(@RequestBody String cpfOuCnpj) {
        if (cpfOuCnpj == null) {
            log.warn("---CPF ou CNPJ não informado---");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF ou CNPJ não informado");
        }
        Optional<Fornecedor> fornecedor = fornecedorService.buscarPorCpfouCnpj(cpfOuCnpj);
        if (fornecedor.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fornecedor não encontrado");
        } else {
            return new ResponseEntity<>(fornecedor.get(), HttpStatus.OK);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Fornecedor> atualizarFornecedor(@RequestBody FornecedorDTO fornecedor, @PathVariable Long id) {

        Optional<Fornecedor> fornecedorAtualizado = fornecedorService.atualizarFornecedor(fornecedor, id);

        if(fornecedorAtualizado.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fornecedor não encontrado");
        } else {
            return new ResponseEntity<>(fornecedorAtualizado.get(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Fornecedor> deletarFornecedor(@PathVariable Long id) {

        Optional<Fornecedor> fornecedorDeletado = fornecedorService.deletarFornecedor(id);

        if(fornecedorDeletado.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fornecedor não encontrado");
        } else {
            return new ResponseEntity<>(fornecedorDeletado.get(), HttpStatus.OK);
        }

    }
}
