# TrainLab API

API REST para gerenciamento de treinos, exercícios e estatísticas de usuários.

Criei este projeto com o objetivo de organizar meus próprios treinos e, ao mesmo tempo, aplicar na prática os conceitos que venho estudando em desenvolvimento backend com Java e Spring Boot.

No início, a ideia era apenas um MVP simples, mas com o tempo fui evoluindo a aplicação, melhorando a estrutura e tentando aproximar o projeto de um cenário mais próximo do que é utilizado no mercado.

---

## Deploy da aplicação (beta)

🔗 [Trainlab - v1 (beta)](https://trainlab-mvp.vercel.app/)

> ⚠️ Observações:
>
> - O backend pode levar alguns segundos na primeira requisição (cold start)
> - Algumas rotas podem retornar 404 ao atualizar a página (limitação do frontend)
> - Há um comportamento incorreto na seleção do tipo de treino (fixado em "strength"), que será corrigido nas próximas versões
> - Essas melhorias estão sendo tratadas na versão atual do projeto

---

## Apresentação do projeto

Também fiz uma apresentação do projeto no LinkedIn, mostrando a proposta, arquitetura e funcionamento da aplicação:

🔗 [Trainlab - plataforma para organização de treinos](https://www.linkedin.com/posts/gustavo-braga-4759a1396_trainlab-construindo-uma-aplica%C3%A7%C3%A3o-web-activity-7438332669069000704-UX-a?utm_source=share&utm_medium=member_desktop&rcm=ACoAAGFSf1kBALKyBt552xkYz6Zkga5gP3UOb9g)

---

## Tecnologias utilizadas

* Java
* Spring Boot
* Spring Data JPA
* PostgreSQL / MySQL
* Maven

---

## Arquitetura e decisões

Optei por utilizar uma arquitetura em camadas (Controller → Service → Repository) para manter a separação de responsabilidades e facilitar a evolução do projeto.

Também utilizei DTOs para evitar expor diretamente as entidades e ter mais controle sobre os dados que entram e saem da aplicação.

Além disso, implementei um tratamento global de exceções para padronizar as respostas de erro e melhorar a consistência da API.

---

## Domínio da aplicação

A API foi estruturada com base na seguinte relação:

* **User**

  * **Workout**

    * **Exercise**

Essa organização surgiu da necessidade de manter os treinos vinculados a um usuário e os exercícios vinculados a um treino, garantindo consistência no domínio.

---

## Endpoints

###  Users

```
POST   /users
PUT    /users/{id}
DELETE /users/{id}
```

---

### Workouts

```
POST   /users/{userId}/workouts
GET    /users/{userId}/workouts
GET    /users/{userId}/workouts/{workoutId}
PUT    /users/{userId}/workouts/{workoutId}
DELETE /users/{userId}/workouts/{workoutId}
```

---

### Exercises

```
POST   /users/{userId}/workouts/{workoutId}/exercises
GET    /users/{userId}/workouts/{workoutId}/exercises
PUT    /users/{userId}/workouts/{workoutId}/exercises/{exerciseId}
DELETE /users/{userId}/workouts/{workoutId}/exercises/{exerciseId}
```

---

### Stats

```
GET    /users/{userId}/stats
```

---

## Regras de negócio

* Um usuário pode ter vários treinos
* Um treino pertence a um único usuário
* Um exercício deve sempre estar associado a um treino
* Ao deletar um treino, seus exercícios também são removidos (cascade)
* Erros de validação retornam `400 Bad Request`
* Recursos não encontrados retornam `404 Not Found`

---

## Como executar o projeto

```bash
git clone https://github.com/GustavoB-Braga/trainlab-api-v2.git
cd trainlab-api-v2
./mvnw spring-boot:run
```

---

## Testes

Estou implementando testes unitários utilizando o padrão AAA (Arrange, Act, Assert), com o objetivo de garantir maior confiabilidade e facilitar a manutenção do código.

---

## 📄 Sobre o projeto

Este projeto marcou uma mudança importante na minha forma de aprender, pois foi quando deixei de apenas seguir cursos e comecei a desenvolver algo com mais autonomia, enfrentando problemas reais e tomando decisões por conta própria.

Ainda existem melhorias em andamento, principalmente relacionadas à infraestrutura e evolução da aplicação, que estão sendo trabalhadas na versão atual.

---

## Autor

Desenvolvido por Gustavo Braga
