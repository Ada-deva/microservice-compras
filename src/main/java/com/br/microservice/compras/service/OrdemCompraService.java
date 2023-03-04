package com.br.microservice.compras.service;

import com.br.microservice.compras.cliente.payload.request.FornecedorFinanceiroRequest;
import com.br.microservice.compras.cliente.payload.request.InsumoRequest;
import com.br.microservice.compras.cliente.payload.request.ItemOrdemCompraRequest;
import com.br.microservice.compras.cliente.payload.request.RealizarPagamentoFinanceiro;
import com.br.microservice.compras.model.Fornecedor;
import com.br.microservice.compras.model.Insumo;
import com.br.microservice.compras.model.ItemOrdemCompra;
import com.br.microservice.compras.model.OrdemCompra;
import com.br.microservice.compras.payloads.OrdemCompraRequest;
import com.br.microservice.compras.queue.out.PagarFornecedorMessageSender;
import com.br.microservice.compras.repository.OrdemCompraRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdemCompraService {
    private final OrdemCompraRepository ordemCompraRepository;
    private final FornecedorService fornecedorService;
    private final CreateOrdemCompraService createOrdemCompraService;
    private final InsumoService insumoService;
    private final PagarFornecedorMessageSender pagarFornecedorMessageSender;

    public void salvar(OrdemCompraRequest ordemCompraRequest) {
        Fornecedor fornecedor = salvarFornecedor(ordemCompraRequest);
        Insumo insumo = salvarInsumo(ordemCompraRequest);
        OrdemCompra ordemCompra = salvarOrdemCompra(ordemCompraRequest, insumo, fornecedor);

        enviarOrdemCompraParaFinanceiro(fornecedor, insumo, ordemCompra);

    }

    private void enviarOrdemCompraParaFinanceiro(Fornecedor fornecedor,Insumo insumo,OrdemCompra ordemCompra){
        RealizarPagamentoFinanceiro realizarPagamentoFinanceiro = new RealizarPagamentoFinanceiro();
        FornecedorFinanceiroRequest fornecedorFinanceiroRequest = new FornecedorFinanceiroRequest();
        fornecedorFinanceiroRequest.setNomeFornecedor(fornecedor.getNome());
        fornecedorFinanceiroRequest.setCnpj(fornecedor.getCnpj());
        fornecedorFinanceiroRequest.setIdentificador(fornecedor.getIdentificador());
        realizarPagamentoFinanceiro.setFornecedor(fornecedorFinanceiroRequest);

        ItemOrdemCompraRequest itemOrdemCompraRequest = new ItemOrdemCompraRequest();
        itemOrdemCompraRequest.setPreco(ordemCompra.getListaInsumos().iterator().next().getPreco());
        itemOrdemCompraRequest.setQuantidade(((Double)ordemCompra
                .getListaInsumos().iterator().next().getQuantidade()).intValue());

        InsumoRequest insumoRequest = new InsumoRequest();
        insumoRequest.setNome(insumo.getNome());
        insumoRequest.setIdentificador(insumo.getIdentificador());

        itemOrdemCompraRequest.setInsumo(insumoRequest);
        realizarPagamentoFinanceiro.setItens(List.of(itemOrdemCompraRequest));

        pagarFornecedorMessageSender.send(realizarPagamentoFinanceiro);

    }

    private Fornecedor salvarFornecedor(OrdemCompraRequest ordemCompra) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(ordemCompra.getFornecedor().getNome());
        fornecedor.setCnpj(ordemCompra.getFornecedor().getCnpj());
        return fornecedorService.execute(fornecedor);
    }


    private OrdemCompra salvarOrdemCompra(OrdemCompraRequest ordemCompraRequest, Insumo insumo, Fornecedor fornecedor) {
        OrdemCompra ordemCompra = new OrdemCompra();
        ordemCompra.setFornecedor(fornecedor);
        ItemOrdemCompra itemOrdemCompra = new ItemOrdemCompra();
        itemOrdemCompra.setQuantidade(ordemCompraRequest.getQuantidade());
        itemOrdemCompra.setPreco(ordemCompraRequest.getPrecoCompra());
        itemOrdemCompra.setInsumo(insumo);
        return createOrdemCompraService.execute(ordemCompra, List.of(itemOrdemCompra));
    }


    private Insumo salvarInsumo(OrdemCompraRequest ordemCompraRequest) {
        Insumo insumo = new Insumo();
        insumo.setQuantidade(ordemCompraRequest.getUnidadeQuantidade());
        insumo.setMarca(ordemCompraRequest.getMarca());
        insumo.setNome(ordemCompraRequest.getNome());
        insumo.setUnidade(ordemCompraRequest.getUnidade());
        return insumoService.salvar(insumo);
    }

}


