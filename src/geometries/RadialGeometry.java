package geometries;

import primitives.Util;

/**
 * abstract class of radial geometries
 */
public abstract class RadialGeometry implements Geometry {
    private double _radius;

    /**
     * constructor of RadialGeometry
     * @param radius double value of shape radius
     */
    public RadialGeometry(double radius) {
        if (Util.isZero(radius) || radius < 0.0)
            throw new IllegalArgumentException();
        _radius = radius;
    }

    /**
     * copy constructor of RadialGeometry
     * @param other other RadialGeometry to copy
     */
    public RadialGeometry(RadialGeometry other) {
        _radius = other._radius;
    }

    /**
     * return the radius of the shape
     * @return the radius of the shape
     */
    public double getRadius() {
        return _radius;
    }
}
