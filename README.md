# solvaTest

## Setup
### Setup with docker
* Run (in root folder)
`docker-compose.exe -f docker-compose.yml up -d`

### Setup with maven
1. Setup Postgres, Maven and JDK 11

2. Create new dataBase (default creads below):
* `Database`: "solva"
* `username`: "postgres"
* `password`: "1"

3. Download Cassandra via docker and run it
* `docker pull cassandra:latest`
* `docker run -p 9042:9042 --rm --name cassandra -d cassandra`

5. Register on Twelvedata, get API-key and change it in `application.properties`:
* `constant.twelvedata.api-key= ${API-KEY:_your_api_key_here}`

6. Run maven build command
* `mvnw spring-boot:run -Dmaven.test.skip=true`

#### If KEYSPACE did not auto created
1. , Open cqlsh and Create KEYSPACE
* `docker exec -it cassandra cqlsh` or `cqlsh` in cassandra terminal via docker app
2. Run this script
* `CREATE KEYSPACE spring_cassandra WITH replication = {'class' : 'SimpleStrategy', 'replication_factor' : 1};`

## Testing
* Run app with integration tests

  `mvnw spring-boot:run`


* Import Postman Collection via link below or JSON file in root (solvaPostmanCollection.json)


    `https://api.postman.com/collections/20481002-3db9ec6d-f6d7-4159-ae08-5df3bcd5ab52?access_key=PMAT-01GNHJBZCG43QBRCS9YZW5CNFH`

  * Create a new or open Workplace in Postman
  * Click import button
  * Select JSON file or insert link
  * Click Import

# REST API

The REST API is described below.

### Get list of Things

### 1. Update Limit
Setting a new limit

`POST /api/v1/solva/limit/`
#### Request body
        {
          "accountNumber": 322,
          "limitValue": 1000,
          "expenseCategory": "product",
          "limitCurrency": "USD"
        }
#### Response
    HTTP/1.1 200 OK
    Status: 200 OK

### 2. Save list of transactions

`PUT /api/v1/solva/transactions`
#### Request body
    [
        {
            "accountFrom": 322,
            "accountTo": 123,
            "currencyShortname": "RUB",
            "sum": 2000,
            "expenseCategory": "service"
        },
        {
            "accountFrom": 322,
            "accountTo": 456,
            "currencyShortname": "KZT",
            "sum": 1000,
            "expenseCategory": "product"
        },
    ]
##### Response
    HTTP/1.1 200 OK
    Status: 200 OK

### 3. Save Transaction
Get information about each debit transaction in tenge (KZT) in real time and save it in a database (DB);

`POST /api/v1/solva/transaction/`

#### Request body
        {
          "accountFrom": 322,
          "accountTo": 123,
          "currencyShortname": "KZT",
          "sum": 30000,
          "expenseCategory": "product"
        }

##### Response

    HTTP/1.1 200 OK
    Status: 200 OK

### 4. GET Transaction List
Returns a list of transactions that have exceeded the limit

`GET /api/v1/solva/transactions/{accountNumber}/`

#### Request:  int Account Number

#### Response

    HTTP/1.1 200 OK
    Status: 200 OK
    
    Body:
    
    [
        {
            "id": 1,
            "limitValue": 0,
            "limitDatetime": "2022-12-30T08:47:36.501595Z",
            "limitCurrency": "USD",
            "accountFrom": 322,
            "accountTo": 13131,
            "currencyShortname": "KZT",
            "sum": 99.21,
            "expenseCategory": "service",
            "limitExceeded": true
        },
        {
            "id": 2,
            "limitValue": 0,
            "limitDatetime": "2022-12-30T08:47:36.501595Z",
            "limitCurrency": "USD",
            "accountFrom": 322,
            "accountTo": 13131,
            "currencyShortname": "KZT",
            "sum": 99.21,
            "expenseCategory": "service",
            "limitExceeded": true
        }
    ]

### Schemas
### LimitRequestDto
    {
        accountNumber	integer($int64)
        limitValue	integer($int64)
        expenseCategory	string
        limitCurrency	string
    }
### TransactionRequestDto
    {
        accountFrom	integer($int64)
        accountTo	integer($int64)
        currencyShortname	string
        sum	number
        expenseCategory	string
    }
### TransactionItem
    {
        id	integer($int64)
        limitValue	integer($int64)
        limitDatetime	string($date-time)
        limitCurrency	string
        accountFrom	integer($int64)
        accountTo	integer($int64)
        currencyShortname	string
        sum	number
        expenseCategory	string
        limitExceeded	boolean
    }
### Limit 
    {
        id	integer($int64)
        accountNumber	integer($int64)
        limitValue	integer($int64)
        expenseCategory	string
        limitCurrency	string
        receivedTime	string($date-time)
    }