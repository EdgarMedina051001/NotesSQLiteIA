package com.edgarmedina.notessqlite

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.edgarmedina.notessqlite.databinding.ActivityUpdateNoteBinding

class UpdateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateNoteBinding
    private lateinit var db: NoteDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteDatabaseHelper(this) //Inicializamos la base de datos

        //Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.navigationIcon?.setTint(ContextCompat.getColor(this, R.color.white))

        val noteId = intent.getIntExtra("note_id", 0) //Obtenemos el id de la nota a editar
        val note = db.getNoteById(noteId) //Obtenemos el objeto de la nota a editar
        binding.titleTiet.setText(note.title) //Asignamos el título de la nota a editar
        binding.contentTiet.setText(note.content) //Asignamos el contenido de la nota a editar

        binding.editNoteBtn.setOnClickListener {

            try {
                val title = binding.titleTiet.text.toString() //Obtenemos el texto de los campos de texto
                val content = binding.contentTiet.text.toString()
                val noteId = intent.getIntExtra("note_id", 0) //Obtenemos el id de la nota a editar
                val note = Note(noteId, title, content) //Creamos un objeto de la clase Note
                db.updateNote(note) //Actualizamos el objeto en la base de datos
                finish() //Cerramos la actividad
                Toast.makeText(this, "Nota actualizada 😁", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Error al actualizar la nota 🙁", Toast.LENGTH_SHORT).show()
                Log.d("Error", "UpdateActivity: ${e.message} ")
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