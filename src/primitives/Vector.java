package primitives;

import static primitives.Util.*;

public class Vector {
    Point3D _end;

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
        return new Vector(new Coordinate(_end._a._coord - other._end._a._coord),
                new Coordinate(_end._b._coord - other._end._a._coord),
                new Coordinate(_end._c._coord - other._end._c._coord));
    }

    public Vector add(Vector other) {
        return new Vector(new Coordinate(_end._a._coord + other._end._a._coord),
                new Coordinate(_end._b._coord + other._end._a._coord),
                new Coordinate(_end._c._coord + other._end._c._coord));
    }

    public Vector scale(double scalar) {
        return new Vector(new Coordinate(_end._a._coord * scalar),
                new Coordinate(_end._b._coord * scalar),
                new Coordinate(_end._c._coord * scalar));
    }

    public Vector dotProduct(Vector other) {
        return new Vector(new Coordinate(_end._a._coord * other._end._a._coord),
                new Coordinate(_end._b._coord * other._end._a._coord),
                new Coordinate(_end._c._coord * other._end._c._coord));
    }

    public Vector crossProduct(Vector other) {
        return new Vector(new Coordinate(_end._b._coord * other._end._c._coord - _end._c._coord * other._end._b._coord),
                new Coordinate(_end._c._coord * other._end._a._coord - _end._a._coord * other._end._c._coord),
                new Coordinate(_end._a._coord * other._end._b._coord - _end._b._coord * other._end._a._coord));
    }

    public double lengthSquared(){
        return this._end.distanceSquared(Point3D.ZERO);
    }

    public double length(){
        return Math.sqrt(this.lengthSquared());
    }

    public Vector normalize(){
        Double norma = this.length();
        _end._a = new Coordinate(_end._a._coord/norma);
        _end._b = new Coordinate(_end._b._coord/norma);
        _end._c = new Coordinate(_end._c._coord/norma);
        return this;
    }
    public Vector normalized (){
        return new Vector(this).normalize();
    }
}
