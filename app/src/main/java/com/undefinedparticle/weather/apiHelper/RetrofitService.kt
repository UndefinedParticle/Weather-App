package com.undefinedparticle.weather.apiHelper

import com.google.gson.GsonBuilder
import com.undefinedparticle.weather.BaseClass
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BaseClass.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
        .build()

    inline fun <reified T> createService(java: Class<T>): T {
        return retrofit.create(T::class.java)
    }

}