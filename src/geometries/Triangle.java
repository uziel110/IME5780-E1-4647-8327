package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Triangle extends Polygon {


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