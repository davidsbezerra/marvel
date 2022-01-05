[![Build Status](https://github.com/davidsbezerra/gerenciamento-login/badges/develop/pipeline.svg)](https://github.com/davidsbezerra/gerenciamento-login/pipelines) 

# API Maven

> Descrever objetivo do projeto.

## Referências

Projetos que dependem dos serviços desta API podem ser vistos na página [Stakeholders](https://github.com/davidsbezerra/gerenciamento-login/wikis/Stakeholders).
MRs neste projeto devem passar por aprovação de pelo menos um membro de cada equipe.

## Documentação

### API

Swagger: [http://localhost:8080/api-marvel/swagger-ui.html](http://localhost:8080/api-maven/swagger-ui.html)

### Diagrama(s)

*  Diagrama do fluxo X: `POST /x`

![convite](Diagramas/post-x.png "Fluxo de X.")

## Execução

### Maven

Executando via Maven no ambiente local:

```sh
$ mvn clean package spring-boot:run
```

### Docker

Executando via Docker no ambiente local:

```sh
$ mvn clean package
$ cd target
$ docker build -t api-marvel-login .
$ docker run -d -p 8082:8082 --name api-marvel api-marvel
```


