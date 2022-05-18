package br.com.xbrain.apivendas.modulos.venda.repository;

import br.com.xbrain.apivendas.modulos.venda.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Integer> {

    List<Venda> findByVendedorId(Integer id);

    List<Venda> findByDataCadastroBetween(LocalDateTime inicio, LocalDateTime fim);

    List<Venda> findByVendedorIdAndDataCadastroBetween(Integer id, LocalDateTime inicio, LocalDateTime fim);
}
