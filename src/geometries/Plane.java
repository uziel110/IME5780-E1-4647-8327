package geometries;

import primitives.*;

/**
 * class that implement a plane
 */
public class Plane implements Geometry  {
    private Point3D _p;
    private Vector _normal;

    /**
     * constructor of Plane that receive 3 points on the plane
     * @param pointOne Point3D on the plane
     * @param pointTwo Point3D on the plane
     * @param pointThree Point3D on the plane
     */
    public Plane(Point3D pointOne, Point3D pointTwo, Point3D pointThree) {
        Vector vector1 = pointOne.subtract(pointTwo);
        Vector vector2 = pointOne.subtract(pointThree);
        _normal = vector1.crossProduct(vector2).normalize();
        _p = new Point3D(pointOne);
    }

    /**
     * constructor of Plane
     * @param point Point3D on the plane
     * @param normal vector normal to the plane
     */
    public Plane(Point3D point, Vector normal) {
        _p = point;
        _normal = normal.normalize();
    }

    /**
     *
     * @return point on the plane
     */
    public Point3D getPoint() {
        return _p;
    }

    /**
     *
     * @return Vector normal to the plane
     */
    public Vector getNormal() {
        return _normal;
    }

    @Override
    public Vector getNormal(Point3D point) {
        return _normal;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "Point=" + _p +
                ", normal=" + _normal +
                '}';
    }
}
