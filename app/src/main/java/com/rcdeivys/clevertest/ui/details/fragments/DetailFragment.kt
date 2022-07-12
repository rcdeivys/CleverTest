package com.rcdeivys.clevertest.ui.details.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.rcdeivys.clevertest.R
import com.rcdeivys.clevertest.activities.MainActivity
import com.rcdeivys.clevertest.common.loadImageCircleFromURL
import com.rcdeivys.clevertest.common.orUnknown
import com.rcdeivys.clevertest.databinding.FragmentDetailBinding
import com.rcdeivys.clevertest.models.Result
import com.rcdeivys.clevertest.ui.details.viewmodels.DetailsViewModels
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailsViewModels by viewModel()
    private val args: DetailFragmentArgs by navArgs()

    private val baseActivity by lazy {
        requireActivity() as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater)
        setData(args.character)
        return binding.root
    }

    private fun setData(character: Result) {
        binding.apply {

            with(character) {
                imageViewCharacterPhoto.loadImageCircleFromURL(root.context, image.orEmpty())
                textViewCharacterName.text = name
                textViewCharacterStatus.text = "$status - $species"
                textViewCharacterOrigin.text =
                    origin?.name.orUnknown(root.context)
                textViewCharacterLocation.text =
                    location?.name.orUnknown(root.context)
                textViewCharacterLocationSeen.text = if (episode.isNullOrEmpty().not()) {
                    episode?.get(0).toString()
                } else {
                    root.context.getString(R.string.unknown)
                }
                textViewCharacterGender.text = gender.orUnknown(root.context)
            }
        }
    }
}