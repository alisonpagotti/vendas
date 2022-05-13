package br.com.xbrain.apivendas.modulos.venda.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendaRequest {

    @NotNull
    private Integer idVendedor;
    @NotEmpty(message = "A lista de produto não pode estar vazia!")
    private List<Integer> produtos;
}
