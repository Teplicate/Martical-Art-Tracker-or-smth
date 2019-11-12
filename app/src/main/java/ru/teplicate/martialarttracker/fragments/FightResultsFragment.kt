package ru.teplicate.martialarttracker.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_statistic.view.*

import ru.teplicate.martialarttracker.R
import ru.teplicate.martialarttracker.adapters.RoundItemAdapter
import ru.teplicate.martialarttracker.databinding.FragmentFightResultsBinding

/**
 * A simple [Fragment] subclass.
 */
class FightResultsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFightResultsBinding.inflate(inflater, container, false)
        val roundsAdapter = RoundItemAdapter()
        val roundsList = FightResultsFragmentArgs.fromBundle(arguments!!).roundsData
        binding.recyclerRoundsSummary.adapter = roundsAdapter
        roundsAdapter.submitList(roundsList.toList())

        return binding.root
    }
}
