package div.dablank.hackethonproject

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



object RetrofitInstance {
    private const val BASE_URL = "https://maps.googleapis.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val geocodingApiService: GeocodingApiService = retrofit.create(GeocodingApiService::class.java)
}
