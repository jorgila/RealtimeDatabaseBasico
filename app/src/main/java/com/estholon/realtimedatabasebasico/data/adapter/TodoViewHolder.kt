package com.estholon.realtimedatabasebasico.data.adapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.estholon.realtimedatabasebasico.R
import com.estholon.realtimedatabasebasico.databinding.RvTodoBinding
import com.estholon.realtimedatabasebasico.domain.entities.Actions
import com.estholon.realtimedatabasebasico.domain.entities.Todo

class TodoViewHolder(view: View): RecyclerView.ViewHolder(view){

    private val binding = RvTodoBinding.bind(view)
    fun bind(todoTask: Pair<String, Todo>, onItemSelected: (Actions, String) -> Unit) {
        binding.tvTitle.text = todoTask.second.title
        binding.tvDescription.text = todoTask.second.description
        binding.tvReference.text = todoTask.first
        binding.ivDelete.setOnClickListener { onItemSelected(Actions.DELETE,todoTask.first) }
        binding.ivDone.setOnClickListener { onItemSelected(Actions.DONE,todoTask.first) }
        val color = if(todoTask.second.done == true){
            R.color.gold
        } else {
            R.color.purple_200
        }
        binding.cvTodo.strokeColor = ContextCompat.getColor(binding.cvTodo.context,color)
    }

}
