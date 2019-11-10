package ru.teplicate.martialarttracker.fragments


import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.NumberPicker
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.databinding.DataBindingUtil

import ru.teplicate.martialarttracker.R
import ru.teplicate.martialarttracker.databinding.FragmentRoundRatingBinding
import ru.teplicate.martialarttracker.util.CompetitorColor
import ru.teplicate.martialarttracker.util.TransferContainer

/**
 * A simple [Fragment] subclass.
 */
class RoundRatingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentRoundRatingBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_round_rating,
                container,
                false
            )

        val fightersParameterScores = RoundRatingFragmentArgs.fromBundle(arguments!!).fightersScores
        fillScoreTable(binding, fightersParameterScores)
        preparePickers(binding)
        prepareCheckboxes(binding)

        return binding.root
    }

    private fun prepareCheckboxes(binding: FragmentRoundRatingBinding) {
        binding.blueWrestleDominanceCheckbox.setOnCheckedChangeListener(
            getOnCheckedChangeListener(
                binding.redWrestleDominanceCheckbox
            )
        )
        binding.blueStanceDominanceCheckbox.setOnCheckedChangeListener(
            getOnCheckedChangeListener(
                binding.redStanceDominanceCheckbox
            )
        )
        binding.redWrestleDominanceCheckbox.setOnCheckedChangeListener(
            getOnCheckedChangeListener(
                binding.blueWrestleDominanceCheckbox
            )
        )
        binding.redStanceDominanceCheckbox.setOnCheckedChangeListener(
            getOnCheckedChangeListener(
                binding.blueStanceDominanceCheckbox
            )
        )
    }

    private fun getOnCheckedChangeListener(oppositeCheckbox: CheckBox): CompoundButton.OnCheckedChangeListener? {
        return CompoundButton.OnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                oppositeCheckbox.isChecked = false
        }
    }

    private fun preparePickers(binding: FragmentRoundRatingBinding) {
        binding.blueRoundScorePicker.maxValue = 10
        binding.blueRoundScorePicker.minValue = 7
        binding.redRoundScorePicker.minValue = 7
        binding.redRoundScorePicker.maxValue = 10
        binding.blueRoundScorePicker.setOnValueChangedListener(
            getPickerOnValueChangedListener(
                binding.redRoundScorePicker
            )
        )
        binding.redRoundScorePicker.setOnValueChangedListener(
            getPickerOnValueChangedListener(
                binding.blueRoundScorePicker
            )
        )
    }

    private fun getPickerOnValueChangedListener(oppositePicker: NumberPicker): NumberPicker.OnValueChangeListener {
        return NumberPicker.OnValueChangeListener { picker, _, newVal ->
            if (newVal != picker.maxValue) {
                oppositePicker.value = oppositePicker.maxValue
            }
        }
    }

    private fun fillScoreTable(
        binding: FragmentRoundRatingBinding,
        fightersParameterScores: TransferContainer
    ) {
        var isTd: Boolean
        var index = 0
        fightersParameterScores.blueParameterScoe.forEach forEach@{ (paramName, score) ->
            if (paramName == resources.getString(R.string.takedown_att_button_text))
                return@forEach
            isTd = paramName == resources.getString(R.string.takedown_button_text)
            binding.statTableHeader.addView(
                createHeaderTextView(
                    paramName,
                    binding.statTableHeader.context
                )
            )
            binding.blueScoresRow.addView(
                createScoreTextView(
                    CompetitorColor.BLUE,
                    if (!isTd) score else {
                        getScore(
                            score,
                            fightersParameterScores.blueParameterScoe.getValue(resources.getString(R.string.takedown_att_button_text))
                        )
                    },
                    binding.blueScoresRow.context,
                    index
                )
            )

            binding.redScoresRow.addView(
                createScoreTextView(
                    CompetitorColor.RED,
                    if (!isTd) fightersParameterScores.redParameterScore.getValue(paramName) else {
                        getScore(
                            fightersParameterScores.redParameterScore.getValue(paramName),
                            fightersParameterScores.redParameterScore.getValue(resources.getString(R.string.takedown_att_button_text))
                        )
                    }
                    ,
                    binding.redScoresRow.context,
                    index
                )
            )
            ++index
        }
    }

    private fun getScore(td: Int, tdAtt: Int) = ((td * 1F / (td + tdAtt)) * 100).toInt() / 100F

    private fun createScoreTextView(
        compColor: CompetitorColor,
        score: Number,
        context: Context,
        index: Int
    ): TextView {
        return TextView(context).also {
            it.background = getScoreBackgroundColor(compColor, index, context)
            it.setTextColor(ContextCompat.getColor(context, R.color.white))
            it.textAlignment = View.TEXT_ALIGNMENT_CENTER
            it.text = score.toString()
            it.textSize = resources.getDimension(R.dimen.cell_text_size)
            it.setPadding(resources.getDimension(R.dimen.cell_padding).toInt())
        }
    }

    private fun getScoreBackgroundColor(
        compColor: CompetitorColor,
        index: Int,
        context: Context
    ): Drawable? {
        return when (compColor) {
            CompetitorColor.RED -> {
                if (index % 2 == 0) ContextCompat.getDrawable(
                    context,
                    R.color.lightRed
                ) else ContextCompat.getDrawable(context, R.color.red)
            }
            CompetitorColor.BLUE -> {
                if (index % 2 == 0) ContextCompat.getDrawable(
                    context,
                    R.color.lightBlue
                ) else ContextCompat.getDrawable(context, R.color.blue)
            }
        }
    }

    private fun createHeaderTextView(paramName: String, context: Context): TextView {
        return TextView(context)
            .also {
                it.setTextColor(ContextCompat.getColor(context, R.color.black))
                it.textAlignment = View.TEXT_ALIGNMENT_CENTER
                it.text = paramName
                it.textSize = resources.getDimension(R.dimen.header_text_size)
                it.setPadding(resources.getDimension(R.dimen.cell_padding).toInt())
            }
    }
}
