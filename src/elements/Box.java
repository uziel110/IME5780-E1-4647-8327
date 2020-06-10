package elements;

import geometries.Geometries;
import geometries.Geometry;
import geometries.Intersectable;
import primitives.Point3D;

import java.util.*;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class that implement a grid over all the scene
 * and divide it to voxels
 */
public class Box {
    /**
     * number of voxels in width / height / depth
     */
    private int _density = 1;
    // 3DDDA algorithm to improve rendering performance
    private double _minX = Double.NEGATIVE_INFINITY;
    private double _minY = Double.NEGATIVE_INFINITY;
    private double _minZ = Double.NEGATIVE_INFINITY;
    private double _maxX = Double.POSITIVE_INFINITY;
    private double _maxY = Double.POSITIVE_INFINITY;
    private double _maxZ = Double.POSITIVE_INFINITY;
    private double _voxelSizeX, _voxelSizeY, _voxelSizeZ;
    private Map<Voxel, List<Intersectable>> _voxelGeometriesMap;
    /**
     * factor for calculate the number of voxels in width / height / depth
     */
    private int _lambda = 3;

    /**
     * constructor that create a grid over all the scene
     * by set min and max of x y and z axis of the grid
     * don't send null intersectables
     *
     * @param intersectables the geometries in the scene
     */
    public void setBoxSize(Intersectable... intersectables) {
        List<Intersectable> geometriesList = null;
        for (Intersectable geometries : intersectables) {
            geometriesList = ((Geometries) geometries).getGeometries();
        }
        if (_maxX == Double.POSITIVE_INFINITY) {//todo check if need to check all
            _maxX = Double.NEGATIVE_INFINITY;
            _maxY = Double.NEGATIVE_INFINITY;
            _maxZ = Double.NEGATIVE_INFINITY;
            _minX = Double.POSITIVE_INFINITY;
            _minY = Double.POSITIVE_INFINITY;
            _minZ = Double.POSITIVE_INFINITY;
        }
        Geometry geometry;
        for (Intersectable value : geometriesList) {
            geometry = (Geometry) value;
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
    }

    public void setDensity() {
        double boxVolume = (_maxX - _minX) * (_maxY - _minY) * (_maxZ - _minZ);
        double averageDimensionSize = ((_maxX - _minX) + (_maxY - _minY) + (_maxZ - _minZ)) / 3;
        _density = (int) (averageDimensionSize * Math.pow((_lambda * geometriesList.size()) / boxVolume, 1.0 / 3));

        _voxelSizeX = alignZero((_maxX - _minX) / _density);
        _voxelSizeY = alignZero((_maxY - _minY) / _density);
        _voxelSizeZ = alignZero((_maxZ - _minZ) / _density);

        _voxelGeometriesMap = new HashMap<>();
        for (Intersectable intersectable : geometriesList) {
            geometry = (Geometry) intersectable;
            Voxel minVoxel = getMinVoxelIndex(geometry.getMin());
            Voxel maxVoxel = getMaxVoxelIndex(geometry.getMax());

            for (int i = minVoxel._x; i <= maxVoxel._x; i++) {
                for (int j = minVoxel._y; j <= maxVoxel._y; j++) {
                    for (int k = minVoxel._z; k <= maxVoxel._z; k++) {
                        Voxel voxel = new Voxel(i, j, k);
                        if (_voxelGeometriesMap.containsKey(voxel))
                            _voxelGeometriesMap.get(voxel).add(geometry);
                        else {
                            List<Intersectable> intersects = new LinkedList<>();
                            intersects.add(geometry);
                            _voxelGeometriesMap.put(voxel, intersects);
                        }
                    }
                }
            }
        }
    }

    public Map<Voxel, List<Intersectable>> getVoxelMap() {
        return _voxelGeometriesMap;
    }

    public void setLambda(int lambda) {
        _lambda = lambda;
    }

    public Voxel getMaxVoxelIndex(Point3D max) {
        int voxelMaxIndexX = (int) ((max.getX().get() - _minX) / _voxelSizeX);
        if (isZero((max.getX().get() - _minX) % _voxelSizeX - _voxelSizeX)) voxelMaxIndexX -= 1;
        int voxelMaxIndexY = (int) ((max.getY().get() - _minY) / _voxelSizeY);
        if (isZero((max.getY().get() - _minY) % _voxelSizeY - _voxelSizeY)) voxelMaxIndexY -= 1;
        int voxelMaxIndexZ = (int) ((max.getZ().get() - _minZ) / _voxelSizeZ);
        if (isZero((max.getZ().get() - _minZ) % _voxelSizeZ - _voxelSizeZ)) voxelMaxIndexZ -= 1;
        return new Voxel(voxelMaxIndexX, voxelMaxIndexY, voxelMaxIndexZ);

    }

    public Voxel getMinVoxelIndex(Point3D min) {
        return new Voxel((int) ((min.getX().get() - _minX) / _voxelSizeX),
                (int) ((min.getY().get() - _minY) / _voxelSizeY),
                (int) ((min.getZ().get() - _minZ) / _voxelSizeZ));
    }

    public double getVoxelSizeX() {
        return _voxelSizeX;
    }

    public double getVoxelSizeY() {
        return _voxelSizeY;
    }

    public double getVoxelSizeZ() {
        return _voxelSizeZ;
    }

    /**
     * return density
     *
     * @return density
     */
    public int getDensity() {
        return _density;
    }

    public double getMinX() {
        return _minX;
    }

    public double getMinY() {
        return _minY;
    }

    public double getMinZ() {
        return _minZ;
    }

    public double getMaxX() {
        return _maxX;
    }

    public double getMaxY() {
        return _maxY;
    }

    public double geMaxZ() {
        return _maxZ;
    }

    /**
     * class that implement voxel (piece of the volume)
     */
    public class Voxel {
        private int _x;
        private int _y;
        private int _z;

        public Voxel(int indexX, int indexY, int indexZ) {
            _x = indexX;
            _y = indexY;
            _z = indexZ;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (!(obj instanceof Voxel)) return false;
            Voxel other = (Voxel) obj;
            return _x == other._x && _y == other._y && _z == other._z;
        }

        @Override
        public String toString() {
            return "Voxel{" + _x + "," + _y + "," + _z + '}';
        }

        @Override
        public int hashCode() {
            return Objects.hash(_x, _y, _z);
        }
    }
}