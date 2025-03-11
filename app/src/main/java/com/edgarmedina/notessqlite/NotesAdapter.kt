package com.edgarmedina.notessqlite

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog

//Importamos las librerias necesarias para el adaptador

//Creamos la clase del adaptador y le pasamos la lista de notas y el contexto de la actividad donde se mostrará el adaptador
class NotesAdapter(private var notes: List<Note>, context: Context) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val db: NoteDatabaseHelper = NoteDatabaseHelper(context)

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Obtenemos el TextView de la vista
        val titleTv: TextView = itemView.findViewById(R.id.title_tv)
        val contentTv: TextView = itemView.findViewById(R.id.content_tv)

        //Obtenemos el ImageView de la vista
        val deleteBtn: ImageView = itemView.findViewById(R.id.delete_btn)
        val editBtn: ImageView = itemView.findViewById(R.id.edit_btn)
    }

    //Creamos el metodo onCreateViewHolder para crear los elementos de la vista
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(itemView)
    }

    //Creamos el metodo getItemCount para obtener el número de elementos en la lista
    override fun getItemCount(): Int {
        return notes.size
    }

    //Creamos el metodo onBindViewHolder para mostrar los elementos en la vista
    override fun onBindViewHolder(
        holder: NoteViewHolder,
        position: Int
    ) {
        val currentNote = notes[position]
        holder.titleTv.text = currentNote.title
        holder.contentTv.text = currentNote.content

        //Asignamos los eventos de clic a los botones de eliminar y editar
        holder.editBtn.setOnClickListener {
            //Editar la nota seleccionada
            val intent = Intent(holder.itemView.context, UpdateNoteActivity::class.java).apply {
                putExtra("note_id", currentNote.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteBtn.setOnClickListener {
            //Mostramos el bottom sheet
            val bottomSheet = BottomSheetDialog(holder.itemView.context)
            bottomSheet.setContentView(R.layout.bottom_sheet_delete_note)
            bottomSheet.show()

            //Asignamos los eventos de clic a los botones del bottom sheet
            bottomSheet.findViewById<Button>(R.id.delete_note_btn)?.setOnClickListener {
                //Eliminar la nota seleccionada
                db.deleteNote(currentNote.id)
                refreshData(db.getAllNotes())
                Toast.makeText(holder.itemView.context, "Nota eliminada 😁", Toast.LENGTH_SHORT)
                    .show()
                bottomSheet.dismiss()
            }
        }

    }

    fun refreshData(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }
}