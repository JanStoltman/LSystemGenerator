package graphics

import callback.GraphicsGenerateCallback
import cheloniidae.Vector
import graphics.turtle.Turtle
import loader.ObjManager
import model.Mesh
import kotlin.math.max
import kotlin.math.min

class GraphicsGenerator {
    private var storingMesh = Mesh(FloatArray(0), arrayOfNulls(0), IntArray(0))

    companion object {
        const val KOCH_CURVE = "F++F−F−F++F++F++F−F−F++F−F++F−F−F++F−F++F−F−F++F++F++F−F−F++F++F++F−F−F++F++F++F−F−F++F−F++F−F−F++F−F++F−F−F++F++F++F−F−F++F−F++F−F−F++F++F++F−F−F++F−F++F−F−F++F−F++F−F−F++F++F++F−F−F++F−F++F−F−F++F++F++F−F−F++F−F++F−F−F++F−F++F−F−F++F++F++F−F−F++F++F++F−F−F++F++F++F−F−F++F−F++F−F−F++F−F++F−F−F++F++F++F−F−F++F"
        const val BINARY_TREE = "FFFFFF[+FFF[+FF[+F][-F]][-FF[+F][-F]]][-FFF[+FF[+F][-F]][-FF[+F][-F]]]"
        private val constants = arrayOf('-', '−', '+', 'F', '&', '^', '\\', '/', '[', ']', '@', '*', '#', '%', 'G')

        @JvmStatic
        fun main(args: Array<String>) {
            GraphicsGenerator().generate3DLSystem(BINARY_TREE, object : GraphicsGenerateCallback {
                override fun onModelGenerated() {
                    println("Wygenerowano")
                }
            })
        }
    }

    fun generate3DLSystem(arg: String, callback: GraphicsGenerateCallback) {
        storingMesh = generate(arg.filter { it in constants })
        storingMesh.calculateNormals()
        saveLSystem()
        callback.onModelGenerated()
    }

    private fun generate(arg: String, vecs: Array<Vector>? = null, pitch: Double = 90.0, yaw: Double = 0.0, roll: Double = 0.0): Mesh {
        val turtle = Turtle(vecs, pitch, yaw, roll)
        var subCounter = 0
        for (c in arg.withIndex()) {
            if (subCounter > 0 && c.value != ']') {
                continue
            }
            when (c.value) {
                '-' -> {
                    turtle.yaw(Turtle.YAW_DEG * -1)
                }
                '−' -> {
                    turtle.yaw(-90.0)
                }
                '+' -> {
                    turtle.yaw()
                }
                '%' -> {
                    turtle.yaw(120.0)
                }
                '#' -> {
                    turtle.yaw(-120.0)
                }
                'F', 'G' -> {
                    turtle.moveForward()
                }
                '&' -> {
                    turtle.pitch()
                }
                '^' -> {
                    turtle.pitch(Turtle.PITCH_DEG * -1)
                }
                '\\' -> {
                    turtle.roll()
                }
                '/' -> {
                    turtle.roll(Turtle.ROLL_DEG * -1)
                }
                '[' -> {
                    subCounter += 1
                    val startIndex = min((c.index + 1), arg.length)

                    var ind = 0
                    var tmpsu = 1
                    for (sc in arg.substring(startIndex).withIndex()) {
                        if (sc.value == '[') {
                            tmpsu += 1
                            subCounter += 1
                        } else if (sc.value == ']') {
                            tmpsu -= 1
                        }

                        if (tmpsu == 0) {
                            ind = sc.index + startIndex
                            break
                        }
                    }

                    val endIndex = max(ind, startIndex)
                    val submesh = generate(arg.substring(startIndex, endIndex), turtle.getLocationVectors(), turtle.pitch, turtle.yaw, turtle.roll)
                    submesh.faces = submesh.faces.mapIndexed { i, f -> f + turtle.storingMesh.vertexCount }.toIntArray() //if (i % 2 == 0) turtle.storingMesh.vertexCount else turtle.storingMesh.normalCount
                    turtle.appendMesh(submesh)
                }
                ']' -> {
                    subCounter -= 1
                    if (subCounter < 0) {
                        println("Subcounter is less than zero!")
                    }
                }
                '*' -> {
                    turtle.drawSphere()
                }
                '@' -> {
                    //TODO: Spare symbol
                    turtle.jump(0.5)
                    turtle.drawSphere()
                }
            }
        }

        println("Turtle vertexCount: ${turtle.storingMesh.vertexCount}")

        return turtle.storingMesh
    }

    private fun saveLSystem() {
        val objManager = ObjManager()
        objManager.saveObj(storingMesh, objManager.lighting, objManager.camera, "//home//yggdralisk//Desktop//objs//lsys//lsys.obj")
    }
}
