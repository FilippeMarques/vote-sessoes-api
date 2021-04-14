# VoteSessoes
API Rest para Gerenciamento de Sessões de Votação.


O serviço permite:

- Cadastrar Associado
- Cadastrar Pauta para Votação
- Consultar Pauta por ID
- Iniciar Sessão de votação
- Consultar Pautas com Sessão Iniciada
- Votar em Pautas com Sessão Iniciada
- Apurar Votos


## Tecnologias Utilizadas

- Java 11
- Gradle 
- Lombok
- JUnit5, Mockito
- Swagger
- Kafka
- MessageSource
- MongoDB 




## Execução

```shell
docker-compose up
./gradlew bootRun

```

**Exemplos de requisição:**

Cadastrar Associado
```shell
--request POST 'http://localhost:8080/associados' \
--header 'Content-Type: application/json' \
--{
    "cpf": "82699996289",
    "nome": "Isabelly Mariana Emily Ribeiro"
}
```

Cadastrar Pauta
```shell
--request POST 'http://localhost:8080/pautas' \
--header 'Content-Type: application/json' \
--{
    "nome": "Ensino a distância",
    "descricao": "Ofertar gratuitamente o ensino aos menos favorecidos."
}
```

Consultar Pauta pelo Identificador
```shell
--request GET 'http://localhost:8080/pautas/6076d1cef845fb0b3aa48a08' \
```

Iniciar Sessão de votação
```shell
--request POST 'http://localhost:8080/pautas/iniciarsessao' \
--header 'Content-Type: application/json' \
--{
    "pauta": "6076d1cef845fb0b3aa48a08",
    "duracao": 5
}
```

Listar Pautas disponíveis para votação
```shell
-request GET 'http://localhost:8080/pautas'
```

Votar
```shell
--request POST 'http://localhost:8080/votar' \
--header 'Content-Type: application/json' \
--{
    "pauta": "6076d1cef845fb0b3aa48a08",
    "cpf": "57847566303",
    "opcaoVoto": "Sim"
}
```

Apuração de Votos
```shell
curl --location --request GET 'http://localhost:8080/api/v1/resultado/6006fc239a790046303645fb' 
```


## Documentação da API foi feita com Swagger

API documentada com Swagger pode ser acessado em: 
http://localhost:8080/swagger-ui/index.html