package graphics

import callback.GraphicsGenerateCallback
import cheloniidae.Vector
import graphics.turtle.Turtle
import graphics.turtle.TurtleWrapper
import gui.output.logic.ResultDisplay
import loader.ObjManager
import model.Mesh
import kotlin.math.max
import kotlin.math.min

class GraphicsGenerator {
    private var storingMesh = Mesh(ArrayList(), arrayOfNulls(0), IntArray(0))

    companion object {
        const val BINARY_TREE = "FFFFFF[+FFF[+FF[+F@][-F@]][-FF[+F@][-F@]]][-FFF[+FF[+F@][-F@]][-FF[+F@][-F@]]]"
        const val LOW_POLY_TREE = "FFG[&F@][^FF@][+FF[++F*]G-G@]"
        private val constants = arrayOf('-', '−', '+', 'F', '&', '^', '\\', '/', '[', ']', '@', '*', '#', '%', 'G', 'T', 'Q', 'W')

        @JvmStatic
        fun main(args: Array<String>) {
            GraphicsGenerator().generate3DLSystem("FFF@", 90f, object : GraphicsGenerateCallback {
                override fun onModelGenerated(vertexCount: Long, facesCount: Long) {
                    ResultDisplay().displayOutputView()
                }
            })
        }
    }

    private var angle: Double = 90.0

    fun generate3DLSystem(arg: String, callback: GraphicsGenerateCallback) = generate3DLSystem(arg = arg, angle = 90f, callback = callback)

    fun generate3DLSystem(arg: String, angle: Float = 90f, callback: GraphicsGenerateCallback) {
        this.angle = angle.toDouble();
        storingMesh = generate(arg.filter { it in constants })
        storingMesh.calculateNormals()
        saveLSystem()
        callback.onModelGenerated(storingMesh.vertices.size.toLong(), storingMesh.faceCount.toLong())
        storingMesh = Mesh(ArrayList(), arrayOfNulls(0), IntArray(0))
    }

    private fun generate(arg: String, vecs: Array<Vector>? = null, pitch: Double = 0.0, yaw: Double = 0.0, roll: Double = 0.0): Mesh {
        val turtle: Turtle = TurtleWrapper(vecs, pitch, yaw, roll)
        var subCounter = 0
        for (c in arg.withIndex()) {
            if (subCounter > 0 && c.value != ']') {
                continue
            }
            when (c.value) {
                '-' -> {
                    turtle.yaw(-1 * angle)
                }
                '−' -> {
                    turtle.yaw(-1 * angle)
                }
                '+' -> {
                    turtle.yaw(angle)
                }
                '%' -> {
                    turtle.yaw(angle)
                }
                '#' -> {
                    turtle.yaw(-1 * angle)
                }
                'F' -> {
                    turtle.moveForward()
                }
                'G' -> {
                    turtle.moveForward()
                    turtle.drawBigSphere()
                }
                'T' -> {
                    turtle.drawTwig()
                }
                '&' -> {
                    turtle.pitch(angle)
                }
                '^' -> {
                    turtle.pitch(-1 * angle)
                }
                '\\' -> {
                    turtle.roll()
                }
                '/' -> {
                    turtle.roll(-1 * angle)
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
                    val submesh = generate(arg.substring(startIndex, endIndex), turtle.getLocationVectors(), turtle.getPitch(), turtle.getYaw(), turtle.getRoll())
                    submesh.faces = submesh.faces.mapIndexed { i, f -> f + turtle.storingMesh.vertices.size }.toIntArray()
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
                    turtle.drawBigSphere()
                }
                'Q' -> {
                    turtle.superL()
                }
                'W' ->{
                    turtle.superB()
                }
            }
        }

        //println("TurtleWrapper vertexCount: ${turtle.storingMesh.vertices.size}")

        return turtle.storingMesh
    }

    private fun saveLSystem() {
        val objManager = ObjManager()
        objManager.saveObj(storingMesh, objManager.lighting, objManager.camera, "//home//yggdralisk//Desktop//objs//lsys//lsys.obj")
    }
}
