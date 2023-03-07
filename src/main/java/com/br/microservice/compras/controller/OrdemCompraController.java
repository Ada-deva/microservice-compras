package com.br.microservice.compras.controller;

import com.br.microservice.compras.dto.OrdemCompraDTO;
import com.br.microservice.compras.exception.InformacaoNaoEncontradaException;
import com.br.microservice.compras.model.OrdemCompra;
import com.br.microservice.compras.service.OrdemCompraService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/ordemdecompra")
public class OrdemCompraController {
    private final OrdemCompraService ordemCompraService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<OrdemCompra> cadastrarOrdemCompra(@RequestBody OrdemCompraDTO ordemCompra) throws InformacaoNaoEncontradaException {

        Optional<OrdemCompra> novaOrdemCompra = ordemCompraService.cadastrar(ordemCompra);
        if(novaOrdemCompra.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Ordem de compra não cadastrada");
        } else {
            OrdemCompra response = novaOrdemCompra.get();
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
    }

    @GetMapping
    public ResponseEntity<List<OrdemCompra>> obterListaOrdemCompra() {
        return new ResponseEntity<>(ordemCompraService.ordemCompraList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdemCompra> encontrarPorId(@PathVariable Long id) {
        Optional<OrdemCompra> ordemCompra = ordemCompraService.encontrarPorId(id);

        if(ordemCompra.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ordem de compra não encontrada");
        } else {
            return new ResponseEntity<>(ordemCompra.get(), HttpStatus.OK);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrdemCompra> atualizarOrdemCompra(@RequestBody OrdemCompraDTO ordemCompra,
                                                            @PathVariable Long id) throws InformacaoNaoEncontradaException {

        Optional<OrdemCompra> ordemAtualizada = ordemCompraService.atualizarOrdemCompra(ordemCompra, id);

        if(ordemAtualizada.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ordem de compra não encontrada");
        } else {
            return new ResponseEntity<>(ordemAtualizada.get(), HttpStatus.OK);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrdemCompra> deletarOrdemCompra(@PathVariable Long id) {

        Optional<OrdemCompra> ordemCompraDeletada = ordemCompraService.deletarOrdemCompra(id);

        if(ordemCompraDeletada.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ordem de compra não encontrada");
        } else {
            return new ResponseEntity<>(ordemCompraDeletada.get(), HttpStatus.OK);
        }

    }

    @PatchMapping("/pagar/{identificador}")
    public ResponseEntity<OrdemCompra> enviarOrdemCompraPagamento(@PathVariable String identificador) throws InformacaoNaoEncontradaException {
        Optional<OrdemCompra> ordemCompra = ordemCompraService.enviarOrdemCompraPagamento(identificador);

        if(ordemCompra.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ordem de compra não encontrada");
        } else {
            return new ResponseEntity<>(ordemCompra.get(), HttpStatus.OK);
        }
    }
}
