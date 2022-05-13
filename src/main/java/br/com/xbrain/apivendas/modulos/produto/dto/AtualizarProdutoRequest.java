package br.com.xbrain.apivendas.modulos.produto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AtualizarProdutoRequest {

    @NotBlank(message = "Campo nome não pode estar em branco!")
    private String nome;
    @DecimalMin(value = "0.0", inclusive = false, message = "Valor deve ser maior do que zero.")
    private BigDecimal valorProduto;
}
