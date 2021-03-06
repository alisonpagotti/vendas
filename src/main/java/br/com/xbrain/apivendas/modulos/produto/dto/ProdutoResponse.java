package br.com.xbrain.apivendas.modulos.produto.dto;

import br.com.xbrain.apivendas.modulos.produto.model.Produto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdutoResponse {

    private Integer id;
    private String nome;
    private BigDecimal valorProduto;
    @JsonFormat(pattern="dd-MM-yyyy 'às' HH:mm:ss")
    private LocalDateTime dataCadastro;

    public static ProdutoResponse of(Produto produto) {
        return ProdutoResponse.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .valorProduto(produto.getValorProduto())
                .dataCadastro(produto.getDataCadastro())
                .build();
    }

    public static List<ProdutoResponse> of(List<Produto> produto) {
        return produto.stream().map(ProdutoResponse::of).collect(Collectors.toList());
    }
}
