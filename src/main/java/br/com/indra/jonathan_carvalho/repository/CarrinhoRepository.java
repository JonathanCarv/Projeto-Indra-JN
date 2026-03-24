package br.com.indra.jonathan_carvalho.repository;

import br.com.indra.jonathan_carvalho.model.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
}
