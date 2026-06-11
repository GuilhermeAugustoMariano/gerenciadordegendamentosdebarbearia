# Gerenciador de Agendamentos de Barbearia

Sistema full stack para gerenciar agendamentos de uma barbearia.

O projeto foi construido como estudo guiado, do zero ao deploy, usando Java Spring Boot no backend e Angular no frontend.

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

Deploy planejado:

```text
Render: backend
Supabase: banco PostgreSQL
Netlify: frontend
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

Arquivos de exemplo:

```text
backend/.env.example
frontend/.env.example
```

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

## Qualidade aplicada

- Separacao por camadas: controller, service, repository, model e dto.
- DTOs para entrada e saida da API.
- Regra de negocio centralizada no service.
- Testes automatizados para regras principais.
- Cobertura minima com JaCoCo.
- Logs em pontos importantes do fluxo.
- Configuracao separada para desenvolvimento e producao.

## Status

Projeto preparado para deploy manual em Render, Supabase e Netlify.