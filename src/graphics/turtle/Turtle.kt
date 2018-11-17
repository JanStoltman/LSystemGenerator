package graphics.turtle

import cheloniidae.StandardRotationalTurtle
import cheloniidae.Vector
import loader.ObjManager
import model.Mesh
import java.io.File

class Turtle(vecs: Array<Vector>?, var pitch: Double = 90.0, var yaw: Double = 0.0, var roll: Double = 0.0) {
    companion object {
        const val MOVE_FORWARD_LEN = 2.0
        const val YAW_DEG = 45.0
        const val PITCH_DEG = 45.0
        const val ROLL_DEG = 45.0

        const val CYLINDER_PATH = "//home//yggdralisk//Desktop//objs//cylinder.obj"
        const val SPHERE_PATH = "//home//yggdralisk//Desktop//objs//sphere.obj"
        const val BIG_SPHERE_PATH = "//home//yggdralisk//Desktop//objs//big_sphere.obj"
        const val BR_SPHERE_PATH = "//home//yggdralisk//Desktop//objs//sphere_br.obj"

    }

    private var turtle: StandardRotationalTurtle
    val storingMesh: Mesh = Mesh(ArrayList(), arrayOfNulls(0), IntArray(0))
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
        yaw %= 360

        turtle.turn(deg)
    }

    fun pitch(deg: Double = PITCH_DEG) {
        pitch -= deg
        pitch %= 360


        turtle.pitch(deg)
    }

    fun roll(deg: Double = ROLL_DEG) {
        roll += deg
        roll %= 360

        turtle.bank(deg)
    }

    fun drawSphere() {
        loadObject(SPHERE_PATH)
    }

    fun drawBigSphere() {
        loadObject(BIG_SPHERE_PATH)
    }

    fun drawTwigEnd() {
        loadObject(BR_SPHERE_PATH)
    }

    private fun drawCylinder() {
        loadObject(CYLINDER_PATH)
    }

    private fun loadObject(path: String) {
        objManager.loadObj(File(path))

        addObjectToMesh()
    }

    private fun addObjectToMesh() {
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

        for (i in 0 until objManager.mesh!!.vertices.size) {
            val A = objManager.mesh!!.vertices.get(i).coords.x
            val B = objManager.mesh!!.vertices.get(i).coords.y
            val C = objManager.mesh!!.vertices.get(i).coords.z

            val Aa = A * cosz - B * sinz
            val Bb = A * sinz + B * cosz
            val Cc = C

            val Aaa = Cc * siny + Aa * cosy
            val Bbb = Bb
            val Ccc = Cc * cosy - Aa * siny

            val Aaaa = Aaa
            val Bbbb = Bbb * cosx - Ccc * sinx
            val Cccc = Bbb * sinx + Ccc * cosx

            objManager.mesh!!.vertices.get(i).coords.x = Aaaa.toFloat()
            objManager.mesh!!.vertices.get(i).coords.y = Bbbb.toFloat()
            objManager.mesh!!.vertices.get(i).coords.z = Cccc.toFloat()
        }
    }

    private fun appendObjectManagerMeshToStoringMesh() {
        if (storingMesh.vertices.size == 0) {
            storingMesh.vertices.addAll(objManager.mesh!!.vertices)
            storingMesh.faces = objManager.mesh!!.faces.copyOf()
            storingMesh.normals = objManager.mesh!!.normals.copyOf()
            storingMesh.surfaces = objManager.mesh!!.surfaces.copyOf()

            storingMesh.generateMeshBox()
        } else {
            appendMesh(objManager.mesh!!)
        }
    }

    private fun moveObjectManagerMeshBaseToStoringMeshPosition() {
        val tmp = objManager.mesh!!.vertices.map { v ->
            v.coords.x += turtle.position().x.toFloat()
            v.coords.y += turtle.position().y.toFloat()
            v.coords.z += turtle.position().z.toFloat()
            v
        }

        objManager.mesh!!.vertices.clear()
        objManager.mesh!!.vertices.addAll(tmp)

        objManager.mesh!!.faces = objManager.mesh!!.faces.mapIndexed { i, f -> f + storingMesh.vertices.size }.toIntArray()
    }

    fun appendMesh(submesh: Mesh) {
        if (storingMesh.normals == null) {
            storingMesh.vertices = submesh.vertices
            storingMesh.faces = submesh.faces
            storingMesh.normals = submesh.normals
            storingMesh.generateMeshBox()
        } else if (submesh.normals != null && submesh.vertices.size > 0) {
            storingMesh.vertices.addAll(submesh.vertices)
            storingMesh.faces = storingMesh.faces + submesh.faces
            storingMesh.normals = storingMesh.normals + submesh.normals
            storingMesh.generateMeshBox()
        }
    }

    fun getLocationVectors(): Array<Vector>? =
            arrayOf(turtle.position(), turtle.direction(), turtle.directionComplement())
}