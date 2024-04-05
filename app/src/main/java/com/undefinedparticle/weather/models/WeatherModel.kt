package com.undefinedparticle.weather.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WeatherModel(
    @Expose
    @SerializedName("cod")
    var cod: Double = 0.0,
    @Expose
    @SerializedName("name")
    var name: String = "",
    @Expose
    @SerializedName("id")
    var id: Double = 0.0,
    @Expose
    @SerializedName("timezone")
    var timezone: Double = 0.0,
    @Expose
    @SerializedName("sys")
    var sys: SysEntity? = null,
    @Expose
    @SerializedName("dt")
    var dt: Double = 0.0,
    @Expose
    @SerializedName("clouds")
    var clouds: CloudsEntity? = null,
    @Expose
    @SerializedName("wind")
    var wind: WindEntity? = null,
    @Expose
    @SerializedName("visibility")
    var visibility: Double = 0.0,
    @Expose
    @SerializedName("main")
    var main: MainEntity? = null,
    @Expose
    @SerializedName("base")
    var base: String = "",
    @Expose
    @SerializedName("weather")
    var weather: List<WeatherEntity>? = null,
    @Expose
    @SerializedName("coord")
    var coord: CoordEntity? = null
) : Serializable {

    data class SysEntity(
        @Expose
        @SerializedName("sunset")
        var sunset: Long = 0,
        @Expose
        @SerializedName("sunrise")
        var sunrise: Long = 0,
        @Expose
        @SerializedName("country")
        var country: String = "",
        @Expose
        @SerializedName("id")
        var id: Double = 0.0,
        @Expose
        @SerializedName("type")
        var type: Double = 0.0
    ) : Serializable

    data class CloudsEntity(
        @Expose
        @SerializedName("all")
        var all: Double = 0.0
    ) : Serializable

    data class WindEntity(
        @Expose
        @SerializedName("deg")
        var deg: Double = 0.0,
        @Expose
        @SerializedName("speed")
        var speed: Double = 0.0
    ) : Serializable

    data class MainEntity(
        @Expose
        @SerializedName("humidity")
        var humidity: Double = 0.0,
        @Expose
        @SerializedName("pressure")
        var pressure: Double = 0.0,
        @Expose
        @SerializedName("temp_max")
        var tempMax: Double = 0.0,
        @Expose
        @SerializedName("temp_min")
        var tempMin: Double = 0.0,
        @Expose
        @SerializedName("feels_like")
        var feelsLike: Double = 0.0,
        @Expose
        @SerializedName("temp")
        var temp: Double = 0.0
    ) : Serializable

    data class WeatherEntity(
        @Expose
        @SerializedName("icon")
        var icon: String = "",
        @Expose
        @SerializedName("description")
        var description: String = "",
        @Expose
        @SerializedName("main")
        var main: String = "",
        @Expose
        @SerializedName("id")
        var id: Double = 0.0
    ) : Serializable

    data class CoordEntity(
        @Expose
        @SerializedName("lat")
        var lat: Double = 0.0,
        @Expose
        @SerializedName("lon")
        var lon: Double = 0.0
    ) : Serializable
}
