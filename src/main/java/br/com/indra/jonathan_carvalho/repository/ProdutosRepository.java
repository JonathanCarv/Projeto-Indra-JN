package br.com.indra.jonathan_carvalho.repository;

import br.com.indra.jonathan_carvalho.model.Produtos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutosRepository extends JpaRepository<Produtos, Long> {
    List<Produtos> findByNomeContainingIgnoreCase(String nome);
    List<Produtos> findByCategoriaId(Long categoriaId);
}
