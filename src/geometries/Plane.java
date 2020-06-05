package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class that implement a plane
 */
public class Plane extends Geometry {
    /**
     * point on the plane
     */
    private Point3D _p;
    private Vector _normal;

    /**
     * constructor of Plane that receive 3 points on the plane and a color
     *
     * @param emission   Color emission color of the Plane
     * @param material   the material of the plane
     * @param pointOne   Point3D on the plane
     * @param pointTwo   Point3D on the plane
     * @param pointThree Point3D on the plane
     */
    public Plane(Color emission, Material material, Point3D pointOne, Point3D pointTwo, Point3D pointThree) {
        super(emission, material);
        // if two or three points are equal then an exception will be created at vector constructor
        Vector vector1 = pointTwo.subtract(pointOne);
        Vector vector2 = pointThree.subtract(pointOne);
        _normal = vector1.crossProduct(vector2).normalize();
        _p = new Point3D(pointOne);
    }

    /**
     * constructor of Plane that receive 3 points on the plane
     * set the emission color to Black
     * set material to (0,0,0)
     *
     * @param pointOne   Point3D on the plane
     * @param pointTwo   Point3D on the plane
     * @param pointThree Point3D on the plane
     */
    public Plane(Point3D pointOne, Point3D pointTwo, Point3D pointThree) {
        this(Color.BLACK, new Material(0, 0, 0), pointOne, pointTwo, pointThree);
    }

    /**
     * constructor of Plane
     *
     * @param emission Color emission color of the Plane
     * @param material the material of the cylinder
     * @param point    Point3D on the plane
     * @param normal   vector normal to the plane
     */
    public Plane(Color emission, Material material, Point3D point, Vector normal) {
        super(emission, material);
        _p = point;
        _normal = normal.normalize();
    }

    /**
     * constructor of Plane
     * set the emission color to Black
     * set material to (0,0,0)
     *
     * @param point  Point3D on the plane
     * @param normal vector normal to the plane
     */
    public Plane(Point3D point, Vector normal) {
        this(Color.BLACK, new Material(0, 0, 0), point, normal);
    }

    /**
     * return a point on the plane
     *
     * @return point on the plane
     */
    public Point3D getPoint() {
        return _p;
    }

    /**
     * return Vector normal to the plane
     *
     * @return Vector normal to the plane
     */
    public Vector getNormal() {
        return _normal;
    }

    @Override
    public Point3D getMin() {
        return null;
    }

    @Override
    public Point3D getMax() {
        return null;
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
    public List<GeoPoint> findIntersections(Ray ray, double max) {
        Point3D p0 = ray.getPoint();
        Vector v = ray.getVector();
        if (_p.equals(p0)) return null;

        double t1 = _normal.dotProduct(_p.subtract(p0));
        double t2 = _normal.dotProduct(v);
        if (isZero(t1) || isZero(t2)) return null;
        double t = alignZero(t1 / t2);
        if (t <= 0 || alignZero(t - max) > 0) return null;

        return List.of(new GeoPoint(this, ray.getPoint(t)));
    }
}