package geometries;

import elements.Box;
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

    private List<Intersectable> getRelevantGeometries(Ray ray){

        Vector rayDirection = ray.getVector();
        Point3D rayPoint = ray.getPoint();
        int BoxResolution = _box.getDensity();
        double voxelDimX = _box.getVoxelSizeX();
        double voxelDimY = _box.getVoxelSizeY();
        double voxelDimZ = _box.getVoxelSizeZ();
        Vector nextCrossingT;
        double deltaX, deltaY, deltaZ;
        Vector rayOrigBox = rayPoint.subtract(_box.getMin());
        //X
        if (rayDirection.getEnd().getX().get() < 0) {
            deltaX = (_box.getMin().getX().get() - _box.getMax().getX().get()) / rayDirection.getEnd().getX().get();
            double tX = (Math.floor(rayOrigBox.getEnd().getX().get() / voxelDimX) * voxelDimX) -
                    rayOrigBox.getEnd().getX().get() / rayDirection.getEnd().getX().get();
        } else {
            deltaX = (_box.getMax().getX().get() - _box.getMin().getX().get()) / rayDirection.getEnd().getX().get();
            double tX = ((Math.floor(rayOrigBox.getEnd().getX().get() / voxelDimX) + 1) * voxelDimX) -
                    rayOrigBox.getEnd().getX().get() / rayDirection.getEnd().getX().get();
        }
        //Y
        if (rayDirection.getEnd().getY().get() < 0) {
            deltaY = (_box.getMin().getY().get() - _box.getMax().getY().get()) / rayDirection.getEnd().getY().get();
            double tY = (Math.floor(rayOrigBox.getEnd().getY().get() / voxelDimY) * voxelDimY) -
                    rayOrigBox.getEnd().getY().get() / rayDirection.getEnd().getY().get();
        } else {
            deltaY = (_box.getMax().getY().get() - _box.getMin().getY().get()) / rayDirection.getEnd().getY().get();
            double tY = ((Math.floor(rayOrigBox.getEnd().getY().get() / voxelDimY) + 1) * voxelDimY) -
                    rayOrigBox.getEnd().getY().get() / rayDirection.getEnd().getY().get();
        }
        //Z
        if (rayDirection.getEnd().getZ().get() < 0) {
            deltaZ = (_box.getMin().getZ().get() - _box.getMax().getZ().get()) / rayDirection.getEnd().getZ().get();
            double tZ = (Math.floor(rayOrigBox.getEnd().getZ().get() / voxelDimZ) * voxelDimZ) -
                    rayOrigBox.getEnd().getZ().get() / rayDirection.getEnd().getZ().get();
        } else {
            deltaZ = (_box.getMax().getZ().get() - _box.getMin().getZ().get()) / rayDirection.getEnd().getZ().get();
            double tZ = ((Math.floor(rayOrigBox.getEnd().getZ().get() / voxelDimZ) + 1) * voxelDimZ) -
                    rayOrigBox.getEnd().getZ().get() / rayDirection.getEnd().getZ().get();
        }
        double t = 0;
        return new LinkedList<>();
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
}