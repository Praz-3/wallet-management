# Wallet API Documentation

This README provides information about the Wallet API, which allows users to create, update, retrieve, and delete cryptocurrency wallets.

## Endpoints

### 1. Create a Wallet

- **POST** `http://{host}:{port}/api/wallet/create`

  Creates a new cryptocurrency wallet.

#### Request

- **Content-Type:** `application/json`
- **Body:**

```json
{
  "userId": 1,
  "walletAddress": "0x1234567890abcdef",
  "privateKey": "your_private_key",
  "initialBalances": [
    {
      "currencyType": "BITCOIN",
      "balance": 0.0
    },
    {
      "currencyType": "ETHEREUM",
      "balance": 0.0
    }
  ]
}
```

#### Response

- **Status-Code:** `201-CREATED`
- **Body:**

```json
{
  "id": 1,
  "userId": 1,
  "walletAddress": "0x1234567890abcdef",
  "currencyBalances": [
    {
      "id": 1,
      "currencyType": "BITCOIN",
      "balance": 0.0
    },
    {
      "id": 2,
      "currencyType": "ETHEREUM",
      "balance": 0.0
    }
  ],
  "privateKey": "your_private_key"
}
```

#### Example
```commandline
curl -X POST http://localhost:8080/api/wallet/create \
-H "Content-Type: application/json" \
-d '{
    "userId": 1,
    "walletAddress": "0x1234567890abcdef",
    "privateKey": "your_private_key",
    "initialBalances": [
        {
            "currencyType": "BITCOIN",
            "balance": 0.0
        },
        {
            "currencyType": "ETHEREUM",
            "balance": 0.0
        }
    ]
}'
```

### 2. Update Wallet Balance

- **POST** `http://{host}:{port}/api/wallet/update-balance`

  Updates the balance of a specific currency in a wallet.

#### Request

- **Content-Type:** `application/json`
- **Body:**

```json
{
  "walletId": 1,
  "currencyType": "BITCOIN",
  "amount": 1.0
}
```

#### Response

- **Status-Code:** `200-OK`
- **Body:**

```json
{
  "id": 1,
  "userId": 1,
  "walletAddress": "0x1234567890abcdef",
  "currencyBalances": [
    {
      "id": 1,
      "currencyType": "BITCOIN",
      "balance": 1.0
    }
  ],
  "privateKey": "your_private_key"
}
```

#### Example
```commandline
curl -X PUT http://localhost:8080/api/wallet/update-balance \
-H "Content-Type: application/json" \
-d '{
    "walletId": 1,
    "currencyType": "BITCOIN",
    "amount": 1.0
}'
```

### 3. Get Wallet Details

- **GET** `http://{host}:{port}/api/wallet/detail/{walletId}`

  Retrieves the details of a specific wallet.

#### Request

- **walletId:** The ID of the wallet to retrieve

#### Response

- **Status-Code:** `200-OK`
- **Body:**

```json
{
  "id": 1,
  "userId": 1,
  "walletAddress": "0x1234567890abcdef",
  "currencyBalances": [
    {
      "id": 1,
      "currencyType": "BITCOIN",
      "balance": 0.0
    }
  ],
  "privateKey": "your_private_key"
}
```

#### Example
```commandline
curl -X GET http://localhost:8080/api/wallet/detail/1
```

### 4. Get Wallet Details by User ID

- **GET** `http://{host}:{port}/api/wallet/user/{userId}`

  Retrieves wallet details associated with a specific user.

#### Request

- **userId:** The ID of the user whose wallet to retrieve.

#### Response

- **Status-Code:** `200-OK`
- **Body:**

```json
{
  "id": 1,
  "userId": 1,
  "walletAddress": "0x1234567890abcdef",
  "currencyBalances": [
    {
      "id": 1,
      "currencyType": "BITCOIN",
      "balance": 0.0
    }
  ],
  "privateKey": "your_private_key"
}
```

#### Example
```commandline
curl -X GET http://localhost:8080/api/wallet/user/1
```

### 5. Delete a Wallet

- **DELETE** `http://{host}:{port}/api/wallet/user/{userId}`

  Deletes a specific wallet.

#### Request

- **walletId:** The ID of the wallet to delete.

#### Response

- **Status-Code:** `204-No Content`
- **Body:**

#### Example
```commandline
curl -X DELETE http://localhost:8080/api/wallet/delete/1
```
