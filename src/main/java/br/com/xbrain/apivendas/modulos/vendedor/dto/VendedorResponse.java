package br.com.xbrain.apivendas.modulos.vendedor.dto;

import br.com.xbrain.apivendas.modulos.vendedor.model.Vendedor;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendedorResponse {

    private Integer id;
    private String nome;
    private String cpf;
    private String email;
    @JsonFormat(pattern="dd-MM-yyyy 'às' HH:mm:ss")
    private LocalDateTime dataCadastro;

    public static VendedorResponse of(Vendedor vendedor) {
        return VendedorResponse.builder()
                .id(vendedor.getId())
                .nome(vendedor.getNome())
                .cpf(vendedor.getCpf())
                .email(vendedor.getEmail())
                .dataCadastro(vendedor.getDataCadastro())
                .build();
    }

    public static List<VendedorResponse> of(List<Vendedor> vendedor) {
        return vendedor.stream().map(VendedorResponse::of).collect(Collectors.toList());
    }
}