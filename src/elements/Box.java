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
    private Point3D _min, _max;
    /*    private double _minX, _minY, _minZ;
        private double _maxX, _maxY, _maxZ;*/
    private double _voxelSizeX, _voxelSizeY, _voxelSizeZ;
    private Map<Voxel, List<Intersectable>> _voxelGeometriesMap;
    /**
     * factor for calculate the number of voxels in width / height / depth
     */
    private int _lambda = 3;
    /**
     * constructor that create a grid over all the scene
     * by set min and max of x y and z axis of the grid
     *
     * @param geometries the geometries in the scene
     */
    public Box(Geometries geometries) {
        List<Intersectable> geometriesList = geometries.getGeometries();
        Geometry geometry = (Geometry) geometriesList.get(0);
        double _maxX = -Double.POSITIVE_INFINITY;
        double _maxY = -Double.POSITIVE_INFINITY;
        double _maxZ = -Double.POSITIVE_INFINITY;
        double _minX = Double.POSITIVE_INFINITY;
        double _minY = Double.POSITIVE_INFINITY;
        double _minZ = Double.POSITIVE_INFINITY;
        int numGeometries = geometriesList.size();
        for (int i = 1; i < numGeometries; i++) {
            geometry = (Geometry) geometriesList.get(i);
            if (geometry.getMax().getX().get() > _maxX)
                _maxX = geometry.getMax().getX().get();
            if (geometry.getMin().getX().get() < _minX)
                _minX = geometry.getMin().getX().get();
            if (geometry.getMax().getY().get() > _maxY)
                _maxY = geometry.getMax().getY().get();
            if (geometry.getMin().getY().get() < _minY)
                _minY = geometry.getMin().getY().get();
            if (geometry.getMax().getZ().get() > _maxZ)
                _maxZ = geometry.getMax().getZ().get();
            if (geometry.getMin().getZ().get() < _minZ)
                _minZ = geometry.getMin().getZ().get();
        }
        _min = new Point3D(_minX, _minY, _minZ);
        _max = new Point3D(_maxX, _maxY, _maxZ);
        double boxVolume = (_maxX - _minX) * (_maxY - _minY) * (_maxZ - _minZ);
        double averageDimensionSize = ((_maxX - _minX) + (_maxY - _minY) + (_maxZ - _minZ)) / 3;
        _density = (int) (averageDimensionSize * Math.pow((_lambda * numGeometries) / boxVolume, 1.0 / 3));

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
        int voxelMaxIndexX = (int) ((max.getX().get() - _min.getX().get()) / _voxelSizeX);
        if (isZero((max.getX().get() - _min.getX().get()) % _voxelSizeX - _voxelSizeX)) voxelMaxIndexX -= 1;
        int voxelMaxIndexY = (int) ((max.getY().get() - _min.getY().get()) / _voxelSizeY);
        if (isZero((max.getY().get() - _min.getY().get()) % _voxelSizeY - _voxelSizeY)) voxelMaxIndexY -= 1;
        int voxelMaxIndexZ = (int) ((max.getZ().get() - _min.getZ().get()) / _voxelSizeZ);
        if (isZero((max.getZ().get() - _min.getZ().get()) % _voxelSizeZ - _voxelSizeZ)) voxelMaxIndexZ -= 1;
        return new Voxel(voxelMaxIndexX, voxelMaxIndexY, voxelMaxIndexZ);

    }

    public Voxel getMinVoxelIndex(Point3D min) {
        return new Voxel((int) ((min.getX().get() - _min.getX().get()) / _voxelSizeX),
                (int) ((min.getY().get() - _min.getY().get()) / _voxelSizeY),
                (int) ((min.getZ().get() - _min.getZ().get()) / _voxelSizeZ));
    }

    public Point3D getMin() {
        return _min;
    }

    public Point3D getMax() {
        return _max;
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