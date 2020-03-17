package primitives;

import static primitives.Util.*;

/**
 * class that implemnts ray
 */
public class Ray {
    Point3D _point;
    Vector _vector;

    /**
     * constructor of Ray
     * @param Point3D point
     * @param Vector vector
     */
    public Ray(Point3D point, Vector vector) {
        _point = point;
        _vector = vector.normalized();
    }

    /**
     * constructor of Ray
     * @param Ray other
     */
    public Ray(Ray other) {
        _point = new Point3D(other._point);
        _vector = new Vector(other._vector);
    }

    /**
     *
     * @return point of start of Ray
     */
    public Point3D getPoint() {
        return _point;
    }

    /**
     *
     * @return direction vector of the Ray
     */
    public Vector getVector() {
        return _vector;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Ray)) return false;
        Ray other = (Ray) obj;
        return _point.equals(other._point) && _vector.equals(other._vector);
    }

    @Override
    public String toString() {
        return "Ray{ " + _point + " " + _vector + '}';
    }
}
