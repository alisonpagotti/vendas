package br.com.xbrain.apivendas.modulos.vendedor.controller;

import br.com.xbrain.apivendas.modulos.vendedor.dto.AtualizarVendedorRequest;
import br.com.xbrain.apivendas.modulos.vendedor.dto.VendedorRequest;
import br.com.xbrain.apivendas.modulos.vendedor.dto.VendedorResponse;
import br.com.xbrain.apivendas.modulos.vendedor.service.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("vendedores")
public class VendedorController {

    @Autowired
    private VendedorService service;

    @GetMapping("listar")
    public List<VendedorResponse> listarTodos() {
        return service.listarTodos();
    }

    @PostMapping
    public VendedorResponse cadastrar(@RequestBody @Valid VendedorRequest request) {
        return service.cadastrar(request);
    }

    @GetMapping("detalhar/{idVendedor}")
    public VendedorResponse detalhar(@PathVariable Integer idVendedor) {
        return service.detalhar(idVendedor);
    }

    @PutMapping("{idVendedor}")
    public VendedorResponse atualizar(@PathVariable Integer idVendedor,
                                      @RequestBody @Valid AtualizarVendedorRequest request) {
        return service.atualizar(idVendedor, request);
    }
}
