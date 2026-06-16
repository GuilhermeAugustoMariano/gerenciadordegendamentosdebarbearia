# Guia de Deploy - Render, Supabase e Netlify

Deploy e o processo de colocar o sistema na internet.

Neste projeto, vamos usar:

```text
Supabase: banco PostgreSQL
Render: backend Spring Boot
Netlify: frontend Angular
```

## 1. Criar o banco no Supabase

1. Acesse https://supabase.com.
2. Crie uma conta ou entre na sua conta.
3. Crie um novo projeto.
4. Guarde a senha do banco em local seguro.
5. No painel do projeto, procure a area de conexao do banco.
6. Copie os dados de host, porta, nome do banco, usuario e senha.

No Spring Boot, a URL precisa estar no formato JDBC:

```text
jdbc:postgresql://HOST:5432/postgres?sslmode=require
```

Exemplo ficticio:

```text
jdbc:postgresql://aws-0-sa-east-1.pooler.supabase.com:5432/postgres?sslmode=require
```

Nunca coloque a senha do banco dentro do codigo.

## 2. Criar o backend no Render

1. Acesse https://render.com.
2. Conecte sua conta do GitHub.
3. Crie um novo Web Service usando este repositorio.
4. Use o arquivo `render.yaml` se o Render oferecer Blueprint.
5. Configure estas variaveis de ambiente:

```text
SPRING_PROFILES_ACTIVE=prod
SPRING_DATASOURCE_URL=jdbc:postgresql://HOST:5432/postgres?sslmode=require
SPRING_DATASOURCE_USERNAME=SEU_USUARIO_DO_SUPABASE
SPRING_DATASOURCE_PASSWORD=SUA_SENHA_DO_SUPABASE
APP_CORS_ALLOWED_ORIGINS=https://SEU-SITE.netlify.app
```

Enquanto voce ainda nao souber a URL da Netlify, pode deixar `APP_CORS_ALLOWED_ORIGINS` temporariamente com:

```text
http://localhost:4200
```

Depois que o frontend existir na Netlify, volte no Render e troque para a URL real.

Quando o deploy terminar, teste:

```text
https://SEU-BACKEND.onrender.com/hello
```

Resposta esperada:

```text
API do Gerenciador de Agendamentos de Barbearia rodando!
```

## 3. Criar o frontend no Netlify

1. Acesse https://www.netlify.com.
2. Conecte sua conta do GitHub.
3. Importe este repositorio.
4. O Netlify deve ler o arquivo `netlify.toml` automaticamente.
5. Configure esta variavel de ambiente:

```text
BARBEARIA_API_URL=https://SEU-BACKEND.onrender.com
```

Depois faca o deploy.

## 4. Atualizar CORS no Render

Quando o Netlify gerar a URL do frontend, volte no Render e configure:

```text
APP_CORS_ALLOWED_ORIGINS=https://SEU-SITE.netlify.app
```

Depois clique para fazer redeploy do backend.

## 5. Teste final em producao

Na URL da Netlify:

1. Cadastre um cliente.
2. Cadastre um barbeiro.
3. Cadastre a disponibilidade.
4. Busque horarios.
5. Crie um agendamento.
6. Tente criar outro agendamento no mesmo horario para confirmar que a regra bloqueia.

Se algo falhar, verifique os logs do Render.

## 6. Variaveis de ambiente usadas

Variavel de ambiente e uma configuracao fora do codigo.

Backend no Render:

```text
SPRING_PROFILES_ACTIVE
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
APP_CORS_ALLOWED_ORIGINS
```

Frontend no Netlify:

```text
BARBEARIA_API_URL
```
## 7. Erro de Dialect ou conexao com o banco

Se o Render mostrar uma mensagem parecida com:

```text
Unable to determine Dialect without JDBC metadata
```

verifique primeiro se as variaveis de ambiente do backend foram cadastradas exatamente com estes nomes:

```text
SPRING_PROFILES_ACTIVE
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
APP_CORS_ALLOWED_ORIGINS
```

Para este projeto Supabase, a URL deve ficar neste formato:

```text
jdbc:postgresql://db.zbjucuzwmfqiuiifhywi.supabase.co:5432/postgres?sslmode=require
```

Nao inclua a senha dentro da URL. A senha deve ficar somente em `SPRING_DATASOURCE_PASSWORD`.

Se ainda falhar conexao usando o host direto `db...supabase.co`, procure no Supabase a connection string do pooler e monte a URL JDBC com aquele host. Alguns provedores de hospedagem se conectam melhor pelo pooler do Supabase.