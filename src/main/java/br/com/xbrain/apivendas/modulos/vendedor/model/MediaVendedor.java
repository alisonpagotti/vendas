package br.com.xbrain.apivendas.modulos.vendedor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MediaVendedor {

    private String nome;
    private Integer totalVendas;
    private Double mediaDia;
}
