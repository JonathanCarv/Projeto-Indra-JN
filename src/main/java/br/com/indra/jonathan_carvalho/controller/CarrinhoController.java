package br.com.indra.jonathan_carvalho.controller;

import br.com.indra.jonathan_carvalho.model.Carrinho;
import br.com.indra.jonathan_carvalho.service.CarrinhoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrinhos")
@RequiredArgsConstructor
@Tag(name = "Carrinho de Compras", description = "Endpoints para gerenciamento do carrinho de compras")
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    @Operation(summary = "Busca detalhes de um carrinho pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<Carrinho> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(carrinhoService.buscarPorId(id));
    }

    @Operation(summary = "Cria um novo carrinho aberto")
    @PostMapping
    public ResponseEntity<Carrinho> criar() {
        return ResponseEntity.ok(carrinhoService.criarNovoCarrinho());
    }

    @Operation(summary = "Adiciona um produto ao carrinho")
    @PostMapping("/{id}/itens")
    public ResponseEntity<Carrinho> adicionarItem(
            @PathVariable Long id,
            @RequestParam Long produtoId,
            @RequestParam Integer quantidade) {
        return ResponseEntity.ok(carrinhoService.adicionarItem(id, produtoId, quantidade));
    }

    @Operation(summary = "Finaliza a venda do carrinho (Baixa no estoque)")
    @PostMapping("/{id}/finalizar")
    public ResponseEntity<Carrinho> finalizar(@PathVariable Long id) {
        return ResponseEntity.ok(carrinhoService.finalizarVenda(id));
    }
}
