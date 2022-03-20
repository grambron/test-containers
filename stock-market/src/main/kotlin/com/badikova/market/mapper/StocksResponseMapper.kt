package com.badikova.market.mapper

import com.badikova.common.dto.StocksResponseDto
import com.badikova.market.domain.Stocks

object StocksResponseMapper {
    fun mapToStocksResponse(stocks: Stocks): StocksResponseDto {
        return StocksResponseDto(
            stocks.stocksName,
            stocks.marketPlace,
            stocks.sellPrice.toString(),
            stocks.count
        )
    }
}