package br.com.xbrain.apivendas.modulos.vendedor.controller;

import br.com.xbrain.apivendas.modulos.comum.data.service.DataHoraService;
import br.com.xbrain.apivendas.modulos.vendedor.dto.MediaVendedorResponse;
import br.com.xbrain.apivendas.modulos.vendedor.dto.VendedorResponse;
import br.com.xbrain.apivendas.modulos.vendedor.model.MediaVendedor;
import br.com.xbrain.apivendas.modulos.vendedor.model.Vendedor;
import br.com.xbrain.apivendas.modulos.vendedor.service.VendedorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static br.com.xbrain.apivendas.modulos.helper.TestHelper.umVendedor;
import static br.com.xbrain.apivendas.modulos.helper.TestHelper.umVendedorAtualizado;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VendedorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VendedorService service;

    @MockBean
    private DataHoraService dataHoraService;

    @Test
    public void vendedor_mediaPorVendedor_sucesso() throws Exception {

        var inicio = LocalDate.of(2022, 5, 10);
        var fim = LocalDate.of(2022, 5, 10);

        var mediaVendedor = MediaVendedor.builder()
                .nome("Agenor Ronega")
                .totalVendas(1)
                .mediaDia(1.0)
                .build();

        when(service.mediaPorVendedor(1, inicio, fim)).thenReturn(MediaVendedorResponse.of(mediaVendedor));

        mockMvc.perform(get("/vendedores/media/vendedor")
                        .param("id", String.valueOf(1))
                        .param("inicio", "10-05-2022")
                        .param("fim", "10-05-2022"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Agenor Ronega"))
                .andExpect(jsonPath("$.totalVendas").value(1))
                .andExpect(jsonPath("$.mediaDia").value(1.0));
    }

    @Test
    public void vendedor_mediaTodosVendedores() throws Exception {

        var inicio = LocalDate.of(2022, 5, 10);
        var fim = LocalDate.of(2022, 5, 10);

        var mediaVendedor = List.of(MediaVendedor.builder()
                .nome("Agenor Ronega")
                .totalVendas(1)
                .mediaDia(1.0)
                .build());

        when(service.mediaTodosVendedores(inicio, fim)).thenReturn(MediaVendedorResponse.of(mediaVendedor));

        mockMvc.perform(get("/vendedores/media")
                        .param("inicio", "10-05-2022")
                        .param("fim", "10-05-2022"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].nome").value("Agenor Ronega"))
                .andExpect(jsonPath("$.[0].totalVendas").value(1))
                .andExpect(jsonPath("$.[0].mediaDia").value(1.0));
    }

    @Test
    public void vendedor_cadastrar_sucesso() throws Exception {

        var dataAtual = LocalDateTime.now();

        var vendedor = umVendedor(1, dataAtual);

        when(service.cadastrar(any())).thenReturn(VendedorResponse.of(vendedor));

        mockMvc.perform(post("/vendedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Agenor Ronega\", " +
                                "\"cpf\": \"07745643433\", " +
                                "\"email\":\"agenor.ronega@empresa.com.br\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Agenor Ronega"))
                .andExpect(jsonPath("$.cpf").value("07745643433"))
                .andExpect(jsonPath("$.email").value("agenor.ronega@empresa.com.br"));
    }

    @Test
    public void vendedor_cadastrar_nomeVazio_badRequest() throws Exception {

        doThrow(HttpClientErrorException.BadRequest.class).when(service).cadastrar(any());

        mockMvc.perform(post("/vendedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"\", " +
                                "\"cpf\": \"07745643433\", " +
                                "\"email\":\"agenor.ronega@empresa.com.br\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void vendedor_cadastrar_nomeComMenosDeTresLetras_badRequest() throws Exception {

        doThrow(HttpClientErrorException.BadRequest.class).when(service).cadastrar(any());

        mockMvc.perform(post("/vendedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Ab\", " +
                                "\"cpf\": \"07745643433\", " +
                                "\"email\":\"agenor.ronega@empresa.com.br\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void vendedor_cadastrar_cpfVazio_badRequest() throws Exception {

        doThrow(HttpClientErrorException.BadRequest.class).when(service).cadastrar(any());

        mockMvc.perform(post("/vendedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Agenor Ronega\", " +
                                "\"cpf\": \"\", " +
                                "\"email\":\"agenor.ronega@empresa.com.br\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void vendedor_cadastrar_cpfComMenosDeOnzeCaracteres_badRequest() throws Exception {

        doThrow(HttpClientErrorException.BadRequest.class).when(service).cadastrar(any());

        mockMvc.perform(post("/vendedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Agenor Ronega\", " +
                                "\"cpf\": \"077456434\", " +
                                "\"email\":\"agenor.ronega@empresa.com.br\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void vendedor_cadastrar_cpfComMaisDeOnzeCaracteres_badRequest() throws Exception {

        doThrow(HttpClientErrorException.BadRequest.class).when(service).cadastrar(any());

        mockMvc.perform(post("/vendedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Agenor Ronega\", " +
                                "\"cpf\": \"077456434333\", " +
                                "\"email\":\"agenor.ronega@empresa.com.br\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void vendedor_cadastrar_emailVazio_badRequest() throws Exception {

        doThrow(HttpClientErrorException.BadRequest.class).when(service).cadastrar(any());

        mockMvc.perform(post("/vendedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Agenor Ronega\", " +
                                "\"cpf\": \"07745643433\", " +
                                "\"email\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void vendedor_detalhar_sucesso() throws Exception {

        var dataAtual = LocalDateTime.now();

        var vendedor = umVendedor(1, dataAtual);

        when(service.detalhar(any())).thenReturn(VendedorResponse.of(vendedor));

        mockMvc.perform(get("/vendedores/detalhar")
                        .param("id", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Agenor Ronega"))
                .andExpect(jsonPath("$.cpf").value("07745643433"))
                .andExpect(jsonPath("$.email").value("agenor.ronega@empresa.com.br"));
    }

    @Test
    public void vendedor_detalhar_idNaoCadastrado_notFound() throws Exception {

        doThrow(new EntityNotFoundException("Vendedor não cadastrado!")).when(service).detalhar(1);

        mockMvc.perform(get("/vendedores/detalhar")
                        .param("id", String.valueOf(1)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value("Não foi possível realizar essa operação!"))
                .andExpect(jsonPath("$.detalhes").value("Vendedor não cadastrado!"));
    }

    @Test
    public void vendedor_atualizar_sucesso() throws Exception {

        var dataAtual = LocalDateTime.now();

        var vendedorAtualizado = umVendedorAtualizado(1, dataAtual);

        when(service.atualizar(any(), any())).thenReturn(VendedorResponse.of(vendedorAtualizado));

        mockMvc.perform(put("/vendedores/atualizar")
                        .param("id", String.valueOf(1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Agenor Ronega Junior\", " +
                                "\"email\":\"agenor.ronega.junior@empresa.com.br\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Agenor Ronega Junior"))
                .andExpect(jsonPath("$.cpf").value("07745643433"))
                .andExpect(jsonPath("$.email").value("agenor.ronega.junior@empresa.com.br"));
    }

    @Test
    public void vendedor_atualizar_idNaoCadastrado_notFound() throws Exception {

        doThrow(new EntityNotFoundException("Vendedor não cadastrado!")).when(service).atualizar(any(), any());

        mockMvc.perform(put("/vendedores/atualizar")
                        .param("id", String.valueOf(1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Agenor Ronega Junior\", " +
                                "\"email\":\"agenor.ronega.junior@empresa.com.br\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value("Não foi possível realizar essa operação!"))
                .andExpect(jsonPath("$.detalhes").value("Vendedor não cadastrado!"));
    }

    @Test
    public void vendedor_atualizar_nomeVazio_badRequest() throws Exception {

        doThrow(HttpClientErrorException.BadRequest.class).when(service).atualizar(any(), any());

        mockMvc.perform(put("/vendedores/atualizar")
                        .param("id", String.valueOf(1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"\", " +
                                "\"email\":\"agenor.ronega.junior@empresa.com.br\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void vendedor_atualizar_nomeComMenosDeTresLetras_badRequest() throws Exception {

        doThrow(HttpClientErrorException.BadRequest.class).when(service).atualizar(any(), any());

        mockMvc.perform(put("/vendedores/atualizar")
                        .param("id", String.valueOf(1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Ag\", " +
                                "\"email\":\"agenor.ronega.junior@empresa.com.br\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void vendedor_atualizar_cpfVazio_badRequest() throws Exception {

        doThrow(HttpClientErrorException.BadRequest.class).when(service).atualizar(any(), any());

        mockMvc.perform(put("/vendedores/atualizar")
                        .param("id", String.valueOf(1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Agenor Ronega Junior\", " +
                                "\"email\":\"\"}"))
                .andExpect(status().isBadRequest());
    }
}
