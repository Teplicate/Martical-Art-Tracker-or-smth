package com.pretty_apps.martialarttracker.view_models

import androidx.lifecycle.ViewModel
import com.pretty_apps.martialarttracker.util.RoundData

class FightResultViewModel : ViewModel() {

    private lateinit var _fightResults: MutableList<RoundData>
    val fightResults: List<RoundData>
        get() = _fightResults

    fun setFightResults(f: Array<RoundData>) {
        _fightResults = f.toMutableList()
    }

    fun clearData() {
        _fightResults.clear()
    }
}