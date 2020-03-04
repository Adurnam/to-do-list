package com.example.todolist.database

import androidx.room.ColumnInfo
import java.time.LocalDateTime
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val taskID: Long = 0L,

    @ColumnInfo(name = "task_name")
    var taskName: String = "",

    /*@ColumnInfo(name = "task_sub_tasks")
    var taskSubTasks: List<SubTask?> = ArrayList(),*/

    @ColumnInfo(name = "task_is_completed")
    var taskIsCompleted: Boolean = false,

    /*@ColumnInfo(name = "task_creation")
    val taskCreation: LocalDateTime = LocalDateTime.now(),

    @ColumnInfo(name = "task_completion")
    var taskCompletion: LocalDateTime? = null,

    @ColumnInfo(name = "task_limit")
    var taskLimit: LocalDateTime? = null,*/

    @ColumnInfo(name = "task_comment")
    var taskComment: String? = null,

    @ColumnInfo(name = "task_priority")
    var taskPriority: Int = 0,

    @ColumnInfo(name = "task_is_important")
    var taskIsImportant: Boolean = false
)

@Entity(tableName = "subtask_table")
data class SubTask(
    @PrimaryKey(autoGenerate = true)
    val subTaskID: Long = 0L,

    @ColumnInfo(name = "subtask_name")
    val subTaskName: String = "",

    @ColumnInfo(name = "subtask_is_completed")
    val subTaskIsCompleted: Boolean = false
    )