package com.example.weatherapp.retrofit
import com.example.weatherapp.retrofit.data.remoteResult
import okhttp3.Interceptor
import okhttp3.HttpUrl
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
interface openWeatherMapAPI {
    @GET("weather")
    suspend fun getWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String
    ):remoteResult
}

object RetrofitOpenWeatherAPI{
    private const val API_KEY = "7c898580f323d9df5483f809cc937e69"
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    fun makeRetrofitOpenWeatherAPI():openWeatherMapAPI{
        val interceptor = Interceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url()


            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("appid", API_KEY)
                .build()

            val requestBuilder = original.newBuilder().url(url)
            val request = requestBuilder.build()
            chain.proceed(request)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(openWeatherMapAPI::class.java)
    }
}