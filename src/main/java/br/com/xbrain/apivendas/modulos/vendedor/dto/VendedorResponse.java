package br.com.xbrain.apivendas.modulos.vendedor.dto;

import br.com.xbrain.apivendas.modulos.vendedor.model.Vendedor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public static List<VendedorResponse> of(List<Vendedor> vendedor) {
        return vendedor.stream().map(VendedorResponse::of).collect(Collectors.toList());
    }

    public static VendedorResponse of(Vendedor vendedor) {
        return VendedorResponse.builder()
                .id(vendedor.getId())
                .nome(vendedor.getNome())
                .cpf(vendedor.getCpf())
                .email(vendedor.getEmail())
                .build();
    }
}