plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "wallet-management"
include("wallet-service")
include("api-gateway")
include("transaction-service")
include("price-service")
include("crypto-commons")
include("discovery-server")
