package com.task.tsjtask.main.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.task.tsjtask.R
import com.task.tsjtask.databinding.FragmentMovieDetailBinding
import com.task.tsjtask.main.data.model.get.Result

class MovieDetailFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailBinding
    private val args by navArgs<MovieDetailFragmentArgs>()
    private lateinit var movieDetails: Result

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_movie_detail, container, false)
        binding = FragmentMovieDetailBinding.bind(view)

        movieDetails = args.movieDetails
        Log.d("TAG", movieDetails.toString())


        return binding.root
    }

    override fun onStart() {
        super.onStart()

        movieDetails.apply {

            Glide.with(requireContext()).load("https://image.tmdb.org/t/p/w500" + backdrop_path).into(binding.ivBackdrop)
            Glide.with(requireContext()).load("https://image.tmdb.org/t/p/w500" + poster_path).into(binding.ivFrontImg)
            if(title != null) binding.movieName.text = title else binding.movieName.text = name
            if(adult) binding.age.text = "A" else binding.age.text = "U/A"
            binding.rating.text = vote_average.toString()
            binding.plotText.text = overview
            binding.votes.text = vote_count.toString()
        }

        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

    }
}