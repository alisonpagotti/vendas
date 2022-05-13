package br.com.xbrain.apivendas.modulos.venda.dto;

import br.com.xbrain.apivendas.modulos.produto.model.Produto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AtualizarVendaRequest {

    @NotEmpty(message = "A lista de produto não pode estar vazia!")
    private List<Integer> idProdutos;
}
