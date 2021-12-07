package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.api.models.Asteroid
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: MainViewModel by viewModel()

    private lateinit var adapter: AsteroidsAdapter
    private val headerAdapter = HeaderAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel
        viewModel.loadApiAsteroids()
        viewModel.getImageOfDay()
        adapter = AsteroidsAdapter { asteroidId ->
            findNavController().navigate(MainFragmentDirections.toShowDetail(asteroidId))
        }
        binding.asteroidRecycler.adapter = ConcatAdapter(headerAdapter, adapter)

        setHasOptionsMenu(true)
        setObservers()

        return binding.root
    }

    private fun setObservers() {
        viewModel.allAsteroids.observe(viewLifecycleOwner) {
            updateAdapterList(it)
        }

        viewModel.imageOfDayUrl.observe(viewLifecycleOwner) { pictureOfDay ->
            headerAdapter.setList(pictureOfDay)
        }

        viewModel.asteroids.observe(viewLifecycleOwner) {
            updateAdapterList(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.loadingProgressBar.isVisible = it
        }
    }

    private fun updateAdapterList(itemList: List<Asteroid>) {
        binding.emptyListWarningContainer.isVisible = itemList.isEmpty()
        adapter.submitList(itemList)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val filter: DatabaseFilter = when (item.itemId) {
            R.id.showWeekAsteroids -> {
                DatabaseFilter.WEEK
            }
            R.id.showTodayAsteroids -> {
                DatabaseFilter.DAY
            }
            else -> {
                DatabaseFilter.ALL
            }
        }
        viewModel.loadDatabaseItems(filter)
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
