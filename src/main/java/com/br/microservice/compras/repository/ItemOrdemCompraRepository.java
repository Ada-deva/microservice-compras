package com.br.microservice.compras.repository;

import com.br.microservice.compras.model.ItemOrdemCompra;
import com.br.microservice.compras.model.ItemOrdemCompraComposedKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemOrdemCompraRepository extends JpaRepository<ItemOrdemCompra, ItemOrdemCompraComposedKey> {
}
