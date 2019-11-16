package ru.teplicate.martialarttracker.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import ru.teplicate.martialarttracker.R

import ru.teplicate.martialarttracker.adapters.RoundItemAdapter
import ru.teplicate.martialarttracker.databinding.FragmentFightResultsBinding
import ru.teplicate.martialarttracker.util.CompetitorColor
import ru.teplicate.martialarttracker.util.RoundData
import ru.teplicate.martialarttracker.view_models.ActivityViewModel
import kotlin.system.exitProcess

/**
 * A simple [Fragment] subclass.
 */
class FightResultsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFightResultsBinding.inflate(inflater, container, false)
        val activityViewModel =
            ViewModelProviders.of(requireActivity()).get(ActivityViewModel::class.java)
        val roundsAdapter = RoundItemAdapter()
        val roundsList = FightResultsFragmentArgs.fromBundle(
            arguments!!
        ).roundsData
        binding.recyclerRoundsSummary.adapter = roundsAdapter
        roundsAdapter.submitList(roundsList.toList())
        setOverallScores(roundsList, binding)
        setWinner(binding.winnerName, roundsList)
        binding.newFight.setOnClickListener(getOnNewFightClickListener(activityViewModel))
        binding.exit.setOnClickListener(getOnExitClickListner())

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
            blue < red -> setWinnerName(winnerView, R.color.red, CompetitorColor.RED.title)
            blue > red -> setWinnerName(winnerView, R.color.blue, CompetitorColor.BLUE.title)
            else -> setWinnerName(winnerView, R.color.dark_grey, CompetitorColor.DRAW.title)
        }
    }

    private fun setWinnerName(winnerView: TextView, colorRes: Int, s: String) {
        winnerView.text = s
        winnerView.background = ContextCompat.getDrawable(winnerView.context, colorRes)
    }

    private fun getOnExitClickListner(): View.OnClickListener {
        return View.OnClickListener {
            Toast.makeText(context, "Bye", Toast.LENGTH_SHORT).show()
            exitProcess(0)
        }
    }

    private fun getOnNewFightClickListener(activityViewModel: ActivityViewModel): View.OnClickListener {
        return View.OnClickListener {
            activityViewModel.clearRoundsHistory()
            findNavController().navigate(FightResultsFragmentDirections.actionFightResultsFragmentToStatisticFragment())
        }
    }
}
