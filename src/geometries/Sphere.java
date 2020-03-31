package geometries;

import primitives.*;

import java.util.List;

/**
 * class that implements sphere
 */
public class Sphere extends RadialGeometry {
    private Point3D _center;

    /**
     * @param radius double value of Sphere radius
     * @param point  a point of sphere center
     */
    public Sphere(double radius, Point3D point) {
        super(radius);
        _center = new Point3D(point);
    }

    /**
     * @return the center of the sphere
     */
    public Point3D getCenter() {
        return _center;
    }

    @Override
    public Vector getNormal(Point3D other) {
        return other.subtract(_center).normalize();
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + _center +
                ", radius=" + getRadius() +
                '}';
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}