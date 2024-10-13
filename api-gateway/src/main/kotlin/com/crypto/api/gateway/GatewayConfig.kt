package com.crypto.api.gateway

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GatewayConfig {

    @Bean
    fun routes(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()
            // Route to Wallet Service
            .route("wallet-service") { r ->
                r.path("/wallet/**")
                    .uri("http://localhost:8081")  // Replace with the wallet service URL
            }
            // Route to Transaction Service
            .route("transaction-service") { r ->
                r.path("/transaction/**")
                    .uri("http://localhost:8082")  // Replace with the transaction service URL
            }
            // Route to Price Service
            .route("price-service") { r ->
                r.path("/price/**")
                    .uri("http://localhost:8083")  // Replace with the price service URL
            }
            .build()
    }
}

