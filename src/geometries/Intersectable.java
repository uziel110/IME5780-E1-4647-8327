package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

/**
 * interface for the intersections between the geometry shape and a ray from the camera
 */
public interface Intersectable {

    /**
     *
     * @param ray from the camera
     * @return list of intersections points
     */

    List<Point3D> findIntersections(Ray ray);
}
