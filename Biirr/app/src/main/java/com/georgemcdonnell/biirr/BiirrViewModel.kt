package com.georgemcdonnell.biirr

class BiirrViewModel(
    private val api: BreweryAPI = ServiceBuilder.buildService(BreweryAPI::class.java)
) {

    private val apiKey = "13d7fdca22cbc95434f3f65d7be4a5a9"

    suspend fun getBeers(): List<BeerViewModel> {
        return api.getBeers(apiKey).data.map { BeerViewModel(it) }
    }

}