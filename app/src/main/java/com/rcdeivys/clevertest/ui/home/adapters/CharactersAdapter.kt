package com.rcdeivys.clevertest.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rcdeivys.clevertest.common.loadImageFromURL
import com.rcdeivys.clevertest.common.orUnknown
import com.rcdeivys.clevertest.databinding.CharacterItemViewBinding
import com.rcdeivys.clevertest.models.Result

class CharactersAdapter(
    private val items: List<Result>,
    private val listener: (Result) -> Unit
) : RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val binding = CharacterItemViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CharactersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    override fun getItemCount() = items.size

    class CharactersViewHolder(private val binding: CharacterItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Result, listener: (Result) -> Unit) = with(item) {
            binding.apply {
                textViewCharacterName.text = name
                textViewCharacterStatus.text = "$status - $species"
                textViewCharacterLocation.text = location?.name.orUnknown(root.context)
                textViewCharacterOrigin.text = origin?.name.orUnknown(root.context)
                imageViewCharacterPhoto.loadImageFromURL(root.context, item.image.orEmpty())
                root.setOnClickListener { listener(item) }
            }
        }
    }
}