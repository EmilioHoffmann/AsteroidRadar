package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.utils.loadImage
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: MainViewModel by viewModel()

    private lateinit var adapter: AsteroidsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel
        viewModel.getAsteroids()
        viewModel.getImageOfDay()
        adapter = AsteroidsAdapter { asteroidId ->
            findNavController().navigate(MainFragmentDirections.toShowDetail(asteroidId))
        }
        binding.asteroidRecycler.adapter = adapter

        setHasOptionsMenu(true)
        setObservers()

        return binding.root
    }

    private fun setObservers() {
        viewModel.asteroids.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.imageOfDayUrl.observe(viewLifecycleOwner) { pictureOfDay ->
            binding.imageOfDay.contentDescription = pictureOfDay.title
            pictureOfDay.url?.let { binding.imageOfDay.loadImage(it) }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
