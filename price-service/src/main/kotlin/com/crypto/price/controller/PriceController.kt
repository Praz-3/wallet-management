package com.crypto.price.controller

import com.crypto.price.service.PriceService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/price")
class PriceController(private val priceService: PriceService) {

    @GetMapping("/wallet/{userId}/total-value")
    fun getTotalWalletValue(@PathVariable userId: Long): Mono<Double> {
        return priceService.getTotalWalletValueInUSD(userId)
    }
}
