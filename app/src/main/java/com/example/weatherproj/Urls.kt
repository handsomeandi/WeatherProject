package com.example.weatherproj

class Urls {
    companion object {
        public var BASE_URL: String = "https://api.openweathermap.org/data/2.5/"
        public var URL_BY_NAME : String = "weather?appid=53c6e39cf3ee11a1d7549ffea83d6bd8&units=metric&lang=ru"
        public var URL_BY_COORD : String = "weather?appid=53c6e39cf3ee11a1d7549ffea83d6bd8&units=metric&lang=ru"


        public const val MY_PREFS = "myprefs"
        public const val MY_WEATHER = "myweather"
        public const val DB_NAME = "mydb"
        public const val FRAGMENT_CHANGE = "frag_change"

        public const val BOTTOM_NAV_WEATHER_PAGE_ID = R.id.weatherPage
        public const val BOTTOM_NAV_TOWNS_PAGE_ID = R.id.townsPage
        public const val BOTTOM_NAV_INFO_PAGE_ID = R.id.infoPage
    }
}