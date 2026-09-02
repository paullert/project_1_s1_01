package com.example.cst438_team1_project1.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface CoinbaseApi {
    @GET("exchange-rates")
    suspend fun getExchangeRates(
        @Query("currency") currency: String = "USD"
    ): ExchangeRatesResponse
}