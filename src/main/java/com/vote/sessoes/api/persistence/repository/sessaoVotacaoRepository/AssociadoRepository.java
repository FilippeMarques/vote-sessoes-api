package com.vote.sessoes.api.persistence.repository.sessaoVotacaoRepository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vote.sessoes.api.domain.sessaoVotacaoAgregation.Associado;

public interface AssociadoRepository extends MongoRepository<Associado, String> {

}
