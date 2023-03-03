package com.br.microservice.compras.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Item_Ordem_Compra")
public class ItemOrdemCompra {

    @EmbeddedId
    private ItemOrdemCompraComposedKey itemOrdemCompraComposedKey;
    @ManyToOne
    @JoinColumn(name = "id_ordem_compra", insertable = false, updatable = false)
    private OrdemCompra ordemCompra;
    @ManyToOne
    @JoinColumn(name = "id_insumo", insertable = false, updatable = false)
    private Insumo insumo;
    private double preco;
    private double quantidade;
}
