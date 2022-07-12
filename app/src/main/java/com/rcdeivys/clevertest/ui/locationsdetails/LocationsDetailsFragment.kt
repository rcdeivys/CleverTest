package com.rcdeivys.clevertest.ui.locationsdetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.rcdeivys.clevertest.R
import com.rcdeivys.clevertest.activities.MainActivity
import com.rcdeivys.clevertest.common.collectFlow
import com.rcdeivys.clevertest.common.orUnknown
import com.rcdeivys.clevertest.common.showToastShort
import com.rcdeivys.clevertest.databinding.FragmentLocationsDetailsBinding
import com.rcdeivys.clevertest.models.Result
import com.rcdeivys.clevertest.ui.home.actions.HomeActions
import com.rcdeivys.clevertest.ui.home.adapters.CharactersAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class LocationsDetailsFragment : Fragment() {

    private lateinit var binding: FragmentLocationsDetailsBinding
    private val args: LocationsDetailsFragmentArgs by navArgs()
    private val viewModel: LocationsDetailsViewModel by viewModel()

    private lateinit var charactersAdapter: CharactersAdapter
    private var allCharacters = mutableListOf<Result>()

    private var listener: LocationsDetailsFragmentListener? = null

    private val baseActivity by lazy {
        requireActivity() as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationsDetailsBinding.inflate(inflater)
        setData(args.location)
        setupAdapter()

        lifecycleScope.collectFlow(viewModel.getAction()) {
            when (it) {
                is HomeActions.SetCharacters -> setCharacters(it.characters)
                is HomeActions.ShowMessageText -> showToastShort(requireContext(), it.message)
                is HomeActions.SetShowProgress -> baseActivity.showLoading(it.isVisible)
                else -> {}
            }
        }

        return binding.root
    }

    private fun setData(location: Result) {
        binding.apply {
            locationsItemView.apply {
                with(location) {
                    textViewLocationsName.text = name.orUnknown(root.context)
                    textViewLocationsType.text = type.orUnknown(root.context)
                    textViewLocationsDimension.text = dimension.orUnknown(root.context)
                    textViewLocationsResidents.text =
                        if (residents.isNullOrEmpty().not()) {
                            residents?.size.toString()
                        } else {
                            root.context.getString(R.string.unknown)
                        }
                    viewModel.getCharacterById(residents)
                }
            }
        }
    }

    private fun setupAdapter() {
        charactersAdapter = CharactersAdapter(allCharacters) {
            listener?.launchDetails(it)
        }
        binding.recyclerViewCharacters.adapter = charactersAdapter
    }

    private fun setCharacters(characters: List<Result>) {
        allCharacters.clear()
        allCharacters.addAll(characters)
        charactersAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        baseActivity.showLoading(false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? LocationsDetailsFragmentListener
    }

    override fun onDetach() {
        listener = null
        super.onDetach()
    }

    interface LocationsDetailsFragmentListener {
        fun launchDetails(character: Result)
    }
}