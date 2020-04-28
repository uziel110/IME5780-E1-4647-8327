package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * interface for geometries shapes
 */
public interface Geometry extends Intersectable {
    /**
     * return normal to the geometry shape from the received point
     *
     * @param point point on the surface
     * @return normal to the geometry shape from the received point
     */
    Vector getNormal(Point3D point);
}
