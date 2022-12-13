package com.zenjob.android.tmdb.movie.detail.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.zenjob.android.tmdb.R
import com.zenjob.android.tmdb.common.presentation.model.Event
import com.zenjob.android.tmdb.common.presentation.utils.viewBinding
import com.zenjob.android.tmdb.common.utils.collectOn
import com.zenjob.android.tmdb.databinding.FragmentMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private val binding by viewBinding(FragmentMovieDetailBinding::bind)

    private val viewModel by viewModels<MovieDetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        with(binding) {
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }

            swipeToRefresh.setOnRefreshListener {
                viewModel.onEvent(MovieDetailFragmentEvent.Refresh)
            }
        }

        observeViewStateUpdates()
    }

    private fun observeViewStateUpdates() {
        viewModel.uiState.collectOn(viewLifecycleOwner) {
            updateScreenState(it)
        }
    }

    private fun updateScreenState(state: MovieDetailUiState) {
        binding.uiState = state
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

}
