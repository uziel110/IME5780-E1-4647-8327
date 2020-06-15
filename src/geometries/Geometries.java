package geometries;

import elements.Box;
import elements.Box.Voxel;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

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

    public List<GeoPoint> getRelevantPoints(Ray ray, Box box, boolean shadowRays, double dis) {
        if (box == null) {
            return this.findIntersections(ray, dis);
        } else {
            boolean canStop = false;
            List<GeoPoint> geoPoints = null;
            // System.out.println(ray);
            Voxel voxel = box.getFirstVoxel(ray);
            if (voxel != null) {
                // System.out.println(voxel);
                if (box.getMap().containsKey(voxel)) {
                    Geometries geometries = box.getMap().get(voxel);
                    for (Intersectable geometry : geometries.getGeometries()) {
                        List<GeoPoint> gPoints = geometry.findIntersections(ray, dis);
                        if (gPoints != null) {
                            if (!shadowRays && !canStop)
                                canStop = box.isIntersectInVoxelRange(voxel, gPoints);
                            geoPoints = new LinkedList<GeoPoint>();
                            geoPoints.addAll(gPoints);
                        }
                    }
                    if (canStop)
                        return geoPoints;
                }
                // Traveling on the box
                List<GeoPoint> gP = box.checkNextVoxels(voxel, ray, shadowRays, dis);
                if (gP != null) {
                    if (geoPoints == null)
                        geoPoints = new LinkedList<GeoPoint>();
                    geoPoints.addAll(gP);
                }
            }
            return geoPoints;
        }

    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray, double max) {
        List<GeoPoint> intersectionPoints = null;
        //for (Intersectable geometry : getRelevantGeometries(ray)) {
        for (Intersectable geometry : _geometries) {
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
        for (Intersectable i : _geometries) {
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
        for (Intersectable i : _geometries) {
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