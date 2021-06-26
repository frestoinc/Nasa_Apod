package com.example.android.nasa_apod.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.android.nasa_apod.R
import com.example.android.nasa_apod.databinding.MainFragmentBinding
import com.example.android.nasa_apod.domain.util.Event
import com.example.android.nasa_apod.domain.util.exhaustive
import com.example.android.nasa_apod.domain.util.showSnackBarError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        _binding = MainFragmentBinding.bind(view)/*.also {
            it.listVm = viewModel
            it.lifecycleOwner = this
        }*/
        setupObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.menu_refresh -> {
                viewModel.refreshData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadData()
    }

    private fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.lists.collect()
            viewModel.errorEvents.collect { event ->
                when (event) {
                    is Event.ShowErrorMessage -> showSnackBarError(
                        getString(
                            R.string.mainErrorEvent,
                            event.error.localizedMessage ?: getString(R.string.mainErrorUnknown)
                        )
                    )
                }.exhaustive
            }
        }
    }
}