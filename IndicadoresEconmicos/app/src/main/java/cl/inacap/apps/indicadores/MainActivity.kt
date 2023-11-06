package cl.inacap.apps.indicadores

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import cl.inacap.apps.indicadores.api.IndicadoresApiAdapter
import cl.inacap.apps.indicadores.api.response.IndicadorResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    lateinit var dolarField: EditText
    lateinit var euroField: EditText
    lateinit var ufField: EditText
    lateinit var utmField: EditText

    private var indicadoresApi = IndicadoresApiAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dolarField = findViewById(R.id.dolar_field)
        euroField = findViewById(R.id.euro_field)
        ufField = findViewById(R.id.uf_field)
        utmField = findViewById(R.id.utm_field)

        runBlocking {
            showIndicador("dolar", dolarField)
            showIndicador("euro", euroField)
            showIndicador("uf", ufField)
            showIndicador("utm", utmField)

        }
    }

    private suspend fun showIndicador(tipo: String, field: EditText) {
        val indicadorResponse = GlobalScope.async {
            indicadoresApi.getIndicador("tipo", "03-11-2023")
        }
        val indicador = indicadorResponse.await()
        val unidadMedida = indicador?.unidadMedida
        val valor = indicador?.serie?.get(0)?.get("valor")
        field.setText("${valor} ${unidadMedida}")
    }
}