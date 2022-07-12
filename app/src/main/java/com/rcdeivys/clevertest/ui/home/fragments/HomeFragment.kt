package com.rcdeivys.clevertest.ui.home.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.rcdeivys.clevertest.activities.MainActivity
import com.rcdeivys.clevertest.common.collectFlow
import com.rcdeivys.clevertest.databinding.FragmentHomeBinding
import com.rcdeivys.clevertest.models.Result
import com.rcdeivys.clevertest.ui.home.actions.HomeActions
import com.rcdeivys.clevertest.ui.home.adapters.CharactersAdapter
import com.rcdeivys.clevertest.ui.home.viewmodels.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModel()
    private var listener: HomeFragmentListener? = null

    private lateinit var charactersAdapter: CharactersAdapter
    private var allCharacters = mutableListOf<Result>()

    private val baseActivity by lazy {
        requireActivity() as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        setupAdapter()

        lifecycleScope.collectFlow(viewModel.getAction()) {
            when (it) {
                is HomeActions.SetCharacters -> setCharacters(it.characters)
                // is HomeActions.ShowMessageText -> showToastShort(requireContext(), it.message)
                // is HomeActions.SetShowProgress -> showLoading(it.isVisible)
                else -> {}
            }
        }

        viewModel.getCharacter()

        return binding.root
    }

    private fun setCharacters(characters: List<Result>) {
        allCharacters.clear()
        allCharacters.addAll(characters)
        charactersAdapter.notifyDataSetChanged()
    }

    private fun setupAdapter() {
        charactersAdapter = CharactersAdapter(allCharacters) { character ->
            listener?.launchDetails(character)
        }
        binding.recyclerViewCharacters.adapter = charactersAdapter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? HomeFragmentListener
    }

    override fun onDetach() {
        listener = null
        super.onDetach()
    }

    interface HomeFragmentListener {
        fun launchDetails(character: Result)
    }
}