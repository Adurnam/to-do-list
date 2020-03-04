package com.example.todolist.screens.list


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.database.TaskDatabase
import com.example.todolist.databinding.FragmentListBinding
import com.example.todolist.screens.list.adapter.TaskAdapter

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_list,
            container,
            false
        )
        /**
         * Setup the ListViewModel and bind it to the layout
         */
        val application = requireNotNull(this.activity).application
        val dataSource = TaskDatabase.getInstance(application).taskDatabaseDao
        val viewModelFactory = ListViewModelFactory(dataSource, application)
        val viewModel = ViewModelProvider(
            this, viewModelFactory).get(ListViewModel::class.java)

        binding.lifecycleOwner = this
        binding.listViewModel = viewModel

        /**
         * Setup the recycler view
         * Bind the layout manager, bind the adapter, then setup the navigation
         */
        BuildRecyclerView(viewModel)
        // Observer for Tasks, update the UI on new tasks received by giving new data to the adapter
        TaskObserver(viewModel)
        // Navigate to the TaskBuilder on click
        NavigateToBuilder()

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun NavigateToBuilder() {
        binding.ibAddTask.setOnClickListener {
            this.findNavController().navigate(R.id.action_listFragment_to_taskBuilderFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        val application = requireNotNull(this.activity).application
        val dataSource = TaskDatabase.getInstance(application).taskDatabaseDao
        val viewModelFactory = ListViewModelFactory(dataSource, application)
        val viewModel = ViewModelProvider(
            this, viewModelFactory).get(ListViewModel::class.java)

        if (arguments?.getString("Name") != null || arguments?.getInt("Priority") != null || arguments?.getString("Comment") != null ) {
            viewModel.modifyTask(arguments!!.getString("Name"), arguments!!.getString("Comment"), arguments!!.getInt("Priority"), arguments!!.getLong("ID"))
        }
    }

    private fun TaskObserver(viewModel: ListViewModel) {
        viewModel.tasks.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
            adapter.notifyDataSetChanged()
        })
    }

    private fun BuildRecyclerView(viewModel: ListViewModel) {
        layoutManager = LinearLayoutManager(this.context)
        binding.rvTaskList.layoutManager = layoutManager
        binding.rvTaskList.addItemDecoration(DividerItemDecoration(context, VERTICAL))
        adapter = TaskAdapter(findNavController(), viewModel)
        binding.rvTaskList.adapter = adapter
    }
}
