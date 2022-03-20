package com.badikova.common.dto

data class BaseResponse(val success: Boolean, val error: Error? = null)