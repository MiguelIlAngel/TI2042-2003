package cl.inacap.apps.indicadores.api.response

import com.google.gson.annotations.SerializedName

data class IndicadorResponse(
    @SerializedName("codigo") var codigo:String,
    @SerializedName("nombre") var nombre:String,
    @SerializedName("unidad_medida") var unidadMedida:String,
    @SerializedName("serie") var serie:List<Map<String,String>>
)


