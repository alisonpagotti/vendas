package br.com.xbrain.apivendas.modulos.vendedor.model;

import br.com.xbrain.apivendas.modulos.venda.model.Venda;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "VENDEDORES")
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NOME")
    private String nome;
    @Column(name = "CPF", unique = true)
    private String cpf;
    @Column(name = "EMAIL", unique = true)
    private String email;
    @Column(name = "DATA_CADASTRO")
    private LocalDateTime dataCadastro;
    @OneToMany(mappedBy = "vendedor")
    private List<Venda> vendas;

    public void atualizar(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }
}
