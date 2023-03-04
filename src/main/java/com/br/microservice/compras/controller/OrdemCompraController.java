package com.br.microservice.compras.controller;

import com.br.microservice.compras.payloads.OrdemCompraRequest;
import com.br.microservice.compras.service.OrdemCompraService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/ordemdecompra")
public class OrdemCompraController {
    private final OrdemCompraService ordemCompraService;

    @PostMapping
    public void salvar(@RequestBody OrdemCompraRequest ordemCompraRequest) {
        ordemCompraService.salvar(ordemCompraRequest);
    }

}
