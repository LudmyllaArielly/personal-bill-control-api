# Controle de contas pessoal
*Esse projeto é uma Api de controle de conta pessoal, que utiliza linguagem Java, Framework spring, mapstruct como conversor de dtos, swagger, arquitetura Rest e também consumo de microservices de usuário. O objetivo do projeto e criar um controle de contas, onde o usuário se registra no sistema e pode então lançar suas despensas, definindo, tipo de conta, categorias e meios de pagamento, desse modo, este projeto me proporcionou a aprender a consumir microservices, relacionamentos avançados, mapear dtos, git flow e boas práticas.*

## Tecnologias usadas
- String Framework
  - Spring Web
  - Spring Data Jpa
  - MapStruct
  - Swagger
  - MySQL
  - Maven
  - Java 

## Passos para configuração

**1. Clone a aplicação**

```bash
git clone https://github.com/LudmyllaArielly/personal-bill-control-api.git
```
**2. Execute o aplicativo usando maven**

```bash
mvn personal-bill-control-api:run
```
**3. Pré-requisitos**
```bash
mvn --version
```
Veja a indicação da versão do maven instalada, bem como a versão do JDK, entre outras. Esses requisitos são obrigátorios, portanto é necessário definir corretamento as variáveis de ambiente JAVA_HOME, M2_HOME.
O aplicativo começará a ser executado em: <http://localhost:8080>

**4. Compila**

```bash
mvn compile
```
Este comando compila o projeto e deposita os resultados no diretório de destino.

**5. Executando a Rest Api**

```bash
java -jar target/api.jar
or
mvn personal-bill-control-api:run
```
Neste caminho
A Api foi geradada pelo pacote mvn package -P e esta sendo executada na porta 8080.

Exemplo de endereço: http://localhost:8080/personal-bill-control-api

Exemplo de endereço com swagger: http://localhost:8080/swagger-ui.html#/

## Explorar Rest APIs

### Conta

| Method | Url                                     | Description                              |
| ------ | --------------------------------------- | ---------------------------------------- |
| GET    | /bill                                   | Lista todas as contas                    |
| POST   | /bill                                   | Cria uma conta                           |
| PUT    | /bill                                   | Atualiza a conta                         |
| DELETE | /bill/{id}                              | Deleta uma conta                         |
| GET    | /bill/exportsTheBillToExcel             | Exporta todas as contas para o Excel     |
| GET    | /bill/findBillByAccountTypeAndValueType | Busca conta por tipo da conta e tipo do valor |
| GET    | /bill/findBillByAttributes              | Busca a conta por atributos              |
| GET    | /bill/findBillByDate                    | Busca entre datas                        |
| GET    | /bill/findAllUserAccount                | Busca todas as contas do usuário logado  |
| GET    | /bill/finsInstallmentsByDate            | Busca parcelas da conta por Data         |

## Usuário
| Method | Url          | Description                   |
| ------ | ------------ | ----------------------------- |
| GET    | /users       | Busca todos os usuários       |
| POST   | /users       | Cria usuário                  |
| POST   | /users/login | Faz a autenticação do usuário |

## Categoria

| Method | Url                          | Description                              |
| ------ | ---------------------------- | ---------------------------------------- |
| GET    | /categories                  | Busca todos as categorias                |
| POST   | /categories                  | Cria categoria                           |
| PUT    | /categories                  | Atualiza usuário                         |
| DELETE | /categories/{id}             | Deleta categoria                         |
| GET    | /categories/mostUsedCategory | Lista em orderm as categorias mais usadas |

## Pagamento

| Method | Url                     | Description                           |
| ------ | ----------------------- | ------------------------------------- |
| GET    | /payments               | Busca todos os tipos de pagamentos    |
| POST   | /payments               | Cria tipo de pagamento                |
| PUT    | /payments               | Atualiza tipo de pagamento            |
| gGET   | /payments/{description} | Busca tipo de pagamento pela decrição |
| DELETE | /payments/{id}          | Deleta tipo de pagamento              |

## Solicitação

| Method | Url                                    | Description                              |
| ------ | -------------------------------------- | ---------------------------------------- |
| GET    | /solicitations                         | Busca todas solicitações                 |
| POST   | /solicitations                         | Cria solicitações                        |
| PATCH  | /solicitations                         | Atualiza status da solicitações          |
| GET    | /solicitations/findAllUserSolicitation | Busca todas as solicitações feita pelo usuário logado |
| DELETE | /solicitations/{id}                    | Deleta solicitação                       |

## Exemplo de corpo de solicitações JSON válidos

##### Cria usuário
```json
{
  "cpf": "78985246189",
  "dateOfBirth": "2021-08-02T16:16:51.037Z",
  "email": "maria@gmail.com",
  "lastName": "Silva",
  "name": "Maria",
  "password": "maria457854",
  "roles": [
    "ADMIN"
  ]
}
```

##### Autentica usuário
```json
{
  "email": "string",
  "password": "string"
}
```

##### Cria conta
```json
{
  "accountType": "IN_CASH",
  "categoryCreateAndListAllDto": {
    "name": "Alimentação"
  },
  "description": "Compra de alimentos semanal",
  "justification": "Alimentos",
  "payCreateAndListAllDto": {
    "description": "Cartão de Débito"
  },
  "priceTotal": 350.00,
  "purchaseDate": "2021-08-02T15:11:00.142Z",
  "quantityPaymentInstallments": 0,
  "valueType": "VARIABLE"
}
```
##### Atualizar conta
```json
{
  "accountType": "IN_CASH",
  "categoryCreateAndListAllDto": {
    "name": "Alimentação"
  },
  "description": "Compra de alimentos semanal",
   "id": 1,
  "justification": "Alimentos",
  "payCreateAndListAllDto": {
    "description": "Cartão de Débito"
  },
  "priceTotal": 350.00,
  "purchaseDate": "2021-08-02T15:11:00.142Z",
  "quantityPaymentInstallments": 0,
  "valueType": "VARIABLE"
}
```
##### Cria categoria
```json
{
  "name": "Lazer"
}
```
##### Atualiza Categoria

```json
{
  "id": 1,
  "name": "string"
}
```

##### Cria pagamento

```json
{
  "description": "Cartão de crédito"
}
```

##### Atualiza pagamento

```json
{
 "id":1,
  "description": "Cartão de crédito"
}
```

##### Cria solicitação

```json
{
  "description": "Cria uma categoria educação"
}
```

##### Atualiza status da solicitação

```json
{
  "id":1,
  "status":"CLOSED"
}
```




