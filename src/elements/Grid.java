package elements;

import geometries.Geometries;
import geometries.Geometry;
import geometries.Intersectable;

import java.util.List;
import java.util.Map;

/**
 * class that implement a grid over all the scene
 * and divide it to voxels
 */
public class Grid {
    /**
     * number of voxels in width / height / depth
     */
    private int _density = 1;
    // 3DDDA algorithm to improve rendering performance
    private double _minX, _minY, _minZ;
    private double _maxX, _maxY, _maxZ;
    private Map<Voxel, Geometries> voxelGeometriesMap;

    /**
     * constructor that create a grid over all the scene
     * by set min and max of x y and z axis of the grid
     *
     * @param geometries the geometries in the scene
     * @param density    number of voxels in width / height / depth
     */
    public Grid(Geometries geometries, int density) {
        _density = density;
        List<Intersectable> geometriesArr = geometries.getGeometries();
        Geometry geometry = (Geometry) geometriesArr.get(0);
        _maxX = geometry.getMaxX();
        _minX = geometry.getMinX();
        _maxY = geometry.getMaxY();
        _minY = geometry.getMinY();
        _maxZ = geometry.getMaxZ();
        _minZ = geometry.getMinZ();

        for (int i = 1; i < geometriesArr.size(); i++) {
            geometry = (Geometry) geometriesArr.get(i);
            if (geometry.getMaxX() > _maxX)
                _maxX = geometry.getMaxX();
            if (geometry.getMinX() < _minX)
                _minX = geometry.getMinX();
            if (geometry.getMaxY() > _maxY)
                _maxY = geometry.getMaxY();
            if (geometry.getMinY() < _minY)
                _minY = geometry.getMinY();
            if (geometry.getMaxZ() > _maxZ)
                _maxZ = geometry.getMaxZ();
            if (geometry.getMinZ() < _minZ)
                _minZ = geometry.getMinZ();
        }
        double deltaX = (_maxX - _minX) / _density;
        double deltaY = (_maxY - _minY) / _density;
        double deltaZ = (_maxZ - _minZ) / _density;
    }

    /**
     * class that implement voxel (piece of the volume)
     */
    public static class Voxel {
        private int _x;
        private int _y;
        private int _z;
    }
}