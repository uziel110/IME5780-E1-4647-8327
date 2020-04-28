package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

/**
 * interface for the intersections between the geometry shape and a ray from the camera
 */
public interface Intersectable {

    /**
     * find all the Intersections between the geometry shape and a ray that receive as parameter
     *
     * @param ray
     * @return list of intersections points if no such points return null
     */
    List<Point3D> findIntersections(Ray ray);
}
