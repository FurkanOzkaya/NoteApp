package com.furkanozkaya.notes.dataAccess

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.verbose



class dataAccess(context: Context):SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSİON),AnkoLogger
{
    companion object
    {
        val DATABASE_VERSİON = 1
        val DATABASE_NAME = "noteApp.db"
    }

    private val table_name="notes"
    private val col_id="id"
    private val col_title="title"
    private val col_content="content"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $table_name")
        onCreate(db)
    }

    fun addNote(note:Note): Int
    {
        val db=this.writableDatabase
        val contentvalues=ContentValues()
        contentvalues.put(col_title,note.title)
        contentvalues.put(col_content,note.content)

        val result=db.insert(table_name,null,contentvalues)
        db.close()
        return result.toInt()
    }

    val allNotes : List<Note>
        get()  {
            val notelist= ArrayList<Note>()
            val Query = "SELECT * FROM $table_name"
            val db=this.writableDatabase
            val cursor=db.rawQuery(Query,null)
            if(cursor !=null)
            {
                if (cursor.moveToFirst())
                {
                    val id_index=cursor.getColumnIndex(col_id)
                    val title_index= cursor.getColumnIndex(col_title)
                    val content_index= cursor.getColumnIndex(col_content)
                    do
                    {
                        var note=Note()
                        note.id=cursor.getInt(id_index)
                        note.title= cursor.getString(title_index)
                        note.content= cursor.getString(content_index)

                        notelist.add(note)
                    }while (cursor.moveToNext())
                }
            }
            db.close()
            return notelist
        }

    fun updateNote(note:Note): Int
    {
        val db=this.writableDatabase
        val values=ContentValues()
        values.put(col_title,note.title)
        values.put(col_content,note.content)

        val result=db.update(table_name,values,"$col_id=?", arrayOf(note.id.toString()))
        db.close()
        return result
    }

    fun deleteNote (note:Note): Int
    {
        val db=this.writableDatabase

        val result=db.delete(table_name,"$col_id", arrayOf(note.id.toString()))
        db.close()
        return result
    }




    private val SQL_CREATE_ENTRY=
        "CREATE TABLE IF NOT EXISTS ${table_name} (" +
                "$col_id INTEGER PRIMARY KEY ," +
                "$col_title VARCHAR(50)  NOT NULL," +
                "$col_content TEXT NOT NULL"+
                ")"
}//end fof class

data class Note(var id:Int =0 , var title :String="", var content:String = "")