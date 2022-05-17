package br.com.xbrain.apivendas.modulos.vendedor.service;

import br.com.xbrain.apivendas.modulos.comum.data.service.DataHoraService;
import br.com.xbrain.apivendas.modulos.vendedor.dto.AtualizarVendedorRequest;
import br.com.xbrain.apivendas.modulos.vendedor.dto.VendedorRequest;
import br.com.xbrain.apivendas.modulos.vendedor.model.Vendedor;
import br.com.xbrain.apivendas.modulos.vendedor.repository.VendedorRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

import static br.com.xbrain.apivendas.modulos.helper.TestHelper.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VendedorServiceTest {

    @Mock
    private VendedorRepository repository;

    @Mock
    private DataHoraService dataHoraService;

    @InjectMocks
    private VendedorService service;

    @Test
    public void vendedor_cadastrar_sucesso() {

        var dataAtual = LocalDateTime.now();

        when(dataHoraService.DataHoraAtual()).thenReturn(dataAtual);

        var vendedor = umVendedor(null, dataAtual);
        var vendedorRequest = umVendedorRequest();

        var vendedorCadastrado = service.cadastrar(vendedorRequest);

        assertEquals(vendedor.getNome(), vendedorCadastrado.getNome());
        assertEquals(vendedor.getCpf(), vendedorCadastrado.getCpf());
        assertEquals(vendedor.getEmail(), vendedorCadastrado.getEmail());

        verify(repository, times(1)).save(vendedor);
    }

    @Test
    public void vendedor_cadastrar_nomeJaCadastrado_badRequest() {

        var dataAtual = LocalDateTime.now();

        when(dataHoraService.DataHoraAtual()).thenReturn(dataAtual);

        var vendedor = umVendedor(null, dataAtual);
        var vendedorRequest = umVendedorRequest();

        doThrow(new DataIntegrityViolationException("CPF ou e-mail já cadastrado!")).when(repository).save(vendedor);

        assertThatThrownBy(() -> service.cadastrar(vendedorRequest))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessage("CPF ou e-mail já cadastrado!");

        verify(repository, times(1)).save(vendedor);
    }

    @Test
    public void vendedor_detalhar_sucesso() {

        var dataAtual = LocalDateTime.now();

        var vendedor = umVendedor(1, dataAtual);

        when(repository.getById(1)).thenReturn(vendedor);

        var vendedorDetalhado = service.detalhar(1);

        assertEquals(vendedor.getNome(), vendedorDetalhado.getNome());
        assertEquals(vendedor.getCpf(), vendedorDetalhado.getCpf());
        assertEquals(vendedor.getEmail(), vendedorDetalhado.getEmail());

        verify(repository, times(1)).getById(1);
    }

    @Test
    public void vendedor_detalhar_idNaoCadastrado_notFound() {

        doThrow(new EntityNotFoundException("Vendedor não cadastrado!")).when(repository).getById(1);

        assertThatThrownBy(() -> service.detalhar(1))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Vendedor não cadastrado!");

        verify(repository, times(1)).getById(1);
    }

    @Test
    public void vendedor_atualizar_sucesso() {

        var dataAtual = LocalDateTime.now();

        var vendedor = umVendedor(1, dataAtual);
        var atualizarVendedorRequest = umAtualizarVendedorRequest();

        when(repository.getById(1)).thenReturn(vendedor);

        var vendedorAtualizado = service.atualizar(1, atualizarVendedorRequest);

        assertEquals(vendedor.getNome(), vendedorAtualizado.getNome());
        assertEquals(vendedor.getCpf(), vendedorAtualizado.getCpf());
        assertEquals(vendedor.getEmail(), vendedorAtualizado.getEmail());

        verify(repository, times(1)).getById(1);
    }

    @Test
    public void vendedor_atualizar_idNaoCadastrado_notFound() {

        doThrow(new EntityNotFoundException("Vendedor não cadastrado!")).when(repository).getById(1);

        assertThatThrownBy(() -> service.atualizar(1, any()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Vendedor não cadastrado!");

        verify(repository, times(1)).getById(1);
    }
}
