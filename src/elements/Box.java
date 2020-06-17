package elements;

import geometries.Geometries;
import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.*;

import static primitives.Util.alignZero;

/**
 * 3DDDA algorithm to improve rendering performance
 *
 * class that implement a grid over all the scene
 * and divide it to voxels
 */
public class Box {
    /**
     * number of voxels in width / height / depth
     */
    private int _density;
    private double _minX = Double.NEGATIVE_INFINITY;
    private double _minY = Double.NEGATIVE_INFINITY;
    private double _minZ = Double.NEGATIVE_INFINITY;
    private double _maxX = Double.POSITIVE_INFINITY;
    private double _maxY = Double.POSITIVE_INFINITY;
    private double _maxZ = Double.POSITIVE_INFINITY;
    private double _voxelSizeX, _voxelSizeY, _voxelSizeZ;
    private Map<Voxel, Geometries> _voxelGeometriesMap;

    /**
     * factor for calculate the number of voxels in width / height / depth
     */

    public Box(int lambda, Geometries geometries) {
        setMinBox(geometries.getMinCoordinates());
        setMaxBox(geometries.getMaxCoordinates());
        setDensity(geometries.getGeometries().size(), lambda);
        setDeltas();
        SetMap(geometries);
    }

    /**
     * set the minimum coordinates of the box
     *
     * @param min the minimum coordinates of the geometries
     */
    private void setMinBox(Point3D min) {
        _minX = min.getX().get();
        _minY = min.getY().get();
        _minZ = min.getZ().get();
    }

    /**
     *  set the maximum coordinates of the box
     *
     * @param max the maximum coordinates of the geometries
     */
    private void setMaxBox(Point3D max) {
        _maxX = max.getX().get();
        _maxY = max.getY().get();
        _maxZ = max.getZ().get();
    }

    /**
     * set voxel size
     */
    private void setDeltas() {
        _voxelSizeX = (_maxX - _minX) / _density;
        _voxelSizeY = (_maxY - _minY) / _density;
        _voxelSizeZ = (_maxZ - _minZ) / _density;
    }

    /**
     * convert point to voxel
     *
     * @param point to convert
     * @return voxel
     */
    public Voxel convertPointToVoxel(Point3D point) {
        int x = (int) ((point.getX().get() - _minX) / _voxelSizeX);
        int y = (int) ((point.getY().get() - _minY) / _voxelSizeY);
        int z = (int) ((point.getZ().get() - _minZ) / _voxelSizeZ);
        return new Voxel(x, y, z);
    }

    /**
     * Assigns voxels to each geometry by the location of the geometry, relative to the box,
     * and saves the geometry and voxel where the geometry is in the map
     *
     * @param geometries all the geometries that exist in the scene
     */
    public void SetMap(Geometries geometries) {
        _voxelGeometriesMap = new HashMap<>();
        Voxel minVoxel, maxVoxel, voxel;
        for (Intersectable geometry : geometries.getGeometries()) {
            minVoxel = convertPointToVoxel(geometry.getMinCoordinates());
            maxVoxel = convertPointToVoxel(geometry.getMaxCoordinates());
            for (int x = minVoxel._x; x <= maxVoxel._x; ++x)
                for (int y = minVoxel._y; y <= maxVoxel._y; ++y)
                    for (int z = minVoxel._z; z <= maxVoxel._z; ++z) {
                        voxel = new Voxel(x, y, z);
                        if (_voxelGeometriesMap.containsKey(voxel))
                            _voxelGeometriesMap.get(voxel).add(geometry);
                        else {
                            Geometries g = new Geometries(geometry);
                            _voxelGeometriesMap.put(voxel, g);
                        }
                    }
        }
    }

    public Ray getFirstVoxel(Ray ray) {
        Point3D originRay = ray.getPoint();
        if (isPointInTheBox(originRay))
            return ray;
        double minTX = 0, minTY = 0, minTZ = 0;
        double maxTX = Double.POSITIVE_INFINITY, maxTY = maxTX, maxTZ = maxTX;
        Vector v = ray.getDir();
        Point3D headV = v.getHead();
        double rayX = alignZero(headV.getX().get());
        double rayY = alignZero(headV.getY().get());
        double rayZ = alignZero(headV.getZ().get());
        double rayPX = alignZero(originRay.getX().get());
        double rayPY = alignZero(originRay.getY().get());
        double rayPZ = alignZero(originRay.getZ().get());

        if (rayX == 0 && (rayPX > _maxX || rayPX < _minX))
            return null;
        if (rayX > 0) {
            if (_maxX < rayPX)
                return null;
            maxTX = (_maxX - rayPX) / rayX;
            minTX = Double.max(0, (_minX - rayPX) / rayX);
        }
        if (rayX < 0) {
            if (_minX > rayPX)
                return null;
            maxTX = (_minX - rayPX) / rayX;
            minTX = Double.max(0, (_maxX - rayPX) / rayX);
        }

        if (rayY == 0 && (rayPY > _maxY || rayPY < _minY))
            return null;
        if (rayY > 0) {
            if (_maxY < rayPY)
                return null;
            maxTY = (_maxY - rayPY) / rayY;
            minTY = Double.max(0, (_minY - rayPY) / rayY);
        }
        if (rayY < 0) {
            if (_minY > rayPY)
                return null;
            maxTY = (_minY - rayPY) / rayY;
            minTY = Double.max(0, (_maxY - rayPY) / rayY);
        }

        if (rayZ == 0 && (rayPZ > _maxZ || rayPZ < _minZ))
            return null;
        if (rayZ > 0) {
            if (_maxZ < rayPZ)
                return null;
            maxTZ = (_maxZ - rayPZ) / rayZ;
            minTZ = Double.max(0, (_minZ - rayPZ) / rayZ);
        }
        if (rayZ < 0) {
            if (_minZ > rayPZ)
                return null;
            maxTZ = (_minZ - rayPZ) / rayZ;
            minTZ = Double.max(0, (_maxZ - rayPZ) / rayZ);
        }
        double minT = Double.min(maxTX, maxTY);
        minT = Double.min(minT, maxTZ);
        double maxT = Double.max(minTX, minTY);
        maxT = Double.max(maxT, minTZ);
        if (minT < maxT)
            return null;
        Point3D p = ray.getPoint(maxT);
        return new Ray(p,ray.getDir());
    }

    public double[] getRayFirstDeltaAndT(Ray ray) {
        Vector rayDirection = ray.getDir();
        Point3D rayHead = rayDirection.getHead();
        double rayDirectionX = rayHead.getX().get();
        double rayDirectionY = rayHead.getY().get();
        double rayDirectionZ = rayHead.getZ().get();

        // P0 of the ray it always in the grid
        Point3D rayOrigin = ray.getPoint();

        // vector from the min corner of the grid to the P0 of the ray to get the
        // distance between them for each axis
        Vector rayOrigGrid = rayOrigin.subtract(new Point3D(_minX, _minY, _minZ));
        double rayOrigGridX = rayOrigGrid.getHead().getX().get();
        double rayOrigGridY = rayOrigGrid.getHead().getY().get();
        double rayOrigGridZ = rayOrigGrid.getHead().getZ().get();
        double deltaX, deltaY, deltaZ, tX, tY, tZ;
        if (rayDirectionX < 0) { // Negative direction on the x axis
            deltaX = -_voxelSizeX / rayDirectionX;
            tX = (Math.floor(rayOrigGridX / _voxelSizeX) * _voxelSizeX - rayOrigGridX) / rayDirectionX;
        } else { // Positive direction on the x axis
            deltaX = _voxelSizeX / rayDirectionX;
            tX = (Math.floor(rayOrigGridX / _voxelSizeX + 1) * _voxelSizeX - rayOrigGridX) / rayDirectionX;
        }
        if (rayDirectionY < 0) { // Negative direction on the y axis
            deltaY = -_voxelSizeY / rayDirectionY;
            tY = (Math.floor(rayOrigGridY / _voxelSizeY) * _voxelSizeY - rayOrigGridY) / rayDirectionY;
        } else { // Positive direction on the y axis
            deltaY = _voxelSizeY / rayDirectionY;
            tY = (Math.floor(rayOrigGridY / _voxelSizeY + 1) * _voxelSizeY - rayOrigGridY) / rayDirectionY;
        }
        if (rayDirectionZ < 0) { // Negative direction on the z axis
            deltaZ = -_voxelSizeZ / rayDirectionZ;
            tZ = (Math.floor(rayOrigGridZ / _voxelSizeZ) * _voxelSizeZ - rayOrigGridZ) / rayDirectionZ;
        } else { // Positive direction on the z axis
            deltaZ = _voxelSizeZ / rayDirectionZ;
            tZ = (Math.floor(rayOrigGridZ / _voxelSizeZ + 1) * _voxelSizeZ - rayOrigGridZ) / rayDirectionZ;
        }
        return new double[]{tX, tY, tZ, deltaX, deltaY, deltaZ};
    }

    /**
     *
     *
     * @param voxel
     * @param ray
     * @param TandDelta
     * @return
     */
    public Voxel getNextVoxel(Voxel voxel, Ray ray, double[] TandDelta) {
        int[] voxelIndex = new int[3];
        voxelIndex[0] = voxel.getX();
        voxelIndex[1] = voxel.getY();
        voxelIndex[2] = voxel.getZ();
        if (TandDelta[0] < TandDelta[1]) // tX < tY
            if (TandDelta[0] < TandDelta[2]) { // tX < tZ
                // t = tX; // current t, next intersection with cell along ray
                TandDelta[0] += TandDelta[3]; // increment, next crossing along x
                if (ray.getDir().getHead().getX().get() < 0)
                    voxelIndex[0] -= 1; // tX -= 1
                else
                    voxelIndex[0] += 1; // tX += 1
            } else {
                TandDelta[2] += TandDelta[5]; // tZ += deltaZ
                if (ray.getDir().getHead().getZ().get() < 0)
                    voxelIndex[2] -= 1; // tZ -= 1
                else
                    voxelIndex[2] += 1; // tZ += 1
            }
        else if (TandDelta[1] < TandDelta[2]) { // tY < tZ
            // t = tY;
            TandDelta[1] += TandDelta[4]; // increment, next crossing along y
            if (ray.getDir().getHead().getY().get() < 0)
                voxelIndex[1] -= 1; // tY -= 1
            else
                voxelIndex[1] += 1; // tY += 1
        } else {
            // t = tZ;
            TandDelta[2] += TandDelta[5]; // increment, next crossing along y
            if (ray.getDir().getHead().getZ().get() < 0)
                voxelIndex[2] -= 1; // tZ -= 1
            else
                voxelIndex[2] += 1; // tZ += 1
        }
        // if some condition is met break from the loop
        if (voxelIndex[0] < 0 || voxelIndex[1] < 0 || voxelIndex[2] < 0 || voxelIndex[0] > _density
                || voxelIndex[1] > _density || voxelIndex[2] > _density)
            return null;
        return new Voxel(voxelIndex[0], voxelIndex[1], voxelIndex[2]);
    }

    /**
     *
     * @param voxel
     * @param intersections
     * @return
     */
    public boolean isIntersectInVoxelRange(Voxel voxel, List<GeoPoint> intersections) {
        for (GeoPoint geoPoint : intersections)
            if (convertPointToVoxel(geoPoint._point).equals(voxel))
                return true;
        return false;
    }

    /**
     * return if the point is within the range of the box
     *
     * @param p point
     * @return if the point is within the range of the box
     */
    private boolean
    isPointInTheBox(Point3D p) {
        double x = p.getX().get();
        double y = p.getY().get();
        double z = p.getZ().get();
        return x >= _minX && x <= _maxX && y >= _minY && y <= _maxY && z >= _minZ && z <= _maxZ;
    }



    /**
     * return the x size voxel
     *
     * @return x size voxel
     */
    public double getVoxelSizeX() {
        return _voxelSizeX;
    }

    /**
     * return the y size voxel
     *
     * @return y size voxel
     */
    public double getVoxelSizeY() {
        return _voxelSizeY;
    }

    /**
     * return the z size voxel
     *
     * @return z size voxel
     */
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
     * set the density of the map
     *
     * @param numGeometries amount of the geometries
     * @param lambda parameter for calculating box density (for optimum results the parameter is between 3 to 5)
     */
    public void setDensity(int numGeometries, int lambda) {
        double boxVolume = (_maxX - _minX) * (_maxY - _minY) * (_maxZ - _minZ);
        double averageDimensionSize = ((_maxX - _minX) + (_maxY - _minY) + (_maxZ - _minZ)) / 3;
        _density = (int) (averageDimensionSize * Math.pow((lambda * numGeometries) / boxVolume, 1 / 3d));
    }

    /**
     * return minimum x coordinate of the box
     *
     * @return minimum x coordinate of the box
     */
    public double getMinX() {
        return _minX;
    }

    /**
     * return minimum y coordinate of the box
     *
     * @return minimum y coordinate of the box
     */
    public double getMinY() {
        return _minY;
    }

    /**
     * return minimum z coordinate of the box
     *
     * @return minimum z coordinate of the box
     */
    public double getMinZ() {
        return _minZ;
    }

    /**
     * return maximum x coordinate of the box
     *
     * @return maximum x coordinate of the box
     */
    public double getMaxX() {
        return _maxX;
    }

    /**
     * return maximum y coordinate of the box
     *
     * @return maximum y coordinate of the box
     */
    public double getMaxY() {
        return _maxY;
    }

    /**
     * return maximum z coordinate of the box
     *
     * @return maximum z coordinate of the box
     */
    public double geMaxZ() {
        return _maxZ;
    }

    /**
     * return map
     *
     * @return Map
     */
    public Map<Voxel, Geometries> getMap() {
        return _voxelGeometriesMap;
    }

    /**
     * class that implement voxel (piece of the volume)
     */
    public static class Voxel {
        private final int _x;
        private final int _y;
        private final int _z;

        /**
         * constructor
         *
         * @param indexX x coordinate
         * @param indexY y coordinate
         * @param indexZ z coordinate
         */
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

        /**
         * return x coordinate of the voxel
         *
         * @return x coordinate
         */
        public int getX() {
            return _x;
        }

        /**
         * return y coordinate of the voxel
         *
         * @return y coordinate
         */
        public int getY() {
            return _y;
        }

        /**
         * return Z coordinate of the voxel
         *
         * @return Z coordinate
         */
        public int getZ() {
            return _z;
        }
    }
}