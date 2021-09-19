package ru.unit6.course.android.retrofit.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import ru.unit6.course.android.retrofit.R
import ru.unit6.course.android.retrofit.data.model.User
import ru.unit6.course.android.retrofit.ui.main.viewmodels.DetailsViewModel
import ru.unit6.course.android.retrofit.utils.Status


class DetailsFragment : Fragment() {

    companion object {
        private const val USER_ID = "USER_ID"

        fun newInstance(userId: String) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putString(USER_ID, userId)
            }
        }
    }

    private lateinit var viewModel: DetailsViewModel
    private lateinit var imageView: ImageView
    private lateinit var clInfo: ConstraintLayout
    private lateinit var tvId: TextView
    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var pbLoading: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.fragment_details, container, false)

        with(view) {
            imageView = findViewById(R.id.ivAvatar)
            tvId = findViewById(R.id.tvId)
            tvName = findViewById(R.id.tvName)
            tvEmail = findViewById(R.id.tvEmail)
            clInfo = findViewById(R.id.clInfo)
            pbLoading = findViewById(R.id.pbLoading)
        }
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getCurrentUser(arguments?.getString(USER_ID) ?: "").observe(viewLifecycleOwner) { response ->

            when (response.status) {
                Status.SUCCESS -> {
                    imageView.visibility = View.VISIBLE
                    clInfo.visibility = View.VISIBLE
                    pbLoading.visibility = View.GONE
                    setPersonInfo(response.data ?: User())
                }
                Status.ERROR -> {
                    imageView.visibility = View.GONE
                    clInfo.visibility = View.GONE
                    pbLoading.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    imageView.visibility = View.GONE
                    clInfo.visibility = View.GONE
                    pbLoading.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setPersonInfo(user: User) {
        Glide.with(requireContext())
            .load(user.avatar)
            .circleCrop()
            .into(imageView)
        tvId.text = user.id
        tvName.text = user.name
        tvEmail.text = user.email

    }
}