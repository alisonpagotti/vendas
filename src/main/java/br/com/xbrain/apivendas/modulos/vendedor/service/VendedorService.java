package br.com.xbrain.apivendas.modulos.vendedor.service;

import br.com.xbrain.apivendas.modulos.comum.data.service.DataHoraService;
import br.com.xbrain.apivendas.modulos.vendedor.dto.AtualizarVendedorRequest;
import br.com.xbrain.apivendas.modulos.vendedor.dto.VendedorRequest;
import br.com.xbrain.apivendas.modulos.vendedor.dto.VendedorResponse;
import br.com.xbrain.apivendas.modulos.vendedor.model.Vendedor;
import br.com.xbrain.apivendas.modulos.vendedor.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class VendedorService {

    private static final String EX_VENDEDOR_NAO_CADASTRADO = "Vendedor não cadastrado!";
    private static final String EX_CPF_EMAIL_JA_CADASTRADO = "CPF ou e-mail já cadastrado!";

    @Autowired
    private VendedorRepository repository;

    @Autowired
    private DataHoraService dataHoraService;

    public List<VendedorResponse> listarTodos() {
        var vendedores = repository.findAll();

        return VendedorResponse.of(vendedores);
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

    public VendedorResponse detalhar(Integer idVendedor) {
        try {
            var vendedor = repository.getById(idVendedor);

            return VendedorResponse.of(vendedor);

        } catch (Exception ex) {
            throw new EntityNotFoundException(EX_VENDEDOR_NAO_CADASTRADO);
        }
    }

    @Transactional
    public VendedorResponse atualizar(Integer idVendedor, AtualizarVendedorRequest request) {
        try {
            var vendedor = repository.getById(idVendedor);
            vendedor.atualizar(request.getNome(), request.getEmail());

            return VendedorResponse.of(vendedor);

        } catch (Exception ex) {
            throw new EntityNotFoundException(EX_VENDEDOR_NAO_CADASTRADO);
        }
    }
}
