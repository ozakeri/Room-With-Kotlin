package com.example.kotlinroomviewmodel

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.kotlinroomviewmodel.model.Note

class NoteAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<NoteAdapter.CustomView>() {


    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notes: List<Note> = ArrayList()
    private var listener: onItemClickListener? = null


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomView {
        val itemView = inflater.inflate(R.layout.list_item, p0, false)
        return CustomView(itemView)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(itemView: CustomView, position: Int) {
        val note: Note = notes.get(position)

        itemView.textViewTitle.setText(note.title)
        itemView.textViewDesc.setText(note.description)

        var a: Int = note.priority
        var s: String = a.toString()

        itemView.textViewPriority.setText(s)

        itemView.itemView.setOnClickListener {
            if (listener == null && position != RecyclerView.NO_POSITION) {
                listener?.onClick(it,notes.get(position))
            }
        }
    }

    fun setNote(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    fun getNoteAt(position: Int): Note {
        return notes.get(position)
    }

    interface onItemClickListener {
        fun onClick(view: View, note: Note)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        this.listener = listener
    }


    class CustomView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewTitle = itemView.findViewById<TextView>(R.id.textViewTitle)
        var textViewDesc = itemView.findViewById<TextView>(R.id.textViewDesc)
        var textViewPriority = itemView.findViewById<TextView>(R.id.textViewPriority)
    }
}