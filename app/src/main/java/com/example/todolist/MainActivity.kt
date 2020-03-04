package com.example.todolist

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.room.Room
import androidx.room.Room.inMemoryDatabaseBuilder
import com.example.todolist.database.TaskDatabase
import com.example.todolist.database.TaskDatabaseDao
import com.example.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideActionBar(true)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Permission tests
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.v("permissions","Permission is granted");
            //File write logic here
        } else {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1)
        }
    }

    fun hideActionBar(toShow: Boolean) {
        if (!toShow) supportActionBar?.show() else supportActionBar?.hide()
    }
}
