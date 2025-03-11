package com.edgarmedina.notessqlite

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.edgarmedina.notessqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: NoteDatabaseHelper //Llamamos a la base de datos creada en la clase NoteDatabaseHelper
    private lateinit var notesAdapter: NotesAdapter //Llamamos al adaptador de notas creado en la clase NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteDatabaseHelper(this) //Inicializamos la base de datos
        notesAdapter = NotesAdapter(db.getAllNotes(), this) //Inicializamos el adaptador de notas

        binding.notesRv.layoutManager = LinearLayoutManager(this) //Asignamos el adaptador al RecyclerView
        binding.notesRv.adapter = notesAdapter //Asignamos el adaptador al RecyclerView

        binding.addNoteBtn.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }

        binding.geminiIv.setOnClickListener {
            val intent = Intent(this, GeminiActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        notesAdapter.refreshData(db.getAllNotes()) //Actualizamos el adaptador cuando se reactiva la aplicación
    }
}