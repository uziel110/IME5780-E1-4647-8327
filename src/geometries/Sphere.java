package geometries;

import primitives.*;

public class Sphere extends RadialGeometry {
    protected Point3D _center;
    public Sphere(double radius) {
        super(radius);
    }

    // TODO check this func
    @Override
    public Vector getNormal(Point3D other) {
        return new Vector(other.subtract(_center));
    }
}
