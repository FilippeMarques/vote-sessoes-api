package com.vote.sessoes.api.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class AssociadoControllerTest {

	@Autowired
	private MockMvc mvc;

	private String url = "/associados";

	private String jsonTemplate = "{\n" + "\"cpf\": \"%s\",\n" + "\"nome\": \"%s\"\n" + "}";

	
	private String cpfOk = "62214431993";
	private String cpfErro = "62214431997";
	private String nome = "Agatha Isabella Rebeca Rocha";

	@Test
	public void testCadastroComSucesso() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON)
				.content(String.format(jsonTemplate, cpfOk, nome))).andExpect(status().isOk());
	}
	
	@Test
	public void testCadastroComErro() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON)
				.content(String.format(jsonTemplate, cpfErro, nome))).andExpect(status().isBadRequest());
	}

}
