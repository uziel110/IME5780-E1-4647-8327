package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * class that implements cylinder
 */
public class Cylinder extends Tube{
    double _height;

    /**
     * constructor of Cylinder
     * @param Point3D start point
     * @param Vector direction
     * @param double radius of the Cylinder
     * @param double height of the Cylinder
     */
    public Cylinder(Point3D point,Vector vector, double radius, double height){
        super(point,vector, radius);
        _height = height;
    }

    /**
     * constructor of Cylinder
     * @param Ray axisRay
     * @param double radius of the Cylinder
     * @param double height of the Cylinder
     */
    public Cylinder(Ray axisRay, double radius, double height){
        super(axisRay,radius);
        _height = height;
    }

    /**
     * @return double height of the Cylinder
     */
    public double getHeight() {
        return _height;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + _height +
                ", axisRay=" + _axisRay +
                ", radius=" + _radius +
                '}';
    }

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }
}
