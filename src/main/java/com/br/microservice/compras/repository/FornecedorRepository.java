package com.br.microservice.compras.repository;

import com.br.microservice.compras.model.Fornecedor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FornecedorRepository extends CrudRepository<Fornecedor,Long> {
    Optional<Fornecedor> findByCnpj (String cnpj);
    //Optional<Fornecedor> findById (Long id);
}
