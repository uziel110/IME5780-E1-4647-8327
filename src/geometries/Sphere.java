package geometries;

import primitives.*;

/**
 * class that implements sphere
 */
public class Sphere extends RadialGeometry {
    protected Point3D _center;

    /**
     * ctor of sphere
     * @param radius of the sphere
     */
    public Sphere(double radius) {
        super(radius);
    }

    /**
     *
     * @return the center of the sphere
     */
    public Point3D get_center() {
        return _center;
    }

    @Override
    public Vector getNormal(Point3D other) {
        return new Vector(other.subtract(_center));
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + _center +
                ", radius=" + _radius +
                '}';
    }
}