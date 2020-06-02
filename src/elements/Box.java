package elements;

import geometries.Geometries;
import geometries.Geometry;
import geometries.Intersectable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private double _minX, _minY, _minZ;
    private double _maxX, _maxY, _maxZ;
    private Map<Voxel, Geometries> _voxelGeometriesMap;

    /**
     * constructor that create a grid over all the scene
     * by set min and max of x y and z axis of the grid
     *
     * @param geometries the geometries in the scene
     * @param density    number of voxels in width / height / depth
     */
    public Box(Geometries geometries, int density) {
        _density = density;
        List<Intersectable> geometriesList = geometries.getGeometries();
        Geometry geometry = (Geometry) geometriesList.get(0);
        _maxX = geometry.getMaxX();
        _minX = geometry.getMinX();
        _maxY = geometry.getMaxY();
        _minY = geometry.getMinY();
        _maxZ = geometry.getMaxZ();
        _minZ = geometry.getMinZ();

        for (int i = 1; i < geometriesList.size(); i++) {
            geometry = (Geometry) geometriesList.get(i);
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
        double cellSizeX = (_maxX - _minX) / _density;
        double cellSizeY = (_maxY - _minY) / _density;
        double cellSizeZ = (_maxZ - _minZ) / _density;
        _voxelGeometriesMap = new HashMap<>();
        for (Intersectable intersectable : geometriesList) {
            geometry = (Geometry) intersectable;
            int voxelMinIndexX = (int) ((geometry.getMinX() - _minX) / cellSizeX);
            int voxelMaxIndexX = (int) ((geometry.getMaxX() - _minX) / cellSizeX);
            int voxelMinIndexY = (int) ((geometry.getMinY() - _minY) / cellSizeY);
            int voxelMaxIndexY = (int) ((geometry.getMaxY() - _minY) / cellSizeY);
            int voxelMinIndexZ = (int) ((geometry.getMinZ() - _minZ) / cellSizeZ);
            int voxelMaxIndexZ = (int) ((geometry.getMaxZ() - _minZ) / cellSizeZ);
            for (int i = voxelMinIndexX; i <= voxelMaxIndexX; i++) {
                for (int j = voxelMinIndexY; j <= voxelMaxIndexY; j++) {
                    for (int k = voxelMinIndexZ; k <= voxelMaxIndexZ; k++) {
                        Voxel voxel = new Voxel(i, j, k);
                        if (_voxelGeometriesMap.containsKey(voxel))
                            _voxelGeometriesMap.get(voxel).add(geometry);
                        else
                            _voxelGeometriesMap.put(voxel, new Geometries(geometry));
                    }
                }
            }
        }
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
    }
}