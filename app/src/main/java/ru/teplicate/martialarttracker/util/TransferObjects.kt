package ru.teplicate.martialarttracker.util

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class TransferContainer(
    val redParameterScore: Map<String, Int>,
    val blueParameterScoe: Map<String, Int>
): Parcelable