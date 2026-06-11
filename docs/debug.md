# Guia de Debug - Fluxo de Criar Agendamento

Debug e a tecnica de pausar o codigo enquanto ele roda para enxergar valores reais passando pelo sistema.

Neste projeto, vamos seguir o caminho de um agendamento:

```text
Tela Angular -> Controller Spring -> Service Spring -> Banco H2
```

## 1. Iniciar o backend em modo debug

No VS Code:

1. Abra a aba Run and Debug.
2. Escolha `Debug Backend Spring Boot`.
3. Clique no botao de iniciar.

Quando o terminal mostrar que o Spring iniciou, o backend estara em:

```text
http://localhost:8080
```

## 2. Iniciar o frontend

Em um terminal dentro da pasta `frontend`, rode:

```text
npm.cmd run start
```

Depois abra:

```text
http://localhost:4200
```

## 3. Colocar breakpoints

Breakpoint e um ponto de parada. Quando o codigo chegar nele, o VS Code pausa o programa.

Coloque breakpoints nestes metodos:

```text
frontend/src/app/app.ts
createAppointment()
```

Este breakpoint mostra os dados antes de sairem da tela Angular.

```text
backend/src/main/java/com/guilhermeaugusto/gerenciadordegendamentosdebarbearia/controller/AppointmentController.java
createAppointment(...)
```

Este breakpoint mostra a requisicao chegando na API.

```text
backend/src/main/java/com/guilhermeaugusto/gerenciadordegendamentosdebarbearia/service/AppointmentService.java
createAppointment(...)
```

Este breakpoint mostra a regra de negocio sendo executada.

## 4. Preparar dados na tela

Na tela Angular:

1. Cadastre um cliente.
2. Cadastre um barbeiro.
3. Cadastre a disponibilidade do barbeiro para o dia escolhido.
4. Busque horarios disponiveis.
5. Escolha um horario.
6. Clique em criar agendamento.

O programa deve parar nos breakpoints.

## 5. O que observar no debug

No frontend, observe:

```text
customerId
barberId
appointmentDate
appointmentTime
```

No controller, observe o objeto `request`.

No service, observe:

```text
customer
barber
barberAlreadyBooked
appointment
```

Se `barberAlreadyBooked` for `true`, o sistema vai bloquear o agendamento duplicado.

## 6. Conferir no banco H2

Com o backend rodando, abra:

```text
http://localhost:8080/h2-console
```

Use estes dados:

```text
JDBC URL: jdbc:h2:mem:barbearia
User Name: sa
Password: deixe vazio
```

Depois rode esta consulta:

```sql
SELECT * FROM APPOINTMENTS;
```

Ela mostra os agendamentos gravados no banco em memoria.

## 7. Botoes principais do debug

```text
Continue: deixa o codigo seguir ate o proximo breakpoint.
Step Over: executa a linha atual sem entrar dentro de outro metodo.
Step Into: entra dentro do metodo chamado naquela linha.
Stop: encerra o debug.
```