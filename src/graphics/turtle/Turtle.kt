package graphics.turtle

import cheloniidae.Vector
import model.Mesh

interface Turtle {
    var storingMesh: Mesh

    companion object {
        const val MOVE_FORWARD_LEN = 2.0
        const val YAW_DEG = 45.0
        const val PITCH_DEG = 45.0
        const val ROLL_DEG = 45.0

        const val CYLINDER_PATH = "branch.obj"
        const val SPHERE_PATH = "leaf.obj"
        const val BIG_SPHERE_PATH = "big_leaf.obj"
        const val BR_SPHERE_PATH = "sphere_br.obj"
        const val TWIG_PATH = "twig.obj"
        const val SUPERBRANCH = "superbranch.obj"
        const val SUPERLEAF = "superleaves.obj"

    }

    fun moveForward(len: Double = MOVE_FORWARD_LEN)

    fun jump(len: Double = MOVE_FORWARD_LEN)

    fun yaw(deg: Double = YAW_DEG)

    fun pitch(deg: Double = PITCH_DEG)

    fun roll(deg: Double = ROLL_DEG)

    fun drawSphere()

    fun drawBigSphere()

    fun drawTwigEnd()

    fun drawTwig()
    fun getLocationVectors(): Array<Vector>?
    fun appendMesh(submesh: Mesh)
    fun getPitch(): Double
    fun getYaw(): Double
    fun getRoll(): Double
    fun superL()
    fun superB()
}