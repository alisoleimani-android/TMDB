package com.zenjob.android.tmdb.movie.popular.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.zenjob.android.tmdb.R
import com.zenjob.android.tmdb.common.data.preferences.Preferences
import com.zenjob.android.tmdb.common.domain.model.Theme
import com.zenjob.android.tmdb.common.presentation.listAdapters.MovieListAdapter
import com.zenjob.android.tmdb.common.presentation.model.Event
import com.zenjob.android.tmdb.common.presentation.model.PaginatedListUiState
import com.zenjob.android.tmdb.common.presentation.model.UiMovie
import com.zenjob.android.tmdb.common.presentation.utils.viewBinding
import com.zenjob.android.tmdb.common.utils.collectOn
import com.zenjob.android.tmdb.databinding.FragmentPopularMoviesBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PopularMoviesFragment : Fragment(R.layout.fragment_popular_movies) {

    private val binding by viewBinding(FragmentPopularMoviesBinding::bind)

    private val viewModel by viewModels<PopularMoviesViewModel>()

    @Inject
    lateinit var preferences: Preferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        setupToolbar()

        binding.swipeToRefresh.setOnRefreshListener {
            viewModel.onEvent(PopularMoviesFragmentEvent.RefreshList)
        }

        val adapter = createAdapter()
        setupRecyclerView(adapter)

        observeViewStateUpdates(adapter)
    }

    private fun setupToolbar() {
        when (preferences.theme) {
            Theme.DARK -> {
                binding.toolbar.menu.findItem(R.id.item_theme_mode)
                    .setIcon(R.drawable.ic_light_mode)
            }
            Theme.LIGHT -> {
                binding.toolbar.menu.findItem(R.id.item_theme_mode)
                    .setIcon(R.drawable.ic_night_mode)
            }
        }

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_theme_mode -> switchTheme()
            }
            true
        }
    }

    private fun createAdapter() = MovieListAdapter(
        onItemClicked = {
            findNavController().navigate(
                PopularMoviesFragmentDirections.actionPopularMoviesFragmentToMovieDetailFragment(it.id)
            )
        },
        onBindingLastItem = {
            viewModel.onEvent(PopularMoviesFragmentEvent.LoadNextItems)
        }
    )

    private fun setupRecyclerView(moviesAdapter: MovieListAdapter) {
        binding.movieList.apply {
            adapter = moviesAdapter
            setHasFixedSize(true)
        }
    }

    private fun observeViewStateUpdates(adapter: MovieListAdapter) {
        viewModel.uiState.collectOn(viewLifecycleOwner) {
            updateScreenState(it, adapter)
        }
    }

    private fun updateScreenState(
        state: PaginatedListUiState<UiMovie>,
        adapter: MovieListAdapter
    ) {
        binding.uiState = state
        adapter.submitList(state.items)
        handleFailures(state.failure)
    }

    private fun handleFailures(failure: Event<Throwable>?) {
        val unhandledFailure = failure?.getContentIfNotHandled() ?: return
        val fallbackMessage = getString(R.string.msg_an_error_occurred)
        val throwableMessage = unhandledFailure.message

        val snackbarMessage = if (throwableMessage.isNullOrEmpty()) {
            fallbackMessage
        } else {
            throwableMessage
        }

        if (snackbarMessage.isNotEmpty()) {
            Snackbar.make(requireView(), snackbarMessage, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun switchTheme() {
        val newTheme = when (preferences.theme) {
            Theme.DARK -> Theme.LIGHT
            Theme.LIGHT -> Theme.DARK
        }
        preferences.theme = newTheme
    }

}
