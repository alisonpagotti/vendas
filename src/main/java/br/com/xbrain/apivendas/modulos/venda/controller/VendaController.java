package br.com.xbrain.apivendas.modulos.venda.controller;

import br.com.xbrain.apivendas.modulos.venda.dto.AtualizarVendaRequest;
import br.com.xbrain.apivendas.modulos.venda.dto.VendaRequest;
import br.com.xbrain.apivendas.modulos.venda.dto.VendaResponse;
import br.com.xbrain.apivendas.modulos.venda.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("vendas")
public class VendaController {

    @Autowired
    private VendaService service;

    @GetMapping("listar")
    public List<VendaResponse> listarTodas() {
        return service.listarTodas();
    }

    @PostMapping
    public VendaResponse cadastrar(@RequestBody @Valid VendaRequest request) {
        return service.cadastrar(request);
    }

    @GetMapping("detalhar/{idVenda}")
    public VendaResponse detalhar(@PathVariable Integer idVenda) {
        return service.detalhar(idVenda);
    }

    @PutMapping("{idVenda}")
    public VendaResponse atualizar(@PathVariable Integer idVenda,
                                   @RequestBody @Valid AtualizarVendaRequest request) {
        return service.atualizar(idVenda, request);
    }
}
