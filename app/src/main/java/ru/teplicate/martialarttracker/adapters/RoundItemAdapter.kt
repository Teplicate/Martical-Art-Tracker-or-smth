package ru.teplicate.martialarttracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.teplicate.martialarttracker.databinding.RoundItemBinding
import ru.teplicate.martialarttracker.util.RoundData

class RoundItemAdapter : ListAdapter<RoundData, RoundDataViewHolder>(RoundDataDiffCallback) {
    companion object RoundDataDiffCallback : DiffUtil.ItemCallback<RoundData>() {
        override fun areItemsTheSame(oldItem: RoundData, newItem: RoundData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: RoundData, newItem: RoundData): Boolean {
            return oldItem.number == newItem.number
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoundDataViewHolder {
        val binding = RoundItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RoundDataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoundDataViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class RoundDataViewHolder(private val binding: RoundItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(roundData: RoundData) {
        binding.roundData = roundData
        binding.executePendingBindings()
    }
}