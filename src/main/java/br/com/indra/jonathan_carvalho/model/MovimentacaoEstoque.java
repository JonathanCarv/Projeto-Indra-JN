package br.com.indra.jonathan_carvalho.model;

import br.com.indra.jonathan_carvalho.model.enums.TipoMovimentacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "movimentacoes_estoque")
public class MovimentacaoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    @NotNull(message = "O produto é obrigatório")
    private Produtos produto;

    @NotNull(message = "A quantidade é obrigatória")
    private Integer quantidade;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O tipo de movimentação é obrigatório")
    private TipoMovimentacao tipo;

    @Column(name = "data_movimentacao")
    private LocalDateTime dataMovimentacao;

    @PrePersist
    protected void onCreate() {
        dataMovimentacao = LocalDateTime.now();
    }
}
