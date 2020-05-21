package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Util;

/**
 * abstract class of radial geometries
 */
public abstract class RadialGeometry extends Geometry {
    private double _radius;

    /**
     * constructor of RadialGeometry
     *
     * @param emission emission color of the RadialGeometry
     * @param radius   double value of shape radius
     * @param material the material of the geometry
     */
    public RadialGeometry(Color emission, Material material, double radius) {
        super(emission, material);
        if (Util.isZero(radius) || radius < 0.0)
            throw new IllegalArgumentException();
        _radius = radius;
    }

    /**
     * constructor of RadialGeometry
     * set material to (0,0,0)
     *
     * @param emission emission color of the RadialGeometry
     * @param radius   double value of shape radius
     */
    public RadialGeometry(Color emission, double radius) {
        this(emission, new Material(0, 0, 0), radius);
    }

    /**
     * constructor of RadialGeometry
     * set the emission color to Black
     * set material to (0,0,0)
     *
     * @param radius double value of shape radius
     */
    public RadialGeometry(double radius) {
        this(Color.BLACK,new Material(0, 0, 0), radius);
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