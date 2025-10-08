# euComida - Backend

## 📌 Visão Geral

O `euComida` é um backend projetado para suportar um marketplace de delivery de comida. O sistema foi desenvolvido com foco em escalabilidade, segurança e boas práticas de desenvolvimento. Esta documentação detalha a arquitetura atual, tecnologias utilizadas e estratégias adotadas para autenticação e segurança.

---

## 🏗 Arquitetura do Projeto

O backend do projeto **euComida** está estruturado seguindo a **arquitetura multicamadas** (N-Tier Architecture), organizando o código de forma modular e separando responsabilidades entre as diferentes camadas. Isso permite um desenvolvimento mais organizado, facilitando a manutenção e escalabilidade da aplicação.

### 🔹 Estrutura de Diretórios

O código está estruturado no diretório `src/main/java/com/geosapiens/eucomida` com as seguintes pastas principais:

1. **Camada de Apresentação (`controller`)**

   - **Local:** `src/main/java/com/geosapiens/eucomida/controller`
   - Responsável pelos controladores REST que expõem as APIs da aplicação.
   - **Exemplo:** `OrderController.java` lida com requisições HTTP relacionadas a pedidos.
   - Utiliza anotações como `@RestController`, `@RequestMapping`, `@GetMapping`, `@PostMapping`, etc.

2. **Camada de Serviço (`service`)**

   - **Local:** `src/main/java/com/geosapiens/eucomida/service`
   - Contém a lógica de negócio da aplicação.
   - **Exemplo:** `OrderService.java` contém a lógica para criar e buscar pedidos.
   - Utiliza `@Service` para ser gerenciado pelo Spring.

3. **Camada de Persistência (`repository`)**

   - **Local:** `src/main/java/com/geosapiens/eucomida/repository`
   - Contém os repositórios JPA responsáveis pela interação com o banco de dados.
   - **Exemplo:** `OrderRepository.java`, que estende `JpaRepository<Order, UUID>`.
   - Utiliza `@Repository` e métodos do Spring Data JPA.

4. **Camada de Modelo de Dados (`entity`)**

   - **Local:** `src/main/java/com/geosapiens/eucomida/entity`
   - Contém as classes que representam as entidades do banco de dados.
   - **Exemplo:** `Order.java`, `User.java`, `Courier.java`.
   - Utiliza `@Entity`, `@Id`, `@GeneratedValue`, `@ManyToOne`, etc.

5. **Camada de Transferência de Dados (`dto`)**

   - **Local:** `src/main/java/com/geosapiens/eucomida/dto`
   - Os DTOs (Data Transfer Objects) são usados para transferir dados entre a API e a lógica de negócio.
   - **Exemplo:** `OrderDTO.java`, `UserDTO.java`.
   - Utilizados nos controllers para entrada/saída de dados.

6. **Camada de Mapeamento (`mapper`)**

   - **Local:** `src/main/java/com/geosapiens/eucomida/mapper`
   - Os mappers convertem entidades para DTOs e vice-versa.
   - **Exemplo:** `OrderMapper.java` converte `Order` para `OrderDTO`.

7. **Camada de Segurança (`security`)**

   - **Local:** `src/main/java/com/geosapiens/eucomida/security`
   - Contém a configuração de autenticação e autorização da aplicação.
   - **Exemplo:** Implementação de JWT, filtros de segurança.

8. **Camada de Exceções (`exception`)**

   - **Local:** `src/main/java/com/geosapiens/eucomida/exception`
   - Responsável pelo tratamento de erros e exceções personalizadas.
   - **Exemplo:** `GlobalExceptionHandler.java` trata exceções essas exceções customizadas e retorna objetos padronizados em requisições RESTFUL.

---

### 🔹 Tecnologias e Frameworks Utilizados

- **Linguagem**: Java 21
- **Framework Principal**: Spring Boot 3.4.3
- **Persistência**: Spring Data JPA + Hibernate
- **Banco de Dados**: PostgreSQL 16
- **Autenticação e Segurança**: OAuth2 (Google), JWT, Spring Security
- **Testes**: JUnit 5, AssertJ, Mockito
- **Documentação da API**: OpenAPI (Swagger)
- **Gerenciamento de Dependências**: Maven
- **Migrações de Banco**: Flyway
- **Containerização**: Docker + Docker Compose
- **Gerenciamento de Conexões**: HikariCP

---

## 🛢 Estrutura do Banco de Dados

O sistema utiliza um banco **relacional (PostgreSQL)** com um modelo normalizado para melhor integridade dos dados.

### 📊 Principais Tabelas:

1. **Usuários (********`users`********\*\*\*\*)**

   - `id` (UUID) - Identificador único
   - `name` (String) - Nome do usuário
   - `email` (String) - E-mail (único)
   - `created_at` (TIMESTAMP) - Data de criação
   - `updated_at` (TIMESTAMP) - Última atualização

2. **Entregadores (********`couriers`********\*\*\*\*)**

   - `id` (UUID) - Identificador único
   - `user_id` (UUID) - Relacionamento com a tabela `users`, único e obrigatório
   - `vehicle_type` (VARCHAR) - Tipo de veículo (`BICYCLE`, `CAR`, `MOTORCYCLE`)
   - `plate_number` (VARCHAR) - Placa do veículo (opcional)
   - `created_at` (TIMESTAMP) - Data de criação
   - `updated_at` (TIMESTAMP) - Última atualização

3. **Pedidos (********`orders`********\*\*\*\*)**

   - `id` (UUID) - Identificador único
   - `user_id` (UUID) - Relacionamento com a tabela `users`
   - `courier_id` (UUID) - Relacionamento com `couriers`
   - `status` (VARCHAR) - Status (`PENDING`, `IN_PROGRESS`, `DELIVERED`, `CANCELED`)
   - `total_price` (DECIMAL) - Valor total do pedido
   - `payment_status` (VARCHAR) - Status do pagamento (`PENDING`, `PAID`, `FAILED`)
   - `created_at` (TIMESTAMP) - Data de criação
   - `updated_at` (TIMESTAMP) - Última atualização

O controle de versões do banco de dados é gerenciado pelo **Flyway**.

---

## 🔑 Estratégia de Autenticação e Autorização

A segurança do sistema é baseada em **OAuth2 e JWT**:

- O backend delega a autenticação ao Google via OAuth2.
- O sistema gera **tokens JWT** para sessões autenticadas.
- O **Spring Security** gerencia  a autenticação dos usuários.
- Os tokens possuem tempo de expiração de 1 hora.

---

## 🚀 Estratégia de Escalabilidade e Segurança da API

### 🔹 Escalabilidade:

- O sistema pode ser escalado horizontalmente ao adicionar novas instâncias da aplicação, permitindo distribuição de carga entre múltiplos servidores.
- O sistema foi estruturado para permitir evolução para microserviços no futuro.
- Uso de **HikariCP** para otimização de conexões ao banco.

### 🔹 Segurança:

- **Spring Security** implementado para garantir a segurança da API, fornecendo autenticação e proteção contra acessos não autorizados.
- **Gerenciamento seguro de credenciais**: As variáveis sensíveis (secrets) são armazenadas de forma segura no ambiente de execução, protegendo informações sensíveis.
- **Autenticação via OAuth2 e JWT** para garantir sessões seguras e evitar acessos não autorizados.
- O sistema utiliza **OAuth2 com Google**, e senhas não são armazenadas no banco de dados para usuários autenticados.

---

## 📖 Documentação da API

A documentação interativa da API pode ser acessada via **Swagger UI** no seguinte link:

🔗 **[Swagger UI - Documentação da API](https://eucomida-k37rx7zl3q-uc.a.run.app/swagger-ui/index.html)**

## 🛠 Como Rodar o Projeto em ambiente LOCAL

### 🔹 Pré-requisitos:

1. **Docker e Docker Compose** instalados (versões mais recentes).
2. **Java 21 e Maven** instalados.

### 🔹 Passos para rodar:

```sh
# Clonar o repositório
git clone https://github.com/celestinofabiano/eucomida.git
cd eucomida

# Construir o projeto
mvn clean install

# Subir os containers (PostgreSQL + API)
docker-compose up --build

# A API estará rodando em http://localhost:8080
```

### 🔹 Executar testes:

```sh
mvn test
```

## 📌 Decisões Técnicas Tomadas

### 1️⃣ Arquitetura e Design do Sistema
- Arquitetura baseada em serviços desacoplados para permitir escalabilidade futura.
- Modelo relacional com PostgreSQL, garantindo integridade dos dados e suporte a transações ACID.
- Uso de Spring Boot para acelerar o desenvolvimento, manter padronização e facilitar integração com outras tecnologias.

### 2️⃣ Escolha das Tecnologias
- Java 21: Escolhido por sua estabilidade, suporte a recursos modernos e melhorias de desempenho.
- Spring Boot 3.4.3: Framework principal pela sua robustez, modularidade e facilidade de configuração.
- Spring Data JPA + Hibernate: Simplifica a persistência e abstrai interações com o banco de dados.
- OAuth2 + JWT: Implementação de autenticação moderna e segura.
- HikariCP: Para otimização de conexões ao banco, melhorando performance.
- Flyway: Controle de versões do banco de dados para manter consistência entre ambientes.
- OpenAPI (Swagger): Documentação interativa da API.
- JUnit 5, AssertJ e Mockito: Stack de testes robusta, garantindo qualidade do código.

### 3️⃣ Autenticação e Segurança
- OAuth2 com Google: Delegação de autenticação para um provedor externo confiável.
- JWT para autorização: Sessões autenticadas e seguras, reduzindo carga no banco.
- Spring Security: Controle de acesso granular e proteção contra ataques comuns (XSS, CSRF, etc.).
- Variáveis sensíveis protegidas: Uso de variáveis de ambiente para segredos e chaves.

### 4️⃣ Escalabilidade e Performance
- Arquitetura escalável horizontalmente: Permite aumentar o número de instâncias sem impacto no sistema.
- Gerenciamento eficiente de conexões: HikariCP otimiza o uso do banco, evitando gargalos.
- Possível evolução para microserviços: O sistema foi estruturado para permitir modularização no futuro.

### 5️⃣ Containerização e Facilidade de Deploy
- Uso de Docker e Docker Compose: Facilita configuração e execução do ambiente, garantindo portabilidade.
- Banco PostgreSQL rodando em container: Garantia de um ambiente controlado e padronizado.
---

## 📚 Próximos Passos

- Implementação de **microserviços** e **API Gateway**.
- Adicionar **WebSockets** para rastreamento de pedidos em tempo real.
- Monitoramento com **Prometheus e Grafana**.
- Implementação de **caching com Redis** para otimizar desempenho.

---

