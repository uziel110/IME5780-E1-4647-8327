package geometries;

import primitives.*;

public class Sphere extends RadialGeometry {
    protected Point3D _center;

    public Sphere(double radius) {
        super(radius);
    }

    @Override
    public Vector getNormal(Point3D other) {
        return new Vector(other.subtract(_center));
    }

    public Point3D get_center() {
        return _center;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + _center +
                ", radius=" + _radius +
                '}';
    }
}