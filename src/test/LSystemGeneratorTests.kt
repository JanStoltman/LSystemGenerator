package test

import callback.LSytstemGenerateCallback
import lsystem.LSystemGenerator
import model.LSystemGeneratorData
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class LSystemGeneratorTests {

    lateinit var generator: LSystemGenerator

    @Before
    fun setup() {
        generator = LSystemGenerator()
    }

    @Test
    fun emptyTest() {
    }

    @Test
    fun emptyString_emptyResult() {
        val data = LSystemGeneratorData("", 0, arrayListOf())
        generator.generateLSystem(data, object : LSytstemGenerateCallback {
            override fun onGenerated(lSystem: String) {
                assertEquals("", lSystem)
            }
        })
    }

    @Test
    fun zeroIterations_axiom() {
        val axiom = "X"
        val data = LSystemGeneratorData(axiom, 0, arrayListOf())
        generator.generateLSystem(data, object : LSytstemGenerateCallback {
            override fun onGenerated(lSystem: String) {
                assertEquals(axiom, lSystem)
            }
        })
    }

    @Test
    fun x_xx() {
        val axiom = "X"
        val data = LSystemGeneratorData(axiom, 1, arrayListOf(Pair("X", "xx")))
        generator.generateLSystem(data, object : LSytstemGenerateCallback {
            override fun onGenerated(lSystem: String) {
                assertEquals("xx", lSystem)
            }
        })
    }

    @Test
    fun multipleIterations_multipleX() {
        val axiom = "x"
        val data = LSystemGeneratorData(axiom, 5, arrayListOf(Pair("x", "xx")))
        generator.generateLSystem(data, object : LSytstemGenerateCallback {
            override fun onGenerated(lSystem: String) {
                assertEquals("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", lSystem)
            }
        })
    }

    @Test
    fun multipleTransformations() {
        val axiom = "x"
        val data = LSystemGeneratorData(axiom, 4, arrayListOf(Pair("a", "xx"), Pair("x", "a")))
        generator.generateLSystem(data, object : LSytstemGenerateCallback {
            override fun onGenerated(lSystem: String) {
                assertEquals("xxxx", lSystem)
            }
        })
    }

    @Test
    fun multipleTransformations_emptyAxiom() {
        val axiom = ""
        val data = LSystemGeneratorData(axiom, 4, arrayListOf(Pair("a", "xx"), Pair("x", "a")))
        generator.generateLSystem(data, object : LSytstemGenerateCallback {
            override fun onGenerated(lSystem: String) {
                assertEquals("", lSystem)
            }
        })
    }

    @Test
    fun algaeLsystem_test() {
        val axiom = "A"
        val data = LSystemGeneratorData(axiom, 7,
                arrayListOf(Pair("A", "AB"), Pair("B", "A")))
        val expectedResult = "ABAABABAABAABABAABABAABAABABAABAAB"
        generator.generateLSystem(data, object : LSytstemGenerateCallback {
            override fun onGenerated(lSystem: String) {
                assertEquals(expectedResult, lSystem)
            }
        })
    }

    @Test
    fun kochCurve_test() {
        val axiom = "F"
        val data = LSystemGeneratorData(axiom, 3,
                arrayListOf(Pair("F", "F+F−F−F+F")))


        val expectedResult = "F+F−F−F+F+F+F−F−F+F−F+F−F−F+F−F+F−F−F+F+F+F−F−F+F+" +
                "F+F−F−F+F+F+F−F−F+F−F+F−F−F+F−F+F−F−F+F+F+F−F−F+F−" +
                "F+F−F−F+F+F+F−F−F+F−F+F−F−F+F−F+F−F−F+F+F+F−F−F+F−" +
                "F+F−F−F+F+F+F−F−F+F−F+F−F−F+F−F+F−F−F+F+F+F−F−F+F+" +
                "F+F−F−F+F+F+F−F−F+F−F+F−F−F+F−F+F−F−F+F+F+F−F−F+F"

        generator.generateLSystem(data, object : LSytstemGenerateCallback {
            override fun onGenerated(lSystem: String) {
                assertEquals(expectedResult, lSystem)
            }
        })
    }

}