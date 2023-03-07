package com.br.microservice.compras.dto;

import com.br.microservice.compras.enums.TipoItem;
import com.br.microservice.compras.model.Fornecedor;
import com.br.microservice.compras.model.Insumo;
import com.br.microservice.compras.model.OrdemCompra;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsumoDTO {
    private Long id;
    private String nome;
    private String marca;
    private String unidade;
    private double valor;
    private int quantidade;
    private TipoItem item;

    public InsumoDTO of(Insumo insumo) {
        return InsumoDTO.builder()
                .id(insumo.getId())
                .marca(insumo.getNome())
                .unidade(insumo.getUnidade())
                .valor(insumo.getValor())
                .quantidade(insumo.getQuantidade())
                .item(insumo.getItem())
                .build();
    }
    public Insumo toEntity() {
         Insumo insumo = new Insumo();
         insumo.setId(id);
         insumo.setMarca(marca);
         insumo.setUnidade(unidade);
         insumo.setValor(valor);
         insumo.setQuantidade(quantidade);
         insumo.setItem(item);

     return insumo;
    }

}
