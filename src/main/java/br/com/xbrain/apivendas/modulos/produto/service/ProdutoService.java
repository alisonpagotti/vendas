package br.com.xbrain.apivendas.modulos.produto.service;

import br.com.xbrain.apivendas.modulos.comum.data.service.DataService;
import br.com.xbrain.apivendas.modulos.produto.dto.AtualizarProdutoRequest;
import br.com.xbrain.apivendas.modulos.produto.dto.ProdutoRequest;
import br.com.xbrain.apivendas.modulos.produto.dto.ProdutoResponse;
import br.com.xbrain.apivendas.modulos.produto.model.Produto;
import br.com.xbrain.apivendas.modulos.produto.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private DataService dataService;

    public List<ProdutoResponse> listarTodos() {
        var listaProdutos = repository.findAll();

        return ProdutoResponse.of(listaProdutos);
    }

    private ProdutoResponse cadastrar(ProdutoRequest request) {
        var produto = Produto.builder()
                .nome(request.getNome())
                .valorProduto(request.getValorProduto())
                .dataCadastro(dataService.DataHoraAtual())
                .build();

        return ProdutoResponse.of(produto);
    }

    private ProdutoResponse detalhar(Integer idProduto) {
        var produto = repository.getById(idProduto);

        return ProdutoResponse.of(produto);
    }

    private ProdutoResponse atualizar(Integer idProduto, AtualizarProdutoRequest request) {
        var produto = repository.getById(idProduto);
        produto.atualizar(request.getNome(), request.getValorProduto());

        return ProdutoResponse.of(produto);
    }
}
