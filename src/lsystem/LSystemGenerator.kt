package lsystem

import callback.LSytstemGenerateCallback
import model.LSystemGeneratorData

class LSystemGenerator {
    companion object {
        fun generateLSystem(data: LSystemGeneratorData, callbackLSytstem: LSytstemGenerateCallback) {
            LSystemGenerator().generateLSystem(data, callbackLSytstem)
        }
    }

    fun generateLSystem(data: LSystemGeneratorData, callbackLSytstem: LSytstemGenerateCallback) {
        var lSystem = data.axiom

        for (i in 0 until data.iterations) {
            lSystem = lSystem.map { ch ->
                data.transformations.find { it.first.first() == ch }?.second ?: ch
            }.joinToString("")
        }

        callbackLSytstem.onGenerated(lSystem)
    }
}