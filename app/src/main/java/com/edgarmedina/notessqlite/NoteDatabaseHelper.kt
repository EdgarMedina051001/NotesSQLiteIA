package com.edgarmedina.notessqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NoteDatabaseHelper (context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    //Creamos las constantes de la base de datos y las tablas con sus campos
    companion object{
        private const val DATABASE_NAME = "notesapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allNotes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"

    }

    //Creamos la tabla con sus campos
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_CONTENT TEXT)"
        db?.execSQL(createTableQuery)
    }
    //Actualizamos la base de datos
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }
    //Creamos la funcion para insertar los datos en la tabla de la base de datos
    fun insertNote(note: Note){
        val db = writableDatabase
        val values = ContentValues().apply{
            put(COLUMN_TITLE, note.title)
            put(COLUMN_CONTENT, note.content)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    //Creamos la funcion para obtener todos los datos de la tabla de la base de datos
    fun getAllNotes(): List<Note>{
        val db = writableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val notes = mutableListOf<Note>()
        while (cursor.moveToNext()){
            val note = Note(cursor.getInt(0), cursor.getString(1), cursor.getString(2))
            notes.add(note)
        }
        cursor.close()
        db.close()
        return notes
    }

    //Creamos la funcion para obtener un dato de la tabla de la base de datos
    fun getNoteById(noteId: Int): Note{
        val db = writableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

        cursor.close()
        db.close()
        return Note(id, title, content)
    }

    //Creamos la funcion para actualizar los datos de una nota en la tabla de la base de datos
    fun updateNote(note: Note){
        val db = writableDatabase
        val values = ContentValues().apply{
            put(COLUMN_TITLE, note.title)
            put(COLUMN_CONTENT, note.content)
        }
        db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(note.id.toString()))
        db.close()
    }

    fun deleteNote(noteId: Int){
        val db = writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(noteId.toString()))
        db.close()
    }
}