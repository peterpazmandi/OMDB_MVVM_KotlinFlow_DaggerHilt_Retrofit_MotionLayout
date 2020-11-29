package com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.search.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.R
import com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.databinding.ItemMovieBinding
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.model.Search

class SearchResultAdapter : RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {

    private val TAG = this.javaClass.simpleName

    private var searchItems = ArrayList<Search>()

    lateinit var listener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(movie : Search, imageView: ImageView, textView: TextView)
    }

    fun updateItems (newList : ArrayList<Search>) {
        if (newList != null) {
            if (searchItems.isNotEmpty())
                searchItems.removeAt(searchItems.size - 1)
            searchItems.clear()
            searchItems.addAll(newList)
        } else {
            searchItems.add(newList)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(
            layoutInflater, parent, false
        )
        return SearchResultViewHolder(binding)
    }

    override fun getItemCount() = searchItems.size

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.bind(searchItem = searchItems[position])
    }

    inner class SearchResultViewHolder (val binding : ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (searchItem : Search) {

            binding.ivPoster.transitionName = searchItem.Poster
            binding.tvTitle.transitionName = searchItem.Title

            binding.tvTitle.text = searchItem.Title
            binding.tvYear.text = searchItem.Year

            searchItem.Poster?.let {
                Log.d(TAG, it)

                Glide
                    .with(binding.root)
                    .load(it)
                    .centerCrop()
                    .placeholder(R.drawable.image_not_found)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.ivPoster)
            }

            binding.mcvItemLayout.setOnClickListener {
                listener.onItemClick(searchItem, binding.ivPoster, binding.tvTitle)
            }

        }

    }

}