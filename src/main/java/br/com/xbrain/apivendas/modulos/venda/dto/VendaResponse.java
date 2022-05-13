package br.com.xbrain.apivendas.modulos.venda.dto;

import br.com.xbrain.apivendas.modulos.produto.model.Produto;
import br.com.xbrain.apivendas.modulos.venda.model.Venda;
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
public class VendaResponse {

    private Integer id;
    private LocalDateTime dataCadastro;
    private BigDecimal valorVenda;
    private Integer idVendedor;
    private String nomeVendedor;
    private List<String> produtos;

    public static List<VendaResponse> of(List<Venda> venda) {
        return venda.stream().map(VendaResponse::of).collect(Collectors.toList());
    }

    public static VendaResponse of(Venda venda) {
        var idVendedor = venda.getVendedor().getId();
        var nomeVendedor = venda.getVendedor().getNome();

        var listaProdutos = venda.getProdutos().stream().map(Produto::getNome).collect(Collectors.toList());

        return VendaResponse.builder()
                .id(venda.getId())
                .dataCadastro(venda.getDataCadastro())
                .valorVenda(venda.getValorVenda())
                .idVendedor(idVendedor)
                .nomeVendedor(nomeVendedor)
                .produtos(listaProdutos)
                .build();
    }
}
