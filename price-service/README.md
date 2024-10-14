# Price Fetching Service API Documentation

This README provides information about the Price Service API, which allows users to retrieve the total value of their cryptocurrency wallets in USD.


## Endpoints

### 1. Get Total Wallet Value

- **GET** `http://{host}:{port}/api/price/wallet/total-value/{userId}`

  Retrieves the total value of a user's wallet in USD.

#### Request

- **userId:** The ID of the user whose wallet value is to be retrieved.

#### Response

- **Status-Code:** `200-OK`
- **Body:**

```json
{
  "totalValue": 1000.0
}
```

#### Example
```commandline
curl -X GET http://localhost:8080/api/price/wallet/total-value/1
```
