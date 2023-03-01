package com.br.microservice.compras.controller;

import com.br.microservice.compras.service.InsumoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/insumo")
public class InsumoController {
    private final InsumoService insumoService;
}
