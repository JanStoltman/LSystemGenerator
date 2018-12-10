package model

import java.util.ArrayList

data class LSystemGeneratorData(
        var axiom: String,
        val iterations: Int,
        val transformations: ArrayList<Pair<String, String>>,
        var angle: Float = 45f
)