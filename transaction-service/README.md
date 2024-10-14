# Transaction API Documentation

This README provides information about the Transaction API, which allows users to execute cryptocurrency transactions and retrieve transaction histories.


## Endpoints

### 1. Execute a Transaction

- **POST** `http://{host}:{port}/api/transaction/execute`

  Executes a cryptocurrency transaction between two wallets.

#### Request

- **Content-Type:** `application/json`
- **Body:**

```json
{
    "senderWalletId": 1,
    "recipientWalletId": 2,
    "cryptoCurrencyType": "BITCOIN",
    "amount": 0.01
}
```

#### Response

- **Status-Code:** `201-CREATED`
- **Body:**

```json
{
  "id": 1,
  "senderWalletId": 1,
  "recipientWalletId": 2,
  "cryptoCurrencyType": "BITCOIN",
  "amount": 0.01,
  "timestamp": "2024-10-14T10:00:00",
  "status": "COMPLETED",
  "transactionId": "abc123",
  "exceptionMessage": null
}
```

#### Example
```commandline
curl -X POST http://localhost:8080/api/transaction/execute \
-H "Content-Type: application/json" \
-d '{"senderWalletId": 1, "recipientWalletId": 2, "cryptoCurrencyType": "BITCOIN", "amount": 0.01}'
```

### 2. Get Transaction History

- **GET** `http://{host}:{port}/api/transaction/history/{walletId}`

  Retrieves the transaction history for a specific wallet.

#### Request

- **walletId:** The ID of the wallet for which to retrieve the transaction history.

#### Response

- **Status-Code:** `200-OK`
- **Body:**

```json
[
  {
    "id": 1,
    "senderWalletId": 1,
    "recipientWalletId": 2,
    "cryptoCurrencyType": "BITCOIN",
    "amount": 0.01,
    "timestamp": "2024-10-14T10:00:00",
    "status": "COMPLETED",
    "transactionId": "abc123",
    "exceptionMessage": null
  },
  {
    "id": 2,
    "senderWalletId": 2,
    "recipientWalletId": 1,
    "cryptoCurrencyType": "ETHEREUM",
    "amount": 0.5,
    "timestamp": "2024-10-14T10:05:00",
    "status": "COMPLETED",
    "transactionId": "def456",
    "exceptionMessage": null
  }
]
```

#### Example
```commandline
curl -X GET http://localhost:8080/api/transaction/history/1
```
