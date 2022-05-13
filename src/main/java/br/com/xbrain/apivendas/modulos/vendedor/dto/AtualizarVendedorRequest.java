package br.com.xbrain.apivendas.modulos.vendedor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AtualizarVendedorRequest {

    @NotBlank(message = "Campo nome não pode estar em branco!")
    @Size(min=3, max=40, message = "O nome deve no mínimo 3 letras!")
    private String nome;
    @NotBlank(message = "Campo email não pode estar em branco!")
    private String email;
}
