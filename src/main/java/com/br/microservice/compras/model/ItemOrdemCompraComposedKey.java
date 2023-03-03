package com.br.microservice.compras.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class ItemOrdemCompraComposedKey implements Serializable {
    @Column(name = "id_ordem_compra")
    private long idOrdemCompra;
    @Column(name = "id_insumo")
    private long idInsumo;

    public ItemOrdemCompraComposedKey(long idOrdemCompra, long idInsumo) {
        this.idOrdemCompra = idOrdemCompra;
        this.idInsumo = idInsumo;
    }
}
