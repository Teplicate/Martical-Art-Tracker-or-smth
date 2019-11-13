package ru.teplicate.martialarttracker.util

import ru.teplicate.martialarttracker.R

enum class ParamScoreName(val title: String) {
    TAKEDOWN("Takedown"),
    SIGNIFICANT_STRIKE("Sig. str."),
    STRIKE("Strike"),
    SUBMISSION_ATTEMPT("Submission att."),
    TAKEDOWN_ATTEMPT("Takedown att."),
    PASSES("Passes"),
    REVERSALS("Reversals")
}

enum class ParamBoolName(val title: String) {
    //bool
    AGRESSIVNESS("Agressivness"),
    DAMAGE_DEALT("Damage dealt"),
    STANCE_DOMINANCE("Stance dominance"),
    WRESTLE_DOMINANCE("Wrestle dominance")
    //bool
}

enum class CompetitorColor(val title: String) {
    RED("RED"),
    BLUE("BLUE"),
    DRAW("DRAW")
}