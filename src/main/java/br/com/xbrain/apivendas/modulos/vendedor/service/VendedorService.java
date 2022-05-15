package br.com.xbrain.apivendas.modulos.vendedor.service;

import br.com.xbrain.apivendas.modulos.comum.data.service.DataHoraService;
import br.com.xbrain.apivendas.modulos.venda.model.Venda;
import br.com.xbrain.apivendas.modulos.venda.repository.VendaRepository;
import br.com.xbrain.apivendas.modulos.vendedor.dto.AtualizarVendedorRequest;
import br.com.xbrain.apivendas.modulos.vendedor.dto.MediaVendedorResponse;
import br.com.xbrain.apivendas.modulos.vendedor.dto.VendedorRequest;
import br.com.xbrain.apivendas.modulos.vendedor.dto.VendedorResponse;
import br.com.xbrain.apivendas.modulos.vendedor.model.MediaVendedor;
import br.com.xbrain.apivendas.modulos.vendedor.model.Vendedor;
import br.com.xbrain.apivendas.modulos.vendedor.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendedorService {

    private static final String EX_VENDEDOR_NAO_CADASTRADO = "Vendedor não cadastrado!";
    private static final String EX_CPF_EMAIL_JA_CADASTRADO = "CPF ou e-mail já cadastrado!";

    @Autowired
    private VendedorRepository repository;

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private DataHoraService dataHoraService;

    public List<VendedorResponse> listarTodos() {
        var vendedores = repository.findAll();

        return VendedorResponse.of(vendedores);
    }

    public MediaVendedorResponse mediaPorVendedor(Integer id, LocalDate inicio, LocalDate fim) {
        var vendedor = repository.getById(id);

        var quantidadeDeVendas = vendaRepository.findByVendedorIdAndDataCadastroBetween(
                id,
                dataHoraService.dataInicial(inicio),
                dataHoraService.dataFinal(fim))
                .size();

        var dias = inicio.until(fim, ChronoUnit.DAYS) + 1;
        var media = (double) quantidadeDeVendas / dias;

        var mediaVendedor = MediaVendedor.builder()
                .nome(vendedor.getNome())
                .totalVendas(quantidadeDeVendas)
                .mediaDia(media)
                .build();

        return MediaVendedorResponse.of(mediaVendedor);
    }

    public List<MediaVendedorResponse> mediaTodosVendedores(LocalDate inicio, LocalDate fim) {
        var vendedores = repository.findByVendasDataCadastroBetween(
                dataHoraService.dataInicial(inicio),
                dataHoraService.dataFinal(fim)
        );

        var dias = inicio.until(fim, ChronoUnit.DAYS) + 1;

        var mediaVendedores = vendedores
                .stream()
                .map(vendedor -> MediaVendedor.builder()
                        .nome(vendedor.getNome())
                        .totalVendas(vendaRepository.findByVendedorIdAndDataCadastroBetween(
                                        vendedor.getId(),
                                        dataHoraService.dataInicial(inicio),
                                        dataHoraService.dataFinal(fim)).size())
                        .mediaDia(vendaRepository.findByVendedorIdAndDataCadastroBetween(
                                        vendedor.getId(),
                                        dataHoraService.dataInicial(inicio),
                                        dataHoraService.dataFinal(fim)).size() / (double) dias)
                        .build())
                .collect(Collectors.toList()
                );

        return MediaVendedorResponse.of(mediaVendedores);
    }

    @Transactional
    public VendedorResponse cadastrar(VendedorRequest request) {
        try {
            var vendedor = Vendedor.builder()
                    .nome(request.getNome())
                    .cpf(request.getCpf())
                    .email(request.getEmail())
                    .dataCadastro(dataHoraService.DataHoraAtual())
                    .build();

            repository.save(vendedor);

            return VendedorResponse.of(vendedor);

        } catch (Exception ex) {
            throw new DataIntegrityViolationException(EX_CPF_EMAIL_JA_CADASTRADO);
        }
    }

    public VendedorResponse detalhar(Integer id) {
        try {
            var vendedor = repository.getById(id);

            return VendedorResponse.of(vendedor);

        } catch (Exception ex) {
            throw new EntityNotFoundException(EX_VENDEDOR_NAO_CADASTRADO);
        }
    }

    @Transactional
    public VendedorResponse atualizar(Integer id, AtualizarVendedorRequest request) {
        try {
            var vendedor = repository.getById(id);
            vendedor.atualizar(request.getNome(), request.getEmail());

            return VendedorResponse.of(vendedor);

        } catch (Exception ex) {
            throw new EntityNotFoundException(EX_VENDEDOR_NAO_CADASTRADO);
        }
    }
}
