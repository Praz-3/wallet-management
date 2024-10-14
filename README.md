# Crypto Wallet Management System

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Services](#services)
- [Database Setup](#database-setup)
- [Docker Deployment](#docker-deployment)
- [API Documentation](#api-documentation)
- [Future Scope](#future-scope)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)

## Overview

This is a microservices-based application that enables management of cryptocurrency wallets, handling of transactions, and real-time price fetching for various cryptocurrencies. Built with Spring Boot and Kotlin, and PostgreSQL as application database.

## Features

- **User Wallet Management**:
  - Create, retrieve, update, and delete user wallets.
  - Support for multiple cryptocurrencies, including Bitcoin and Ethereum.

- **Transaction Handling**:
  - Send and receive crypto assets with simulated blockchain confirmations.
  - Maintain transaction history with detailed records, including timestamps and transaction IDs.

- **Real-Time Price Fetching**:
  - Integrate with external APIs to fetch real-time cryptocurrency prices.
  - Calculate and display the total value of user wallets in USD.

- **Security Measures**:
  - Implement encryption for wallet private keys.

## Architecture

The system is designed with a microservices architecture, enabling independent deployment and scaling of services.

### Services

1. **Wallet Service**:
  - Manages wallet-related operations and user wallet information, including currency balances.

2. **Transaction Service**:
  - Handles transactions, making synchronous calls to the Wallet Service for balance updates and validations.

3. **Price Service**:
  - Fetches real-time cryptocurrency prices using asynchronous third-party API calls to calculate wallet values.
  - Also makes call to Wallet Service to get the wallet details for specified user.

4. **Discovery Service**:
  - Utilizes service discovery pattern for dynamic service registration and management.

5. **API Gateway**:
  - Acts as a single entry point for client requests, simplifying API interactions.

## Database Setup

The application uses **PostgreSQL** for data storage. Ensure that you have a PostgreSQL instance running for the application to connect to.

### Docker Deployment

To deploy the PostgreSQL database and the microservices, follow these steps:

1. **Set Up PostgreSQL**:
  - Use the following command to start a PostgreSQL container:
    ```bash
    docker build -t crypto-db .
    ```

2. **Build and Run the Microservices**:
  - Ensure you have Docker and Docker Compose installed.
  - Navigate to the database deployment directory of the project and run:
    ```bash
    docker-compose up
    ```

3. **Accessing the APIs**:
  - Once all services are running, you can hit the respective endpoints through the API Gateway.

## API Documentation

Each service comes with its own set of APIs. Detailed API documentation can be found in the README files located in each module's directory.

[wallet-service README](wallet-service/README.md)

[transaction-service README](transaction-service/README.md)

[price-service README](price-service/README.md)

## Future Scope

- **Dockerization of All Modules**:
  - Plans to dockerize all modules for improved deployment and scalability.

## Technologies Used

- **Spring Boot**: For building the microservices.
- **Kotlin**: As the primary programming language.
- **PostgreSQL**: For data persistence.
- **Spring Cloud**: For service discovery and gateway management.
- **Docker**: For containerization and deployment.

## Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/Praz-3/wallet-management.git
   cd crypto-wallet-management
   ```
2. Follow the Docker deployment instructions above to set up the database and services.
3. Explore the APIs as documented in each service's README file.