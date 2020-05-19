package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * class that implements sphere
 */
public class Sphere extends RadialGeometry {
    private Point3D _center;

    /**
     * constructor of sphere that receive center point, radius, color and Material as parameters
     *
     * @param emission Color emission color of the Sphere
     * @param material the material of the sphere
     * @param radius   double value of Sphere radius
     * @param point    a point of sphere center
     */
    public Sphere(Color emission, Material material, double radius, Point3D point) {
        super(emission, material, radius);
        _center = new Point3D(point);
    }

    /**
     * constructor of sphere that receive center point, radius and color as parameters
     * set material to (0,0,0)
     *
     * @param emission Color emission color of the Sphere
     * @param radius   double value of Sphere radius
     * @param point    a point of sphere center
     */
    public Sphere(Color emission, double radius, Point3D point) {
        this(emission, new Material(0, 0, 0), radius, point);
    }

    /**
     * constructor of sphere that receive center point and radius as parameters
     * set material to (0,0,0)
     * set the emission color to Black
     *
     * @param radius double value of Sphere radius
     * @param point  a point of sphere center
     */
    public Sphere(double radius, Point3D point) {
        this(Color.BLACK, new Material(0, 0, 0), radius, point);
    }

    /**
     * return the center of the sphere
     *
     * @return Point3D in the center of the sphere
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
    public List<GeoPoint> findIntersections(Ray ray, double max) {
        Point3D p0 = ray.getPoint();
        Vector v = ray.getVector();

        double radius = getRadius();

        if (_center.equals(p0))
            return List.of(new GeoPoint(this, _center.add(v.scale(radius))));

        Vector u = _center.subtract(p0);
        double tm = alignZero(v.dotProduct(u));
        double d = Math.sqrt(u.lengthSquared() - tm * tm);
        if (alignZero(d - radius) >= 0) return null;

        double th = Math.sqrt(radius * radius - d * d); // is always positive
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

        if (alignZero(t1 - max) > 0) return null; // always t1 < t2
        if (t2 <= 0) return null;
        if (alignZero(t2 - max) > 0) return null;

        if (t1 <= 0) // only t2 > 0
            return List.of(new GeoPoint(this, ray.getPoint(t2)));
        return List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
    }
}