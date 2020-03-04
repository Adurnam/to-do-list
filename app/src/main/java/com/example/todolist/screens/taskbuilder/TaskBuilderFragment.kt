package com.example.todolist.screens.taskbuilder


import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.example.todolist.databinding.FragmentTaskBuilderBinding
import android.util.Log
import com.example.todolist.MainActivity
import com.example.todolist.database.Task
import com.example.todolist.database.TaskDatabase


/**
 * A simple [Fragment] subclass.
 */
class TaskBuilderFragment : Fragment() {

    private lateinit var binding: FragmentTaskBuilderBinding
    private var newTask: Task = Task()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_task_builder,
            container,
            false
        )

        // On Up Button click, navigate to ListFragment
        CancelTaskBuilding()

        // On Priority click, open an alertDialog & change UI depending on choice.
        AlertDialogBuilder()

        // When task is created, link back to ListFargment & insert data into database
        OnCreateTask()
        return binding.root
    }

    private fun AlertDialogBuilder() {
        binding.buTaskBuilderPriority.setOnClickListener {
            var builder = AlertDialog.Builder(this.context)
            var checkedItem = newTask.taskPriority
            builder.setTitle("Priorité de tâche")
            var priorityList:Array<String> = arrayOf("Aucune", "Basse", "Moyenne", "Haute")

            builder.setSingleChoiceItems(priorityList, checkedItem
            ) { dialog, which ->
                when (which) {
                    0 -> {
                        Log.d("alert", "Checked item : " + 0)
                        newTask.taskPriority = 0
                    }
                    1 -> {
                        Log.d("alert", "Checked item : " + 1)
                        newTask.taskPriority = 1
                    }
                    2 -> {
                        Log.d("alert", "Checked item : " + 2)
                        newTask.taskPriority = 2
                    }
                    3 -> {
                        Log.d("alert", "Checked item : " + 3)
                        newTask.taskPriority = 3
                    }
                }
            }
            // add OK and Cancel buttons
            builder.setPositiveButton("OK") { dialog, which ->
                Log.d("alert", "NewTasksPriority : " + newTask.taskPriority)
                when (newTask.taskPriority) {
                    0 -> binding.tvTaskBuilderPriority.text = "Aucune"
                    1 -> binding.tvTaskBuilderPriority.text = "Basse"
                    2 -> binding.tvTaskBuilderPriority.text = "Moyenne"
                    3 -> binding.tvTaskBuilderPriority.text = "Haute"
                }
            }
            // create and show the alert dialog
            val dialog = builder.create()
            dialog.show()
        }
    }

    private fun CancelTaskBuilding() {
        binding.ibUpButtonTaskBuilder.setOnClickListener {
            this.findNavController().navigate(R.id.action_taskBuilderFragment_to_listFragment)
        }
    }

    private fun OnCreateTask() {
        binding.ibCreateTask.setOnClickListener {
            /**
             * To do : Add an error message if [newTask.taskName] is empty
             */
            if(binding.etTaskName.text.toString() != "")
                newTask.taskName = binding.etTaskName.text.toString()
            newTask.taskComment = binding.etTaskComment.text.toString()

            TaskDatabase.getInstance(context!!).taskDatabaseDao.insert(newTask)

            this.findNavController().navigate(R.id.action_taskBuilderFragment_to_listFragment)
        }
    }
}
