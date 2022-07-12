package com.rcdeivys.clevertest.ui.details.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.rcdeivys.clevertest.activities.MainActivity
import com.rcdeivys.clevertest.common.collectFlow
import com.rcdeivys.clevertest.common.loadImageCircleFromURL
import com.rcdeivys.clevertest.common.orUnknown
import com.rcdeivys.clevertest.databinding.FragmentDetailBinding
import com.rcdeivys.clevertest.models.EpisodeResponse
import com.rcdeivys.clevertest.models.Result
import com.rcdeivys.clevertest.ui.details.viewmodels.DetailsViewModel
import com.rcdeivys.clevertest.ui.home.actions.HomeActions
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailsViewModel by viewModel()
    private val args: DetailFragmentArgs by navArgs()

    private val baseActivity by lazy {
        requireActivity() as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater)
        setData(args.character)
        lifecycleScope.collectFlow(viewModel.getAction()) {
            when (it) {
                is HomeActions.SetEpisode -> setEpisode(it.episodeResponse)
                else -> {}
            }
        }
        return binding.root
    }

    private fun setEpisode(episodeResponse: EpisodeResponse) {
        binding.apply {
            with(episodeResponse) {
                textViewCharacterEpisodeName.text = name.orUnknown(root.context)
                textViewCharacterEpisodeAirDate.text = airDate.orEmpty()
                textViewCharacterEpisode.text = episode.orEmpty()
            }
        }
    }

    private fun setData(character: Result) {
        binding.apply {
            with(character) {
                viewModel.getEpisode(getFirstSeen().toInt())
                imageViewCharacterPhoto.loadImageCircleFromURL(root.context, image.orEmpty())
                textViewCharacterName.text = name
                textViewCharacterStatus.text = "$status - $species"
                textViewCharacterOrigin.text =
                    origin?.name.orUnknown(root.context)
                textViewCharacterLocation.text =
                    location?.name.orUnknown(root.context)
                textViewCharacterGender.text = gender.orUnknown(root.context)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        baseActivity.showLoading(false)
    }
}