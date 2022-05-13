package br.com.xbrain.apivendas.modulos.produto.controller;

import br.com.xbrain.apivendas.modulos.produto.dto.AtualizarProdutoRequest;
import br.com.xbrain.apivendas.modulos.produto.dto.ProdutoRequest;
import br.com.xbrain.apivendas.modulos.produto.dto.ProdutoResponse;
import br.com.xbrain.apivendas.modulos.produto.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @GetMapping("listar")
    public List<ProdutoResponse> listarTodos() {
        return service.listarTodos();
    }

    @PostMapping
    public ProdutoResponse cadastrar(@RequestBody @Valid ProdutoRequest request) {
        return service.cadastrar(request);
    }

    @GetMapping("detalhar/{idProduto}")
    public ProdutoResponse detalhar(@PathVariable Integer idProduto) {
        return service.detalhar(idProduto);
    }

    @PutMapping("{idProduto}")
    public ProdutoResponse atualizar(@PathVariable Integer idProduto,
                                     @RequestBody @Valid AtualizarProdutoRequest request) {
        return service.atualizar(idProduto ,request);
    }
}
