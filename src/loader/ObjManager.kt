package loader

import model.*
import model.Vector
import java.awt.Component
import java.io.*
import java.util.*
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

class ObjManager {

    private val fc = JFileChooser()
    private var parent: Component? = null
    private var vertices: MutableList<Float>? = null
    private var surfaces: MutableList<Surface>? = null
    private var faces: MutableList<Int>? = null
    private var normals: MutableList<Float>? = null
    private var currentSurface: Int = 0
    private var hasNormals: Boolean = false

    var mesh: Mesh? = null
        private set
    var camera: Camera? = null
        private set
    var lighting: Lighting? = null
        private set

    constructor(parent: Component) {
        this.parent = parent
        val filter = FileNameExtensionFilter("OBJ Files", "obj")
        fc.fileFilter = filter
        fc.currentDirectory = File("//home//yggdralisk//Desktop//objs//lsys")
        camera = Camera()
    }

    constructor() {
        camera = Camera()
        lighting = Lighting()
    }

    fun loadObj(file: File): Boolean {
        mesh = null
        hasNormals = false
        camera = Camera()
        lighting = Lighting()
        vertices = LinkedList()
        surfaces = LinkedList()
        surfaces!!.add(Surface())
        faces = LinkedList()
        normals = LinkedList()

        val fr: FileReader
        val br: BufferedReader
        try {
            fr = FileReader(file)
            br = BufferedReader(fr)
            var line: String? = br.readLine()
            while (line != null) {
                if (line.length >= 2 && line.startsWith("v "))
                    addVert(line)
                if (line.length >= 2 && line.startsWith("f "))
                    addFace(line)
                if (line.length >= 2 && line.startsWith("vn "))
                    addNormal(line)
                if (line.length >= 2 && line.startsWith("#a "))
                    addAmbient(line)
                if (line.length >= 2 && line.startsWith("#d "))
                    addDiffuse(line)
                if (line.length >= 2 && line.startsWith("#sp "))
                    addSpecular(line)
                if (line.length >= 2 && line.startsWith("#sh "))
                    addShininess(line)
                if (line.length >= 2 && line.startsWith("#p "))
                    addPosition(line)
                if (line.length >= 2 && line.startsWith("s "))
                    setCurrentSurface(line)
                if (line.length >= 2 && line.startsWith("#angle "))
                    addAngle(line)
                line = br.readLine()
            }
            mesh = Mesh(toArrayF(vertices!!), toArrayF(normals!!), toArrayS(surfaces!!), toArrayI(faces!!))
            if (!hasNormals) {
                mesh!!.calculateNormals()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            mesh = null
        }

        try {
            val bw = BufferedWriter(FileWriter("//home//yggdralisk//Desktop//objs//lsys//meshData.txt"))
            println(mesh!!.faceCount)
            for (i in 0 until mesh!!.faceCount) {

                bw.write(0.0f.toString() + System.lineSeparator())
                bw.write(0.0f.toString() + System.lineSeparator())
                bw.write(1f.toString() + System.lineSeparator())

                bw.write(mesh!!.getVC(mesh!!.getFV(i, 0), 0).toString() + System.lineSeparator())
                bw.write(mesh!!.getVC(mesh!!.getFV(i, 0), 1).toString() + System.lineSeparator())
                bw.write(mesh!!.getVC(mesh!!.getFV(i, 0), 2).toString() + System.lineSeparator())

                bw.write(mesh!!.getVC(mesh!!.getFV(i, 1), 0).toString() + System.lineSeparator())
                bw.write(mesh!!.getVC(mesh!!.getFV(i, 1), 1).toString() + System.lineSeparator())
                bw.write(mesh!!.getVC(mesh!!.getFV(i, 1), 2).toString() + System.lineSeparator())

                bw.write(mesh!!.getVC(mesh!!.getFV(i, 2), 0).toString() + System.lineSeparator())
                bw.write(mesh!!.getVC(mesh!!.getFV(i, 2), 1).toString() + System.lineSeparator())
                bw.write(mesh!!.getVC(mesh!!.getFV(i, 2), 2).toString() + System.lineSeparator())

                bw.write(System.lineSeparator())

            }
            bw.flush()
            bw.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return mesh != null
    }

    private fun setCurrentSurface(line: String) {
        val strS = line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        try {
            currentSurface = Integer.parseInt(strS[1]) - 1
            while (surfaces!!.size <= currentSurface) {
                surfaces!!.add(Surface())
            }
        } catch (e: Exception) {
            println("Wrong Surface")
        }
    }

    private fun addVert(line: String) {
        val strV = line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (i in 1 until strV.size) {
            try {
                vertices!!.add(java.lang.Float.parseFloat(strV[i]))
            } catch (e: Exception) {
            }
        }
    }

    private fun addFace(line: String) {
        val strF = line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (i in 1..3) {

            try {
                if (hasNormals) {
                    val face = strF[i].split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    faces!!.add(Integer.parseInt(face[0]) - 1)
                    faces!!.add(Integer.parseInt(face[2]) - 1)
                } else {
                    faces!!.add(Integer.parseInt(strF[i]) - 1)
                    faces!!.add(Integer.parseInt(strF[i]) - 1)
                }
            } catch (e: Exception) {
                println("Wrong face")
            }

        }
        faces!!.add(currentSurface)
        //println(faces!![faces!!.size - 1])
        //println(faces)
    }

    private fun addNormal(line: String) {
        hasNormals = true
        val strN = line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (i in 1 until strN.size) {
            try {
                normals!!.add(java.lang.Float.parseFloat(strN[i]))
            } catch (e: Exception) {
            }

        }
        //println(normals)
    }

    private fun addAmbient(line: String) {
        val parts = line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        try {
            val component = FloatArray(4)
            for (i in 2..5) {
                component[i - 2] = java.lang.Float.parseFloat(parts[i])
            }
            if (parts[1].compareTo("l") == 0)
                lighting!!.ambient = component
            else if (parts[1].compareTo("m") == 0)
                lighting!!.lmAmbient = component
            else {
                val s = Integer.parseInt(parts[1]) - 1
                surfaces!![s].ambient = component
            }
        } catch (e: Exception) {
            println("Wrogn ambient")
        }

    }

    private fun addDiffuse(line: String) {
        val parts = line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        try {
            val component = FloatArray(4)
            for (i in 2..5) {
                component[i - 2] = java.lang.Float.parseFloat(parts[i])
            }
            if (parts[1].compareTo("l") == 0) {
                lighting!!.diffuse = component
            } else {
                val s = Integer.parseInt(parts[1]) - 1
                surfaces!![s].diffuse = component
            }
        } catch (e: Exception) {
            println("Wrogn diffuse")
        }

    }

    private fun addSpecular(line: String) {
        val parts = line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        try {
            val component = FloatArray(4)
            for (i in 2..5) {
                component[i - 2] = java.lang.Float.parseFloat(parts[i])
            }
            if (parts[1].compareTo("l") == 0) {
                lighting!!.specular = component
            } else {
                val s = Integer.parseInt(parts[1]) - 1
                surfaces!![s].specular = component
            }
        } catch (e: Exception) {
            println("Wrogn Specular")
        }

    }

    private fun addShininess(line: String) {
        val parts = line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        try {
            val component = floatArrayOf(java.lang.Float.parseFloat(parts[2]))
            val s = Integer.parseInt(parts[1]) - 1
            surfaces!![s].shininess = component
        } catch (e: Exception) {
            println("Wrogn Shininess")
        }

    }

    private fun addPosition(line: String) {
        val parts = line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        try {
            val component = FloatArray(3)
            for (i in 2..4) {
                component[i - 2] = java.lang.Float.parseFloat(parts[i])
            }
            if (parts[1].compareTo("l") == 0)
                lighting!!.position = component
            else if (parts[1].compareTo("ceye") == 0)
                camera!!.eye = Vector(component[0], component[1], component[2])
            else if (parts[1].compareTo("cat") == 0)
                camera!!.at = Vector(component[0], component[1], component[2])

        } catch (e: Exception) {
            println("Wrogn position")
        }

    }

    private fun addAngle(line: String) {
        val parts = line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        try {
            camera!!.angle = java.lang.Float.parseFloat(parts[1])
        } catch (e: Exception) {
            println("Wrogn angle")
        }

    }

    private fun toArrayF(list: List<Float>): FloatArray {
        val array = FloatArray(list.size)
        for (i in list.indices) {
            array[i] = list[i]
        }
        return array
    }

    private fun toArrayI(list: List<Int>): IntArray {
        val array = IntArray(list.size)
        for (i in list.indices) {
            array[i] = list[i]
        }
        return array
    }

    private fun toArrayS(list: MutableList<Surface>): Array<Surface?> {
        val array = arrayOfNulls<Surface>(list.size)
        for (i in array.indices) {
            array[i] = list.removeAt(0)
        }
        return array
    }

    fun saveObj(mesh: Mesh, lighting: Lighting, camera: Camera) {
        if (fc.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            var fileName = fc.selectedFile.toString()
            if (!fileName.endsWith(".obj")) {
                fileName = fc.selectedFile.toString() + ".obj"
            }
            saveObj(mesh, lighting, camera, fileName)
        }
    }

    fun saveObj(mesh: Mesh?, lighting: Lighting?, camera: Camera?, fileName: String) {
        if (mesh != null && lighting != null && camera != null) {
            val sb = bufferMesh(mesh, lighting, camera)

            var fw: FileWriter? = null
            try {
                fw = FileWriter(fileName)
                fw.write(sb.toString())
                fw.close()
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error while saving!")
            } finally {
                try {
                    fw!!.close()
                } catch (e2: Exception) {
                    e2.printStackTrace()
                }

            }
        }
    }

    private fun bufferMesh(mesh: Mesh, lighting: Lighting, camera: Camera): StringBuilder {
        val sb = StringBuilder()
        sb.append("-vertices: ").append(mesh.vertexCount).append(System.lineSeparator())
        for (i in 0 until mesh.vertexCount) {
            sb.append("v ")
            for (j in 0 until Mesh.VERTEX_SIZE) {
                sb.append(mesh.vertices[i * Mesh.VERTEX_SIZE + j]).append(" ")
            }
            sb.append(System.lineSeparator())
        }
        sb.append(System.lineSeparator())

        sb.append("-normals: ").append(mesh.normalCount).append(System.lineSeparator())
        for (i in 0 until mesh.normalCount) {
            sb.append("vn ")
            for (j in 0 until Mesh.NORMAL_SIZE) {
                sb.append(mesh.normals[i * Mesh.NORMAL_SIZE + j]).append(" ")
            }
            //sb.deleteCharAt(sb.length()-1);
            sb.append(System.lineSeparator())
        }
        sb.append(System.lineSeparator())

        sb.append("-faces: ").append(mesh.faceCount).append(System.lineSeparator())
        var surface = -1
        for (i in 0 until mesh.faceCount) {
            if (surface != mesh.getFS(i)) {
                surface = mesh.getFS(i)
                sb.append("s ").append(surface + 1).append(System.lineSeparator())
            }
            sb.append("f ")
            sb.append(mesh.getFV(i, 0) + 1).append("//").append(mesh.getFN(i, 0) + 1).append(" ")
            sb.append(mesh.getFV(i, 1) + 1).append("//").append(mesh.getFN(i, 1) + 1).append(" ")
            sb.append(mesh.getFV(i, 2) + 1).append("//").append(mesh.getFN(i, 2) + 1).append(" ")
            sb.append(System.lineSeparator())
        }
        sb.append(System.lineSeparator())

        sb.append("-surfaces: ").append(mesh.faceCount).append(System.lineSeparator())
        for (i in 0 until mesh.surfaces.size) {
            sb.append("-surface ").append(i + 1).append(System.lineSeparator())
            var a: FloatArray
            a = mesh.surfaces[i].diffuse
            sb.append("#d ").append(i + 1).append(" ").append(a[0]).append(" ").append(a[1]).append(" ").append(a[2]).append(" ").append(a[3]).append(System.lineSeparator())

            a = mesh.surfaces[i].ambient
            sb.append("#a ").append(i + 1).append(" ").append(a[0]).append(" ").append(a[1]).append(" ").append(a[2]).append(" ").append(a[3]).append(System.lineSeparator())

            a = mesh.surfaces[i].specular
            sb.append("#sp ").append(i + 1).append(" ").append(a[0]).append(" ").append(a[1]).append(" ").append(a[2]).append(" ").append(a[3]).append(System.lineSeparator())

            a = mesh.surfaces[i].shininess
            sb.append("#sh ").append(i + 1).append(" ").append(a[0]).append(System.lineSeparator())
        }

        sb.append("-lighting: ").append(mesh.faceCount).append(System.lineSeparator())
        var a: FloatArray
        a = lighting.ambient
        sb.append("#a l ").append(a[0]).append(" ").append(a[1]).append(" ").append(a[2]).append(" ").append(a[3]).append(System.lineSeparator())

        a = lighting.diffuse
        sb.append("#d l ").append(a[0]).append(" ").append(a[1]).append(" ").append(a[2]).append(" ").append(a[3]).append(System.lineSeparator())

        a = lighting.lmAmbient
        sb.append("#a m ").append(a[0]).append(" ").append(a[1]).append(" ").append(a[2]).append(" ").append(a[3]).append(System.lineSeparator())

        a = lighting.specular
        sb.append("#sp l ").append(a[0]).append(" ").append(a[1]).append(" ").append(a[2]).append(" ").append(a[3]).append(System.lineSeparator())

        a = lighting.position
        sb.append("#p l ").append(a[0]).append(" ").append(a[1]).append(" ").append(a[2]).append(System.lineSeparator())

        sb.append("-camera: ").append(mesh.faceCount).append(System.lineSeparator())
        var v: Vector
        v = camera.eye
        sb.append("#p ceye ").append(v.x).append(" ").append(v.y).append(" ").append(v.z).append(System.lineSeparator())

        v = camera.at
        sb.append("#p cat ").append(v.x).append(" ").append(v.y).append(" ").append(v.z).append(System.lineSeparator())

        sb.append("#angle ").append(camera.angle).append(System.lineSeparator())
        return sb
    }
}
