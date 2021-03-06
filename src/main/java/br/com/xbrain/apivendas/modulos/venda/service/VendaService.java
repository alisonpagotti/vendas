package br.com.xbrain.apivendas.modulos.venda.service;

import br.com.xbrain.apivendas.modulos.comum.data.service.DataHoraService;
import br.com.xbrain.apivendas.modulos.produto.model.Produto;
import br.com.xbrain.apivendas.modulos.produto.repository.ProdutoRepository;
import br.com.xbrain.apivendas.modulos.venda.dto.AtualizarVendaRequest;
import br.com.xbrain.apivendas.modulos.venda.dto.VendaRequest;
import br.com.xbrain.apivendas.modulos.venda.dto.VendaResponse;
import br.com.xbrain.apivendas.modulos.venda.model.Venda;
import br.com.xbrain.apivendas.modulos.venda.repository.VendaRepository;
import br.com.xbrain.apivendas.modulos.vendedor.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendaService {

    private static final String EX_VENDA_NAO_CADASTRADA = "Venda não cadastrada!";
    private static final String EX_VENDEDOR_PRODUTO_NAO_CADASTRADO = "Vendedor ou produto(s) não cadastrado(s)!";

    @Autowired
    private VendaRepository repository;

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private DataHoraService dataHoraService;

    public List<VendaResponse> listarTodas() {
        var vendas = repository.findAll();

        return VendaResponse.of(vendas);
    }

    public List<VendaResponse> listarPorVendedor(Integer id) {
        var vendas = repository.findByVendedorId(id);

        return VendaResponse.of(vendas);
    }

    public List<VendaResponse> listarPorPeriodo(LocalDate inicio, LocalDate fim) {
        var vendas = repository.findByDataCadastroBetween(
                dataHoraService.dataInicial(inicio),
                dataHoraService.dataFinal(fim)
        );

        return VendaResponse.of(vendas);
    }

    public List<VendaResponse> listarPorVendedorPeriodo(Integer id, LocalDate inicio, LocalDate fim) {
        var idVendedor = vendedorRepository.getById(id).getId();

        var vendas = repository.findByVendedorIdAndDataCadastroBetween(
                idVendedor,
                dataHoraService.dataInicial(inicio),
                dataHoraService.dataFinal(fim)
        );

        return VendaResponse.of(vendas);
    }

    @Transactional
    public VendaResponse cadastrar(VendaRequest request) {
        try {
            var vendedor = vendedorRepository.getById(request.getIdVendedor());

            var listaProdutos = request.getIdProdutos()
                    .stream()
                    .map(id -> produtoRepository.getById(id))
                    .collect(Collectors.toList());

            var valorVenda = listaProdutos
                    .stream()
                    .map(Produto::getValorProduto)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            var venda = Venda.builder()
                    .valorVenda(valorVenda)
                    .dataCadastro(dataHoraService.DataHoraAtual())
                    .vendedor(vendedor)
                    .produtos(listaProdutos)
                    .build();

            repository.save(venda);

            return VendaResponse.of(venda);

        } catch (Exception ex) {
            throw new DataIntegrityViolationException(EX_VENDEDOR_PRODUTO_NAO_CADASTRADO);
        }
    }

    public VendaResponse detalhar(Integer id) {
        try {
            var venda = repository.getById(id);

            return VendaResponse.of(venda);

        } catch (Exception ex) {
            throw new EntityNotFoundException(EX_VENDA_NAO_CADASTRADA);
        }
    }

    @Transactional
    public VendaResponse atualizar(Integer id, AtualizarVendaRequest request) {
        try {
            var venda = repository.getById(id);

            var listaProdutos = request.getIdProdutos()
                    .stream()
                    .map(idProduto -> produtoRepository.getById(idProduto))
                    .collect(Collectors.toList());

            var valorVenda = listaProdutos
                    .stream()
                    .map(Produto::getValorProduto)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            venda.atualizar(listaProdutos, valorVenda);

            return VendaResponse.of(venda);

        } catch (Exception ex) {
            throw new EntityNotFoundException(EX_VENDA_NAO_CADASTRADA);
        }
    }
}
