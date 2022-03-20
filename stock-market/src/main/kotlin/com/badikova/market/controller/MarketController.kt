package com.badikova.market.controller

import com.badikova.common.dto.*
import com.badikova.market.dto.AddStocksRequestDto
import com.badikova.market.dto.UpdateStocksCountRequestDto
import com.badikova.market.dto.UpdateStocksPriceRequestDto
import com.badikova.market.service.MarketService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/market")
class MarketController(@Autowired private val service: MarketService) {
    @PostMapping("/add")
    fun addStocks(
        @RequestHeader("marketId") marketId: String,
        @RequestBody dto: AddStocksRequestDto
    ): Mono<StocksResponseDto> {
        return service.addStocks(dto)
    }

    @PostMapping("/stock/update/count")
    fun updateStocksCount(
        @RequestHeader("marketId") marketId: String,
        @RequestBody dto: UpdateStocksCountRequestDto
    ): Mono<StocksResponseDto> {
        return service.updateStocksCount(dto)
    }

    @PostMapping("/stock/update/price")
    fun updateStocksCount(
        @RequestHeader("marketId") marketId: String,
        @RequestBody dto: UpdateStocksPriceRequestDto
    ): Mono<StocksResponseDto> {
        return service.updateStocksPrice(dto)
    }

    @GetMapping("/stocks")
    fun allStocks(): Flux<StocksResponseDto> {
        return service.getAllStocks()
    }

    @PostMapping("/stocks/buy")
    fun buyStock(@RequestBody dto: BuyStockRequestDto): Mono<PaymentResponseDto> {
        return service.buyStock(dto)
    }

    @PostMapping("/stocks/sell")
    fun sellStocks(@RequestBody dto: UserSellStockRequestDto): Mono<SellReportResponseDto> {
        return service.sellStocks(dto)
    }
}