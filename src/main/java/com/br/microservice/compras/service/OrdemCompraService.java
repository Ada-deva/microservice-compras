package com.br.microservice.compras.service;

import com.br.microservice.compras.repository.OrdemCompraRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdemCompraService {
    private final OrdemCompraRepository ordemCompraRepository;

}
