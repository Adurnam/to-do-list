package com.example.todolist.screens.list.adapter

import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.database.Task
import com.example.todolist.screens.list.ListViewModel

class TaskAdapter(nc: NavController, viewModel: ListViewModel): RecyclerView.Adapter<TaskItemViewHolder>() {
    var navController = nc
    var viewModel = viewModel
    var data =  listOf<Task>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        val item = data[position]
        holder.bindTask(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {
        val view = parent
            .inflate(R.layout.task_item_view, false)
        return TaskItemViewHolder(view, viewModel, navController)
    }
}