package com.furkanozkaya.notes
import android.annotation.TargetApi
import android.content.Intent

import android.os.Build
import android.widget.EditText


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import org.jetbrains.anko.AnkoLogger
import android.view.View.OnFocusChangeListener
import com.furkanozkaya.notes.dataAccess.Note
import com.furkanozkaya.notes.dataAccess.dataAccess
import org.jetbrains.anko.info
import org.jetbrains.anko.toast


class addNote : AppCompatActivity(),AnkoLogger {
    var titlechanged: Boolean = false
    lateinit var content : EditText
    lateinit var title : EditText
    lateinit var dataAccess :dataAccess
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        //creating data access object
        dataAccess= dataAccess(this)

         content= findViewById(R.id.content)
         title  = findViewById(R.id.title)

        run()

    }



    @TargetApi(Build.VERSION_CODES.O)
    fun run()
    {

        content.requestFocus()

        //date.text = LocalDate.now().toString()

        content.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(text: Editable?) {
                if (titlechanged!=true)
                {
                    if (text != null) {
                        if(text.length <=15) {
                            title.setText(text)
                        }
                    }
                }
            }
            override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        /* understand title focused dont change title */
        title.onFocusChangeListener = OnFocusChangeListener { view, hasfocus ->
            if (hasfocus)
            {
                titlechanged=true
            }
        }




    }//end of run

    override fun onBackPressed()
    {
        var result: Int =0

        if(content.text.toString().isEmpty() && title.text.toString().isEmpty())
        {
            toast("Empty Note!!")
        }
        else if(title.text.toString().isEmpty())
        {
            result = dataAccess.addNote(Note(title="Secret Note",content = content.text.toString()))
        }
        else
        {
            result=dataAccess.addNote(Note(title=title.text.toString(),content = content.text.toString()))
        }

        toast("Result of Add"+result.toString())
        val intent =Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

}
