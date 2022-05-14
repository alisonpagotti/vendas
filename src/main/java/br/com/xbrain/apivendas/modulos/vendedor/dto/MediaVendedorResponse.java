package br.com.xbrain.apivendas.modulos.vendedor.dto;

import br.com.xbrain.apivendas.modulos.vendedor.model.MediaVendedor;
import br.com.xbrain.apivendas.modulos.vendedor.model.Vendedor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MediaVendedorResponse {

    private String nome;
    private Integer totalVendas;
    private Double mediaDia;

    public static MediaVendedorResponse of(MediaVendedor mediaVendedor) {
        return MediaVendedorResponse.builder()
                .nome(mediaVendedor.getNome())
                .totalVendas(mediaVendedor.getTotalVendas())
                .mediaDia(mediaVendedor.getMediaDia())
                .build();
    }

    public static List<MediaVendedorResponse> of(List<MediaVendedor> mediaVendedor) {
        return mediaVendedor.stream().map(MediaVendedorResponse::of).collect(Collectors.toList());
    }
}
