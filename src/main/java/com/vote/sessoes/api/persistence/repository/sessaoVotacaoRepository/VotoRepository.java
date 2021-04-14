package com.vote.sessoes.api.persistence.repository.sessaoVotacaoRepository;

import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.vote.sessoes.api.domain.sessaoVotacaoAgregation.TotalVotoOpcao;
import com.vote.sessoes.api.domain.sessaoVotacaoAgregation.Voto;

public interface VotoRepository extends MongoRepository<Voto, String> {

	public Voto findByPautaIdAndCpf(String pautaId, String cpf);

	@Aggregation(pipeline = { "{$match: {'pautaId': {'$eq' : ?0}}}", "{$group: {_id: '$opcao', 'count': {$sum: 1}}}" })
	public AggregationResults<TotalVotoOpcao> calcularResultadoVotacao(String pautaId);

}
