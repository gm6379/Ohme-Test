package com.georgemcdonnell.biirr

import retrofit2.http.GET
import retrofit2.http.Query


interface BreweryAPI {

    @GET("beers")
    suspend fun getBeers(@Query("key") key: String): Beers
}