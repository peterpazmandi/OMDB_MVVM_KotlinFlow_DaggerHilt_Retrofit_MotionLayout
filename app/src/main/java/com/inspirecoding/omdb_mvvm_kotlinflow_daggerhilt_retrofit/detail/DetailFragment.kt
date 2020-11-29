package com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.R
import com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.databinding.DetailFragmentBinding
import com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.utils.*
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.model.Movie
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.model.Search
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.detail_fragment) {

    private val viewModel by viewModels<DetailViewModel>()
    private val safeArgs : DetailFragmentArgs by navArgs()
    private lateinit var binding : DetailFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DetailFragmentBinding.bind(view)

        sharedElementEnterTransition = TransitionInflater
            .from(context)
            .inflateTransition(android.R.transition.move)
        postponeEnterTransition(250, TimeUnit.MILLISECONDS)

        val movie = safeArgs.movie
        updatePostAndTitle(movie)

        viewModel.getMovieDetailsData(movie.imdbID)

        setupBackButton()
        setupMovieObserver()
    }

    private fun setupBackButton() {
        binding.ivBackButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupMovieObserver() {

        viewModel.movieResult.observe(viewLifecycleOwner, Observer { _result ->

            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        updateUi(it)
                    }

                    binding.progressBar.gone()
                }
                Status.LOADING -> {
                    binding.progressBar.visible()
                }
                Status.ERROR -> {
                    _result.message?.let {
                        context?.showToast(it)
                    }
                    binding.progressBar.gone()
                }
            }

        })

    }

    private fun updatePostAndTitle(search : Search) {

        binding.ivPoster.transitionName = search.Poster

        Glide
            .with(this)
            .load(search.Poster)
            .centerCrop()
            .placeholder(R.drawable.image_not_found)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.ivPoster)

        binding.tvTitle.text = search.Title
        binding.tvYear.text = search.Year

        binding.ivPoster.transitionName = search.Poster
        binding.tvTitle.transitionName = search.Title

    }

    private fun updateUi(movie : Movie) {

        binding.tvRating.text = resources.getString(R.string.rating, movie.imdbRating)
        binding.tvDescription.text = movie.Plot

        binding.tvDirectors.text = context?.fromHtmlWithParams(R.string.directors, movie.Director)
        binding.tvActors.text = context?.fromHtmlWithParams(R.string.actors, movie.Actors)
        binding.tvAwards.text = context?.fromHtmlWithParams(R.string.awards, movie.Awards)
    }

}