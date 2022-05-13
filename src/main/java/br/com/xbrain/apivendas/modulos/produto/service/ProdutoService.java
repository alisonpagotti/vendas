package br.com.xbrain.apivendas.modulos.produto.service;

import br.com.xbrain.apivendas.modulos.comum.data.service.DataHoraService;
import br.com.xbrain.apivendas.modulos.produto.dto.AtualizarProdutoRequest;
import br.com.xbrain.apivendas.modulos.produto.dto.ProdutoRequest;
import br.com.xbrain.apivendas.modulos.produto.dto.ProdutoResponse;
import br.com.xbrain.apivendas.modulos.produto.model.Produto;
import br.com.xbrain.apivendas.modulos.produto.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProdutoService {

    private static final String EX_PRODUTO_NAO_CADASTRADO = "Produto não cadastrado!";

    @Autowired
    private ProdutoRepository repository;
    @Autowired
    private DataHoraService dataHoraService;

    public List<ProdutoResponse> listarTodos() {
        var listaProdutos = repository.findAll();

        return ProdutoResponse.of(listaProdutos);
    }

    @Transactional
    public ProdutoResponse cadastrar(ProdutoRequest request) {
        var produto = Produto.builder()
                .nome(request.getNome())
                .valorProduto(request.getValorProduto())
                .dataCadastro(dataHoraService.DataHoraAtual())
                .build();

        repository.save(produto);

        return ProdutoResponse.of(produto);
    }

    public ProdutoResponse detalhar(Integer idProduto) {
        try {
            var produto = repository.getById(idProduto);

            return ProdutoResponse.of(produto);

        } catch (Exception ex) {
            throw new EntityNotFoundException(EX_PRODUTO_NAO_CADASTRADO);
        }
    }

    @Transactional
    public ProdutoResponse atualizar(Integer idProduto, AtualizarProdutoRequest request) {
        try {
            var produto = repository.getById(idProduto);
            produto.atualizar(request.getNome(), request.getValorProduto());

            return ProdutoResponse.of(produto);

        } catch (Exception ex) {
            throw new EntityNotFoundException(EX_PRODUTO_NAO_CADASTRADO);
        }
    }
}
