package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

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
        Point3D p0 = new Point3D(ray.getPoint());
        Vector v = new Vector(ray.getVector());

        double radius = this.getRadius();

        if (_center.equals(p0))
            return List.of(new Point3D(_center.add(v.scale(radius))));

        Vector u = new Vector(_center.subtract(p0));
        double tm = alignZero( v.dotProduct(u));
        double d = alignZero(Math.sqrt(u.lengthSquared() - tm * tm));
        if (d >= radius)
            return null;

        double th = alignZero(Math.sqrt(radius * radius - d * d));
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);
        if (t1 <= 0 && t2 <= 0)
            return null;

        if (t1 <= 0)
            return List.of(new Point3D(p0.add(v.scale(t2))));

        return List.of(new Point3D(p0.add(v.scale(t1))), new Point3D(p0.add(v.scale(t2))));
    }
}