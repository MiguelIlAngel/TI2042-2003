package cl.inacap.apps.musica.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MusicAPIAdapter {
    private const val BASE_URL = "https://musica-cba8a-default-rtdb.firebaseio.com/"
    private var musicAPIService: MusicAPIService? = null

    fun getMusicAPIService(): MusicAPIService {
        if (musicAPIService == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            musicAPIService = retrofit.create(MusicAPIService::class.java)
        }
        return musicAPIService!!
    }
}