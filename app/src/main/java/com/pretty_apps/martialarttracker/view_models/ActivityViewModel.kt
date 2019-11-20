package com.pretty_apps.martialarttracker.view_models

import androidx.lifecycle.ViewModel
import com.pretty_apps.martialarttracker.util.RoundData

class ActivityViewModel : ViewModel() {
    private val _rounds = ArrayList<RoundData>()
    val rounds: List<RoundData>
        get() = _rounds

    fun passRoundData(roundData: RoundData) {
        roundData.number = (_rounds.size + 1).toShort()
        _rounds.add(roundData)
    }

    fun clearRoundsHistory() {
        _rounds.clear()
    }
}