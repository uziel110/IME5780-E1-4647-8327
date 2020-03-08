package primitives;

import java.util.Objects;

public class Point3D {
    Coordinate _a;
    Coordinate _b;
    Coordinate _c;

    final static Point3D ZERO = new Point3D(0, 0, 0);

    public Point3D(Coordinate one, Coordinate two, Coordinate three) {
    }

    public Point3D(double one, double two, double three) {
    }

    public Point3D(Point3D point) {
    }

    Vector subtract(Point3D other) {
        return new Vector(new Coordinate(_a._coord - other._a._coord),
                new Coordinate(_b._coord - other._b._coord),
                new Coordinate(_c._coord - other._c._coord));
    }

    Point3D add(Vector vector) {
        return new Point3D(_a._coord + vector._end._a._coord,
                _b._coord + vector._end._b._coord,
                _c._coord + vector._end._c._coord);
    }

    double distanceSquared(Point3D other) {
        return ((_a._coord - other._a._coord) * (_a._coord - other._a._coord) +
                (_b._coord - other._b._coord) * (_b._coord - other._b._coord) +
                (_c._coord - other._c._coord) * (_c._coord - other._c._coord));
    }

    Double distance(Point3D other) {
        return Math.sqrt(this.distanceSquared(other));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point3D)) return false;
        Point3D other = (Point3D) obj;
        return _a.equals(other._a) && _b.equals(other._b) && _c.equals(other._c);
    }
}
