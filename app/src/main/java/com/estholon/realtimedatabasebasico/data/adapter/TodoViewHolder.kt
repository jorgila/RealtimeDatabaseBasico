package com.estholon.realtimedatabasebasico.data.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.estholon.realtimedatabasebasico.databinding.RvTodoBinding
import com.estholon.realtimedatabasebasico.domain.entities.Todo

class TodoViewHolder(view: View): RecyclerView.ViewHolder(view){

    private val binding = RvTodoBinding.bind(view)
    fun bind(todoTask: Pair<String, Todo>, onItemSelected: (String) -> Unit) {
        binding.tvTitle.text = todoTask.second.title
        binding.tvDescription.text = todoTask.second.description
        binding.tvReference.text = todoTask.first
        binding.cvTodo.setOnClickListener { onItemSelected(todoTask.first)}
    }

}
