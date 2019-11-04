package ru.teplicate.martialarttracker.fragments


import android.graphics.drawable.ShapeDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ru.teplicate.martialarttracker.MutablePair

import ru.teplicate.martialarttracker.R
import ru.teplicate.martialarttracker.custom_view.OnSwipeListener
import ru.teplicate.martialarttracker.custom_view.SwipeableButton
import ru.teplicate.martialarttracker.databinding.FragmentStatisticBinding
import ru.teplicate.martialarttracker.view_models.StatisticViewModel
import ru.teplicate.martialarttracker.view_models.StatisticViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class StatisticFragment : Fragment() {
    private val className = this::class.java.name

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentStatisticBinding>(
            inflater,
            R.layout.fragment_statistic,
            container,
            false
        )
        binding.lifecycleOwner = this

        val blueScoreIds = binding.bluePointsContainer.children.map { it.id }.toList()
        val blueButtonIdToScoreId = binding.blueTilesContainer.children.mapIndexed { index, view ->
            view.id to blueScoreIds[index]
        }

        val redScoreIds = binding.redPointsContainer.children.map { it.id }.toList()
        val redButtonIdToScoreId = binding.redTilesContainer.children.mapIndexed { index, view ->
            view.id to redScoreIds[index]
        }

        val pointsList =
            (binding.redPointsContainer.children + binding.bluePointsContainer.children)

        val scoreViewIdToSelf =
            pointsList
                .map { it.id to it as TextView }.toMap()

        val idToScoreMap = (blueButtonIdToScoreId + redButtonIdToScoreId)
            .map { it.first to it.second }.toMap().toMutableMap()

        val viewModelFactory = StatisticViewModelFactory(idToScoreMap)
        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(StatisticViewModel::class.java)

        val onClickListener = getOnClickListener(viewModel)
        val onSwipeListener = getOnSwipeListener(viewModel)

        (binding.blueTilesContainer.children + binding.redTilesContainer.children).forEach {
            (it as SwipeableButton).setupOnSwipeListener(
                onSwipeListener
            ).setOnClickListener(onClickListener)
        }

        viewModel.textViewIdToUpdate.observe(
            this,
            getScoreViewObserver(viewModel, scoreViewIdToSelf)
        )
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun getOnSwipeListener(viewModel: StatisticViewModel): OnSwipeListener {
        return object : OnSwipeListener {
            override fun onSwipe(view: View) {
                viewModel.decrementScore(view.id)
            }
        }
    }

    private fun getOnClickListener(viewModel: StatisticViewModel) = View.OnClickListener {
        viewModel.incrementScore(it.id)
    }

    private fun getScoreViewObserver(
        viewModel: StatisticViewModel,
        scoreViewIdToSelf: Map<Int, TextView>
    ) = Observer<Int?> { i ->
        i?.let {
            Log.i(className, "Setting value to $i")
            scoreViewIdToSelf.getValue(i).text =
                viewModel.scoreIdToScoreMap.getValue(i).toString()
            scoreViewIdToSelf.getValue(i).invalidate()
        }
    }
}
