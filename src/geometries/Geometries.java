package geometries;

import primitives.Ray;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * class that implements collection of shapes
 */
public class Geometries implements Intersectable {

    private List<Intersectable> _geometries;

    /**
     * default constructor of Geometries
     */
    public Geometries() {
        _geometries = new LinkedList<>();
    }

    /**
     * constructor of Geometries that receive list of Intersctable
     *
     * @param geometries list of Intersectable
     */
    public Geometries(Intersectable... geometries) {
        _geometries = new LinkedList<>();
        _geometries.addAll(Arrays.asList(geometries));
    }

    /**
     * add list of Intersctable to the list
     *
     * @param geometries list of Intersctable
     */
    public void add(Intersectable... geometries) {
        _geometries.addAll(Arrays.asList(geometries));
    }

    /**
     * return list of all geometries
     *
     * @return list of geometries
     */
    public List<Intersectable> getGeometries() {
        return _geometries;
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray, double max) {

        List<Intersectable> geometries = _geometries;
        List<GeoPoint> intersectionPoints = null;
        for (Intersectable geometry : geometries) {
            List<GeoPoint> geometryIntersections = geometry.findIntersections(ray, max);
            // if geometry intersections is null don't add anything
            if (geometryIntersections != null) {
                // first time where there are geometry intersections - create the list
                if (intersectionPoints == null)
                    intersectionPoints = new LinkedList<>();
                intersectionPoints.addAll(geometryIntersections);
            }
        }
        return intersectionPoints;
    }
}