package br.com.xbrain.apivendas.modulos.venda.controller;

import br.com.xbrain.apivendas.modulos.venda.dto.AtualizarVendaRequest;
import br.com.xbrain.apivendas.modulos.venda.dto.VendaRequest;
import br.com.xbrain.apivendas.modulos.venda.dto.VendaResponse;
import br.com.xbrain.apivendas.modulos.venda.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @GetMapping("listar/vendedor")
    public List<VendaResponse> listarPorVendedor(@RequestParam Integer id) {
        return service.listarPorVendedor(id);
    }

    @GetMapping("listar/periodo")
    public List<VendaResponse> listarPorPeriodo(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate inicio,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fim) {
        return service.listarPorPeriodo(inicio, fim);
    }

    @GetMapping("listar/vendedor/periodo")
    public List<VendaResponse> listarPorVendedorPeriodo(
            @RequestParam Integer id,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate inicio,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fim) {
        return service.listarPorVendedorPeriodo(id, inicio, fim);
    }

    @PostMapping
    public VendaResponse cadastrar(@RequestBody @Valid VendaRequest request) {
        return service.cadastrar(request);
    }

    @GetMapping("detalhar")
    public VendaResponse detalhar(@RequestParam Integer id) {
        return service.detalhar(id);
    }

    @PutMapping("atualizar")
    public VendaResponse atualizar(@RequestParam Integer id,
                                   @RequestBody @Valid AtualizarVendaRequest request) {
        return service.atualizar(id, request);
    }
}
