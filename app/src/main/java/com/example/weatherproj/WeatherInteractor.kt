package com.example.weatherproj

class WeatherInteractor(weatherRepository: WeatherRepository) : WeatherMvpInteractor {

    private var mWeatherRepository : WeatherRepository = weatherRepository

    override fun changeTown(town: String) {
        mWeatherRepository.setWeatherTown(town)
    }

    override fun getUrl() : String? {
        return mWeatherRepository.getWeatherUrl()
    }
}