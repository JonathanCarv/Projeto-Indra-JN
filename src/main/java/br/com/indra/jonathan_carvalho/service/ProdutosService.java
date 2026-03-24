package br.com.indra.jonathan_carvalho.service;

import br.com.indra.jonathan_carvalho.model.HistoricoPreco;
import br.com.indra.jonathan_carvalho.model.Produtos;
import br.com.indra.jonathan_carvalho.repository.HistoricoPrecoRepository;
import br.com.indra.jonathan_carvalho.repository.ProdutosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutosService {

    private final ProdutosRepository produtosRepository;
    private final HistoricoPrecoRepository historicoPrecoRepository;
    private final CategoriaService categoriaService;

    public List<Produtos> getAll() {
        return produtosRepository.findAll();
    }

    public List<Produtos> buscarPorNome(String nome) {
        return produtosRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Produtos> buscarPorCategoria(Long categoriaId) {
        return produtosRepository.findByCategoriaId(categoriaId);
    }

    public Produtos createdProduto(Produtos produto) {
        validarCategoria(produto);
        return produtosRepository.save(produto);
    }

    public Produtos atualiza(Produtos produto) {
        validarCategoria(produto);
        return produtosRepository.save(produto);
    }

    private void validarCategoria(Produtos produto) {
        if (produto.getCategoria() != null && produto.getCategoria().getId() != null) {
            var categoria = categoriaService.getById(produto.getCategoria().getId());
            produto.setCategoria(categoria);
        }
    }

    public void deletarProduto(Long id) {
        produtosRepository.deleteById(id);
    }

    public Produtos getById(Long id) {
        return produtosRepository.findById(id).get();
    }

    public Produtos atualizaPreco(Long id, BigDecimal preco) {
        final var produto = produtosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        produto.setPreco(preco);
        final var historico = new HistoricoPreco();
        historico.setPrecoAntigo(produto.getPreco());
        historico.setProdutos(produto);
        historico.setPrecoNovo(preco);
        historicoPrecoRepository.save(historico);
        return produtosRepository.saveAndFlush(produto);

    }
}
