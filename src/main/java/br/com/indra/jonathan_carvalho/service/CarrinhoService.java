package br.com.indra.jonathan_carvalho.service;

import br.com.indra.jonathan_carvalho.model.Carrinho;
import br.com.indra.jonathan_carvalho.model.ItemCarrinho;
import br.com.indra.jonathan_carvalho.model.MovimentacaoEstoque;
import br.com.indra.jonathan_carvalho.model.Produtos;
import br.com.indra.jonathan_carvalho.model.enums.StatusCarrinho;
import br.com.indra.jonathan_carvalho.model.enums.TipoMovimentacao;
import br.com.indra.jonathan_carvalho.repository.CarrinhoRepository;
import br.com.indra.jonathan_carvalho.repository.ProdutosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final ProdutosRepository produtosRepository;
    private final MovimentacaoEstoqueService movimentacaoService;

    public Carrinho buscarPorId(Long id) {
        return carrinhoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));
    }

    @Transactional
    public Carrinho criarNovoCarrinho() {
        Carrinho carrinho = new Carrinho();
        carrinho.setStatus(StatusCarrinho.ABERTO);
        return carrinhoRepository.save(carrinho);
    }

    @Transactional
    public Carrinho adicionarItem(Long carrinhoId, Long produtoId, Integer quantidade) {
        Carrinho carrinho = buscarPorId(carrinhoId);
        
        if (carrinho.getStatus() != StatusCarrinho.ABERTO) {
            throw new RuntimeException("Não é possível adicionar itens a um carrinho finalizado ou cancelado");
        }

        Produtos produto = produtosRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (produto.getQuantidadeEstoque() < quantidade) {
            throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNome());
        }

        ItemCarrinho item = ItemCarrinho.builder()
                .carrinho(carrinho)
                .produto(produto)
                .quantidade(quantidade)
                .precoUnitario(produto.getPreco())
                .build();

        carrinho.getItens().add(item);
        return carrinhoRepository.save(carrinho);
    }

    @Transactional
    public Carrinho finalizarVenda(Long carrinhoId) {
        Carrinho carrinho = buscarPorId(carrinhoId);

        if (carrinho.getStatus() != StatusCarrinho.ABERTO) {
            throw new RuntimeException("O carrinho já foi finalizado ou cancelado");
        }

        if (carrinho.getItens().isEmpty()) {
            throw new RuntimeException("Não é possível finalizar um carrinho vazio");
        }

        for (ItemCarrinho item : carrinho.getItens()) {
            MovimentacaoEstoque movimentacao = MovimentacaoEstoque.builder()
                    .produto(item.getProduto())
                    .quantidade(item.getQuantidade())
                    .tipo(TipoMovimentacao.VENDA)
                    .build();
            
            movimentacaoService.registrarMovimentacao(movimentacao);
        }

        carrinho.setStatus(StatusCarrinho.FINALIZADO);
        return carrinhoRepository.save(carrinho);
    }
}
