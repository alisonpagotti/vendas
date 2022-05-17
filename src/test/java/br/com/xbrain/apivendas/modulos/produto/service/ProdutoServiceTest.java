package br.com.xbrain.apivendas.modulos.produto.service;

import br.com.xbrain.apivendas.modulos.comum.data.service.DataHoraService;
import br.com.xbrain.apivendas.modulos.produto.dto.AtualizarProdutoRequest;
import br.com.xbrain.apivendas.modulos.produto.dto.ProdutoRequest;
import br.com.xbrain.apivendas.modulos.produto.model.Produto;
import br.com.xbrain.apivendas.modulos.produto.repository.ProdutoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static br.com.xbrain.apivendas.modulos.helper.TestHelper.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository repository;

    @Mock
    private DataHoraService dataHoraService;

    @InjectMocks
    private ProdutoService service;

    @Test
    public void produto_cadastrar_sucesso() {

        var dataAtual = LocalDateTime.now();

        when(dataHoraService.DataHoraAtual()).thenReturn(dataAtual);

        var produto = umProduto(null, dataAtual);
        var produtoRequest = umProdutoRequest();

        var produtoCadastrado = service.cadastrar(produtoRequest);

        assertEquals(produto.getNome(), produtoCadastrado.getNome());
        assertEquals(produto.getValorProduto(), produtoCadastrado.getValorProduto());

        verify(repository, times(1)).save(produto);
    }

    @Test
    public void produto_cadastrar_nomeJaCadastrado_badRequest() {

        var dataAtual = LocalDateTime.now();

        when(dataHoraService.DataHoraAtual()).thenReturn(dataAtual);

        var produto = umProduto(null, dataAtual);
        var produtoRequest = umProdutoRequest();

        doThrow(new DataIntegrityViolationException("Produto já cadastrado!")).when(repository).save(produto);

        assertThatThrownBy(() -> service.cadastrar(produtoRequest))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessage("Produto já cadastrado!");

        verify(repository, times(1)).save(produto);
    }

    @Test
    public void produto_detalhar_sucesso() {

        var dataAtual = LocalDateTime.now();

        var produto = umProduto(1, dataAtual);

        when(repository.getById(1)).thenReturn(produto);

        var produtoDetalhado = service.detalhar(1);

        assertEquals(produto.getNome(), produtoDetalhado.getNome());
        assertEquals(produto.getValorProduto(), produtoDetalhado.getValorProduto());

        verify(repository, times(1)).getById(1);
    }

    @Test
    public void produto_detalhar_idNaoCadastrado_notFound() {

        doThrow(new EntityNotFoundException("Produto não cadastrado!")).when(repository).getById(1);

        assertThatThrownBy(() -> service.detalhar(1))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Produto não cadastrado!");

        verify(repository, times(1)).getById(1);
    }

    @Test
    public void produto_atualizar_sucesso() {

        var dataAtual = LocalDateTime.now();

        var produto = umProduto(1, dataAtual);
        var atualizarProdutoRequest = umAtualizarProdutoRequest();

        when(repository.getById(1)).thenReturn(produto);

        var produtoAtualizado = service.atualizar(1, atualizarProdutoRequest);

        assertEquals(produto.getNome(), produtoAtualizado.getNome());
        assertEquals(produto.getValorProduto(), produtoAtualizado.getValorProduto());

        verify(repository, times(1)).getById(1);
    }

    @Test
    public void produto_atualizar_idNaoCadastrado_notFound() {

        doThrow(new EntityNotFoundException("Produto não cadastrado!")).when(repository).getById(1);

        assertThatThrownBy(() -> service.atualizar(1, any()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Produto não cadastrado!");

        verify(repository, times(1)).getById(1);
    }
}
