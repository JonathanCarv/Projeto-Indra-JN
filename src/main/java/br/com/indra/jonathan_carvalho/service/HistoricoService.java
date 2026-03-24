package br.com.indra.jonathan_carvalho.service;

import br.com.indra.jonathan_carvalho.model.HistoricoPreco;
import br.com.indra.jonathan_carvalho.repository.HistoricoPrecoRepository;
import br.com.indra.jonathan_carvalho.service.dto.HistoricoProdutoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class HistoricoService {

    private final HistoricoPrecoRepository historicoPrecoRepository;

    public HistoricoProdutoDTO getHistoricoByProdutoId(Long produtoId) {
        return historicoPrecoRepository.findByProdutosId(produtoId)
                .stream()
                .findFirst()
                .map(hp -> new HistoricoProdutoDTO(
                        hp.getId(),
                        hp.getProdutos().getNome(),
                        hp.getPrecoAntigo(),
                        hp.getPrecoNovo(),
                        hp.getDataAlteracao()
                ))
                .orElse(null);
    }
}
