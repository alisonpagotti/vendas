package br.com.xbrain.apivendas.modulos.venda.repository;

import br.com.xbrain.apivendas.modulos.venda.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Integer> {
}
