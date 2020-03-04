package com.example.todolist.screens.taskbuilder

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.todolist.database.TaskDatabaseDao

class TaskBuilderViewModel(
    val database: TaskDatabaseDao,
    application: Application
) : AndroidViewModel(application)  {


}