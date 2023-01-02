# solvaTest

## Setup
1. Setup Postgres (v. 14.6 recommended)

2. Create new dataBase:
   `- Default creds`
* `$url`: jdbc:postgresql://localhost:5432/solva
* `$username`: postgres
* `$password`: 1

3. Register on Twelvedata, get API-key and change it in `application.properties`:
   `constant.twelvedata.api-key= ${API-KEY:_your_api_key_here}`

4. Run maven build command
*    `$./mvnw spring-boot:run`

## Testing
* Run integration tests `(/src/test/)`
* Import Postman Collection via link below or JSON file in root (solvaPostmanCollection.json)
  `https://api.postman.com/collections/20481002-3db9ec6d-f6d7-4159-ae08-5df3bcd5ab52?access_key=PMAT-01GNHJBZCG43QBRCS9YZW5CNFH`
    * Create a new or open Workplace in Postman
    * Click import button
    * Select JSON file or insert link
    * Click Import

# REST API

The REST API is described below.

## Get list of Things

### Update Limit

`PUT /api/v1/solva/limit/`

    Setting a new limit

## Request body

        {
          "accountNumber": 322,
          "limitValue": 1000,
          "expenseCategory": "product",
          "limitCurrency": "USD"
        }

### Response

    HTTP/1.1 200 OK
    Status: 200 OK

### Save Transaction

`POST /api/v1/solva/save/`

    Get information about each debit transaction in tenge (KZT) in real time and save it in a database (DB);

## Request body

        {
          "accountFrom": 322,
          "accountTo": 123,
          "currencyShortname": "KZT",
          "sum": 30000,
          "expenseCategory": "product"
        }

### Response

    HTTP/1.1 200 OK
    Status: 200 OK

### Get Transaction List

`GET /api/v1/solva/transactions/{accountNumber}/`

    Returns a list of transactions that have exceeded the limit

## Request - Variable {accountNumber}

### Response

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

# Schemas

## LimitRequestDto
    {
        accountNumber	integer($int64)
        limitValue	integer($int64)
        expenseCategory	string
        limitCurrency	string
    }

## TransactionRequestDto
    {
        accountFrom	integer($int64)
        accountTo	integer($int64)
        currencyShortname	string
        sum	number
        expenseCategory	string
    }

## TransactionItem
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
