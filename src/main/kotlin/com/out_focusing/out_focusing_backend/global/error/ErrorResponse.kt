package com.out_focusing.out_focusing_backend.global.error

import com.fasterxml.jackson.databind.ObjectMapper

class ErrorResponse(
    val code: Int,
    val message: String
) {

    fun convertToJson(): String {
        val mapper = ObjectMapper()
        return mapper.writeValueAsString(this)
    }

}