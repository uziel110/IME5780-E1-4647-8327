package geometries;

import primitives.Point3D;
import primitives.Util;
import primitives.Vector;
import primitives.Util.*;

/**
 * abstract class of radial geometries
 */
public abstract class RadialGeometry implements Geometry {
    protected double _radius;

    /**
     * ctor of RadialGeometry
     * @param radius of the shape
     */
    public RadialGeometry(double radius) {
        if (Util.isZero(radius))
            throw new IllegalArgumentException();
        _radius = radius;
    }

    /**
     * copy ctor of RadialGeometry
     * @param geometry
     */
    public RadialGeometry(RadialGeometry geometry) {
        _radius = geometry._radius;
    }

    /**
     *
     * @return the radius of the shape
     */
    public double get_radius() {
        return _radius;
    }
}
