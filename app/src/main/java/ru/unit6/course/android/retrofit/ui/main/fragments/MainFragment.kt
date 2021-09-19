package ru.unit6.course.android.retrofit.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import ru.unit6.course.android.retrofit.R
import ru.unit6.course.android.retrofit.ui.main.adapters.MainAdapter
import ru.unit6.course.android.retrofit.ui.main.viewmodels.MainViewModel
import ru.unit6.course.android.retrofit.utils.Status

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)

        view.apply {
            recyclerView = findViewById(R.id.recyclerView)
            progressBar = findViewById(R.id.progressBar)
            findViewById<ShapeableImageView>(R.id.ivAddPerson).setOnClickListener {
                toAddPersonFragment()
            }
        }

        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = MainAdapter(arrayListOf()) { userId ->
            toDetailsFragment(userId = userId)
        }
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getUsers().observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    recyclerView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    resource.data?.let { users -> adapter.addUsers(users) }
                }
                Status.ERROR -> {
                    recyclerView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    Toast.makeText(context, resource.message, Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
            }
        }
    }

    private fun toDetailsFragment(userId: String) {

        parentFragmentManager.beginTransaction()
            .addToBackStack("MainFragment")
            .replace(R.id.itemContainer, DetailsFragment.newInstance(userId = userId))
            .commit()
    }

    private fun toAddPersonFragment(){
        parentFragmentManager.beginTransaction()
            .addToBackStack("MainFragment")
            .replace(R.id.itemContainer, AddPersonFragment.newInstance())
            .commit()
    }
}