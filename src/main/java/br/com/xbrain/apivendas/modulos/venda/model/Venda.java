package br.com.xbrain.apivendas.modulos.venda.model;

import br.com.xbrain.apivendas.modulos.produto.model.Produto;
import br.com.xbrain.apivendas.modulos.vendedor.model.Vendedor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "VENDAS")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "DATA_CADASTRO")
    private LocalDateTime dataCadastro;
    @Column(name = "VALOR_VENDA")
    private BigDecimal valorVenda;
    @ManyToOne
    private Vendedor vendedor;
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "VENDA_PRODUTO",
            joinColumns = { @JoinColumn(name = "venda_id") },
            inverseJoinColumns = { @JoinColumn(name = "produto_id") }
    )
    private List<Produto> produtos;
}
