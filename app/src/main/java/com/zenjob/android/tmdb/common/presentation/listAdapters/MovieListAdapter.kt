package com.zenjob.android.tmdb.common.presentation.listAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zenjob.android.tmdb.common.presentation.model.UiMovie
import com.zenjob.android.tmdb.databinding.ViewholderGridMovieBinding


class MovieListAdapter(
    private val onItemClicked: (UiMovie) -> Unit,
    private val onBindingLastItem: (lastItemPosition: Int) -> Unit
) : ListAdapter<UiMovie, MovieListAdapter.MovieGridViewHolder>(ITEM_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieGridViewHolder {
        return MovieGridViewHolder(
            ViewholderGridMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieGridViewHolder, position: Int) {
        holder.bind(getItem(position))
        if (position == itemCount - 1) onBindingLastItem(position)
    }

    inner class MovieGridViewHolder(
        private val binding: ViewholderGridMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION)
                    onItemClicked(getItem(adapterPosition))
            }
        }

        fun bind(uiMovie: UiMovie) {
            binding.uiMovie = uiMovie
        }
    }
}

private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<UiMovie>() {
    override fun areItemsTheSame(oldItem: UiMovie, newItem: UiMovie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UiMovie, newItem: UiMovie): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}
