package cl.inacap.apps.musica.api

import retrofit2.Call
import retrofit2.http.*
import cl.inacap.apps.musica.models.Song


interface MusicAPIService {
    @GET("canciones.json")
    fun getAllSongs(): Call<List<Song>>

    @POST("canciones.json")
    fun addSong(@Body song: Song): Call<Song>
}