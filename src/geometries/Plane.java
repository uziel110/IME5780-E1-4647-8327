package geometries;

import primitives.*;

public class Plane implements Geometry  {
    protected Point3D _p;
    protected Vector _normal;

    public Plane(Point3D pointOne, Point3D pointTwo, Point3D pointThree) {
        Vector vector1 = new Vector(pointOne.subtract(pointTwo));
        Vector vector2 = new Vector(pointOne.subtract(pointThree));
        _normal = new Vector(vector1.crossProduct(vector2).normalize());
        _p = new Point3D(pointOne);
    }

    public Plane(Point3D _p, Vector _normal) {
        this._p = _p;
        this._normal = _normal.normalize();
    }

    public Point3D getPoint() {
        return _p;
    }

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
