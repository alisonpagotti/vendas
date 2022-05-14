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

    @GetMapping("detalhar")
    public ProdutoResponse detalhar(@RequestParam Integer id) {
        return service.detalhar(id);
    }

    @PutMapping("atualizar")
    public ProdutoResponse atualizar(@RequestParam Integer id,
                                     @RequestBody @Valid AtualizarProdutoRequest request) {
        return service.atualizar(id ,request);
    }
}
