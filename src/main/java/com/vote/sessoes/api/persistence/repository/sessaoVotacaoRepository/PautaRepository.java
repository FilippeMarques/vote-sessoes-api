package com.vote.sessoes.api.persistence.repository.sessaoVotacaoRepository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.vote.sessoes.api.domain.sessaoVotacaoAgregation.Pauta;

public interface PautaRepository extends MongoRepository<Pauta, String> {

	@Query("{'sessao' : {'$ne' : null}, 'sessao.dataHoraFim' : {'$gt' : ?0}}")
	public List<Pauta> findAllSessaoEmVotacao(LocalDateTime dateTime);

	@Query("{'sessao' : {'$ne' : null}, 'sessao.dataHoraFim' : {'$lt' : ?0}, 'resultadoProcessado' : {'$eq' : false}}")
	public List<Pauta> findAllResultadoNaoApurado(LocalDateTime dateTime);

	
}
