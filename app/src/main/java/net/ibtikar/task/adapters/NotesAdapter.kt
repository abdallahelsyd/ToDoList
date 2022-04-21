package net.ibtikar.task.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.note_item.view.*
import net.ibtikar.task.R
import net.ibtikar.task.data.local.Note
import net.ibtikar.task.ui.fragments.listFragment.NotesListFragmentDirections
import javax.inject.Inject

class NotesAdapter @Inject constructor() : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)
    var notesList: List<Note>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.note_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = notesList[position]
        holder.itemView.apply {
            titleItemXML.text = note.title
            descItemXML.text = note.discription
            dateItemXML.text = note.date
        }
        holder.itemView.setOnClickListener {
            var b = Bundle()
            b.putInt("id", (note.id) ?: -1)
            b.putString("title", note.title)
            b.putString("desc", note.discription)
            b.putString("date", note.date)
            val action =
                NotesListFragmentDirections.actionNotesListFragmentToAddFragment(notesList[position])
            holder.itemView.findNavController().navigate(action)
        }
    }
}