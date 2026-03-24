package br.com.indra.jonathan_carvalho.repository;

import br.com.indra.jonathan_carvalho.model.HistoricoPreco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface HistoricoPrecoRepository extends JpaRepository<HistoricoPreco, UUID> {
    Set<HistoricoPreco> findByProdutosId(Long produtoId);

}
