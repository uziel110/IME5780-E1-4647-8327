package geometries;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * interface for geometries shapes
 */
public abstract class Geometry implements Intersectable {

    protected Color _emission;

    public Geometry(Color emission) {
        _emission = new Color(emission);
    }

    public Geometry() {
        this(Color.BLACK);
    }

    /**
     * return emmission light
     * @return emmission light Color
     */
    public Color getEmission() {
        return _emission;
    }

    /**
     * return normal to the geometry shape from the received point
     *
     * @param point point on the surface
     * @return normal to the geometry shape from the received point
     */
    public abstract Vector getNormal(Point3D point);
}
