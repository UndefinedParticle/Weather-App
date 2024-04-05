package com.undefinedparticle.weather.apiHelper

import com.undefinedparticle.weather.models.WeatherModel
import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApi {
    // Define the endpoint for fetching the list of users
    @GET("weather")
    fun getWeatherData(@Query("q") cityName: String?, @Query("appid") apiKey: String?, @Query("units") units: String?): Call<WeatherModel?>?;
}