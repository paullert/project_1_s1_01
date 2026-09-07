package com.example.cst438_team1_project1.data.api

import com.google.gson.annotations.SerializedName

data class ExchangeRatesResponse(
    @SerializedName("data")
    val data: ExchangeRatesData
)

data class ExchangeRatesData(
    @SerializedName("currency")
    val currency: String,
    @SerializedName("rates")
    val rates: Map<String, String>
)