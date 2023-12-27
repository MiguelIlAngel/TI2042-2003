package cl.inacap.apps.humidificador

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var humidityValue: Int = 50  // Porcentaje de humedad actual
    private var deviceStatus: Int = 0    // Estado del humidificador (0 es apagado)
    private lateinit var mqttClientHelper: MqttClientHelper
    private lateinit var humidityTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mqttClientHelper = MqttClientHelper(applicationContext)
        humidityTextView = findViewById(R.id.humidityTextView)

        val buttonStart: Button = findViewById(R.id.buttonStart)
        val buttonStop: Button = findViewById(R.id.buttonStop)
        val buttonOff: Button = findViewById(R.id.buttonOff)

        buttonStart.setOnClickListener {
            deviceStatus = 1 // Cuando está humidificando (aumenta la humedad)
        }

        buttonStop.setOnClickListener {
            deviceStatus = -1 // Cuando está deshumidificando (desciende la humedad)
        }

        buttonOff.setOnClickListener {
            deviceStatus = 0 // Cuando está apagado
        }

        GlobalScope.launch(context = Dispatchers.Main) {
            deviceOperation(1000)
        }
    }

    private suspend fun deviceOperation(sleepTime: Long) {
        while (true) {
            humidityValue += 5 * deviceStatus
            if (humidityValue > 100) humidityValue = 100
            else if (humidityValue < 0) humidityValue = 0

            val humidityStatus: String = when {
                humidityValue < 15 -> "RED-"
                humidityValue < 30 -> "YELLOW-"
                humidityValue < 65 -> "GREEN"
                humidityValue < 75 -> "YELLOW+"
                else -> "RED+"
            }

            mqttClientHelper.publishMessage(MqttClientHelper.SENSOR_TOPIC, humidityStatus)
            delay(sleepTime)
        }
    }
}
