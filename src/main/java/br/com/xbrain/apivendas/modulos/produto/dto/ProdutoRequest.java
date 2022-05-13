package br.com.xbrain.apivendas.modulos.produto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdutoRequest {

    @NotBlank(message = "Campo nome não pode estar em branco!")
    @Size(min=3, max=40, message = "O nome deve ter no mínimo 3 letras!")
    private String nome;
    @DecimalMin(value = "0.0", inclusive = false, message = "Valor deve ser maior do que zero.")
    private BigDecimal valorProduto;
}
