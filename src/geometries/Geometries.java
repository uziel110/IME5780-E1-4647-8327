package geometries;

import primitives.Point3D;
import primitives.Ray;
import scene.Box;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * class that implements collection of shapes
 */
public class Geometries implements Intersectable {
    private List<Intersectable> _geometriesList;

    /**
     * default constructor of Geometries
     */
    public Geometries() {
        _geometriesList = new LinkedList<>();
    }

    /**
     * constructor of Geometries that receive list of Intersctable
     *
     * @param geometries list of Intersectable
     */
    public Geometries(Intersectable... geometries) {
        _geometriesList = new LinkedList<>();
        _geometriesList.addAll(Arrays.asList(geometries));
    }

    /**
     * add list of Intersctable to the list
     *
     * @param geometries list of Intersctable
     */
    public void add(Intersectable... geometries) {
        Collections.addAll(_geometriesList, geometries);
    }

    /**
     * return list of all geometries
     *
     * @return list of geometries
     */
    public List<Intersectable> getGeometries() {
        return _geometriesList;
    }

    /**
     * return list of GeoPoints that intersect with the ray
     *
     * @param ray            the ray that we find intersections points on it
     * @param box            the box that contains the scene
     * @param shadowRaysCase boolean variable - true in the case that we want all the intersection points
     * @param dis            upper bound of distance from the ray head to the intersection point
     * @return list of GeoPoints that intersect with the ray
     */
    public List<GeoPoint> getRelevantGeoPoints(Ray ray, Box box, boolean shadowRaysCase, double dis) {
        if (box == null)
            return this.findIntersections(ray, dis);
        return box.getRelevantGeoPointsInBox(ray, shadowRaysCase, dis);
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray, double max) {
        List<GeoPoint> intersectionPoints = null;
        for (Intersectable geometry : _geometriesList) {
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

    @Override
    public Point3D getMinCoordinates() {
        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double minZ = Double.POSITIVE_INFINITY;
        double x, y, z;
        Point3D p;
        for (Intersectable i : _geometriesList) {
            if (i.getMinCoordinates() == null)
                continue;
            p = i.getMinCoordinates();
            x = p.getX().get();
            y = p.getY().get();
            z = p.getZ().get();
            if (x < minX)
                minX = x;
            if (y < minY)
                minY = y;
            if (z < minZ)
                minZ = z;
        }
        return new Point3D(minX, minY, minZ);
    }

    @Override
    public Point3D getMaxCoordinates() {
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        double maxZ = Double.NEGATIVE_INFINITY;
        double x, y, z;
        Point3D p;
        for (Intersectable i : _geometriesList) {
            if (i.getMaxCoordinates() == null)
                continue;
            p = i.getMaxCoordinates();
            x = p.getX().get();
            y = p.getY().get();
            z = p.getZ().get();
            if (x > maxX)
                maxX = x;
            if (y > maxY)
                maxY = y;
            if (z > maxZ)
                maxZ = z;
        }
        return new Point3D(maxX, maxY, maxZ);
    }
}