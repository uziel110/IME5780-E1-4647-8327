package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * interface for geometries shapes
 */
public interface Geometry {
    public Vector getNormal(Point3D point);
}
