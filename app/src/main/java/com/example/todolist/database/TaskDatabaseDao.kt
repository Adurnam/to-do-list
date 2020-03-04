package com.example.todolist.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDatabaseDao {
    @Insert
    fun insert(task: Task)

    @Update
    fun update(task: Task)

    @Query("SELECT * FROM task_table WHERE taskID = :key")
    fun getTask(key: Long): Task?

    @Query("SELECT * FROM task_table")
    fun getAllTasks() : List<Task>?

    @Query("SELECT * FROM task_table WHERE task_is_completed = 0")
    fun getOnGoing(): List<Task>?

    @Query("UPDATE task_table SET task_name = :name, task_comment = :comment, task_priority = :priority WHERE taskID = :key")
    fun updateTask(name: String?, comment: String?, priority: Int, key: Long)

    @Query("DELETE FROM task_table")
    fun clear()

    @Query("DELETE FROM task_table WHERE taskID = :key")
    fun deleteTask(key: Long)
}