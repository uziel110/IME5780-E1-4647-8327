package primitives;

/**
 * class that implements 3d point
 */
public class Point3D {
    /**
     * static point ZERO
     */
    public final static Point3D ZERO = new Point3D(0, 0, 0);
    Coordinate _x;
    Coordinate _y;
    Coordinate _z;

    /**
     * constructor that receive 3 double parameters
     *
     * @param x double value of x coordinate
     * @param y double value of y coordinate
     * @param z double value of z coordinate
     */
    public Point3D(double x, double y, double z) {
        _x = new Coordinate(x);
        _y = new Coordinate(y);
        _z = new Coordinate(z);
    }

    /**
     * constructor that receive 3 coordinates
     *
     * @param x Coordinate x
     * @param y Coordinate y
     * @param z Coordinate z
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this(x._coord, y._coord, z._coord);
    }

    /**
     * copy constructor - receive another point
     *
     * @param other other point3D to copy
     */
    public Point3D(Point3D other) {
        this(other._x._coord, other._y._coord, other._z._coord);
    }

    /**
     * subtract other point from this point
     *
     * @param other Point3D to start from
     * @return new Vector from this point to other point
     */
    public Vector subtract(Point3D other) {
        return new Vector(_x._coord - other._x._coord,
                _y._coord - other._y._coord,
                _z._coord - other._z._coord);
    }

    /**
     * add vector to this point
     *
     * @param vector Vector to add
     * @return new Point at the end of vector that begin from this point
     */
    public Point3D add(Vector vector) {
        return new Point3D(_x._coord + vector.getHead()._x._coord,
                _y._coord + vector.getHead()._y._coord,
                _z._coord + vector.getHead()._z._coord);
    }

    /**
     * return distance from this point to other point squared
     *
     * @param other Point3D to measure distance to it
     * @return double value of the distance squared
     */
    public double distanceSquared(Point3D other) {
        return ((_x._coord - other._x._coord) * (_x._coord - other._x._coord) +
                (_y._coord - other._y._coord) * (_y._coord - other._y._coord) +
                (_z._coord - other._z._coord) * (_z._coord - other._z._coord));
    }

    /**
     * return distance from this point to other point
     *
     * @param other Point3D to measure distance to it
     * @return double value of the distance
     */
    public double distance(Point3D other) {
        return Math.sqrt(this.distanceSquared(other));
    }

    /**
     * check equality between two points that close by the accuracy of Util Class
     *
     * @param obj Object
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
     * return Coordinate x of the point
     *
     * @return Coordinate x of the point
     */
    public Coordinate getX() {
        return _x;
    }

    /**
     * return Coordinate y of the point
     *
     * @return Coordinate y of the point
     */
    public Coordinate getY() {
        return _y;
    }

    /**
     * return Coordinate z of the point
     *
     * @return Coordinate z of the point
     */
    public Coordinate getZ() {
        return _z;
    }

    @Override
    public String toString() {
        return "(" + _x + "," + _y + "," + _z + ')';
    }
}
