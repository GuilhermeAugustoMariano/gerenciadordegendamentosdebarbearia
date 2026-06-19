# Gerenciador de Agendamentos de Barbearia

Sistema full stack para gerenciar agendamentos de uma barbearia.

O projeto foi construido como estudo guiado, do zero ao deploy, usando Java Spring Boot no backend, Angular no frontend e PostgreSQL em producao.

## Projeto em producao

Frontend:

```text
https://iridescent-panda-47cb0b.netlify.app
```

Backend:

```text
https://barbearia-backend-111u.onrender.com/hello
```

Arquitetura em producao:

```text
Netlify -> Render -> Supabase
```

- Netlify hospeda a tela Angular.
- Render hospeda a API Spring Boot.
- Supabase hospeda o banco PostgreSQL.

## Funcionalidades

- Cadastro de clientes.
- Cadastro de barbeiros.
- Cadastro de disponibilidade do barbeiro por dia da semana.
- Listagem de horarios disponiveis em blocos de 30 minutos.
- Criacao de agendamento.
- Bloqueio de dois clientes no mesmo barbeiro, data e horario.
- Cancelamento logico de agendamento, mudando o status para `CANCELED`.
- Tratamento basico de erros em JSON.
- Logs basicos para observabilidade.
- Testes automatizados com cobertura minima de 70% no backend.

## Stack

Backend:

```text
Java 21
Spring Boot
Spring Web MVC
Spring Data JPA
H2 para desenvolvimento local
PostgreSQL para producao
JaCoCo para cobertura de testes
```

Frontend:

```text
Angular
Angular Material
TypeScript
```

Deploy:

```text
Render: backend
Supabase: banco PostgreSQL
Netlify: frontend
GitHub Actions: CI com testes e build
```

## Estrutura do projeto

```text
gerenciadordegendamentosdebarbearia/
  backend/      API Java Spring Boot
  frontend/     Aplicacao Angular
  docs/         Guias de debug e deploy
  pom.xml       Projeto Maven pai
  mvnw.cmd      Maven Wrapper para Windows
  render.yaml   Configuracao para Render
  netlify.toml  Configuracao para Netlify
```

## Modelo de dados

Tabelas principais:

```text
customers                 clientes
barbers                   barbeiros
barber_availabilities     disponibilidade dos barbeiros
appointments              agendamentos
```

A tabela `appointments` se conecta com `customers` e `barbers`.

Regra principal:

```text
Um barbeiro nao pode ter dois agendamentos ativos no mesmo dia e horario.
```

## Endpoints principais

```text
GET /hello
POST /customers
POST /barbers
POST /appointments
PATCH /appointments/{appointmentId}/cancel
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

## Rodando localmente

### Backend

Todos os comandos Maven devem ser executados na raiz do projeto.

Rodar os testes:

```text
cmd /c mvnw.cmd test
```

Rodar testes com cobertura:

```text
cmd /c mvnw.cmd clean verify
```

Iniciar a API:

```text
cmd /c mvnw.cmd -pl backend spring-boot:run
```

Depois acesse:

```text
http://localhost:8080/hello
```

### Frontend

Entre na pasta `frontend`:

```text
cd frontend
```

Instale as dependencias:

```text
npm.cmd install
```

Inicie o Angular:

```text
npm.cmd run start
```

Depois acesse:

```text
http://localhost:4200
```

## Testes e cobertura

Backend:

```text
cmd /c mvnw.cmd clean verify
```

O JaCoCo gera o relatorio em:

```text
backend/target/site/jacoco/index.html
```

Meta atual:

```text
Cobertura minima de linhas: 70%
```

Frontend:

```text
cd frontend
npm.cmd test -- --watch=false
```

## Variaveis de ambiente

Backend em producao:

```text
SPRING_PROFILES_ACTIVE=prod
SPRING_DATASOURCE_URL=jdbc:postgresql://HOST:5432/postgres?sslmode=require
SPRING_DATASOURCE_USERNAME=usuario
SPRING_DATASOURCE_PASSWORD=senha
APP_CORS_ALLOWED_ORIGINS=https://seu-site.netlify.app
```

Frontend em producao:

```text
BARBEARIA_API_URL=https://seu-backend.onrender.com
```

Variaveis usadas neste deploy:

```text
APP_CORS_ALLOWED_ORIGINS=https://iridescent-panda-47cb0b.netlify.app
BARBEARIA_API_URL=https://barbearia-backend-111u.onrender.com
```

Nunca salve senha real de banco em arquivos versionados no Git.

## Debug

Guia pratico do fluxo de criacao de agendamento:

```text
docs/debug.md
```

## Deploy

Guia de publicacao em Render, Supabase e Netlify:

```text
docs/deploy.md
```

Observacao sobre o plano gratuito do Render:

```text
A primeira chamada pode demorar porque o servidor pode dormir quando fica sem uso.
```

## Qualidade aplicada

- Separacao por camadas: controller, service, repository, model e dto.
- DTOs para entrada e saida da API.
- Regra de negocio centralizada no service.
- Testes automatizados para regras principais.
- Cobertura minima com JaCoCo.
- Logs em pontos importantes do fluxo.
- Configuracao separada para desenvolvimento e producao.
- CI no GitHub Actions para backend e frontend.

## Status

Projeto publicado e validado em producao.

Fluxo validado:

```text
Cadastrar cliente
Cadastrar barbeiro
Cadastrar disponibilidade
Buscar horarios disponiveis
Criar agendamento
Bloquear segundo agendamento no mesmo horario
```
