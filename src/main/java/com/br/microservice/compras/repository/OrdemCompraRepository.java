package com.br.microservice.compras.repository;

import com.br.microservice.compras.model.OrdemCompra;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrdemCompraRepository extends CrudRepository<OrdemCompra, Long> {
    Optional<OrdemCompra> findByIdentificador(String identificador);
}
