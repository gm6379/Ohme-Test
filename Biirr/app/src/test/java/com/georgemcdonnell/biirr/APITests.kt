package com.georgemcdonnell.biirr

import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class APITests {

    private val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BreweryAPI::class.java)

    private val sut = BiirrViewModel(api)

    @Test
    fun `should fetch beers correctly given 200 response`() {
        mockWebServer.enqueueResponse("response_success.json", 200)

        runBlocking {
            val actual = sut.getBeers()

            val expected = listOf(
                BeerViewModel(
                    beer = Beer(
                        id = "c4f2KE",
                        name = "'Murican Pilsner",
                        labels = Labels(icon = "https://brewerydb-images.s3.amazonaws.com/beer/c4f2KE/upload_jjKJ7g-icon.png"),
                        style = Style(
                            Category(name = "North American Lager"),
                            description = "Description"
                        ),
                        abv = "5.5",
                        ibu = null
                    )
                )
            )
            assertEquals(expected.toString(), actual.toString())
        }
    }

    @Test(expected = HttpException::class)
    fun `should return error correctly given 401 response`() {
        mockWebServer.enqueueResponse("response_failure.json", 401)

        runBlocking {
            sut.getBeers()
        }
    }


    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

}