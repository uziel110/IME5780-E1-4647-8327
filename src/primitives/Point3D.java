package primitives;

import java.util.Objects;

/**
 * @author Uziel Shemesh
 * class that implements 3d point
 */
public class Point3D {
    Coordinate _a;
    Coordinate _b;
    Coordinate _c;

    final static Point3D ZERO = new Point3D(0, 0, 0);

    /**
     * ctor that receive 3 coordinates
     * @param one
     * @param two
     * @param three
     */
    public Point3D(Coordinate one, Coordinate two, Coordinate three) {
    }

    /**
     * ctor that receive 3 double parameters
     * @param one
     * @param two
     * @param three
     */
    public Point3D(double one, double two, double three) {
    }

    /**
     * copy ctor - receive another point
     * @param point
     */
    public Point3D(Point3D point) {
    }

    /**
     * subtract other point from this point
     * @param other 3d point
     * @return new Vector from this point to other point
     */
    public Vector subtract(Point3D other) {
        return new Vector(new Coordinate(_a._coord - other._a._coord),
                new Coordinate(_b._coord - other._b._coord),
                new Coordinate(_c._coord - other._c._coord));
    }

    /**
     * add vector to this point
     * @param vector
     * @return new Point at the end of vector that begin from this point
     */
    public Point3D add(Vector vector) {
        return new Point3D(_a._coord + vector._end._a._coord,
                _b._coord + vector._end._b._coord,
                _c._coord + vector._end._c._coord);
    }

    /**
     * @param other point
     * @return distance from this point to other point squared
     */
    public double distanceSquared(Point3D other) {
        return ((_a._coord - other._a._coord) * (_a._coord - other._a._coord) +
                (_b._coord - other._b._coord) * (_b._coord - other._b._coord) +
                (_c._coord - other._c._coord) * (_c._coord - other._c._coord));
    }

    /**
     * @param other point
     * @return distance from this point to other point
     */
    public double distance(Point3D other) {
        return Math.sqrt(this.distanceSquared(other));
    }

    /**
     * check equality between two points
     * @param obj
     * @return true if this point is equals to other point or close by the accuracy of Util Class
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point3D)) return false;
        Point3D other = (Point3D) obj;
        return _a.equals(other._a) && _b.equals(other._b) && _c.equals(other._c);
    }
}
