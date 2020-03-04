package com.example.todolist.screens.task


import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.example.todolist.database.Task
import com.example.todolist.databinding.FragmentTaskBinding
import kotlinx.android.synthetic.main.task_item_view.view.*

/**
 * A simple [Fragment] subclass.
 */
class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTaskBinding
    private var newTask: Int = 0
    private var newTaskName: String? = null
    private var newTaskComment: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_task,
            container,
            false
        )

        // Test presence of bundle and associate informations to variable if so
        // To do : Should display an error and link back to ListFragment if no args found
        OnReceivedArg()
        // Display the UI concerning the clicked task
        DisplayTaskUI()
        // Display an alertDialog with priority choices. Change the UI depending on choice
        OnPriorityButtonClick()
        // Sends a bundle to ListFragment, in order to insert new data in the database & modify
        // ListFragment's UI
        OnBackButtonClick()

        binding.etTaskTitle.doOnTextChanged { text, start, count, after ->
            newTaskName = text.toString()
        }
        binding.etTaskComment.doOnTextChanged { text, start, count, after ->
            newTaskComment = text.toString()
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun DisplayTaskUI() {
        binding.etTaskTitle.setText(newTaskName)
        binding.etTaskComment.setText(newTaskComment)

        when (arguments?.getInt("Priority")) {
            0 -> {
                binding.tvPriority.text = "Aucune"
                binding.rlPriority.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
            1 -> {
                binding.tvPriority.text = "Low"
                binding.rlPriority.setBackgroundColor(Color.parseColor("#D0FFA8"))
            }
            2 -> {
                binding.tvPriority.text = "Medium"
                binding.rlPriority.setBackgroundColor(Color.parseColor("#FFC691"))
            }
            3 -> {
                binding.tvPriority.text = "High"
                binding.rlPriority.setBackgroundColor(Color.parseColor("#FF9191"))
            }
        }
    }

    private fun OnPriorityButtonClick() {
        binding.buPriority.setOnClickListener {
            var builder = AlertDialog.Builder(this.context)
            var checkedItem = newTask
            builder.setTitle("Priorité de tâche")
            var priorityList:Array<String> = arrayOf("Aucune", "Basse", "Moyenne", "Haute")

            builder.setSingleChoiceItems(priorityList, checkedItem
            ) { dialog, which ->
                when (which) {
                    0 -> {
                        Log.d("alert", "Checked item : " + 0)
                        newTask = 0
                    }
                    1 -> {
                        Log.d("alert", "Checked item : " + 1)
                        newTask = 1
                    }
                    2 -> {
                        Log.d("alert", "Checked item : " + 2)
                        newTask = 2
                    }
                    3 -> {
                        Log.d("alert", "Checked item : " + 3)
                        newTask = 3
                    }
                }
            }
            // add OK button
            builder.setPositiveButton("OK") { dialog, which ->
                Log.d("alert", "NewTasksPriority : " + newTask)
                when (newTask) {
                    0 -> {
                        binding.tvPriority.text = "Aucune"
                        binding.rlPriority.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    }
                    1 ->  {
                        binding.tvPriority.text = "Basse"
                        binding.rlPriority.setBackgroundColor(Color.parseColor("#D0FFA8"))
                    }
                    2 -> {
                        binding.tvPriority.text = "Moyenne"
                        binding.rlPriority.setBackgroundColor(Color.parseColor("#FFC691"))
                    }
                    3 -> {
                        binding.tvPriority.text = "Haute"
                        binding.rlPriority.setBackgroundColor(Color.parseColor("#FF9191"))
                    }
                }
            }
            // create and show the alert dialog
            val dialog = builder.create()
            dialog.show()
        }
    }

    private fun OnReceivedArg() {
        if (arguments?.getInt("Priority") != null) newTask = arguments!!.getInt("Priority")
        if (arguments?.getString("Name") != null) newTaskName = arguments!!.getString("Name")
        if (arguments?.getString("Comment") != null) newTaskComment = arguments!!.getString("Comment")
    }

    private fun OnBackButtonClick() {
        binding.ibUpButtonTaskBuilder.setOnClickListener {
            var bundle = bundleOf(
                "ID" to arguments?.getLong("ID"),
                "Name" to newTaskName,
                "Comment" to newTaskComment,
                "Priority" to newTask,
                "IsImportant" to arguments?.getBoolean("IsImportant")
            )

            this.findNavController().navigate(R.id.action_taskFragment_to_listFragment, bundle)
        }
    }
}
