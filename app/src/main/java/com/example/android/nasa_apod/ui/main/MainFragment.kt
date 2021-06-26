package com.example.android.nasa_apod.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.nasa_apod.R
import com.example.android.nasa_apod.databinding.MainFragmentBinding
import com.example.android.nasa_apod.domain.util.Event
import com.example.android.nasa_apod.domain.util.Resource
import com.example.android.nasa_apod.domain.util.exhaustive
import com.example.android.nasa_apod.domain.util.showSnackBarError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {

    private val viewModel: MainViewModel by viewModels()

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var mainAdapter: MainAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        _binding = MainFragmentBinding.bind(view)/*.also {
            it.listVm = viewModel
            it.lifecycleOwner = this
        }*/
        setupBinding()
        setupObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.menu_refresh -> {
                Timber.e("abc")
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

    private fun setupBinding() {
        binding.mfSrl.setOnRefreshListener { viewModel.refreshData() }
        binding.mfRc.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.also {
            it.launchWhenStarted {
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
            it.launchWhenStarted {
                viewModel.lists.collect { resource ->
                    val result = resource ?: return@collect
                    Timber.e("result : ${result.data}")
                    binding.mfSrl.isRefreshing = resource is Resource.Loading
                    binding.mfRc.isVisible = !result.data.isNullOrEmpty()
                    binding.mfGroup.isVisible = !binding.mfRc.isVisible
                    mainAdapter.submitList(result.data)
                }
            }
        }
    }
}