package cl.inacap.apps.indicadores.api

import cl.inacap.apps.indicadores.api.response.IndicadorResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IndicadoresApi {
    @GET("{tipo}/{fecha}")
    fun getIndicador(
        @Path("tipo") tipo:String,
        @Path("fecha") fecha:String
    ): Call<IndicadorResponse>
}