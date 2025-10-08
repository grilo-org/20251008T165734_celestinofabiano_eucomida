# Configuração do Workspace e Variáveis de Ambiente no Postman

Este guia orienta sobre como importar e configurar um workspace no **Postman**, incluindo a definição das variáveis de ambiente e o fluxo de autenticação OAuth2 para a API **euComida**.

---

## 📥 Importando a Collection e o Ambiente

### 1️⃣ **Baixando os arquivos necessários**
Antes de prosseguir, baixe os arquivos JSON necessários para importar no Postman:

- 📄 **[Download da Collection](https://github.com/celestinofabiano/eucomida/raw/main/postman/euComida.postman_collection.json)**
- 🌍 **[Download do Ambiente](https://github.com/celestinofabiano/eucomida/raw/main/postman/euComida.postman_environment.json)**

### 2️⃣ **Importando a Collection**
1. Abra o **Postman**.
2. No menu lateral esquerdo, clique em **Collections**.
3. Clique em **Import** (ou pressione `Ctrl + O` / `Cmd + O` no macOS).
4. Selecione o arquivo `euComida.postman_collection.json` baixado e clique em **Open**.
5. A coleção será adicionada ao seu workspace.

### 3️⃣ **Importando o Ambiente**
1. No **Postman**, vá até **Environments** (ícone de engrenagem no canto superior direito).
2. Clique em **Import**.
3. Selecione `euComida.postman_environment.json` e clique em **Open**.
4. Após a importação, clique em **Set as active** para ativar o ambiente.

---

## 🔑 **Autenticação OAuth2 com Google**

A API **euComida** utiliza autenticação via **OAuth2 do Google**. Para realizar a autenticação e armazenar o token automaticamente, siga os passos abaixo:

### 1️⃣ **Solicitando um novo token**
1. No Postman, acesse a requisição **"Authenticate With Google"** dentro da coleção **euComida**.
2. Vá até a aba **Authorization** e confirme que o tipo está definido como **OAuth 2.0**.
3. Clique em **Get New Access Token**.
4. Será aberta uma tela para autenticação em **2 fatores** do Google.
5. Escolha a conta do Google desejada e **autorize o acesso** ao aplicativo **euComida** clicando em **Permitir**.

### 2️⃣ **Aplicando o Token no Postman**
1. Após a autenticação bem-sucedida, o Postman exibirá uma mensagem de sucesso.
2. Clique no botão **"Use Token"** no canto superior da tela aberta.
   - 🚨 *O tempo de expiração do token é de 1 hora*
4. Role até a opção **"Use Token Type"** e altere de **"Access token"** para **"ID Token"**.
   ![use_token_type](https://github.com/user-attachments/assets/f7eae41e-a06c-456c-89ad-e07df1ebb37e)
   
   - 🚨 *Essa alteração é necessária porque estamos utilizando o token para um contexto de chamadas de API*
5. Envie a requisição para o endpoint **`/user/me`**.
6. Se a autenticação for bem-sucedida, o **Bearer Token** será automaticamente armazenado na variável de ambiente `token`.

Agora, todas as requisições que necessitam de autenticação poderão ser enviadas sem precisar configurar o token manualmente.

---

## ⚙️ Configurando Variáveis de Ambiente

O ambiente **euComida** inclui as seguintes variáveis:

| Nome da Variável | Valor Padrão | Descrição |
|-----------------|--------------|-----------|
| `baseUri` | `https://eucomida-k37rx7zl3q-uc.a.run.app` | URL base da API |
| `apiUri` | `{{baseUri}}/api/v1` | URL da versão 1 da API |
| `token` | *(vazio, preenchido automaticamente após login)* | Token de autenticação |
| `clientId` | `120667016166-s6vre300jo8r9lgjmv1aaegn9jpb8e2r.apps.googleusercontent.com` | ID do cliente OAuth2 |
| `clientSecret` | *(oculto)* | Segredo do cliente OAuth2 |
| `newOrderId` | *(vazio, preenchido dinamicamente)* | ID da última ordem criada |

---

## 🚀 Testando a API

1. **Selecione** o ambiente **euComida** no canto superior direito do Postman.
2. Escolha uma requisição dentro da coleção **euComida**.
3. Certifique-se de que a autenticação foi realizada e que a variável `token` está preenchida.
4. Clique em **Send** para testar os endpoints.
   - 🚨 Status possíveis de pedidos: (`PENDING`, `IN_PROGRESS`, `DELIVERED`, `CANCELED`)
   - 🚨 Status possíveis de pagamentos: (`PENDING`, `PAID`, `FAILED`)

Para informações detalhadas sobre os endpoints disponíveis, acesse a documentação completa no Swagger:

📄 **[Documentação da API - Swagger](https://eucomida-k37rx7zl3q-uc.a.run.app/swagger-ui/index.html)**

---

## 🎯 Testes Automatizados

A collection já possui scripts que armazenam dinamicamente informações úteis para facilitar os testes:

- Após criar um pedido (`Create Order`), o ID retornado será salvo na variável `newOrderId`, permitindo que requisições subsequentes (`Update Order`, `Get Order By ID`) sejam executadas sem precisar informar o ID manualmente.
- O `token` de autenticação é salvo automaticamente após o login, eliminando a necessidade de copiá-lo manualmente.

---

## ❗ Dicas e Solução de Problemas

- **Erro 401 Unauthorized**: Verifique se o token (`token`) está preenchido corretamente e tente refazer a autenticação.
- **Erro 403 Forbidden**: Sua conta pode não ter permissão para acessar os endpoints.
- **Erro de conexão**: Certifique-se de que `baseUri` está correto e que a API está online.

---
