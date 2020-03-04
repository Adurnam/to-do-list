package com.example.todolist.screens.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.database.Task
import com.example.todolist.database.TaskDatabaseDao
import kotlinx.coroutines.*

class ListViewModel(
    val database: TaskDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>>
    get() = _tasks

    init {
        // Pour clear la database manuellement, uncomment
        // clearDatabase()
        initTasks()
    }

    private fun initTasks() {
        uiScope.launch {
            _tasks.value = getTasksFromDatabase()
        }
    }

    fun deleteTask(id: Long) {
        uiScope.launch {
            database.deleteTask(id)
            _tasks.value = getTasksFromDatabase()
        }
    }

    private suspend fun getTasksFromDatabase(): List<Task>? {
        return withContext(Dispatchers.IO) {
            var task = database.getOnGoing()
            if (task == null) {
                task = ArrayList()
            }
            task
        }
    }

    fun modifyTask(name: String?, comment: String?, priority: Int, key: Long) {
        uiScope.launch {
            database.updateTask(name, comment, priority, key)
            _tasks.value = getTasksFromDatabase()
        }
    }

    fun clearDatabase() {
        uiScope.launch {
            database.clear()
        }
    }

    fun addTask(task: List<Task>) {
        _tasks.value = task
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}