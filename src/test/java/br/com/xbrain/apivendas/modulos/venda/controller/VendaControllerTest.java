package br.com.xbrain.apivendas.modulos.venda.controller;

import br.com.xbrain.apivendas.modulos.comum.data.service.DataHoraService;
import br.com.xbrain.apivendas.modulos.produto.model.Produto;
import br.com.xbrain.apivendas.modulos.venda.dto.AtualizarVendaRequest;
import br.com.xbrain.apivendas.modulos.venda.dto.VendaResponse;
import br.com.xbrain.apivendas.modulos.venda.model.Venda;
import br.com.xbrain.apivendas.modulos.venda.service.VendaService;
import br.com.xbrain.apivendas.modulos.vendedor.model.Vendedor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static br.com.xbrain.apivendas.modulos.helper.TestHelper.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VendaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VendaService service;

    @MockBean
    private DataHoraService dataHoraService;

    @Test
    public void venda_listarPorVendedor_sucesso() throws Exception {

        var dataAtual = LocalDateTime.now();

        var vendas = List.of(
                umaVenda(1, dataAtual, umVendedor(1, dataAtual), List.of(umProduto(1, dataAtual))));

        when(service.listarPorVendedor(1)).thenReturn(VendaResponse.of(vendas));

        mockMvc.perform(get("/vendas/listar/vendedor")
                        .param("id", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].valorVenda").value(3.0))
                .andExpect(jsonPath("$.[0].idVendedor").value(1))
                .andExpect(jsonPath("$.[0].nomeVendedor").value("Agenor Ronega"));
    }

    @Test
    public void venda_listarPorPeriodo_sucesso() throws Exception {

        var dataAtual = LocalDateTime.now();

        var inicio = LocalDate.of(2022, 5, 10);
        var fim = LocalDate.of(2022, 5, 10);

        var vendas = List.of(
                umaVenda(1, dataAtual, umVendedor(1, dataAtual), List.of(umProduto(1, dataAtual))));

        when(service.listarPorPeriodo(inicio, fim)).thenReturn(VendaResponse.of(vendas));

        mockMvc.perform(get("/vendas/listar/periodo")
                        .param("inicio", "10-05-2022")
                        .param("fim", "10-05-2022"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].valorVenda").value(3.0))
                .andExpect(jsonPath("$.[0].idVendedor").value(1))
                .andExpect(jsonPath("$.[0].nomeVendedor").value("Agenor Ronega"));
    }

    @Test
    public void venda_listarPorVendedorPeriodo_sucesso() throws Exception {

        var dataAtual = LocalDateTime.now();

        var inicio = LocalDate.of(2022, 5, 10);
        var fim = LocalDate.of(2022, 5, 10);

        var vendas = List.of(
                umaVenda(1, dataAtual, umVendedor(1, dataAtual), List.of(umProduto(1, dataAtual))));

        when(service.listarPorVendedorPeriodo(1, inicio, fim)).thenReturn(VendaResponse.of(vendas));

        mockMvc.perform(get("/vendas/listar/vendedor/periodo")
                        .param("id", String.valueOf(1))
                        .param("inicio", "10-05-2022")
                        .param("fim", "10-05-2022"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].valorVenda").value(3.0))
                .andExpect(jsonPath("$.[0].idVendedor").value(1))
                .andExpect(jsonPath("$.[0].nomeVendedor").value("Agenor Ronega"));
    }

    @Test
    public void venda_cadastrar_sucesso() throws Exception {

        var dataAtual = LocalDateTime.now();

        var vendedor = umVendedor(1, dataAtual);
        var produtos = List.of(umProduto(1, dataAtual));
        var venda = umaVenda(1, dataAtual, vendedor, produtos);

        when(service.cadastrar(any())).thenReturn(VendaResponse.of(venda));

        mockMvc.perform(post("/vendas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idVendedor\": 1," +
                                " \"idProdutos\": [1]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idVendedor").value(1))
                .andExpect(jsonPath("$.nomeVendedor").value("Agenor Ronega"))
                .andExpect(jsonPath("$.valorVenda").value(3.0));
    }

    @Test
    public void venda_cadastrar_idVendedorNulo_badRequest() throws Exception {

        doThrow(HttpClientErrorException.BadRequest.class).when(service).cadastrar(any());

        mockMvc.perform(post("/vendas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idVendedor\": ," +
                                "\"idProdutos\": [1]}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void venda_cadastrar_produtoVazio_badRequest() throws Exception {

        doThrow(HttpClientErrorException.BadRequest.class).when(service).cadastrar(any());

        mockMvc.perform(post("/vendas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idVendedor\": 1," +
                                "\"idProdutos\": []}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void venda_detalhar_sucesso() throws Exception {

        var dataAtual = LocalDateTime.now();

        var vendedor = umVendedor(1, dataAtual);
        var produtos = List.of(umProduto(1, dataAtual));
        var venda = umaVenda(1, dataAtual, vendedor, produtos);

        when(service.detalhar(any())).thenReturn(VendaResponse.of(venda));

        mockMvc.perform(get("/vendas/detalhar")
                        .param("id", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idVendedor").value(1))
                .andExpect(jsonPath("$.nomeVendedor").value("Agenor Ronega"))
                .andExpect(jsonPath("$.valorVenda").value(3.0));
    }

    @Test
    public void venda_detalhar_idNaoCadastrado_notFound() throws Exception {

        doThrow(new EntityNotFoundException("Venda n??o cadastrada!")).when(service).detalhar(1);

        mockMvc.perform(get("/vendas/detalhar")
                        .param("id", String.valueOf(1)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value("N??o foi poss??vel realizar essa opera????o!"))
                .andExpect(jsonPath("$.detalhes").value("Venda n??o cadastrada!"));
    }

    @Test
    public void venda_atualizar_sucesso() throws Exception {

        var dataAtual = LocalDateTime.now();

        var vendedor = umVendedor(1, dataAtual);
        var produtos = List.of(umProduto(1, dataAtual));
        var vendaAtualizada = umaVenda(1, dataAtual, vendedor, produtos);

        when(service.atualizar(any(), any())).thenReturn(VendaResponse.of(vendaAtualizada));

        mockMvc.perform(put("/vendas/atualizar")
                        .param("id", String.valueOf(1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idProdutos\": [1]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idVendedor").value(1))
                .andExpect(jsonPath("$.nomeVendedor").value("Agenor Ronega"))
                .andExpect(jsonPath("$.valorVenda").value(3.0));
    }

    @Test
    public void venda_atualizar_idNaoCadastrado_notFound() throws Exception {

        doThrow(new EntityNotFoundException("Venda n??o cadastrada!")).when(service).atualizar(any(), any());

        mockMvc.perform(put("/vendas/atualizar")
                        .param("id", String.valueOf(1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idProdutos\": [1]}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value("N??o foi poss??vel realizar essa opera????o!"))
                .andExpect(jsonPath("$.detalhes").value("Venda n??o cadastrada!"));
    }

    @Test
    public void venda_atualizar_produtoVazio_badRequest() throws Exception {

        doThrow(HttpClientErrorException.BadRequest.class).when(service).atualizar(any(), any());

        mockMvc.perform(put("/vendas/atualizar")
                        .param("id", String.valueOf(1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idProdutos\": []}"))
                .andExpect(status().isBadRequest());
    }
}
