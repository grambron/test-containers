package com.badikova.client.dto

import com.badikova.common.dto.BuyStockRequestDto

data class BuyStockUserRequestDto(
    val login: String,
    val id: Long,
    val request: BuyStockRequestDto
)