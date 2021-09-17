package com.task.tsjtask.main.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.task.tsjtask.R
import com.task.tsjtask.databinding.ViewMovieBinding
import com.task.tsjtask.main.data.model.get.Result
import com.task.tsjtask.main.ui.HomeFragment
import com.task.tsjtask.main.ui.HomeFragmentDirections

class MovieAdapter(private val list: List<Result>, val homeFragment: HomeFragment): RecyclerView.Adapter<MovieAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: ViewMovieBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                Glide.with(binding.movieImg)
                    .load("https://image.tmdb.org/t/p/w500/" + this.backdrop_path)
                    .error(R.drawable.ic_baseline_error_24)
                    .centerCrop()
                    .into(binding.movieImg)
                if(title != null) binding.movieName.text = this.title else binding.movieName.text = this.name
                if(this.adult) binding.age.text = "A" else binding.age.text = "U/A"
                binding.rating.text = this.vote_average.toString()
                binding.mediaType.text = this.media_type[0].toUpperCase() + this.media_type.substring(1, this.media_type.length)
                binding.nextBtn.setOnClickListener {
                    val action = HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(this)
                    homeFragment.findNavController().navigate(action)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}