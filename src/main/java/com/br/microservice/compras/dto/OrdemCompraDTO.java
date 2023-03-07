package com.br.microservice.compras.dto;

import com.br.microservice.compras.model.Insumo;
import com.br.microservice.compras.model.OrdemCompra;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdemCompraDTO {

    private long id;

    private LocalDateTime dataCriacao;

    private int quantidadeTotal;
    private double valorTotal;
    private LocalDate dataVencimento;
    private boolean isPago;
    private LocalDateTime dataPagamento;
    private List<Insumo> listaInsumos;
    private long fornecedor;

    public OrdemCompraDTO of(OrdemCompra ordemCompra) {
        return OrdemCompraDTO.builder()
                .id(ordemCompra.getId())
                .dataCriacao(getDataCriacao())
                .quantidadeTotal(ordemCompra.getQuantidadeTotal())
                .valorTotal(ordemCompra.getValorTotal())
                .dataVencimento(ordemCompra.getDataVencimento())
                .isPago(ordemCompra.isPago())
                .dataPagamento(ordemCompra.getDataPagamento())
                .listaInsumos(ordemCompra.getListaInsumos())
                .fornecedor(ordemCompra.getFornecedor().getId())
                .build();
    }

    public OrdemCompra toEntity() {
        return OrdemCompra.builder()
                .id(id)
                .dataCriacao(dataCriacao)
                .quantidadeTotal(quantidadeTotal)
                .valorTotal(valorTotal)
                .dataVencimento(dataVencimento)
                .isPago(isPago)
                .dataPagamento(dataPagamento)
                .listaInsumos(listaInsumos)
                .build();
    }
}







