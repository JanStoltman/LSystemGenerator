package graphics.turtle

import cheloniidae.StandardRotationalTurtle
import cheloniidae.Vector
import loader.ObjManager
import model.Mesh
import java.io.File

class Turtle(vecs: Array<Vector>?, var pitch: Double = 0.0, var yaw: Double = 0.0, var roll: Double = 0.0) {
    companion object {
        const val MOVE_FORWARD_LEN = 2.0
        const val YAW_DEG = 45.0
        const val PITCH_DEG = 45.0
        const val ROLL_DEG = 45.0

        const val CYLINDER_PATH = "//home//yggdralisk//Desktop//objs//cylinder.obj"
        const val SPHERE_PATH = "//home//yggdralisk//Desktop//objs//sphere.obj"
    }

    private var turtle: StandardRotationalTurtle
    val storingMesh: Mesh = Mesh(FloatArray(0), arrayOfNulls(0), IntArray(0))
    private var objManager: ObjManager = ObjManager()

    init {
        turtle = StandardRotationalTurtle()
        vecs?.let {
            turtle.position(vecs[0])
            turtle.direction(vecs[1])
            turtle.directionComplement(vecs[2])
        }
    }

    fun moveForward(len: Double = MOVE_FORWARD_LEN) {
        drawCylinder()
        turtle.jump(len)
    }

    fun jump(len: Double = MOVE_FORWARD_LEN) {
        turtle.jump(len)
    }

    fun yaw(deg: Double = YAW_DEG) {
        yaw -= deg

        println(turtle.direction())
        turtle.turn(deg)
        println(turtle.direction())

    }

    fun pitch(deg: Double = PITCH_DEG) {
        pitch -= deg

        println(turtle.direction())
        turtle.pitch(deg)
        println(turtle.direction())
    }

    fun roll(deg: Double = ROLL_DEG) {
        roll -= deg
        turtle.bank(deg)
    }

    fun finish() {
        drawSphere()
    }

    fun drawSphere() {
        objManager.loadObj(File(SPHERE_PATH))

        rotateMeshToMatchTurtleHeading()
        moveObjectManagerMeshBaseToStoringMeshPosition()
        appendObjectManagerMeshToStoringMesh()
    }

    private fun drawCylinder() {
        objManager.loadObj(File(CYLINDER_PATH))

        rotateMeshToMatchTurtleHeading()
        moveObjectManagerMeshBaseToStoringMeshPosition()
        appendObjectManagerMeshToStoringMesh()
    }

    private fun rotateMeshToMatchTurtleHeading() {
        val cosz = Math.cos(Math.toRadians(yaw))
        val sinz = Math.sin(Math.toRadians(yaw))

        val cosy = Math.cos(Math.toRadians(roll))
        val siny = Math.sin(Math.toRadians(roll))

        val cosx = Math.cos(Math.toRadians(pitch))
        val sinx = Math.sin(Math.toRadians(pitch))

        for (i in 0 until objManager.mesh!!.vertexCount) {
            val A = objManager.mesh!!.vertices[i * 3]
            val B = objManager.mesh!!.vertices[i * 3 + 1]
            val C = objManager.mesh!!.vertices[i * 3 + 2]

            val Aa = A * cosz - B * sinz
            val Bb = A * sinz + B * cosz
            val Cc = C

            val Aaa = Cc * siny + Aa * cosy
            val Bbb = Bb
            val Ccc = Cc * cosy - Aa * siny

            val Aaaa = Aaa
            val Bbbb = Bbb * cosx - Ccc * sinx
            val Cccc = Bbb * sinx + Ccc * cosx

            objManager.mesh!!.vertices[i * 3] = Aaaa.toFloat()
            objManager.mesh!!.vertices[i * 3 + 1] = Bbbb.toFloat()
            objManager.mesh!!.vertices[i * 3 + 2] = Cccc.toFloat()
        }
    }

    private fun appendObjectManagerMeshToStoringMesh() {
        if (storingMesh.vertexCount == 0) {
            storingMesh.vertices = objManager.mesh!!.vertices.copyOf()
            storingMesh.faces = objManager.mesh!!.faces.copyOf()
            storingMesh.normals = objManager.mesh!!.normals.copyOf()
            storingMesh.surfaces = objManager.mesh!!.surfaces.copyOf()

            storingMesh.generateMeshBox()
        } else {
            appendMesh(objManager.mesh!!)
        }
    }

    private fun moveObjectManagerMeshBaseToStoringMeshPosition() {
        objManager.mesh!!.vertices = objManager.mesh!!.vertices.mapIndexed { i, v ->
            if (i % 3 == 0) {
                (v + turtle.position().x).toFloat()
            } else if (i % 3 == 1) {
                (v + turtle.position().y).toFloat()
            } else if (i % 3 == 2) {
                (v + turtle.position().z).toFloat()
            } else {
                v
            }
        }.toFloatArray()
        objManager.mesh!!.faces = objManager.mesh!!.faces.mapIndexed { i, f -> f + storingMesh.vertexCount }.toIntArray()
    }

    fun appendMesh(submesh: Mesh) {
        if (storingMesh.normals == null) {
            storingMesh.vertices = submesh.vertices
            storingMesh.faces = submesh.faces
            storingMesh.normals = submesh.normals
            storingMesh.generateMeshBox()
        } else if (submesh.normals != null && submesh.vertexCount > 0) {
            storingMesh.vertices = storingMesh.vertices + submesh.vertices
            storingMesh.faces = storingMesh.faces + submesh.faces
            storingMesh.normals = storingMesh.normals + submesh.normals
            storingMesh.generateMeshBox()
        }
    }

    fun getLocationVectors(): Array<Vector>? =
            arrayOf(turtle.position(), turtle.direction(), turtle.directionComplement())
}