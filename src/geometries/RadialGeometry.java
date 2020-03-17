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
     * constructor of RadialGeometry
     * @param double radius of the shape
     */
    public RadialGeometry(double radius) {
        if (Util.isZero(radius))
            throw new IllegalArgumentException();
        _radius = radius;
    }

    /**
     * copy constructor of RadialGeometry
     * @param RadialGeometry other
     */
    public RadialGeometry(RadialGeometry other) {
        _radius = other._radius;
    }

    /**
     *
     * @return the radius of the shape
     */
    public double get_radius() {
        return _radius;
    }
}
