package com.vote.sessoes.api.domain.sessaoVotacaoAgregation;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.vote.sessoes.api.dto.sessaoVotacao.PautaDto;

import lombok.Getter;
import lombok.Setter;
import springfox.documentation.annotations.ApiIgnore;

@Getter
@Setter
@ApiIgnore
@Document(value = "pauta")
public class Pauta {

	@Id
	private String id;

	@NotEmpty(message = "nome.not.blank")
	private String nome;

	private String descricao;

	private Sessao sessao;

	private boolean resultadoApurado;

	private List<TotalVotoOpcao> totalVotos;

	public Pauta() {
	}

	public Pauta(String nome, String descricao) {
		this.nome = nome;
		this.descricao = descricao;
	}

	public PautaDto toDto() {
		return new PautaDto(id, nome, descricao, sessao);
	}

	public List<TotalVotoOpcao> getTotalVotos() {
		return totalVotos;
	}

	public void setTotalVotos(List<TotalVotoOpcao> totalVotos) {
		this.totalVotos = totalVotos;
	}

}