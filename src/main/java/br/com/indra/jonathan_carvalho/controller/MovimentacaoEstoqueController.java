package br.com.indra.jonathan_carvalho.controller;

import br.com.indra.jonathan_carvalho.model.MovimentacaoEstoque;
import br.com.indra.jonathan_carvalho.service.MovimentacaoEstoqueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estoque")
@RequiredArgsConstructor
@Tag(name = "Estoque", description = "Gerenciamento de estoque e movimentações")
public class MovimentacaoEstoqueController {

    private final MovimentacaoEstoqueService movimentacaoService;

    @Operation(summary = "Lista todas as movimentações de estoque")
    @GetMapping("/movimentacoes")
    public ResponseEntity<List<MovimentacaoEstoque>> listarTodas() {
        return ResponseEntity.ok(movimentacaoService.listarTodas());
    }

    @Operation(summary = "Lista movimentações por produto")
    @GetMapping("/movimentacoes/produto/{produtoId}")
    public ResponseEntity<List<MovimentacaoEstoque>> listarPorProduto(@PathVariable Long produtoId) {
        return ResponseEntity.ok(movimentacaoService.listarPorProduto(produtoId));
    }

    @Operation(summary = "Registra uma nova movimentação de estoque")
    @PostMapping("/registrar")
    public ResponseEntity<MovimentacaoEstoque> registrar(@Valid @RequestBody MovimentacaoEstoque movimentacao) {
        return ResponseEntity.ok(movimentacaoService.registrarMovimentacao(movimentacao));
    }
}
