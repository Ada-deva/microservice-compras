package com.br.microservice.compras.model;

import com.br.microservice.compras.enums.TipoFornecedor;
import com.br.microservice.compras.enums.TipoSeguimento;
import com.br.microservice.compras.validation.ClienteGroupSequenceProvider;
import com.br.microservice.compras.validation.PessoaFisica;
import com.br.microservice.compras.validation.PessoaJuridica;
import lombok.*;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;

import javax.persistence.*;


@Data
@Entity
@Table(name = "Fornecedor")
@GroupSequenceProvider(value = ClienteGroupSequenceProvider.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;

    @Column(unique = true, nullable = false)
    @CPF(groups = PessoaFisica.class)
    @CNPJ(groups = PessoaJuridica.class)
    private String cpfOuCnpj;

    private TipoSeguimento seguimento = TipoSeguimento.OUTRO;
    private TipoFornecedor tipoFornecedor;
    private String identificador;

}
