package geometries;

import elements.Box;
import primitives.Point3D;
import primitives.Ray;

import java.util.Arrays;
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
        for (Intersectable intersectable : geometries) {
            _geometriesList.add(intersectable);
        }
    }

    /**
     * return list of all geometries
     *
     * @return list of geometries
     */
    public List<Intersectable> getGeometries() {
        return _geometriesList;
    }

    public List<GeoPoint> getRelevantPoints(Ray ray, Box box, boolean shadowRaysCase, double dis) {
        if (box == null)
            return this.findIntersections(ray, dis);
        boolean intersectionFoundInVoxel = false;
        List<GeoPoint> geoPoints = null;
        List<GeoPoint> geometryIntersectionPoints = null;
/*        Set<Intersectable> geometriesSet = new HashSet<>();
        Geometries currentGeometries = new Geometries();*/

        //Voxel currentVoxel = box.getFirstVoxel(ray);
        ray = box.getFirstVoxel(ray);
        if (ray == null) return null;
        Box.Voxel currentVoxel = box.convertPointToVoxel(ray.getPoint());

        double[] deltaAndTArr = box.getRayFirstDeltaAndT(ray);

        while (currentVoxel != null) {
            if (!box.getMap().containsKey(currentVoxel)) {
                currentVoxel = box.getNextVoxel(currentVoxel, ray, deltaAndTArr);
                continue;
            }
            Geometries geometries = box.getMap().get(currentVoxel);
/*            for (Intersectable intersectable : geometries.getGeometries())
                if (!geometriesSet.contains(intersectable)) {
                    currentGeometries.add(intersectable);
                    geometriesSet.add(intersectable);
                }
            geometryIntersectionPoints = currentGeometries.findIntersections(ray, dis);*/
            geometryIntersectionPoints = geometries.findIntersections(ray, dis);
            if (geometryIntersectionPoints != null) {
                if (geoPoints == null)
                    geoPoints = new LinkedList<>();
                geoPoints.addAll(geometryIntersectionPoints);
                if (!shadowRaysCase && !intersectionFoundInVoxel)
                    intersectionFoundInVoxel = box.isIntersectInVoxelRange(currentVoxel, geometryIntersectionPoints);
                else if (intersectionFoundInVoxel)
                    return geoPoints;
            }
            currentVoxel = box.getNextVoxel(currentVoxel, ray, deltaAndTArr);
        }
        return geoPoints;
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