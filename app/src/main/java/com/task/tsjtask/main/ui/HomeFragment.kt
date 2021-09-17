package com.task.tsjtask.main.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.task.tsjtask.R
import com.task.tsjtask.auth.ui.AuthActivity
import com.task.tsjtask.databinding.FragmentHomeBinding
import com.task.tsjtask.main.api.MovieApi
import com.task.tsjtask.main.data.repo.MovieRepo
import com.task.tsjtask.main.ui.adapter.MovieAdapter
import com.task.tsjtask.main.ui.viewmodel.MovieViewModel
import com.task.tsjtask.utils.*

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: MovieViewModel
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.bind(view)

        loadingDialog = LoadingDialog(requireActivity())


        val api = RetrofitInstance.buildApi(MovieApi::class.java)
        val repo = MovieRepo(api)
        val factory = ViewModelFactory(repo)
        viewModel = ViewModelProvider(this, factory).get(MovieViewModel::class.java)



        return binding.root
    }


    override fun onStart() {
        super.onStart()
        makeApiCall()

        binding.swipeRefreshLayout.setOnRefreshListener {
            makeApiCall()
        }

        /*binding.logout.setOnClickListener {
            MaterialAlertDialogBuilder(requireActivity()).setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes"
                ) { dialog, which ->
                    Firebase.auth.signOut()
                    startActivity(Intent(requireActivity(), AuthActivity::class.java))
                }
                .setNegativeButton("No") {dialog, which ->
                    dialog.dismiss()
                }
                .show()
        }*/

    }


    private fun makeApiCall() {
        binding.mainLayout.visibility = View.VISIBLE
        loadingDialog.startLoading()
        viewModel.getTrendingMovies()
        viewModel.movieList.observe(viewLifecycleOwner, {response->
            loadingDialog.stopLoading()
            binding.swipeRefreshLayout.isRefreshing = false
            when(response) {
                is ApiResponseHandler.Success -> {
                    binding.mainLayout.visibility = View.VISIBLE
                    val adapter = MovieAdapter(response.value.results, this)
                    binding.recyclerView.adapter = adapter
                }

                is ApiResponseHandler.Failure -> handleApiError(response)
            }
        })
    }

}