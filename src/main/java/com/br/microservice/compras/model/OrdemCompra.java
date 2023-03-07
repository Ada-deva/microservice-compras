package com.br.microservice.compras.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@RequiredArgsConstructor
@Data
@Table(name = "ordem_compra")
@Builder
@AllArgsConstructor
public class OrdemCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime dataCriacao;

    private double valorTotal;
    private int quantidadeTotal;

    @Column(nullable = false)
    private LocalDate dataVencimento;

    private boolean isPago = false;

    private LocalDateTime dataPagamento;

    @OneToMany
    @JoinColumn(name = "insumo_id")
    private List<Insumo> listaInsumos;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fornecedor_id")
    private Fornecedor fornecedor;
    private String identificador;

}
