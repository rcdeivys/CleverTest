package com.rcdeivys.clevertest.ui.home.fragments

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
import com.rcdeivys.clevertest.common.showToastShort
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
                is HomeActions.UpdateCharacters -> updateCharacter(it.characters)
                is HomeActions.ShowMessageText -> showToastShort(requireContext(), it.message)
                is HomeActions.SetShowProgress -> baseActivity.showLoading(it.isVisible)
                else -> {}
            }
        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCharacter()
    }

    private fun setCharacters(characters: List<Result>) {
        allCharacters.clear()
        allCharacters.addAll(characters)
        charactersAdapter.notifyDataSetChanged()
    }

    private fun updateCharacter(characters: List<Result>) {
        binding.progress.hide()
        allCharacters.addAll(characters)
        charactersAdapter.notifyDataSetChanged()
    }

    private fun setupAdapter() {
        charactersAdapter = CharactersAdapter(allCharacters) { character ->
            listener?.launchDetails(character)
        }
        binding.recyclerViewCharacters.adapter = charactersAdapter
        binding.recyclerViewCharacters.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (recyclerView.isLastItemDisplaying()) {
                    binding.progress.show()
                    viewModel.getCharacterPaginated()
                }
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? HomeFragmentListener
    }

    override fun onDetach() {
        listener = null
        super.onDetach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        baseActivity.showLoading(false)
    }

    interface HomeFragmentListener {
        fun launchDetails(character: Result)
    }
}