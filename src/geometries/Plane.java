package geometries;

import primitives.*;

/**
 * class that implement a plane
 */
public class Plane implements Geometry  {
    protected Point3D _p;
    protected Vector _normal;

    /**
     * constructor of Plane
     * @param  pointOne
     * @param  pointTwo
     * @param  pointThree
     * @throws IllegalArgumentException if one or two points or all three points are on the same line
     */
    public Plane(Point3D pointOne, Point3D pointTwo, Point3D pointThree) {
        Vector vector1 = new Vector(pointOne.subtract(pointTwo));
        Vector vector2 = new Vector(pointOne.subtract(pointThree));
        _normal = new Vector(vector1.crossProduct(vector2).normalize());
        _p = new Point3D(pointOne);
    }

    /**
     * constructor of Plane
     * @param Point3D point
     * @param Vector normal
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
