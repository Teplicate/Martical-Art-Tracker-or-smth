package ru.teplicate.martialarttracker.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_statistic.view.*

import ru.teplicate.martialarttracker.R
import ru.teplicate.martialarttracker.adapters.RoundItemAdapter
import ru.teplicate.martialarttracker.databinding.FragmentFightResultsBinding
import ru.teplicate.martialarttracker.util.RoundData

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
        setOverallScores(roundsList, binding)
        setWinner(binding.winnerName, roundsList)

        return binding.root
    }

    private fun setOverallScores(
        roundsList: Array<RoundData>,
        binding: FragmentFightResultsBinding
    ) {
        val blue = roundsList.sumBy { it.blueScore.toInt() }
        val red = roundsList.sumBy { it.redScore.toInt() }

        binding.redOverall.text = red.toString()
        binding.blueOverall.text = blue.toString()
    }

    private fun setWinner(winnerView: TextView, roundsList: Array<RoundData>) {
        var blue = 0
        var red = 0
        roundsList.forEach { r ->
            if (r.redScore > r.blueScore)
                red++
            else if (r.redScore < r.blueScore)
                blue++
        }

        when {
            blue < red -> setWinnerName(winnerView, R.color.red, "RED")
            blue > red -> setWinnerName(winnerView, R.color.blue, "BLUE")
            else -> setWinnerName(winnerView, R.color.dark_grey, "DRAW")
        }
    }

    private fun setWinnerName(winnerView: TextView, colorRes: Int, s: String) {
        winnerView.text = s
        winnerView.background = ContextCompat.getDrawable(winnerView.context, colorRes)
    }
}
