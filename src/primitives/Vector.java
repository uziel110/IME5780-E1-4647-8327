package primitives;

/**
 * @author Uziel Shemesh
 * class that implements Vector
 */
public class Vector {
    Point3D _end;

    public Point3D getEnd() {
        return _end;
    }

    public Vector(Coordinate one, Coordinate two, Coordinate three) {
        if (Util.isZero(one._coord) && Util.isZero(two._coord) && Util.isZero(three._coord))
            throw new IllegalArgumentException();
        _end = new Point3D(one, two, three);
    }

    public Vector(double one, double two, double three) {
        if (Util.isZero(one) && Util.isZero(two) && Util.isZero(three))
            throw new IllegalArgumentException();
        _end = new Point3D(one, two, three);
    }

    public Vector(Point3D end) {
        if (_end.equals(Point3D.ZERO))
            throw new IllegalArgumentException();
        _end = new Point3D(end);
    }

    public Vector(Vector other) {
        _end = new Point3D(other._end);
    }

    public Vector subtract(Vector other) {
        return new Vector(new Coordinate(_end._x._coord - other._end._x._coord),
                new Coordinate(_end._y._coord - other._end._x._coord),
                new Coordinate(_end._z._coord - other._end._z._coord));
    }

    public Vector add(Vector other) {
        return new Vector(new Coordinate(_end._x._coord + other._end._x._coord),
                new Coordinate(_end._y._coord + other._end._x._coord),
                new Coordinate(_end._z._coord + other._end._z._coord));
    }

    public Vector scale(double scalar) {
        return new Vector(new Coordinate(_end._x._coord * scalar),
                new Coordinate(_end._y._coord * scalar),
                new Coordinate(_end._z._coord * scalar));
    }

    public double dotProduct(Vector other) {
        return _end._x._coord * other._end._x._coord +
                _end._y._coord * other._end._y._coord +
                _end._z._coord * other._end._z._coord;
    }

    public Vector crossProduct(Vector other) {
        return new Vector(new Coordinate(_end._y._coord * other._end._z._coord - _end._z._coord * other._end._y._coord),
                new Coordinate(_end._z._coord * other._end._x._coord - _end._x._coord * other._end._z._coord),
                new Coordinate(_end._x._coord * other._end._y._coord - _end._y._coord * other._end._x._coord));
    }

    public double lengthSquared() {
        return this._end.distanceSquared(Point3D.ZERO);
    }

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

    @Override
    public String toString() {
        return "Vector(" + _end + ')';
    }
}
