package com.kode.remindme.UI

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kode.remindme.databinding.NotesrecyclerBinding

class HomeRecyclerView(val a: MainViewModel) :
    RecyclerView.Adapter<HomeRecyclerView.HomeViewHolder>() {
    val vm = a

    inner class HomeViewHolder(val binding: NotesrecyclerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            NotesrecyclerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {

        holder.binding.tvHeading.text = vm.allNotes.get(position).heading
        holder.binding.tvDescription.text = vm.allNotes.get(position).mainContent
        holder.binding.btnDeleteNote.setOnClickListener {
            vm.deleteNote(vm.allNotes.get(position).location,vm.allNotes.get(position).marketID)
            vm.getNotes()
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return vm.allNotes.size
    }
}