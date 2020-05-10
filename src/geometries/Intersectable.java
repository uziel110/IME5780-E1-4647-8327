package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * interface for the intersections between the geometry shape and a ray from the camera
 */
public interface Intersectable {

    /**
     * class for groping point and his geometry
     */
    public static class GeoPoint {
        public Geometry _geometry;
        public Point3D _point;

        /**
         * constructor for GeoPoint calss that receive two parameters
         * @param geometry Geometry shape
         * @param point Point3D of intersection that lie on the geometry
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            _geometry = geometry;
            _point = new Point3D(point);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (!(obj instanceof GeoPoint)) return false;
            GeoPoint other = (GeoPoint) obj;
            return this._geometry.equals(other._geometry) &&
                    this._point.equals(other._point);
        }
    }


    /**
     * find all the Intersections between the geometry shape and a ray that receive as parameter
     *
     * @param ray
     * @return list of intersections points if no such points return null
     */
    List<GeoPoint> findIntersections(Ray ray);


}


