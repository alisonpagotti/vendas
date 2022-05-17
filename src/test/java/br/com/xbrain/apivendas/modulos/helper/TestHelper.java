package br.com.xbrain.apivendas.modulos.helper;

import br.com.xbrain.apivendas.modulos.produto.dto.AtualizarProdutoRequest;
import br.com.xbrain.apivendas.modulos.produto.dto.ProdutoRequest;
import br.com.xbrain.apivendas.modulos.produto.model.Produto;
import br.com.xbrain.apivendas.modulos.venda.dto.AtualizarVendaRequest;
import br.com.xbrain.apivendas.modulos.venda.dto.VendaRequest;
import br.com.xbrain.apivendas.modulos.venda.model.Venda;
import br.com.xbrain.apivendas.modulos.vendedor.dto.AtualizarVendedorRequest;
import br.com.xbrain.apivendas.modulos.vendedor.dto.VendedorRequest;
import br.com.xbrain.apivendas.modulos.vendedor.model.Vendedor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class TestHelper {

    public static Vendedor umVendedor(Integer id, LocalDateTime dataCadastro) {
        return Vendedor.builder()
                .id(id)
                .nome("Agenor Ronega")
                .cpf("07745643433")
                .email("agenor.ronega@empresa.com.br")
                .dataCadastro(dataCadastro)
                .build();
    }

    public static Vendedor umVendedorAtualizado(Integer id, LocalDateTime dataCadastro) {
        return Vendedor.builder()
                .id(id)
                .nome("Agenor Ronega Junior")
                .cpf("07745643433")
                .email("agenor.ronega.junior@empresa.com.br")
                .dataCadastro(dataCadastro)
                .build();
    }

    public static AtualizarVendedorRequest umAtualizarVendedorRequest() {
        return AtualizarVendedorRequest.builder()
                .nome("Agenor Ronega Junior")
                .email("agenor.ronega.junior@empresa.com.br")
                .build();
    }

    public static VendedorRequest umVendedorRequest() {
        return VendedorRequest.builder()
                .nome("Agenor Ronega")
                .cpf("07745643433")
                .email("agenor.ronega@empresa.com.br")
                .build();
    }

    public static Produto umProduto(Integer id, LocalDateTime dataCadastro) {
        return Produto.builder()
                .id(id)
                .nome("Caneta Vermelha")
                .valorProduto(new BigDecimal("3.0"))
                .dataCadastro(dataCadastro)
                .build();
    }

    public static Produto umProdutoAtualizado(Integer id, LocalDateTime dataCadastro) {
        return Produto.builder()
                .id(id)
                .nome("Caneta Vermelha Atualizada")
                .valorProduto(new BigDecimal("4.0"))
                .dataCadastro(dataCadastro)
                .build();
    }

    public static ProdutoRequest umProdutoRequest() {
        return ProdutoRequest.builder()
                .nome("Caneta Vermelha")
                .valorProduto(new BigDecimal("3.0"))
                .build();
    }

    public static AtualizarProdutoRequest umAtualizarProdutoRequest(){
        return AtualizarProdutoRequest.builder()
                .nome("Caneta Vermelha Atualizada")
                .valorProduto(new BigDecimal("4.0"))
                .build();
    }

    public static Venda umaVenda(Integer id, LocalDateTime dataCadastro, Vendedor vendedor, List<Produto> produtos) {
        return Venda.builder()
                .id(id)
                .dataCadastro(dataCadastro)
                .valorVenda(new BigDecimal("3.0"))
                .vendedor(vendedor)
                .produtos(produtos)
                .build();
    }

    public static VendaRequest umaVendaRequest(Integer id, List<Integer> idProdutos) {
        return VendaRequest.builder()
                .idVendedor(id)
                .idProdutos(idProdutos)
                .build();
    }

    public static AtualizarVendaRequest umaAtualizarVendaRequest(List<Integer> idProdutos) {
        return AtualizarVendaRequest.builder()
                .idProdutos(idProdutos)
                .build();
    }
}
