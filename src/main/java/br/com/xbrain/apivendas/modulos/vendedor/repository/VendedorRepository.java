package br.com.xbrain.apivendas.modulos.vendedor.repository;

import br.com.xbrain.apivendas.modulos.vendedor.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VendedorRepository extends JpaRepository<Vendedor, Integer> {

    List<Vendedor> findByVendasDataCadastroBetween(LocalDateTime inicio, LocalDateTime fim);
}
