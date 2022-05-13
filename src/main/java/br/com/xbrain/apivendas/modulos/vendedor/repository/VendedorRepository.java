package br.com.xbrain.apivendas.modulos.vendedor.repository;

import br.com.xbrain.apivendas.modulos.vendedor.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendedorRepository extends JpaRepository<Vendedor, Integer> {
}
