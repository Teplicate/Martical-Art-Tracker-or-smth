package ru.teplicate.martialarttracker.binding

import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.databinding.BindingAdapter
import ru.teplicate.martialarttracker.R
import ru.teplicate.martialarttracker.util.CompetitorColor
import ru.teplicate.martialarttracker.util.RoundData

@BindingAdapter("round_number")
fun bindRoundNumber(textView: TextView, round: Short?) {
    round?.let {
        textView.text = textView.context.resources.getString(R.string.round_number, it)
    }
}

@BindingAdapter("fighters_efforts")
fun bindEfforts(tableRow: TableRow, roundData: RoundData?) {
    var container: LinearLayout
    roundData?.let {
        for (i in 0 until roundData.blueEffort.effortList.size) {
            container = getContainer(tableRow.context)
            if (roundData.blueEffort.effortList[i])
                container.addView(
                    getMark(
                        tableRow.context,
                        R.drawable.blue_mark
                    )
                )
            else container.addView(
                getMark(
                    tableRow.context,
                    R.drawable.transparent_mark
                )
            )
            if (roundData.redEffort.effortList[i])
                container.addView(
                    getMark(
                        tableRow.context,
                        R.drawable.red_mark
                    )
                )
            else container.addView(
                getMark(
                    tableRow.context,
                    R.drawable.transparent_mark
                )
            )
            tableRow.addView(container, TableRow.LayoutParams(i))
        }
    }

    tableRow.invalidate()
}

@BindingAdapter("red_score")
fun bindRedScore(textView: TextView, roundData: RoundData?) {
    roundData?.let {
        textView.text = roundData.redScore.toString()
    }
}

@BindingAdapter("blue_score")
fun bindBlueScore(textView: TextView, roundData: RoundData?) {
    roundData?.let {
        textView.text = roundData.blueScore.toString()
    }
}

@BindingAdapter("winner")
fun bindWinner(textView: TextView, roundData: RoundData?) {
    roundData?.let {
        when {
            roundData.blueScore > roundData.redScore -> {
                textView.setTextColor(
                    ContextCompat.getColor(
                        textView.context,
                        R.color.blue
                    )
                )
                textView.text = CompetitorColor.BLUE.title
            }
            roundData.blueScore < roundData.redScore -> {
                textView.setTextColor(
                    ContextCompat.getColor(
                        textView.context,
                        R.color.red
                    )
                )
                textView.text = CompetitorColor.RED.title
            }
            else -> {
                textView.setTextColor(
                    ContextCompat.getColor(
                        textView.context,
                        R.color.black
                    )
                )
                textView.text = CompetitorColor.DRAW.title
            }
        }
    }
}

private fun getMark(context: Context, color: Int): ImageView {
    return getImageView(context, color)
}

private fun getImageView(context: Context, imgResource: Int): ImageView {
    return ImageView(context)
        .also {
            it.setPadding(
                context.resources.getDimension(R.dimen.cell_padding).toInt()
            )
            it.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            it.setImageResource(imgResource)
        }
}

private fun getContainer(context: Context): LinearLayout {
    val l = LinearLayout(context)
    l.layoutParams = ViewGroup.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.MATCH_PARENT
    )
    l.gravity = Gravity.CENTER
    l.orientation = LinearLayout.HORIZONTAL

    return l
}