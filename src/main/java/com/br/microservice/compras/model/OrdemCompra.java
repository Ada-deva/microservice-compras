package com.br.microservice.compras.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@RequiredArgsConstructor
@Data
@Table(name = "ordem_compra")
public class OrdemCompra {
    @Id
    @GeneratedValue
    private Long id;
    @CreatedDate
    private LocalDateTime dataCriacao;
    @ManyToOne
    @JoinColumn(name = "id_fornecedor")
    private Fornecedor fornecedor;
    @OneToMany(mappedBy = "ordem_compra")
    private List<ItemOrdemCompra> listaInsumos;


}
