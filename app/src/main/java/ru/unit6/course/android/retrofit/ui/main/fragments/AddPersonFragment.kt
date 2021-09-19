package ru.unit6.course.android.retrofit.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.unit6.course.android.retrofit.R
import ru.unit6.course.android.retrofit.ui.main.viewmodels.AddPersonViewModel
import ru.unit6.course.android.retrofit.ui.main.viewmodels.DetailsViewModel
import ru.unit6.course.android.retrofit.utils.Status


class AddPersonFragment : Fragment() {


    companion object {

        fun newInstance() = AddPersonFragment()
    }

    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etAvatar: EditText
    private lateinit var btnSend: Button
    private lateinit var pbLoading: ProgressBar
    private lateinit var tvError: TextView

    private lateinit var viewModel: AddPersonViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_add_person, container, false).apply {
            etName = findViewById(R.id.etName)
            etEmail = findViewById(R.id.etEmail)
            etAvatar = findViewById(R.id.etAvatar)
            btnSend = findViewById(R.id.btnSend)
            pbLoading = findViewById(R.id.pbLoading)
            tvError = findViewById(R.id.tvError)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddPersonViewModel::class.java)

        setupUI()
    }

    private fun setupUI() {

        btnSend.setOnClickListener {
            viewModel.setUser(
                name = etName.text.toString(),
                email = etEmail.text.toString(),
                avatar = etAvatar.text.toString()
            ).observe(viewLifecycleOwner) { response ->

                when (response.status) {
                    Status.LOADING -> {
                        pbLoading.visibility = View.VISIBLE
                        tvError.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        pbLoading.visibility = View.GONE
                        tvError.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        pbLoading.visibility = View.GONE
                        tvError.visibility = View.GONE
                        Toast.makeText(requireContext(), "Пользователь создан!", Toast.LENGTH_LONG).show()
                        etName.setText("")
                        etEmail.setText("")
                        etAvatar.setText("")
                    }
                }

            }
        }
    }

}