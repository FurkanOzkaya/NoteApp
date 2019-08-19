package com.furkanozkaya.notes.Adapter

import android.content.Context
import android.content.Intent
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.furkanozkaya.notes.R
import com.furkanozkaya.notes.dataAccess.Note
import com.furkanozkaya.notes.updateNote
import org.jetbrains.anko.AnkoLogger

class noteListAdapter(val notelist: List<Note>,val context:Context) : RecyclerView.Adapter<noteListAdapter.ModelViewHolder>(),AnkoLogger
{

    class ModelViewHolder(view:View) : RecyclerView.ViewHolder(view)
    {
       // val id:TextView=view.findViewById(R.id.Recyleview_id)
        val title:TextView=view.findViewById(R.id.Recyleview_title)
        //val content:TextView=view.findViewById(R.id.Recyleview_content)





        fun binditems(items : Note,context: Context)
        {
            //id.setText(items.id.toString())
            title.setText(items.title)
           // content.setText(items.content)i


            itemView.setOnClickListener{
                var intent=Intent(context,updateNote::class.java)
                intent.putExtra("id",items.id)
                intent.putExtra("title",items.title)
                intent.putExtra("content",items.content)
                context.startActivity(intent)
            }
        }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.note_recylerview,parent,false)

        return  ModelViewHolder(view)
    }
    override fun getItemCount(): Int {
     return notelist.size
    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        val context:Context=holder.title.context
       holder.binditems(notelist[position],context)
    }


}