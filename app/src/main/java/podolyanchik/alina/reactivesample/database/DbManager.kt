package podolyanchik.alina.reactivesample.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import podolyanchik.alina.reactivesample.model.Note


class DbManager(context: Context) : SQLiteOpenHelper(context, SQLite_NAME, null, DATABASE_VERSION) {

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        val sql = "CREATE TABLE $TABLE_NAME (ID INTEGER PRIMARY KEY, TITLE TEXT, DESCRIPTION TEXT)"
        sqLiteDatabase.execSQL(sql)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(sqLiteDatabase)
    }


    fun selectAll(): List<Note> {
        val sqLiteDatabase = readableDatabase
        val cursor = sqLiteDatabase.query(TABLE_NAME, arrayOf("ID", "TITLE", "DESCRIPTION"), null, null, null, null, "ID")
        val movies = ArrayList<Note>()
        while (cursor.moveToNext()) {
            val movie = Note()
            movie.id = cursor.getInt(0)
            movie.title = cursor.getString(1)
            movie.description = cursor.getString(2)
            movies.add(movie)
        }
        cursor.close()
        return movies
    }

    fun updateTask(note: Note): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("ID", note.id)
        values.put("TITLE", note.title)
        values.put("DESCRIPTION", note.description)
        val _success = db.update(TABLE_NAME, values, "ID" + "=?", arrayOf(note.id.toString()))
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun delete(note: Note): Boolean{
        val sqLiteDatabase = writableDatabase
        val _success = sqLiteDatabase.delete(TABLE_NAME, "ID" + "=?", arrayOf(note.id.toString()))
        sqLiteDatabase.close()
        return Integer.parseInt("$_success") != -1
    }


    fun insert(note: Note) {
        val cv = ContentValues()

        cv.put("TITLE", note.title)
        cv.put("DESCRIPTION", note.description)
        val sqLiteDatabase = writableDatabase
        sqLiteDatabase.insert(TABLE_NAME, null, cv)
        sqLiteDatabase.close()
    }

    companion object {

        private val SQLite_NAME = "AndroidRESTSQLite.db"
        private val DATABASE_VERSION = 1
        private val TABLE_NAME = "Note"
    }
}