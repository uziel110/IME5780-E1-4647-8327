package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class that implement a plane
 */
public class Plane implements Geometry {
    private Point3D _p;
    private Vector _normal;

    /**
     * constructor of Plane that receive 3 points on the plane
     *
     * @param pointOne   Point3D on the plane
     * @param pointTwo   Point3D on the plane
     * @param pointThree Point3D on the plane
     */
    public Plane(Point3D pointOne, Point3D pointTwo, Point3D pointThree) {

        // if two or three points are equal then an exception will be created at vector constructor
        Vector vector1 = pointTwo.subtract(pointOne);
        Vector vector2 = pointThree.subtract(pointOne);
        _normal = vector1.crossProduct(vector2).normalize();
        _p = new Point3D(pointOne);
    }

    /**
     * constructor of Plane
     *
     * @param point  Point3D on the plane
     * @param normal vector normal to the plane
     */
    public Plane(Point3D point, Vector normal) {
        _p = point;
        _normal = normal.normalize();
    }

    /**
     * return a point on the plane
     * @return point on the plane
     */
    public Point3D getPoint() {
        return _p;
    }

    /**
     * return Vector normal to the plane
     * @return Vector normal to the plane
     */
    public Vector getNormal() {
        return _normal;
    }

    @Override
    public Vector getNormal(Point3D point) {
        return getNormal();
    }

    @Override
    public String toString() {
        return "Plane{" +
                "Point=" + _p +
                ", normal=" + _normal +
                '}';
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        Point3D p0 = ray.getPoint();
        Vector v = ray.getVector();
        if (_p.equals(p0))
            return null;

        double t1 = alignZero(_normal.dotProduct(_p.subtract(p0)));
        double t2 = alignZero(_normal.dotProduct(v));
        if (isZero(t2) || isZero(t1))
            return null;
        double t = alignZero(t1 / t2);
        if (t <= 0)
            return null;

        return List.of(ray.getPoint(t));
    }
}
