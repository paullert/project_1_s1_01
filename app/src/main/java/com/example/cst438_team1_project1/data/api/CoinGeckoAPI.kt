package com.example.cst438_team1_project1.data.api

import com.example.cst438_team1_project1.data.api.api_responses.SearchCoinsResponse
import retrofit2.http.Query
import retrofit2.http.GET
import retrofit2.http.Header

interface CoinGeckoAPI {
    // Retrieves Coins Via Generalized Search
    // API Description: "To search for coins, categories and markets listed on CoinGecko"
    @GET("search")
    suspend fun searchCoins(
        @Query("query") query: String,
        @Header("x-cg-demo-api-key") apiKey: String
    ): SearchCoinsResponse

    // Retrieves Coin prices via comma separated names
    // Can be 1 or many, no spaces
    // EX: "Etherium,Bitcoin,Solana,<...>"
    // EX: "Etherium"
    @GET("simple/price")
    suspend fun getCoinPricesByName(
        @Query("names") name: String
    ): SearchCoinsResponse

    // Retrieves Coin prices via internal ids (kebabcase name)
    // Can be 1 or many, no spaces
    // EX: "etherium,world-liberty-financial,<...>"
    // EX: "etherium"
    @GET("simple/price")
    suspend fun getCoinPricesByID(
        @Query("ids") ids: String,
        @Query("vs_currencies") vsCurrencies: String,
        @Header("x-cg-demo-api-key") apiKey: String
    ): Map<String, Map<String, Double>>

    // Retrieves Coin prices via Ticker (3-4 letter combo)
    // Can be 1 or many, no spaces
    // EX: "btc,sol,<...>"
    // EX: "btc"
    @GET("simple/price")
    suspend fun getCoinPricesBySymbol(
        @Query("symbols") symbols: String
    ): SearchCoinsResponse

}
