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

Banco de dados configurado para desenvolvimento:

```text
H2 em memoria
```

O H2 em memoria funciona como um banco temporario: ele nasce quando a aplicacao inicia e some quando a aplicacao para.

Tabelas modeladas ate agora:

```text
customers     clientes
barbers       barbeiros
appointments  agendamentos
```

A tabela `appointments` se conecta com `customers` e `barbers`.

Primeira regra de negocio implementada:

```text
Um barbeiro nao pode ter dois agendamentos marcados para a mesma data e hora.
```

Hoje ele tem uma rota de teste:

```text
GET /hello
```

Resposta esperada:

```text
API do Gerenciador de Agendamentos de Barbearia rodando!
```

Endpoints principais ate agora:

```text
POST /customers
POST /barbers
POST /appointments
POST /barbers/{barberId}/availabilities
GET /barbers/{barberId}/available-times?date=2026-06-05
```

Exemplo de criacao de agendamento:

```json
{
  "customerId": 1,
  "barberId": 1,
  "appointmentDate": "2026-06-05",
  "appointmentTime": "14:00:00"
}
```

Exemplo de cadastro de disponibilidade:

```json
{
  "dayOfWeek": "FRIDAY",
  "startTime": "09:00:00",
  "endTime": "18:00:00"
}
```

Os horarios disponiveis sao calculados em blocos de 30 minutos.

Ao cancelar um agendamento, ele nao e apagado: o status muda de `SCHEDULED` para CANCELED.

Erros de regra de negocio retornam HTTP 400 com uma mensagem em JSON.

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


## Debug

Guia pratico do fluxo de criacao de agendamento:

```text
docs/debug.md
```
## Status atual

- Java 21 configurado
- Spring Boot configurado
- Estrutura com `backend` e `frontend`
- Git iniciado
- Primeiro endpoint de teste criado
- Angular CLI instalado
- H2 configurado para desenvolvimento
- PostgreSQL adicionado para uso futuro
- Primeiras tabelas modeladas com JPA
- Regra inicial de criacao de agendamento implementada
- Endpoints iniciais de cliente, barbeiro e agendamento criados
- Disponibilidade por barbeiro criada
- Listagem de horarios disponiveis em blocos de 30 minutos criada
- Cancelamento logico de agendamento criado
- Tratamento basico de erros da API criado
