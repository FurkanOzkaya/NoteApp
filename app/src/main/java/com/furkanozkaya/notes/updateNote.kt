package com.furkanozkaya.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.furkanozkaya.notes.dataAccess.Note
import com.furkanozkaya.notes.dataAccess.dataAccess
import org.jetbrains.anko.toast

class updateNote : AppCompatActivity() {

    var titlechanged: Boolean = false
    lateinit var content: EditText
    lateinit var title: EditText
    lateinit var dataAccess: dataAccess
    var id : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_note)

        // initialized values
        dataAccess =dataAccess(this)
        id=intent.getIntExtra("id",0)

        content = findViewById(R.id.content)
        title = findViewById(R.id.title)

        title.setText(intent.getStringExtra("title").toString())
        content.setText(intent.getStringExtra("content").toString())
    }




    override fun onBackPressed()
    {
        var result: Int =0

        if(content.text.toString().isEmpty() && title.text.toString().isEmpty())
        {
            toast("Empty Note!!")
        }
        else if(title.text.toString().isEmpty())
        {
            result = dataAccess.updateNote(Note(id=id ,title="Secret Note",content = content.text.toString()))
        }
        else
        {
            result=dataAccess.updateNote(Note(id=id,title=title.text.toString(),content = content.text.toString()))
        }

        toast("Result of Add"+result.toString())
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}
