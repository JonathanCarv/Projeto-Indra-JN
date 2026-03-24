package br.com.indra.jonathan_carvalho.service;

import br.com.indra.jonathan_carvalho.model.MovimentacaoEstoque;
import br.com.indra.jonathan_carvalho.model.Produtos;
import br.com.indra.jonathan_carvalho.model.enums.TipoMovimentacao;
import br.com.indra.jonathan_carvalho.repository.MovimentacaoEstoqueRepository;
import br.com.indra.jonathan_carvalho.repository.ProdutosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimentacaoEstoqueService {

    private final MovimentacaoEstoqueRepository movimentacaoRepository;
    private final ProdutosRepository produtosRepository;

    public List<MovimentacaoEstoque> listarTodas() {
        return movimentacaoRepository.findAll();
    }

    public List<MovimentacaoEstoque> listarPorProduto(Long produtoId) {
        return movimentacaoRepository.findByProdutoId(produtoId);
    }

    @Transactional
    public MovimentacaoEstoque registrarMovimentacao(MovimentacaoEstoque movimentacao) {
        Produtos produto = produtosRepository.findById(movimentacao.getProduto().getId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        Integer quantidadeReq = movimentacao.getQuantidade();
        
        if (movimentacao.getTipo() == TipoMovimentacao.VENDA) {
            if (produto.getQuantidadeEstoque() < quantidadeReq) {
                throw new RuntimeException("Estoque insuficiente para a venda");
            }
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidadeReq);
        } else if (movimentacao.getTipo() == TipoMovimentacao.COMPRA || movimentacao.getTipo() == TipoMovimentacao.DEVOLUCAO) {
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + quantidadeReq);
        } else if (movimentacao.getTipo() == TipoMovimentacao.AJUSTE) {
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + quantidadeReq);
        }

        movimentacao.setProduto(produto);
        produtosRepository.save(produto);
        return movimentacaoRepository.save(movimentacao);
    }
}
