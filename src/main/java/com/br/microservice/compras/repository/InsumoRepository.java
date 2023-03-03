package com.br.microservice.compras.repository;

import com.br.microservice.compras.model.Insumo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface InsumoRepository extends CrudRepository<Insumo,Long> {
    Optional<Insumo> findByNome(String nome);
}
