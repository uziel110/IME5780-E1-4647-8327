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
 * <p>
 * class that implement a grid over all the scene
 * and divide it to voxels
 */
public class Box {
    /**
     * number of voxels in width / height / depth
     */
    private int _density = 1;
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
    private int _lambda = 3;

    public Box(int boxDensity, Geometries geometries) {
        _density = boxDensity;
        setMinBox(geometries.getMinCoordinates());
        setMaxBox(geometries.getMaxCoordinates());
        setDeltas();
        SetMap(geometries);
    }

    private void setMinBox(Point3D min) {
        _minX = min.getX().get();
        _minY = min.getY().get();
        _minZ = min.getZ().get();
    }

    private void setMaxBox(Point3D max) {
        _maxX = max.getX().get();
        _maxY = max.getY().get();
        _maxZ = max.getZ().get();
    }

    private void setDeltas() {
        _voxelSizeX = (_maxX - _minX) / _density;
        _voxelSizeY = (_maxY - _minY) / _density;
        _voxelSizeZ = (_maxZ - _minZ) / _density;
    }

    private Voxel convertPointToVoxel(Point3D min) {
        int x = (int) ((min.getX().get() - _minX) / _voxelSizeX);
        int y = (int) ((min.getY().get() - _minY) / _voxelSizeY);
        int z = (int) ((min.getZ().get() - _minZ) / _voxelSizeZ);
        return new Voxel(x,y,z);
    }

    public void SetMap(Geometries geometries) {
        _voxelGeometriesMap = new HashMap<Voxel, Geometries>();
        Voxel minVoxel, maxVoxel, voxel;
        for (Intersectable geometry : geometries.getGeometries()) {
            minVoxel = convertPointToVoxel(geometry.getMinCoordinates());
            maxVoxel = convertPointToVoxel(geometry.getMaxCoordinates());
            for (int x = minVoxel._x; x <= maxVoxel._x; x++)
                for (int y = minVoxel._y; x <= maxVoxel._y; y++)
                    for (int z = minVoxel._z; x <= maxVoxel._z; z++) {
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

/*    public void setDensity() {
        double boxVolume = (_maxX - _minX) * (_maxY - _minY) * (_maxZ - _minZ);
        double averageDimensionSize = ((_maxX - _minX) + (_maxY - _minY) + (_maxZ - _minZ)) / 3;
        _density = (int) (averageDimensionSize * Math.pow((_lambda * geometriesList.size()) / boxVolume, 1.0 / 3));

        _voxelSizeX = alignZero((_maxX - _minX) / _density);
        _voxelSizeY = alignZero((_maxY - _minY) / _density);
        _voxelSizeZ = alignZero((_maxZ - _minZ) / _density);
    }*/

    public Voxel getFirstVoxel(Ray ray) {
        Point3D p0 = ray.getPoint();
        if (isPointInTheBox(p0)) {
            return convertPointToVoxel(p0);
        }
        double minTX = 0, minTY = 0, minTZ = 0;
        double maxTX = Double.POSITIVE_INFINITY, maxTY = maxTX, maxTZ = maxTX;
        Vector v = ray.getDir();
        Point3D headV = v.getHead();
        Point3D originRay = ray.getPoint();
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
        // System.out.println(p);
        return convertPointToVoxel(p);
    }

    public List<GeoPoint> checkNextVoxels(Voxel voxel, Ray ray, boolean shadowRays, double dis) {
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
//		//////
//		Vector rayDirection = ray.getDir();
//		Point3D dirHeadP = rayDirection.getHeadPoint();
//		Point3D rayOrigin = ray.getP0();
//		// int BoxResolution = _boxDensity;
//		double voxelDimX = _voxelSizeX;
//		double voxelDimY = _voxelSizeY;
//		double voxelDimZ = _voxelSizeZ;
//		// Vector nextCrossingT;
//		double deltaX, deltaY, deltaZ;
//		double tX, tY, tZ;
//		Vector rayOrigBox = rayOrigin.subtract(new Point3D(_minX, _minY, _minZ));
//		Point3D originPoint = rayOrigBox.getHeadPoint();
//		// X
//		if (dirHeadP.getX().get() < 0) {
//			deltaX = (_minX - _maxX) / dirHeadP.getX().get();
//			tX = (Math.floor(originPoint.getX().get() / voxelDimX) * voxelDimX - originPoint.getX().get())
//					/ dirHeadP.getX().get();
//		} else {
//			deltaX = (_maxX - _minX) / dirHeadP.getX().get();
//			tX = ((Math.floor(originPoint.getX().get() / voxelDimX) + 1) * voxelDimX - originPoint.getX().get())
//					/ dirHeadP.getX().get();
//		}
//		// Y
//		if (dirHeadP.getY().get() < 0) {
//			deltaY = (_minY - _maxY) / dirHeadP.getY().get();
//			tY = (Math.floor(originPoint.getY().get() / voxelDimY) * voxelDimY - originPoint.getY().get())
//					/ dirHeadP.getY().get();
//		} else {
//			deltaY = (_maxY - _minY) / dirHeadP.getY().get();
//			tY = ((Math.floor(originPoint.getY().get() / voxelDimY) + 1) * voxelDimY - originPoint.getY().get())
//					/ dirHeadP.getY().get();
//		}
//		// Z
//		if (dirHeadP.getZ().get() < 0) {
//			deltaZ = (_minZ - _maxZ) / dirHeadP.getZ().get();
//			tZ = (Math.floor(originPoint.getZ().get() / voxelDimZ) * voxelDimZ - originPoint.getZ().get())
//					/ dirHeadP.getZ().get();
//		} else {
//			deltaZ = (_maxZ - _minZ) / dirHeadP.getZ().get();
//			tZ = ((Math.floor(originPoint.getZ().get() / voxelDimZ) + 1) * voxelDimZ - originPoint.getZ().get())
//					/ dirHeadP.getZ().get();
//		}
        int[] voxelIndex = new int[3];
        voxelIndex[0] = voxel.getX();
        voxelIndex[1] = voxel.getY();
        voxelIndex[2] = voxel.getZ();
        List<GeoPoint> geoPoints = null;
        boolean canStop = false;
        // double t;
        while (true) {
            if (tX < tY)
                if (tX < tZ) {
                    // t = tX; // current t, next intersection with cell along ray
                    tX += deltaX; // increment, next crossing along x
                    if (rayDirectionX < 0)
                        voxelIndex[0] -= 1;
                    else
                        voxelIndex[0] += 1;
                } else {
                    tZ += deltaZ;
                    if (rayDirectionZ < 0)
                        voxelIndex[2] -= 1;
                    else
                        voxelIndex[2] += 1;
                }
            else if (tY < tZ) {
                // t = tY;
                tY += deltaY; // increment, next crossing along y
                if (rayDirectionY < 0)
                    voxelIndex[1] -= 1;
                else
                    voxelIndex[1] += 1;
            } else {
                // t = tZ;
                tZ += deltaZ; // increment, next crossing along y
                if (rayDirectionZ < 0)
                    voxelIndex[2] -= 1;
                else
                    voxelIndex[2] += 1;
            }
            // if some condition is met break from the loop
            if (voxelIndex[0] < 0 || voxelIndex[1] < 0 || voxelIndex[2] < 0 || voxelIndex[0] >= _density
                    || voxelIndex[1] >= _density || voxelIndex[2] >= _density)
                return geoPoints;
            Voxel v = new Voxel(voxelIndex[0], voxelIndex[0], voxelIndex[0]);
            if (_voxelGeometriesMap.containsKey(v)) {
                Geometries geometries = _voxelGeometriesMap.get(v);
                for (Intersectable geometry : geometries.getGeometries()) {
                    List<GeoPoint> gPoints = geometry.findIntersections(ray, dis);
                    if (gPoints != null) {
                        if (geoPoints == null)
                            geoPoints = new LinkedList<GeoPoint>();
                        if (!shadowRays && !canStop)
                            if (isIntersectInVoxelRange(v, gPoints))
                                canStop = true;
                        geoPoints.addAll(gPoints);
                    }
                }
                if (canStop)
                    return geoPoints;
            }
        }
    }

    public boolean isIntersectInVoxelRange(Voxel voxel, List<GeoPoint> intersections) {
        double minX = _minX + voxel.getX() * _voxelSizeX;
        double maxX = minX + _voxelSizeX;
        double minY = _minY + voxel.getY() * _voxelSizeY;
        double maxY = minY + _voxelSizeY;
        double minZ = _minZ + voxel.getZ() * _voxelSizeZ;
        double maxZ = minZ + _voxelSizeZ;
        double x, y, z;
        for (GeoPoint geoPoint : intersections) {
            x = geoPoint._point.getX().get();
            y = geoPoint._point.getY().get();
            z = geoPoint._point.getZ().get();
            if (x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ)
                return true;
        }
        return false;
    }

    private boolean isPointInTheBox(Point3D p) {
        double x = p.getX().get();
        double y = p.getY().get();
        double z = p.getZ().get();
        return x >= _minX && x <= _maxX && y >= _minY && y <= _maxY && z >= _minZ && z <= _maxZ;
    }

    public Map<Voxel, Geometries> getVoxelMap() {
        return _voxelGeometriesMap;
    }

    public void setLambda(int lambda) {
        _lambda = lambda;
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

    public Map<Voxel, Geometries> getMap() {
        return _voxelGeometriesMap;
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

        public int getX() {
            return _x;
        }

        public int getY() {
            return _y;
        }

        public int getZ() {
            return _z;
        }
    }
}