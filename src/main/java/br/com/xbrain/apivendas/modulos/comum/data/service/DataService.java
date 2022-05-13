package br.com.xbrain.apivendas.modulos.comum.data.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DataService {

    public LocalDateTime DataHoraAtual() {
        return java.time.LocalDateTime.now()
                .withNano(0);
    }
}
