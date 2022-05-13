package br.com.xbrain.apivendas.modulos.produto.model;

import br.com.xbrain.apivendas.modulos.venda.model.Venda;
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
@Entity(name = "PRODUTOS")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NOME")
    private String nome;
    @Column(name = "VALOR_PRODUTO")
    private BigDecimal valorProduto;
    @Column(name = "DATA_CADASTRO")
    private LocalDateTime dataCadastro;

    @ManyToMany(mappedBy = "produtos", fetch = FetchType.LAZY)
    private List<Venda> venda;

    public void atualizar(String nome, BigDecimal valorProduto) {
        this.nome = nome;
        this.valorProduto = valorProduto;
    }
}
