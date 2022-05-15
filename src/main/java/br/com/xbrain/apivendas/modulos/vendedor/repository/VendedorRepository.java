package br.com.xbrain.apivendas.modulos.vendedor.repository;

import br.com.xbrain.apivendas.modulos.vendedor.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface VendedorRepository extends JpaRepository<Vendedor, Integer> {

    @Query("select distinct v from VENDEDORES v inner join v.vendas vendas where vendas.dataCadastro between ?1 and ?2")
    List<Vendedor> findByVendasDataCadastroBetween(LocalDateTime inicio, LocalDateTime fim);
}
