package com.example.note_frontend.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.note_frontend.databinding.LayoutNoteBinding
import com.example.note_frontend.model.Note

class NoteAdapter(val onItemClickListener: OnItemClickListener) :
    ListAdapter<Note, NoteAdapter.ViewHolder>(NotesComparator()), Filterable {

    private var noteList = mutableListOf<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutNoteBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    fun setData(noteList: List<Note>) {
        if (this.noteList.isEmpty())
            this.noteList = noteList as MutableList<Note>
        submitList(noteList)
        Log.d("sdfsdf", "setData: ")
    }

    inner class ViewHolder(private val binding: LayoutNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val note = getItem(position)
            binding.cardView.setCardBackgroundColor(
                binding.root.resources.getColor(
                    note.color, null
                )
            )

            binding.cardView.setOnClickListener {
                onItemClickListener.onItemClick(it, note)
            }

            binding.cardView.setOnLongClickListener{
                onItemClickListener.onItemLongClick(it, note)
                return@setOnLongClickListener true
            }

            binding.tvTitle.text = note.title
            binding.tvTitleDescription.text = note.description
            binding.tvTime.text = note.time
        }
    }

    class NotesComparator : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, note: Note?)
        fun onItemLongClick(view: View, note: Note?)
    }

    override fun getFilter(): Filter {
        return customFilter
    }

    private val customFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = mutableListOf<Note>()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(noteList)
                Log.d("sdfsdf", "performFiltering: ")
                Log.d("sdfsdf", "noteList size: " + noteList.size)
            } else {
                for (item in noteList) {
                    if (item.title.toLowerCase().contains(
                            constraint.toString().toLowerCase()
                        ) || item.description.toLowerCase()
                            .contains(constraint.toString().toLowerCase())
                    ) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
            submitList(filterResults?.values as MutableList<Note>)
        }

    }
}