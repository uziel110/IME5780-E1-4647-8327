package geometries;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * interface for geometries shapes
 */
public abstract class Geometry implements Intersectable {

    protected Color _emmission;

    public Color get_emmission() {
        return _emmission;
    }

    /**
     * return normal to the geometry shape from the received point
     *
     * @param point point on the surface
     * @return normal to the geometry shape from the received point
     */
    public abstract Vector getNormal(Point3D point);
}
