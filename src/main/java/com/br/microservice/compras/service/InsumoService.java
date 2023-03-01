package com.br.microservice.compras.service;

import com.br.microservice.compras.model.Insumo;
import com.br.microservice.compras.repository.InsumoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InsumoService {
    private final InsumoRepository insumoRepository;


    public void salvar(Insumo insumo) {
        Optional<Insumo> insumoEncontrado = buscaPorNome(insumo.getNome());
        log.info("---Cadastro de insumo, buscando por nome---");
        if (insumoEncontrado.isPresent()) {
            log.warn("---Cadastro invalido,já existe um insumo cadastrado com o mesmo nome---");
            throw new IllegalArgumentException("Nome já está cadastrado em outro insumo.");
        }
        log.info("---Salvando insumo no repository---");
        insumoRepository.save(insumo);
    }

    public Optional<Insumo> buscaPorNome(String nome) {
        return insumoRepository.findByNome(nome.toUpperCase());
    }

    public Optional<Insumo> buscaPorId(Long id) {
        log.info("---Buscando Insumo por ID informado---");
        return insumoRepository.findById(id);
    }

    public List<Insumo> obterListaInsumos() {
        return (List<Insumo>) insumoRepository.findAll();
    }

}
