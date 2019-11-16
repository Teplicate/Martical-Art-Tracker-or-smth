package ru.teplicate.martialarttracker.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class StatisticViewModelFactory(private val buttonIdsToScoresMap: MutableMap<Int, Int>) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatisticViewModel::class.java))
            return StatisticViewModel(
                buttonIdsToScoresMap
            ) as T
        else throw IllegalArgumentException("Unknown class ${modelClass.name}")
    }
}

class StatisticViewModel(val buttonIdsToScoresMap: MutableMap<Int, Int>) :
    ViewModel() {

    private val className = this::class.java.name

    val scoreIdToScoreMap =
        buttonIdsToScoresMap.values.map { it to 0 }.toMap().toMutableMap()
    private val _textViewIdToUpdate: MutableLiveData<Int?> = MutableLiveData()
    val textViewIdToUpdate: LiveData<Int?>
        get() = _textViewIdToUpdate


    fun decrementScore(id: Int) {
        Log.i(className, "Score decremented")
        val scoreId = buttonIdsToScoresMap.getValue(id)
        scoreIdToScoreMap[scoreId] =
            if (scoreIdToScoreMap.getValue(scoreId) == 0) 0 else scoreIdToScoreMap.getValue(scoreId) - 1
        _textViewIdToUpdate.value = scoreId
    }

    fun incrementScore(id: Int) {
        Log.i(className, "Clicked to incr $id")
        val scoreId = buttonIdsToScoresMap.getValue(id)
        scoreIdToScoreMap[scoreId] = scoreIdToScoreMap.getValue(scoreId) + 1
        _textViewIdToUpdate.value = scoreId
    }

    fun getButtonIdToScoreValMap(): Map<Int, Int> {
        return buttonIdsToScoresMap.map { (buttonId, scoreId) ->
            buttonId to scoreIdToScoreMap.getValue(scoreId)
        }.toMap()
    }
}