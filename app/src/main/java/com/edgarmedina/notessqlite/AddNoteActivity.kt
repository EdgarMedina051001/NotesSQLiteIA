package com.edgarmedina.notessqlite

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.edgarmedina.notessqlite.databinding.ActivityAddNoteBinding

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var db: NoteDatabaseHelper //Llamamos a la base de datos creada en la clase NoteDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.navigationIcon?.setTint(ContextCompat.getColor(this, R.color.white))

        db = NoteDatabaseHelper(this) //Inicializamos la base de datos

        binding.saveBtn.setOnClickListener {
            try {
                val title =
                    binding.titleTiet.text.toString() //Obtenemos el texto de los campos de texto
                val content = binding.contentTiet.text.toString()
                val note = Note(0, title, content) //Creamos un objeto de la clase Note
                db.insertNote(note) //Insertamos el objeto en la base de datos
                finish() //Cerramos la actividad
                Toast.makeText(this, "¡Nota Guadada! 😁", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Error al guardar la nota 🙁", Toast.LENGTH_SHORT).show()
                Log.d("Error", "AddNoteActivity: ${e.message} ")
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}