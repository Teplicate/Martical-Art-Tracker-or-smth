package ru.teplicate.martialarttracker.view_models

import androidx.lifecycle.ViewModel
import ru.teplicate.martialarttracker.util.RoundData

class ActivityViewModel : ViewModel() {
    private val _rounds = ArrayList<RoundData>()
    val rounds: List<RoundData>
        get() = _rounds

    fun passRoundData(roundData: RoundData) {
        roundData.number = (_rounds.size + 1).toShort()
        _rounds.add(roundData)
    }
}