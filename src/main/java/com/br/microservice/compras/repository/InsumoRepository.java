package com.br.microservice.compras.repository;

import com.br.microservice.compras.model.Insumo;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InsumoRepository extends CrudRepository<Insumo,Long> {
    Optional<Insumo> findByNome(String nome);
}
