package primitives;

/**
 * class that implements 3d point
 */
public class Point3D {
    Coordinate _x;
    Coordinate _y;
    Coordinate _z;

    //static point ZERO
    public final static Point3D ZERO = new Point3D(0, 0, 0);

    /**
     * constructor that receive 3 coordinates
     *
     * @param Coordinate x
     * @param Coordinate y
     * @param Coordinate z
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        _x = new Coordinate(x);
        _y = new Coordinate(y);
        _z = new Coordinate(z);
    }

    /**
     * constructor that receive 3 double parameters
     *
     * @param double x
     * @param double y
     * @param double z
     */
    public Point3D(double x, double y, double z) {
        _x = new Coordinate(x);
        _y = new Coordinate(y);
        _z = new Coordinate(z);
    }

    /**
     * copy constructor - receive another point
     *
     * @param Point3D other
     */
    public Point3D(Point3D other) {
        _x = new Coordinate(other._x);
        _y = new Coordinate(other._y);
        _z = new Coordinate(other._z);
    }

    /**
     * subtract other point from this point
     *
     * @param Point3D other
     * @return new Vector from this point to other point
     */
    public Vector subtract(Point3D other) {
        return new Vector(new Coordinate(_x._coord - other._x._coord),
                new Coordinate(_y._coord - other._y._coord),
                new Coordinate(_z._coord - other._z._coord));
    }

    /**
     * add vector to this point
     *
     * @param vector
     * @return new Point at the end of vector that begin from this point
     */
    public Point3D add(Vector vector) {
        return new Point3D(_x._coord + vector._end._x._coord,
                _y._coord + vector._end._y._coord,
                _z._coord + vector._end._z._coord);
    }

    /**
     * @param Point3D other
     * @return distance from this point to other point squared
     */
    public double distanceSquared(Point3D other) {
        return ((_x._coord - other._x._coord) * (_x._coord - other._x._coord) +
                (_y._coord - other._y._coord) * (_y._coord - other._y._coord) +
                (_z._coord - other._z._coord) * (_z._coord - other._z._coord));
    }

    /**
     * @param Point3D other
     * @return distance from this point to other point
     */
    public double distance(Point3D other) {
        return Math.sqrt(this.distanceSquared(other));
    }

    /**
     * check equality between two points that close by the accuracy of Util Class
     *
     * @param Object obj
     * @return true if this point is equals to other point or close by the accuracy of Util Class
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point3D)) return false;
        Point3D other = (Point3D) obj;
        return _x.equals(other._x) && _y.equals(other._y) && _z.equals(other._z);
    }

    /**
     *
     * @return Coordinate x
     */
    public Coordinate getX() {
        return _x;
    }

    /**
     *
     * @return Coordinate y
     */
    public Coordinate getY() {
        return _y;
    }

    /**
     *
     * @return Coordinate z
     */
    public Coordinate getZ() {
        return _z;
    }

    @Override
    public String toString() {
        return "(" + _x + "," + _y + "," + _z + ')';
    }
}
