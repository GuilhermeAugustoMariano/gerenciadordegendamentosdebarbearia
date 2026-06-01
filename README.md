# Gerenciador de Agendamentos de Barbearia

Projeto de estudo para construir um sistema de agendamentos de barbearia com Java Spring Boot no backend e Angular no frontend.

## Estrutura do projeto

```text
gerenciadordegendamentosdebarbearia/
  backend/   API Java Spring Boot
  frontend/  Aplicacao Angular
  pom.xml    Projeto Maven pai
  mvnw.cmd   Maven Wrapper para Windows
```

## Backend

O backend representa a parte do sistema que recebe pedidos, executa regras e devolve respostas.

Hoje ele tem uma rota de teste:

```text
GET /hello
```

Resposta esperada:

```text
API do Gerenciador de Agendamentos de Barbearia rodando!
```

## Comandos principais

Todos os comandos Maven devem ser executados na raiz do projeto.

Rodar os testes:

```text
cmd /c mvnw.cmd test
```

Limpar arquivos gerados e rodar os testes:

```text
cmd /c mvnw.cmd clean test
```

Iniciar a API:

```text
cmd /c mvnw.cmd -pl backend spring-boot:run
```

Depois de iniciar a API, acesse:

```text
http://localhost:8080/hello
```

## Status atual

- Java 21 configurado
- Spring Boot configurado
- Estrutura com `backend` e `frontend`
- Git iniciado
- Primeiro endpoint de teste criado
- Angular CLI instalado
