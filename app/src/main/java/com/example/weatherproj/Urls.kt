package com.example.weatherproj

class Urls {
    companion object {
        var BASE_URL: String = "https://api.openweathermap.org/data/2.5/"
        const val URL_WEATHER : String = "weather?appid=53c6e39cf3ee11a1d7549ffea83d6bd8&units=metric&lang=ru"
        const val MY_PREFS = "myprefs"
        const val MY_WEATHER = "myweather"
        const val DB_NAME = "mydb"
        const val FRAGMENT_CHANGE = "frag_change"

        const val CURRENT_TOWN = "curtown"
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
        const val LOAD_FROM_LOC = "loadfromloc"

        const val TOWN_TABLE = "town"

        const val BOTTOM_NAV_WEATHER_PAGE_ID = R.id.weatherPage


    }
}