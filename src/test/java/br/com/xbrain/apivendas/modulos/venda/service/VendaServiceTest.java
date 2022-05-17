package br.com.xbrain.apivendas.modulos.venda.service;

import br.com.xbrain.apivendas.modulos.comum.data.service.DataHoraService;
import br.com.xbrain.apivendas.modulos.produto.model.Produto;
import br.com.xbrain.apivendas.modulos.produto.repository.ProdutoRepository;
import br.com.xbrain.apivendas.modulos.venda.dto.AtualizarVendaRequest;
import br.com.xbrain.apivendas.modulos.venda.dto.VendaRequest;
import br.com.xbrain.apivendas.modulos.venda.model.Venda;
import br.com.xbrain.apivendas.modulos.venda.repository.VendaRepository;
import br.com.xbrain.apivendas.modulos.vendedor.model.Vendedor;
import br.com.xbrain.apivendas.modulos.vendedor.repository.VendedorRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VendaServiceTest {

    @Mock
    private VendaRepository repository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private VendedorRepository vendedorRepository;

    @Mock
    private DataHoraService dataHoraService;

    @InjectMocks
    private VendaService service;

    @Test
    public void venda_cadastrar_sucesso() {
        var dataAtual = LocalDateTime.now();

        when(dataHoraService.DataHoraAtual()).thenReturn(dataAtual);

        var vendedor = Vendedor.builder()
                .id(1)
                .nome("Agenor Ronega")
                .cpf("07745643433")
                .email("agenor.ronega@empresa.com.br")
                .dataCadastro(dataAtual)
                .build();

        var produto = Produto.builder()
                .id(1)
                .nome("Caneta Vermelha")
                .valorProduto(new BigDecimal("3.0"))
                .dataCadastro(dataAtual)
                .build();

        var venda = Venda.builder()
                .dataCadastro(dataAtual)
                .valorVenda(new BigDecimal("3.0"))
                .vendedor(vendedor)
                .produtos(List.of(produto))
                .build();

        var vendaRequest = VendaRequest.builder()
                .idVendedor(vendedor.getId())
                .idProdutos(List.of(1))
                .build();

        when(produtoRepository.getById(1)).thenReturn(produto);
        when(vendedorRepository.getById(1)).thenReturn(vendedor);

        var vendaCadastrada = service.cadastrar(vendaRequest);

        assertEquals(venda.getVendedor().getId(), vendaCadastrada.getIdVendedor());
        assertEquals(venda.getValorVenda(), vendaCadastrada.getValorVenda());

        verify(repository, times(1)).save(venda);
    }

    @Test
    public void venda_detalhar_sucesso() throws Exception {
        var dataAtual = LocalDateTime.now();

        var vendedor = Vendedor.builder()
                .id(1)
                .nome("Agenor Ronega")
                .cpf("07745643433")
                .email("agenor.ronega@empresa.com.br")
                .dataCadastro(dataAtual)
                .build();

        var produto = Produto.builder()
                .id(1)
                .nome("Caneta Vermelha")
                .valorProduto(new BigDecimal("3.0"))
                .dataCadastro(dataAtual)
                .build();

        var venda = Venda.builder()
                .id(1)
                .dataCadastro(dataAtual)
                .valorVenda(new BigDecimal("3.0"))
                .vendedor(vendedor)
                .produtos(List.of(produto))
                .build();

        when(repository.getById(1)).thenReturn(venda);

        var vendaCadastrada = service.detalhar(1);

        assertEquals(venda.getId(), vendaCadastrada.getId());
        assertEquals(venda.getVendedor().getId(), vendaCadastrada.getIdVendedor());
        assertEquals(venda.getValorVenda(), vendaCadastrada.getValorVenda());

        verify(repository, times(1)).getById(venda.getId());
    }

    @Test
    public void venda_detalhar_idNaoCadastrado_notFound() {

        doThrow(new EntityNotFoundException("Venda não cadastrada!")).when(repository).getById(1);

        assertThatThrownBy(() -> service.detalhar(1))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Venda não cadastrada!");

        verify(repository, times(1)).getById(1);
    }

    @Test
    public void venda_atualizar_sucesso() {
        var dataAtual = LocalDateTime.now();

        var vendedor = Vendedor.builder()
                .id(1)
                .nome("Agenor Ronega")
                .cpf("07745643433")
                .email("agenor.ronega@empresa.com.br")
                .dataCadastro(dataAtual)
                .build();

        var produto = Produto.builder()
                .id(1)
                .nome("Caneta Vermelha")
                .valorProduto(new BigDecimal("3.0"))
                .dataCadastro(dataAtual)
                .build();

        var venda = Venda.builder()
                .id(1)
                .dataCadastro(dataAtual)
                .valorVenda(new BigDecimal("3.0"))
                .vendedor(vendedor)
                .produtos(List.of(produto))
                .build();

        var atualizarVendaRequest = AtualizarVendaRequest.builder()
                .idProdutos(List.of(produto.getId()))
                .build();

        when(produtoRepository.getById(1)).thenReturn(produto);
        when(repository.getById(1)).thenReturn(venda);

        var vendaCadastrada = service.atualizar(1, atualizarVendaRequest);

        assertEquals(venda.getVendedor().getId(), vendaCadastrada.getIdVendedor());
        assertEquals(venda.getValorVenda(), vendaCadastrada.getValorVenda());

        verify(repository, times(1)).getById(venda.getId());
    }

    @Test
    public void venda_atualizar_idNaoCadastrado_notFound() {

        doThrow(new EntityNotFoundException("Venda não cadastrada!")).when(repository).getById(1);

        assertThatThrownBy(() -> service.atualizar(1, any()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Venda não cadastrada!");

        verify(repository, times(1)).getById(1);
    }
}
