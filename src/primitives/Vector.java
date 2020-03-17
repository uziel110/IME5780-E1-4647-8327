package primitives;

import java.awt.*;

/**
 * class that implements Vector
 */
public class Vector {
    Point3D _end;

    /**
     * ctor of Vector that receive 3 coordinates
     * @param x
     * @param y
     * @param z
     */
    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        Point3D point = new Point3D(x ,y, z);
        if(point.equals(Point3D.ZERO))
            throw new IllegalArgumentException();
        _end = point;
    }

    /**
     * ctor of Vector that receive 3 double values
     * @param x
     * @param y
     * @param z
     */
    public Vector(double x, double y, double z) {
        Point3D point = new Point3D(x ,y, z);
        if(point.equals(Point3D.ZERO))
            throw new IllegalArgumentException();
        _end = point;
    }

    /**
     * ctor of Vector that receive point of end of the vector
     * @param Point3D end
     */
    public Vector(Point3D end) {
        if (end.equals(Point3D.ZERO))
            throw new IllegalArgumentException();
        _end = new Point3D(end);
    }

    /**
     * copy ctor
     * @param Vector other
     */
    public Vector(Vector other) {
        _end = new Point3D(other._end);
    }

    /**
     * subtract this vector from other
     * @param Vector other
     * @return new Vector from end of other to end of this
     */
    public Vector subtract(Vector other) {
        return new Vector(new Coordinate(_end._x._coord - other._end._x._coord),
                new Coordinate(_end._y._coord - other._end._x._coord),
                new Coordinate(_end._z._coord - other._end._z._coord));
    }

    /**
     * add this vector to other vector
     * @param Vector other
     * @return new Vector of the adding
     */
    public Vector add(Vector other) {
        return new Vector(new Coordinate(_end._x._coord + other._end._x._coord),
                new Coordinate(_end._y._coord + other._end._x._coord),
                new Coordinate(_end._z._coord + other._end._z._coord));
    }

    /**
     * multiply this vector by scalar
     * @param double scalar
     * @return new Vector of the multiply
     */
    public Vector scale(double scalar) {
        return new Vector(new Coordinate(_end._x._coord * scalar),
                new Coordinate(_end._y._coord * scalar),
                new Coordinate(_end._z._coord * scalar));
    }

    /**
     * implementation of dot product
     * @param double scalar
     * @return double value of dot product
     */
    public double dotProduct(Vector other) {
        return _end._x._coord * other._end._x._coord +
                _end._y._coord * other._end._y._coord +
                _end._z._coord * other._end._z._coord;
    }

    /**
     * implementation of cross product
     * @param Vector other
     * @return new Vector - the result of cross product
     */
    public Vector crossProduct(Vector other) {
        return new Vector(new Coordinate(_end._y._coord * other._end._z._coord - _end._z._coord * other._end._y._coord),
                new Coordinate(_end._z._coord * other._end._x._coord - _end._x._coord * other._end._z._coord),
                new Coordinate(_end._x._coord * other._end._y._coord - _end._y._coord * other._end._x._coord));
    }

    /**
     *
     * @return the length Squared
     */
    public double lengthSquared() {
        return this._end.distanceSquared(Point3D.ZERO);
    }

    /**
     *
     * @return the length
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
        Double norma = this.length();
        _end._x = new Coordinate(_end._x._coord / norma);
        _end._y = new Coordinate(_end._y._coord / norma);
        _end._z = new Coordinate(_end._z._coord / norma);
        return this;
    }

    /**
     * @return new normalized vector from this vector
     */
    public Vector normalized() {
        return new Vector(this).normalize();
    }

    /**
     *
     * @return point of end of vector that start from "first the hinges"
     */
    public Point3D getEnd() {
        return _end;
    }

    @Override
    public String toString() {
        return "Vector" + _end ;
    }
}
