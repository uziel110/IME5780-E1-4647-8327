package geometries;

import primitives.Color;
import primitives.Point3D;

/**
 * class that implements Triangle
 */
public class Triangle extends Polygon {

    /**
     * constructor of Cylinder that receive 3 points of the edges of the triangle
     * set the emission color to Black
     *
     * @param pointOne   Point3D edge of the Triangle
     * @param pointTwo   Point3D edge of the Triangle
     * @param pointThree Point3D edge of the Triangle
     */
    public Triangle(Point3D pointOne, Point3D pointTwo, Point3D pointThree) {
        super(pointOne, pointTwo, pointThree);
    }

    /**
     * constructor of Cylinder that receive 3 points of the edges of the triangle
     *
     * @param emission   Color emission color of the Triangle
     * @param pointOne   Point3D edge of the Triangle
     * @param pointTwo   Point3D edge of the Triangle
     * @param pointThree Point3D edge of the Triangle
     */
    public Triangle(Color emission, Point3D pointOne, Point3D pointTwo, Point3D pointThree) {
        super(emission, pointOne, pointTwo, pointThree);
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + _vertices +
                ", plane=" + _plane +
                '}';
    }
}