package model;

import java.util.ArrayList;

public class Mesh {
    public static final int FACE_SIZE = 7;
    public static final int NORMAL_SIZE = 3;

    public ArrayList<Vertex> vertices = new ArrayList();
    public Surface[] surfaces;
    public float[] normals;
    public int[] faces;
    public MeshBox box;

    public Mesh(ArrayList<Vertex> vertices, Surface[] surfaces, int[] faces) {
        if (surfaces != null && faces.length % FACE_SIZE == 0) {
            this.vertices = vertices;
            this.faces = faces;
            this.surfaces = surfaces;
            this.box = new MeshBox(vertices);
        } else {
            System.out.println("Wrong Mesh argumnets");
        }
    }

    public Mesh(ArrayList<Vertex> vertices, float[] normals, Surface[] surfaces, int[] faces) {
        if (surfaces != null &&
                faces.length % FACE_SIZE == 0 &&
                normals.length % NORMAL_SIZE == 0) {
            this.vertices = vertices;
            this.faces = faces;
            this.surfaces = surfaces;
            this.normals = normals;
            this.box = new MeshBox(vertices);
        } else {
            System.out.println("Wrong Mesh argumnets");
        }
    }

    public int getFaceCount() {
        return faces.length / FACE_SIZE;
    }

    public int getNormalCount() {
        return normals.length / NORMAL_SIZE;
    }

    public Vector getVC(int vertex) {
        return vertices.get(vertex).getCoords();
    }

    public Vector getCC(int vertex) {
        return vertices.get(vertex).getColor();
    }

    public String getTT(int vertex){return vertices.get(vertex).getTextureName();}

    public float getNC(int normal, int component) {
        return normals[normal * NORMAL_SIZE + component];
    }

    public int getFV(int face, int vertex) {
        return faces[face * FACE_SIZE + vertex * 2];
    }

    public int getFN(int face, int normal) {
        return faces[face * FACE_SIZE + normal * 2 + 1];
    }

    public int getFC(int face, int component) {
        return faces[face * FACE_SIZE + component * 2 + 1];
    }

    public int getFS(int face) {
        return faces[face * FACE_SIZE + FACE_SIZE - 1];
    }

    public float[] getSC(int surface, int component) {
        float[] sc = null;
        switch (component) {
            case Surface.AMBIENT_COMPONENT:
                sc = surfaces[surface].getAmbient();
                break;
            case Surface.DIFFUSE_COMPONENT:
                sc = surfaces[surface].getDiffuse();
                break;
            case Surface.SPECULAR_COMPONENT:
                sc = surfaces[surface].getSpecular();
                break;
            case Surface.SHININESS_COMPONENT:
                sc = surfaces[surface].getShininess();
                break;
            default:
                System.out.println("Error geting surface");
        }
        return sc;
    }

    public void calculateNormals() {
        normals = new float[vertices.size() * NORMAL_SIZE];
        int[] vertexMultiples = new int[vertices.size()];
        for (int i = 0; i < getFaceCount(); i++) {
            Vector v0 = getVC(getFV(i, 0));
            Vector v1 = getVC(getFV(i, 1));
            Vector v2 = getVC(getFV(i, 2));

            Vector norm = Vector.cross(new Vector(v0, v1), new Vector(v1, v2));
            norm.norm();

            normals[getFV(i, 0) * NORMAL_SIZE] += norm.getX();
            normals[getFV(i, 0) * NORMAL_SIZE + 1] += norm.getY();
            normals[getFV(i, 0) * NORMAL_SIZE + 2] += norm.getZ();
            vertexMultiples[getFV(i, 0)] += 1;

            normals[getFV(i, 1) * NORMAL_SIZE] += norm.getX();
            normals[getFV(i, 1) * NORMAL_SIZE + 1] += norm.getY();
            normals[getFV(i, 1) * NORMAL_SIZE + 2] += norm.getZ();
            vertexMultiples[getFV(i, 1)] += 1;

            normals[getFV(i, 2) * NORMAL_SIZE] += norm.getX();
            normals[getFV(i, 2) * NORMAL_SIZE + 1] += norm.getY();
            normals[getFV(i, 2) * NORMAL_SIZE + 2] += norm.getZ();
            vertexMultiples[getFV(i, 2)] += 1;
        }
        for (int i = 0; i < getNormalCount(); i++) {
            normals[i * NORMAL_SIZE] = normals[i * NORMAL_SIZE] / vertexMultiples[i];
            normals[i * NORMAL_SIZE + 1] = normals[i * NORMAL_SIZE + 1] / vertexMultiples[i];
            normals[i * NORMAL_SIZE + 2] = normals[i * NORMAL_SIZE + 2] / vertexMultiples[i];
            float len = normals[i * NORMAL_SIZE] * normals[i * NORMAL_SIZE] +
                    normals[i * NORMAL_SIZE + 1] * normals[i * NORMAL_SIZE + 1] +
                    normals[i * NORMAL_SIZE + 2] * normals[i * NORMAL_SIZE + 2];
            len = (float) Math.sqrt(len);

            normals[i * NORMAL_SIZE] = normals[i * NORMAL_SIZE] / len;
            normals[i * NORMAL_SIZE + 1] = normals[i * NORMAL_SIZE + 1] / len;
            normals[i * NORMAL_SIZE + 2] = normals[i * NORMAL_SIZE + 2] / len;
        }
    }

    public void generateMeshBox() {
        box = new MeshBox(vertices);
    }


}
