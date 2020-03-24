package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * interface for geometries shapes
 */
public interface Geometry {
    /**
     *
     * @param point point on the surface
     * @return normal to the geometry shape
     */
    public Vector getNormal(Point3D point);
}
