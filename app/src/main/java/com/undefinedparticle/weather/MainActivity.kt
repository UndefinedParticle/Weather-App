package com.undefinedparticle.weather

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import com.undefinedparticle.weather.apiHelper.RetrofitService
import com.undefinedparticle.weather.apiHelper.WeatherApi
import com.undefinedparticle.weather.databinding.ActivityMainBinding
import com.undefinedparticle.weather.models.WeatherModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var weatherApi: WeatherApi
    private var cityName:String = ""
    lateinit var weatherModel: WeatherModel
    private lateinit var sharedPreferences: SharedPreferences
    var sunRise: String = ""
    var sunSet: String = ""
    private var today: String = ""
    private var curDate: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.lifecycleOwner = this

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        cityName = sharedPreferences.getString("cityName","India").toString()

        weatherModel = WeatherModel()
        weatherApi = RetrofitService.createService(WeatherApi::class.java)

        binding.model = weatherModel
        binding.cityName = cityName

        fetchWeatherData(cityName)
        getTodayDayAndDate()
        searchHere()

    }

    private fun searchHere(){

        binding.searchText.setOnEditorActionListener(TextView.OnEditorActionListener { textView, actionId, event ->


            try {

                if(actionId == EditorInfo.IME_ACTION_SEARCH
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || actionId == EditorInfo.IME_ACTION_NONE
                    || event.action == KeyEvent.ACTION_DOWN
                    || actionId == EditorInfo.IME_ACTION_UNSPECIFIED
                    && event.keyCode == KeyEvent.KEYCODE_ENTER){

                    if(textView.text.toString() != ""){
                        fetchWeatherData(textView.text.toString().trim())
                    }
                    removeKeyboard()

                    return@OnEditorActionListener true
                }

            }catch (e: NullPointerException){
                e.printStackTrace()

                if(textView.text.toString() != ""){
                    fetchWeatherData(textView.text.toString())
                }
                removeKeyboard()
            }

            false
        })

    }

    private fun saveCityNameToPreferences(cityName: String) {

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = preferences.edit()
        editor.putString("cityName",cityName)
        editor.apply()

    }

    private fun fetchWeatherData(cityName: String){

        weatherApi.getWeatherData(cityName,BaseClass.API_KEY,BaseClass.UNITS)
            ?.enqueue(object : Callback<WeatherModel?> {
                override fun onResponse(call: Call<WeatherModel?>, response: Response<WeatherModel?>) {
                    Log.e("getWeatherData", "response: " + response + " msg: " + response.message())

                    if(response.isSuccessful){
                        if(response.body() != null){
                            weatherModel = response.body()!!

                            binding.cityName = cityName;
                            binding.model = weatherModel;

//                            sunRise = formatTime(weatherModel.getSys().getSunrise());
//                            sunSet = formatTime(weatherModel.getSys().getSunset());
                            sunRise = weatherModel.sys?.let { formatTime(it.sunrise) }.toString()
                            sunSet = weatherModel.sys?.let { formatTime(it.sunset) }.toString()

                            binding.sunrise.text = sunRise;
                            binding.sunset.text = sunSet;

                            if (weatherModel.weather?.isNotEmpty() == true) {
                                binding.todaysWeather.text = weatherModel.weather!![0].main
                                binding.sunny.text = weatherModel.weather!![0].main
                            }

                            saveCityNameToPreferences(cityName)

                        }
                    }

                }

                override fun onFailure(call: Call<WeatherModel?>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })


    }

    private fun formatTime(timestamp: Long): String{

        try {
            // Convert timestamp to milliseconds
            val milliseconds = timestamp * 1000

            // Create a SimpleDateFormat object with the desired format
            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())

            // Create a Date object using the milliseconds
            val date = Date(milliseconds)

            // Format the date and return as a string
            return sdf.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            return "" // Handle the exception as needed
        }

    }

    private fun getTodayDayAndDate(){

        try {
            val currentDate = Date()

            val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
            val dateFormat = SimpleDateFormat("dd:MM:yyyy", Locale.getDefault())

            today = dayFormat.format(currentDate)
            curDate = dateFormat.format(currentDate)

            binding.weekDay.text = today
            binding.todaysDate.text = curDate
        }catch (e: Exception){
            e.printStackTrace()
        }

    }

    private fun removeKeyboard(){

        binding.searchText.clearFocus()

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        imm.hideSoftInputFromWindow(binding.root.windowToken,0)

    }

}