package br.com.xbrain.apivendas.modulos.produto.controller;

import br.com.xbrain.apivendas.modulos.comum.data.service.DataHoraService;
import br.com.xbrain.apivendas.modulos.produto.dto.ProdutoResponse;
import br.com.xbrain.apivendas.modulos.produto.model.Produto;
import br.com.xbrain.apivendas.modulos.produto.service.ProdutoService;
import br.com.xbrain.apivendas.modulos.vendedor.dto.VendedorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoService service;

    @MockBean
    private DataHoraService dataHoraService;

    @Test
    public void produto_cadastrar_sucesso() throws Exception {
        var dataAtual = LocalDateTime.now();

        var produto = Produto.builder()
                .id(1)
                .nome("Caneta Vermelha")
                .valorProduto(new BigDecimal(3.0))
                .dataCadastro(dataAtual)
                .build();

        when(service.cadastrar(any())).thenReturn(ProdutoResponse.of(produto));

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Caneta Vermelha\", " +
                                "\"valorProduto\": 3.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Caneta Vermelha"))
                .andExpect(jsonPath("$.valorProduto").value(3.0));
    }

    @Test
    public void produto_cadastrar_nomeVazio_badRequest() throws Exception {

        doThrow(HttpClientErrorException.BadRequest.class).when(service).cadastrar(any());

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"\", " +
                                "\"valorProduto\": 3.0}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void produto_cadastrar_nomeComMenosDeTresLetras_badRequest() throws Exception {

        doThrow(HttpClientErrorException.BadRequest.class).when(service).cadastrar(any());

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Ab\", " +
                                "\"valorProduto\": 3.0}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void produto_cadastrar_valorZero_badRequest() throws Exception {

        doThrow(HttpClientErrorException.BadRequest.class).when(service).cadastrar(any());

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Caneta Vermelha\", " +
                                "\"valorProduto\": 0.0}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void produto_detalhar_sucesso() throws Exception {
        var dataAtual = LocalDateTime.now();

        var produto = Produto.builder()
                .id(1)
                .nome("Caneta Vermelha")
                .valorProduto(new BigDecimal(3.0))
                .dataCadastro(dataAtual)
                .build();

        when(service.detalhar(any())).thenReturn(ProdutoResponse.of(produto));

        mockMvc.perform(get("/produtos/detalhar")
                        .param("id", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Caneta Vermelha"))
                .andExpect(jsonPath("$.valorProduto").value(3.0));

    }

    @Test
    public void produto_detalhar_idNaoCadastrado_notFound() throws Exception {

        doThrow(new EntityNotFoundException("Produto não cadastrado!")).when(service).detalhar(1);

        mockMvc.perform(get("/produtos/detalhar")
                        .param("id", String.valueOf(1)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value("Não foi possível realizar essa operação!"))
                .andExpect(jsonPath("$.detalhes").value("Produto não cadastrado!"));
    }

    @Test
    public void produto_atualizar_sucesso() throws Exception {
        var dataAtual = LocalDateTime.now();

        var produto = Produto.builder()
                .id(1)
                .nome("Caneta Vermelha Atualizada")
                .valorProduto(new BigDecimal(4.0))
                .dataCadastro(dataAtual)
                .build();

        when(service.atualizar(any(), any())).thenReturn(ProdutoResponse.of(produto));

        mockMvc.perform(put("/produtos/atualizar")
                        .param("id", String.valueOf(1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Caneta Vermelha Atualizada\", " +
                                "\"valorProduto\": 4.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Caneta Vermelha Atualizada"))
                .andExpect(jsonPath("$.valorProduto").value(4.0));

    }

    @Test
    public void produto_atualizar_idNaoCadastrado_notFound() throws Exception {

        doThrow(new EntityNotFoundException("Produto não cadastrado!")).when(service).atualizar(any(), any());

        mockMvc.perform(put("/produtos/atualizar")
                        .param("id", String.valueOf(1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Caneta Vermelha Atualizada\", " +
                                "\"valorProduto\": 4.0}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value("Não foi possível realizar essa operação!"))
                .andExpect(jsonPath("$.detalhes").value("Produto não cadastrado!"));
    }

    @Test
    public void produto_atualizar_nomeVazio_badRequest() throws Exception {

        doThrow(HttpClientErrorException.BadRequest.class).when(service).atualizar(any(), any());

        mockMvc.perform(put("/produtos/atualizar")
                        .param("id", String.valueOf(1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"\", " +
                                "\"valorProduto\": 3.0}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void produto_atualizar_nomeComMenosDeTresLetras_badRequest() throws Exception {

        doThrow(HttpClientErrorException.BadRequest.class).when(service).atualizar(any(), any());

        mockMvc.perform(put("/produtos/atualizar")
                        .param("id", String.valueOf(1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Ab\", " +
                                "\"valorProduto\": 3.0}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void produto_atualizar_valorZero_badRequest() throws Exception {

        doThrow(HttpClientErrorException.BadRequest.class).when(service).atualizar(any(), any());

        mockMvc.perform(put("/produtos/atualizar")
                        .param("id", String.valueOf(1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Caneta Vermelha\", " +
                                "\"valorProduto\": 0.0}"))
                .andExpect(status().isBadRequest());
    }
}