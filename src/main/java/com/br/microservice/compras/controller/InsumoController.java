package com.br.microservice.compras.controller;

import com.br.microservice.compras.model.Insumo;
import com.br.microservice.compras.service.InsumoService;
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
@RequestMapping("/insumo")
@Slf4j
public class InsumoController {
    private final InsumoService insumoService;


    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody Insumo insumo) {
        log.info("---Recebendo informações do Insumo---");
        if (insumo == null || insumo.getNome() == null) {
            log.warn("---Insumo ou nome do insumo null---");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insumo oi nome está nulo");
        }
        try {
            insumoService.salvar(insumo);
        } catch (IllegalArgumentException e) {
            log.warn("---Não foi possível cadastrar o insumo---");
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Não foi possível realizar o cadastro");
        }
        return ResponseEntity.created(URI.create("/insumo/" + insumo.getNome())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Insumo> buscarPorId(@PathVariable Long id) {
        if (id == null){
            log.warn("---ID não foi informado---");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID não informado");
        }
        Optional<Insumo> insumo = insumoService.buscaPorId(id);
        if (!insumo.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Insumo não encontrado");
        } else {
            return new ResponseEntity<>(insumo.get(), HttpStatus.OK);
        }
    }

    @GetMapping
    public List<Insumo> obterListaInsumo() {
        return insumoService.obterListaInsumos();
    }

}
