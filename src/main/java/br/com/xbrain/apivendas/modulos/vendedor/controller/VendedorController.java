package br.com.xbrain.apivendas.modulos.vendedor.controller;

import br.com.xbrain.apivendas.modulos.vendedor.dto.AtualizarVendedorRequest;
import br.com.xbrain.apivendas.modulos.vendedor.dto.MediaVendedorResponse;
import br.com.xbrain.apivendas.modulos.vendedor.dto.VendedorRequest;
import br.com.xbrain.apivendas.modulos.vendedor.dto.VendedorResponse;
import br.com.xbrain.apivendas.modulos.vendedor.service.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
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

    @GetMapping("media")
    public List<MediaVendedorResponse> mediaTodosVendedores(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate inicio,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fim) {
        return service.mediaTodosVendedores(inicio, fim);
    }

    @GetMapping("media/vendedor")
    public MediaVendedorResponse mediaPorVendedor(@RequestParam Integer id,
                                                  @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate inicio,
                                                  @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fim) {
        return service.mediaPorVendedor(id, inicio, fim);
    }

    @PostMapping
    public VendedorResponse cadastrar(@RequestBody @Valid VendedorRequest request) {
        return service.cadastrar(request);
    }

    @GetMapping("detalhar")
    public VendedorResponse detalhar(@RequestParam Integer id) {
        return service.detalhar(id);
    }

    @PutMapping("atualizar")
    public VendedorResponse atualizar(@RequestParam Integer id,
                                      @RequestBody @Valid AtualizarVendedorRequest request) {
        return service.atualizar(id, request);
    }
}
