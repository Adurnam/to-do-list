package com.example.todolist.screens.list.adapter

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.database.Task
import com.example.todolist.screens.list.ListFragment
import com.example.todolist.screens.list.ListViewModel
import kotlinx.android.synthetic.main.task_item_view.view.*

class TaskItemViewHolder(v: View, viewModel: ListViewModel, navController: NavController): RecyclerView.ViewHolder(v), View.OnClickListener {

    private var view: View = v
    private var task: Task? = null
    private var navController = navController
    private var viewModel = viewModel

    init {
        view.setOnClickListener(this)
        view.btDeleteTask.setOnClickListener {
            viewModel.deleteTask(task!!.taskID)
        }
    }

    override fun onClick(v: View?) {
        Log.d("RecyclerView", "Click !")
        var bundle = bundleOf(
            "ID" to task?.taskID,
            "Name" to task?.taskName,
            "Comment" to task?.taskComment,
            "Priority" to task?.taskPriority,
            "IsImportant" to task?.taskIsImportant
        )

        navController.navigate(R.id.action_listFragment_to_taskFragment, bundle)
    }

    companion object {
        private val TASK_KEY = "TASK"
    }

    fun bindTask(task: Task) {
        this.task = task
        view.tvTestList.text = task.taskName

        when (task.taskPriority) {
            0 -> {

            }
            1 -> {
                view.clView.setBackgroundColor(Color.parseColor("#D0FFA8"))
            }
            2 -> {
                view.clView.setBackgroundColor(Color.parseColor("#FFC691"))
            }
            3 -> {
                view.clView.setBackgroundColor(Color.parseColor("#FF9191"))
            }
        }

        if (task.taskComment != "") {
            view.tvCommentList.text = task.taskComment
        } else
            view.tvCommentList.text = ""
    }
}