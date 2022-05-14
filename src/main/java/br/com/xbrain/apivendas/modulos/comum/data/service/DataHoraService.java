package br.com.xbrain.apivendas.modulos.comum.data.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class DataHoraService {

    public LocalDateTime DataHoraAtual() {
        return java.time.LocalDateTime.now()
                .withNano(0);
    }

    public LocalDateTime dataInicial(LocalDate inicio) {
        return inicio.atTime(0,0,0);
    }

    public LocalDateTime dataFinal(LocalDate fim) {
        return fim.atTime(23, 59, 59);
    }
}
