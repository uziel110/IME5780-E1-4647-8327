package geometries;

import primitives.Point3D;
import primitives.Util;
import primitives.Vector;
import primitives.Util.*;

public abstract class RadialGeometry implements Geometry {
    protected double _radius;

    public RadialGeometry(double radius) {
        if (Util.isZero(radius))
            throw new IllegalArgumentException();
        _radius = radius;
    }
    public RadialGeometry(RadialGeometry geometry) {
        _radius = geometry._radius;
    }

    public double get_radius() {
        return _radius;
    }
}
