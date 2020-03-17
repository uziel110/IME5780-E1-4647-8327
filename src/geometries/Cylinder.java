package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube{
    double _height;

    public Cylinder(Point3D point,Vector vector, double radius, double height){
        super(point,vector, radius);
        _height = height;
    }

    public Cylinder(Ray axisRay, double radius, double height){
        super(axisRay,radius);
        _height = height;
    }

    public double get_height() {
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
