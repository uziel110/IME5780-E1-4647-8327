package primitives;

/**
 * class that implements Vector
 */
public class Vector {
    private Point3D _end;

    /**
     * constructor of Vector that receive 3 double values
     *
     * @param x double value of x coordinate
     * @param y double value of y coordinate
     * @param z double value of z coordinate
     * @throws IllegalArgumentException in case of zero point
     */
    public Vector(double x, double y, double z) {
        Point3D point = new Point3D(x, y, z);
        if (point.equals(Point3D.ZERO))
            throw new IllegalArgumentException();
        _end = point;
    }

    /**
     * constructor of Vector that receive 3 coordinates
     *
     * @param x Coordinate x
     * @param y Coordinate y
     * @param z Coordinate z
     * @throws IllegalArgumentException in case of zero point
     */
    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        this(x._coord, y._coord, z._coord);
    }

    /**
     * constructor of Vector that receive point of end of the vector
     *
     * @param end Point3D end of other vector
     * @throws IllegalArgumentException when try to create zero vector
     */
    public Vector(Point3D end) {
        this(end._x._coord, end._y._coord, end._z._coord);
    }

    /**
     * copy constructor
     *
     * @param other Vector to copy
     */
    public Vector(Vector other) {
        _end = new Point3D(other._end);
    }

    /**
     * subtract this vector from other
     *
     * @param other Vector to subtract
     * @return new Vector from end of other to end of this
     */
    public Vector subtract(Vector other) {
        return this._end.subtract(other._end);
    }

    /**
     * add this vector to other vector
     *
     * @param other Vector to add
     * @return new Vector of the adding
     */
    public Vector add(Vector other) {
        return new Vector(this._end.add(other));
    }

    /**
     * multiply this vector by scalar
     *
     * @param scalar double value to scale by
     * @return new Vector of the multiply
     */
    public Vector scale(double scalar) {
        return new Vector(_end._x._coord * scalar,
                _end._y._coord * scalar,
                _end._z._coord * scalar);
    }

    /**
     * implementation of dot product
     *
     * @param other vector to multiply
     * @return double value of dot product
     */
    public double dotProduct(Vector other) {
        return _end._x._coord * other._end._x._coord +
                _end._y._coord * other._end._y._coord +
                _end._z._coord * other._end._z._coord;
    }

    /**
     * implementation of cross product
     *
     * @param other vector for cross product
     * @return new Vector - the result of cross product
     */
    public Vector crossProduct(Vector other) {
        return new Vector((_end._y._coord * other._end._z._coord - _end._z._coord * other._end._y._coord),
                (_end._z._coord * other._end._x._coord - _end._x._coord * other._end._z._coord),
                (_end._x._coord * other._end._y._coord - _end._y._coord * other._end._x._coord));
    }

    /**
     * return the length squared of this vector
     *
     * @return double value the length squared of this vector
     */
    public double lengthSquared() {
        return this._end.distanceSquared(Point3D.ZERO);
    }

    /**
     * return the length
     *
     * @return double value of the length
     */
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * normalize the vector
     *
     * @return this vector normalized
     */
    public Vector normalize() {
        double norma = this.length();
        _end._x = new Coordinate(_end._x._coord / norma);
        _end._y = new Coordinate(_end._y._coord / norma);
        _end._z = new Coordinate(_end._z._coord / norma);
        return this;
    }

    /**
     * return normalized vector from this vector
     *
     * @return new normalized vector
     */
    public Vector normalized() {
        return new Vector(this).normalize();
    }

    /**
     * return point that presents the end point of the vector that start from first the hinges
     *
     * @return point of end of vector that start from "first the hinges"
     */
    public Point3D getEnd() {
        return _end;
    }

    @Override
    public String toString() {
        return "Vector" + _end;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Vector)) return false;
        return this._end.equals(((Vector) obj)._end);
    }
}
