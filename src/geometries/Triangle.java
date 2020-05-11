package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;

/**
 * class that implements Triangle
 */
public class Triangle extends Polygon {

    /**
     * constructor of Cylinder that receive 3 points of the edges of the triangle
     *
     * @param emission   emission color of the Triangle
     * @param material   the material of the Triangle
     * @param pointOne   edge of the Triangle
     * @param pointTwo   edge of the Triangle
     * @param pointThree edge of the Triangle
     */
    public Triangle(Color emission, Material material, Point3D pointOne, Point3D pointTwo, Point3D pointThree) {
        super(emission, material, pointOne, pointTwo, pointThree);
    }

    /**
     * constructor of Cylinder that receive 3 points of the edges of the triangle
     *
     * @param emission   emission color of the Triangle
     * @param pointOne   edge of the Triangle
     * @param pointTwo   edge of the Triangle
     * @param pointThree edge of the Triangle
     */
    public Triangle(Color emission, Point3D pointOne, Point3D pointTwo, Point3D pointThree) {
        super(emission, new Material(0, 0, 0), pointOne, pointTwo, pointThree);
    }

    /**
     * constructor of Cylinder that receive 3 points of the edges of the triangle
     * set the emission color to Black
     * set material to (0,0,0)
     *
     * @param pointOne   Point3D edge of the Triangle
     * @param pointTwo   Point3D edge of the Triangle
     * @param pointThree Point3D edge of the Triangle
     */
    public Triangle(Point3D pointOne, Point3D pointTwo, Point3D pointThree) {
        this(Color.BLACK, new Material(0, 0, 0), pointOne, pointTwo, pointThree);
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + _vertices +
                ", plane=" + _plane +
                '}';
    }
}