package br.com.indra.jonathan_carvalho.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tipos de movimentação de estoque")
public enum TipoMovimentacao {
    COMPRA,
    VENDA,
    DEVOLUCAO,
    AJUSTE
}
