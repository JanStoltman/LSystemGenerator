package model;

import java.util.ArrayList;

public class MeshBox {
    public float minX, minY, minZ = minY = minX = Float.MAX_VALUE;
    public float maxX, maxY, maxZ = maxY = maxX = -Float.MAX_VALUE;

    public MeshBox(ArrayList<Vertex> vertices) {

        for (Vertex vertice : vertices) {
            Vector coords = vertice.getCoords();
            minX = Math.min(coords.getX(), minX);
            minY = Math.min(coords.getY(), minY);
            minZ = Math.min(coords.getZ(), minZ);

            maxX = Math.max(coords.getX(), maxX);
            maxY = Math.max(coords.getY(), maxY);
            maxZ = Math.max(coords.getZ(), maxZ);
        }
    }

    public String toString() {
        return "[X: " + minX + "; " + maxX + " Y: " + minY + "; " + maxY + " Z: " + minZ + "; " + maxZ + "]\n";
    }
}
