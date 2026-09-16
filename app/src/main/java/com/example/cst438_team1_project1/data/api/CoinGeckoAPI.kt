package com.example.cst438_team1_project1.data.api

import retrofit2.http.Query
import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.Headers

const val API_KEY = ""
interface CoinGeckoAPI {
    // Retrieves Coins Via Generalized Search
    // API Description: "To search for coins, categories and markets listed on CoinGecko"
    @Headers("x-cg-demo-api-key: $API_KEY")
    @GET("search")
    suspend fun searchBy(
        @Query("query") query: String
    ): JSONObject

    // Retrieves Coin prices via comma separated names
    // Can be 1 or many
    // EX: "Etherium, Bitcoin, Solana, <...>"
    // EX: "Etherium"
    @GET("simple/price")
    suspend fun getCoinPricesByName(
        @Query("names") name: String
    ): JSONObject

    // Retrieves Coin prices via internal ids (kebabcase name)
    // Can be 1 or many
    // EX: "etherium, world-liberty-financial, <...>"
    // EX: "etherium"
    @GET("simple/price")
    suspend fun getCoinPricesByID(
        @Query("ids") ids: String
    ): JSONObject

    // Retrieves Coin prices via Ticker (3-4 letter combo)
    // Can be 1 or many
    // EX: "btc, sol, <...>"
    // EX: "btc"
    @GET("simple/price")
    suspend fun getCoinPricesBySymbol(
        @Query("symbols") symbols: String
    ): JSONObject

}