package primitives;

/**
 * class that implements ray
 */
public class Ray {
    /**
     * Fixed size for moving the beginning of the beam
     * at shading rays transparency and reflection
     */
    private static final double DELTA = 0.1;
    private Point3D _point;
    private Vector _vector;

    /**
     * constructor of Ray that his head moved DELTA from the surface in vector direction
     *
     * @param head      on the surface of the geometry
     * @param direction the vector that we move his head by DELTA
     * @param normal    vector normal to the reflected geometry
     */
    public Ray(Point3D head, Vector direction, Vector normal) {
        // if normal.dotProduct(direction) == 0 so the angle is 90 degree and the ray continue straight
        _point = head.add(normal.scale(normal.dotProduct(direction) > 0 ? DELTA : -DELTA));
        _vector = direction.normalized();
    }

    /**
     * constructor of Ray
     *
     * @param point  Point3D start of the ray
     * @param vector Vector direction of the ray
     */

    public Ray(Point3D point, Vector vector) {
        _point = point;
        _vector = vector.normalized();
    }

    /**
     * constructor of Ray
     *
     * @param other Ray to copy
     */
    public Ray(Ray other) {
        this(other._point, other._vector);
    }

    /**
     * return start point of the Ray
     *
     * @return Point3D start point of the Ray
     */
    public Point3D getPoint() {
        return _point;
    }

    /**
     * return the point that lies on the ray: p0 + t * v;
     * p0 - start point of the ray
     * v - vector of the ray
     *
     * @param t double value to scale vector of the ray
     * @return new Point3D at the end of the ray scaled by t
     */
    public Point3D getPoint(double t) {
        Vector scaledVector;
        // for the situation of after scale the vector Passes the value of isZero
        try {
            scaledVector = _vector.scale(t);
        } catch (Exception e) {
            return _point;
        }
        return _point.add(scaledVector);
    }

    /**
     * return direction vector of the Ray
     *
     * @return direction vector of the Ray
     */
    public Vector getDir() {
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
