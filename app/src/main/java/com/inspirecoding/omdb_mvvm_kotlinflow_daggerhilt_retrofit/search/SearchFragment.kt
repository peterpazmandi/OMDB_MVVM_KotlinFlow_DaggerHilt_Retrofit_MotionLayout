package com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.search

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.R
import com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.databinding.SearchFragmentBinding
import com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.search.adapter.SearchResultAdapter
import com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.utils.*
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.model.Search
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.search_fragment), SearchResultAdapter.OnItemClickListener {

    private val TAG = this.javaClass.simpleName

    private val viewModel by viewModels<SearchViewModel>()
    private lateinit var binding : SearchFragmentBinding

    @Inject
    lateinit var searchResultAdapter : SearchResultAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SearchFragmentBinding.bind(view)

        initSearchResultRecyclerView()
        setupSearchResultObserver()

        searchResultAdapter.listener = this

        binding.ivSearchIcon.setOnClickListener {
            searchItemWithDelay(
                binding.etSearchField.text.toString()
            )
            context?.dismissKeyboard(it)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            searchItemWithDelay(
                binding.etSearchField.text.toString()
            )
        }
    }

    private fun setupSearchResultObserver() {
        viewModel.searchResult.observe(viewLifecycleOwner, Observer { _result ->
            when(_result.status)
            {
                Status.SUCCESS -> {
                    _result._data?.let {
                        searchResultAdapter.updateItems(it)

                        if(it.size > 0) {
                            isMovieFound(true)
                        } else {
                            isMovieFound(false)
                        }
                    }

                    binding.swipeRefreshLayout.isRefreshing = false
                }
                Status.LOADING -> {
                    binding.swipeRefreshLayout.isRefreshing = true
                }
                Status.ERROR -> {
                    _result.message?.let {
                        context?.showToast(it)
                    }
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        })
    }

    private fun searchItemWithDelay(searchText : String) {

        lifecycleScope.launchWhenCreated {
            delay(5_00)

            if (searchText.isNotEmpty() && searchText.length > 3) {
                viewModel.getSearchResultData(searchText)
                binding.etSearchLayout.error = null

                isMovieFound(true)
            } else {
                searchResultAdapter.updateItems(arrayListOf())
                binding.etSearchLayout.error = getString(R.string.type_at_least_four_characters)

                isMovieFound(false)
            }
        }

    }

    private fun isMovieFound(found : Boolean) {

        if (found) {

            binding.swipeRefreshLayout.visible()
            binding.llNoMovieFound.gone()

        } else {

            binding.swipeRefreshLayout.gone()
            binding.llNoMovieFound.visible()

        }

    }

    private fun initSearchResultRecyclerView() {

        postponeEnterTransition()
        binding.rvSearchResult.apply {
            doOnPreDraw {
                startPostponedEnterTransition()
            }

            itemAnimator = DefaultItemAnimator()
            this.adapter = searchResultAdapter
        }

    }

    override fun onItemClick(movie: Search, imageView: ImageView, textView: TextView) {

        sharedElementEnterTransition = TransitionInflater
            .from(context)
            .inflateTransition(android.R.transition.move)

        val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(movie)
        val extras = FragmentNavigatorExtras(
            imageView to movie.Poster,
            textView to movie.Title
        )
        findNavController().navigate(action, extras)

    }


}