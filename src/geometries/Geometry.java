package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * interface for geometries shapes
 */
public interface Geometry {
    /**
     *
     * @param point point on the surface to gey normal from it
     * @return normal to the geometry shape from the received point
     */
    Vector getNormal(Point3D point);
}
