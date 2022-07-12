package com.rcdeivys.clevertest.ui.locations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rcdeivys.clevertest.R
import com.rcdeivys.clevertest.common.orUnknown
import com.rcdeivys.clevertest.databinding.LocationsItemViewBinding
import com.rcdeivys.clevertest.models.Result

class LocationsAdapters(
    private val items: List<Result>,
    private val listener: (Result) -> Unit
) : RecyclerView.Adapter<LocationsAdapters.LocationsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder {
        val binding = LocationsItemViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LocationsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    override fun getItemCount() = items.size

    class LocationsViewHolder(private val binding: LocationsItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Result, listener: (Result) -> Unit) = with(item) {
            binding.apply {
                with(item) {
                    textViewLocationsName.text = name.orUnknown(root.context)
                    textViewLocationsType.text = type.orUnknown(root.context)
                    textViewLocationsDimension.text = dimension.orUnknown(root.context)
                    textViewLocationsResidents.text =
                        if (residents.isNullOrEmpty().not()) {
                            residents?.size.toString()
                        } else {
                            root.context.getString(R.string.unknown)
                        }
                    root.setOnClickListener { listener(item) }
                }
            }
        }
    }
}