package com.pretty_apps.martialarttracker.util

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class TransferContainer(
    val redParameterScore: Map<String, Pair<String, Int>>,
    val blueParameterScoe: Map<String, Pair<String, Int>>
) : Parcelable

@Parcelize
class RoundData(
    val redScore: Short,
    val blueScore: Short,
    val redEffort: FighterEffort,
    val blueEffort: FighterEffort
) : Parcelable {
    var number: Short? = null
}

@Parcelize
data class FighterEffort(
    val name: String,
    val effortList: List<Boolean>
) : Parcelable