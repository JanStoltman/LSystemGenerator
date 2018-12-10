package test

import callback.GraphicsGenerateCallback
import graphics.GraphicsGenerator
import org.junit.Before
import org.junit.Test

class GenerationTests {
    lateinit var generator: GraphicsGenerator

    private var testIndx = 1

    @Before
    fun setup() {
        generator = GraphicsGenerator()
    }

    @Test
    fun emptyLsys() {
        printIndx()
        val start = System.nanoTime()
        generator.generate3DLSystem("", object: GraphicsGenerateCallback{
            override fun onModelGenerated(vertexCount: Long, facesCount: Long) {
                printRes(vertexCount, facesCount, start)
                println()
            }
        })
    }

    @Test
    fun noCommandsLsys() {
        printIndx()
        val start = System.nanoTime()
        generator.generate3DLSystem("xxxaaaa", object: GraphicsGenerateCallback{
            override fun onModelGenerated(vertexCount: Long, facesCount: Long) {
                printRes(vertexCount, facesCount, start)
            }
        })
    }


    @Test
    fun singleF() {
        printIndx()
        val start = System.nanoTime()
        generator.generate3DLSystem("F", object: GraphicsGenerateCallback{
            override fun onModelGenerated(vertexCount: Long, facesCount: Long) {
                printRes(vertexCount, facesCount, start)
            }
        })
    }

    @Test
    fun turnsF() {
        printIndx()
        val start = System.nanoTime()
        generator.generate3DLSystem("+F", object: GraphicsGenerateCallback{
            override fun onModelGenerated(vertexCount: Long, facesCount: Long) {
                printRes(vertexCount, facesCount, start)
            }
        })
    }

    @Test
    fun doubleFTurns() {
        printIndx()
        val start = System.nanoTime()
        generator.generate3DLSystem("F+\\F", object: GraphicsGenerateCallback{
            override fun onModelGenerated(vertexCount: Long, facesCount: Long) {
                printRes(vertexCount, facesCount, start)
            }
        })
    }

    @Test
    fun bigLSys() {
        printIndx()
        val start = System.nanoTime()
        generator.generate3DLSystem("FFF[FF@+F*]FF[\\FFFF@@@][FFFFFFFFFFFFFFFFFFFFF+++FFFFFF][*FFFF*FFFFFFFFF*FFFFF]", object: GraphicsGenerateCallback{
            override fun onModelGenerated(vertexCount: Long, facesCount: Long) {
                printRes(vertexCount, facesCount, start)
            }
        })
    }

    @Test
    fun multipleBranches() {
        printIndx()
        val start = System.nanoTime()
        generator.generate3DLSystem("FFF[FF@+F*]FF[\\FFFF@@@][FFFFFFF][FFFF][FFFFFFFFF][F+++FFFFFF][*FFFF*][FFFF][FFFFF*][FFFFF][\\FFFF@@@][FFFFFFF][FFFF][FFFFFFFFF][F+++FFFFFF][*FFFF*][FFFF][FFFFF*][FFFFF][\\FFFF@@@][FFFFFFF][FFFF][FFFFFFFFF][F+++FFFFFF][*FFFF*][FFFF][FFFFF*][FFFFF]", object: GraphicsGenerateCallback{
            override fun onModelGenerated(vertexCount: Long, facesCount: Long) {
                printRes(vertexCount, facesCount, start)
            }
        })
    }

    @Test
    fun hugeLSys() {
        printIndx()
        val start = System.nanoTime()
        val lsys = "FFFFFFFFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\+FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]]FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][-&FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][\\FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][\\+FFFFFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\+FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]]FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][-&FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][\\FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*]]FFFFFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\+FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]]FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][-&FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][\\FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][-&FFFFFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\+FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]]FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][-&FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][\\FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*]*][\\FFFFFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\+FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]]FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][-&FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][\\FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*]*][\\+FFFFFFFFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\+FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]]FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][-&FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][\\FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][\\+FFFFFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\+FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]]FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][-&FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][\\FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*]]FFFFFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\+FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]]FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][-&FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][\\FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][-&FFFFFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\+FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]]FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][-&FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][\\FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*]*][\\FFFFFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\+FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]]FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][-&FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][\\FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*]*]]FFFFFFFFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\+FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]]FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][-&FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][\\FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][\\+FFFFFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\+FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]]FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][-&FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][\\FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*]]FFFFFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\+FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]]FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][-&FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][\\FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][-&FFFFFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\+FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]]FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][-&FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][\\FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*]*][\\FFFFFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\+FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]]FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][-&FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][\\FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*]*][-&FFFFFFFFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\+FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]]FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][-&FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][\\FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][\\+FFFFFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\+FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]]FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][-&FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][\\FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*]]FFFFFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\+FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]]FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][-&FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][\\FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][-&FFFFFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\+FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]]FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][-&FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][\\FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*]*][\\FFFFFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\+FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]]FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][-&FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][\\FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*]*]*][\\FFFFFFFFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\+FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]]FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][-&FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][\\FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][\\+FFFFFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\+FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]]FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][-&FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][\\FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*]]FFFFFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\+FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]]FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][-&FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][\\FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][-&FFFFFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\+FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]]FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][-&FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][\\FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*]*][\\FFFFFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\+FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]]FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][-&FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*][\\FFFFFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][\\+FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]]FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*][-&FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*][\\FFFFFFX[\\+FFFX]FFFX[-&FFFX*][\\FFFX*]*]*]*]*]"
        generator.generate3DLSystem(lsys, object: GraphicsGenerateCallback{
            override fun onModelGenerated(vertexCount: Long, facesCount: Long) {
                printRes(vertexCount, facesCount, start)
                println("Lsys length: ${lsys.length}")
            }
        })
    }

    @Test
    fun singleFL() {
        printIndx()
        val start = System.nanoTime()
        generator.generate3DLSystem("F*", object: GraphicsGenerateCallback{
            override fun onModelGenerated(vertexCount: Long, facesCount: Long) {
                printRes(vertexCount, facesCount, start)
            }
        })
    }

    private fun printRes(vertexCount: Long, facesCount: Long, start: Long) {
        println("Vertex: $vertexCount")
        println("Faces: $facesCount")
        println("Has taken: ${(System.nanoTime() - start) * 1.0e-9} s")
    }

    private fun printIndx() {
        println("Test $testIndx: ")
        testIndx++
    }

}