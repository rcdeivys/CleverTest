package com.rcdeivys.clevertest.ui.locations

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.rcdeivys.clevertest.activities.MainActivity
import com.rcdeivys.clevertest.common.collectFlow
import com.rcdeivys.clevertest.common.isLastItemDisplaying
import com.rcdeivys.clevertest.common.logError
import com.rcdeivys.clevertest.common.showToastShort
import com.rcdeivys.clevertest.databinding.FragmentLocationsBinding
import com.rcdeivys.clevertest.models.Result
import com.rcdeivys.clevertest.ui.home.actions.HomeActions
import org.koin.androidx.viewmodel.ext.android.viewModel

class LocationsFragment : Fragment() {

    private lateinit var binding: FragmentLocationsBinding
    private val viewModel: LocationsViewModel by viewModel()

    private lateinit var locationsAdapters: LocationsAdapters
    private var allLocations = mutableListOf<Result>()

    private var listener: LocationsFragmentListener? = null

    private val baseActivity by lazy {
        requireActivity() as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationsBinding.inflate(inflater)
        setupAdapter()
        viewModel.getLocations()
        lifecycleScope.collectFlow(viewModel.getAction()) {
            when (it) {
                is HomeActions.SetShowProgress -> baseActivity.showLoading(it.isVisible)
                is HomeActions.ShowMessageText -> showToastShort(requireContext(), it.message)
                is HomeActions.SetLocations -> setLocations(it.locations)
                is HomeActions.UpdateLocations -> updateLocations(it.locations)
                else -> {}
            }
        }
        return binding.root
    }

    private fun setLocations(locations: List<Result>) {
        allLocations.clear()
        allLocations.addAll(locations)
        locationsAdapters.notifyDataSetChanged()
    }

    private fun updateLocations(locations: List<Result>) {
        locations.forEach {
            logError(it.name.orEmpty())
        }
        allLocations.addAll(locations)
        locationsAdapters.notifyDataSetChanged()
    }

    private fun setupAdapter() {
        locationsAdapters = LocationsAdapters(allLocations) { location ->
            listener?.launchDetailsLocation(location)
        }
        binding.recyclerViewLocations.adapter = locationsAdapters
        binding.recyclerViewLocations.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (recyclerView.isLastItemDisplaying()) {
                    viewModel.getLocationsPaginated()
                }
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? LocationsFragmentListener
    }

    override fun onDetach() {
        listener = null
        super.onDetach()
    }

    interface LocationsFragmentListener {
        fun launchDetailsLocation(location: Result)
    }
}