package podolyanchik.alina.reactivesample.notes

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import podolyanchik.alina.reactivesample.R
import podolyanchik.alina.reactivesample.model.Note


class RecyclerViewAdapter(private val notes: MutableList<Note>,private val context: Context) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private  var selected: Pair<Note?, ViewHolder?> = Pair(null,null)

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val mTextTitle: TextView = v.findViewById(R.id.title)
        val mTextDescription: TextView = v.findViewById(R.id.description)
        val cardView: LinearLayout = v.findViewById(R.id.cardview)
    }

    fun updateAllList(noteList: List<Note>) {
        notes.clear()
        notes += noteList
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mTextTitle.text = notes[position].title
        holder.mTextDescription.text = notes[position].description
        holder.itemView.setOnClickListener {
            setSelectedNull()
            selected = Pair(notes[position], holder)
            holder.cardView.background = context.resources.getDrawable(R.color.colorPrimary)
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun getSelected(): Note? {
        return selected.first
    }

    fun setSelectedNull() {
        selected.second?.cardView?.background = context.resources.getDrawable(R.color.colorPrimaryTransparent)
        selected = Pair(null,null)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(v)
    }
}