package cl.inacap.apps.musica

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import cl.inacap.apps.musica.api.MusicAPIAdapter
import cl.inacap.apps.musica.models.Song
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var editTextTitle: EditText
    private lateinit var editTextArtist: EditText
    private lateinit var editTextGenre: EditText
    private lateinit var editTextDuration: EditText
    private lateinit var btnAgregarCancion: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar vistas
        editTextTitle = findViewById(R.id.editTextTitle)
        editTextArtist = findViewById(R.id.editTextArtist)
        editTextGenre = findViewById(R.id.editTextGenre)
        editTextDuration = findViewById(R.id.editTextDuration)
        btnAgregarCancion = findViewById(R.id.btnAgregarCancion)

        // Asignar un listener al botón para agregar una canción
        btnAgregarCancion.setOnClickListener {
            agregarNuevaCancion()
        }

        // Obtener todas las canciones al inicio
        getAllSongs()
    }

    private fun getAllSongs() {
        val musicService = MusicAPIAdapter.getMusicAPIService()

        musicService.getAllSongs().enqueue(object : Callback<List<Song>> {
            override fun onResponse(call: Call<List<Song>>, response: Response<List<Song>>) {
                if (response.isSuccessful) {
                    val songs: List<Song>? = response.body()
                    // Manejo de la lista de canciones obtenidas
                } else {
                    // Manejo de errores en caso de respuesta no exitosa
                }
            }

            override fun onFailure(call: Call<List<Song>>, t: Throwable) {
                // Manejo de errores en caso de falla en la llamada
            }
        })
    }

    private fun agregarNuevaCancion() {
        val title = editTextTitle.text.toString()
        val artist = editTextArtist.text.toString()
        val genre = editTextGenre.text.toString()
        val duration = editTextDuration.text.toString()

        val newSong = Song(title, artist, genre, duration) // Crear un objeto Song con los datos ingresados por el usuario

        val musicService = MusicAPIAdapter.getMusicAPIService()

        musicService.addSong(newSong).enqueue(object : Callback<Song> {
            override fun onResponse(call: Call<Song>, response: Response<Song>) {
                if (response.isSuccessful) {
                    // La canción se agregó exitosamente
                } else {
                    // Manejo de errores en caso de respuesta no exitosa
                }
            }

            override fun onFailure(call: Call<Song>, t: Throwable) {
                // Manejo de errores en caso de falla en la llamada
            }
        })
    }
}