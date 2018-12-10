package graphics.turtle

import cheloniidae.StandardRotationalTurtle
import cheloniidae.Vector
import graphics.turtle.Turtle.Companion.BIG_SPHERE_PATH
import graphics.turtle.Turtle.Companion.BR_SPHERE_PATH
import graphics.turtle.Turtle.Companion.CYLINDER_PATH
import graphics.turtle.Turtle.Companion.SPHERE_PATH
import graphics.turtle.Turtle.Companion.SUPERBRANCH
import graphics.turtle.Turtle.Companion.SUPERLEAF
import graphics.turtle.Turtle.Companion.TWIG_PATH
import loader.ObjManager
import model.Mesh
import java.io.File

class TurtleWrapper(vecs: Array<Vector>?, private var pitch: Double = 90.0, private var yaw: Double = 0.0, private var roll: Double = 0.0) : Turtle {
    override var storingMesh = Mesh(ArrayList(), arrayOfNulls(0), IntArray(0))
    private var turtle: StandardRotationalTurtle
    private var objManager: ObjManager = ObjManager()

    private var volPos: Vector = Vector(0.0, 0.0, 0.0)

    init {
        turtle = StandardRotationalTurtle()
        vecs?.let {
            volPos = vecs[0]
            turtle.position(vecs[0])
            turtle.direction(vecs[1])
            turtle.directionComplement(vecs[2])
        }
    }

    override fun moveForward(len: Double) {
        drawCylinder()
        turtle.jump(len)
    }

    override fun jump(len: Double) {
        turtle.jump(len)
    }

    override fun yaw(deg: Double) {
        yaw -= deg
        yaw %= 360

        turtle.turn(deg)
    }

    override fun pitch(deg: Double) {
        pitch -= deg
        pitch %= 360


        turtle.pitch(deg)
    }

    override fun roll(deg: Double) {
        roll += deg
        roll %= 360

        turtle.bank(deg)
    }

    override fun superL(){
        loadObject(SUPERLEAF)
    }

    override fun superB(){
        loadObject(SUPERBRANCH)
    }

    override fun drawSphere() {
        val height = turtle.position().y - volPos.y

        if (height / 2 > turtle.position().y / 2) {
            loadObject(BIG_SPHERE_PATH)
        } else {
            loadObject(SPHERE_PATH)
        }
    }

    override fun drawBigSphere() {
        loadObject(BIG_SPHERE_PATH)
    }

    override fun drawTwigEnd() {
        loadObject(BR_SPHERE_PATH)
    }

    override fun drawTwig() {
        loadObject(TWIG_PATH)
    }

    private fun drawCylinder() {
        loadObject(CYLINDER_PATH)
    }

    private fun loadObject(path: String) {
        objManager.loadObj(File(path))

        addObjectToMesh()
    }

    override fun getPitch(): Double = pitch

    override fun getYaw(): Double = yaw

    override fun getRoll(): Double = roll

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

    override fun appendMesh(submesh: Mesh) {
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

    override fun getLocationVectors(): Array<Vector>? =
            arrayOf(turtle.position(), turtle.direction(), turtle.directionComplement())
}