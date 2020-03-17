package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * class that implements Triangle
 */
public class Triangle extends Polygon {

    /**
     * ctor of Cylinder that receive 3 points of the edges of the triangle
     * @param Point3D pointOne
     * @param Point3D pointTwo
     * @param Point3D pointThree
     */
    public Triangle(Point3D pointOne, Point3D pointTwo, Point3D pointThree) {
        super(pointOne, pointTwo, pointThree);
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + _vertices +
                ", plane=" + _plane +
                '}';
    }
}