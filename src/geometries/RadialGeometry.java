package geometries;

import primitives.Color;
import primitives.Util;

/**
 * abstract class of radial geometries
 */
public abstract class RadialGeometry extends Geometry {
    private double _radius;

    /**
     * constructor of RadialGeometry
     *
     * @param emission Color emission color of the RadialGeometry
     * @param radius   double value of shape radius
     */
    public RadialGeometry(Color emission, double radius) {
        super(emission);
        if (Util.isZero(radius) || radius < 0.0)
            throw new IllegalArgumentException();
        _radius = radius;
    }

    /**
     * constructor of RadialGeometry
     * set the emission color to Black
     *
     * @param radius double value of shape radius
     */
    public RadialGeometry(double radius) {
        this(Color.BLACK, radius);
    }

    /**
     * copy constructor of RadialGeometry
     *
     * @param other other RadialGeometry to copy
     */
    public RadialGeometry(RadialGeometry other) {
        _radius = other._radius;
        _emission = new Color(other._emission);
    }

    /**
     * return the radius of the shape
     *
     * @return the radius of the shape
     */
    public double getRadius() {
        return _radius;
    }
}
