package geometries;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 *
 * @author Dan
 */
public class Polygon extends Geometry {
    /**
     * List of polygon's vertices
     */
    protected List<Point3D> _vertices;
    /**
     * Associated plane in which the polygon lays
     */
    protected Plane _plane;

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     *
     * @param emission Color emission color of the Polygon
     * @param material the material of the polygon
     * @param vertices list of vertices according to their order by edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex></li>
     *                                  </ul>
     */
    public Polygon(Color emission, Material material, Point3D... vertices) {
        super(emission, material);
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        _vertices = List.of(vertices);
        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        _plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (vertices.length == 3) return; // no need for more tests for a Triangle

        Vector n = _plane.getNormal();

        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with the normal. If all the rest consequent edges will generate the same sign -
        // the polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (int i = 1; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
    }

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     * set the emission color to Black
     *
     * @param vertices list of vertices according to their order by edge path
     */
    public Polygon(Point3D... vertices) {
        this(Color.BLACK, new Material(0, 0, 0), vertices);
    }

    @Override
    public Point3D getMin() {
        // 3DDDA algorithm to improve rendering performance
        double minX, minY, minZ;
        minX = _vertices.get(0).getX().get();
        minY = _vertices.get(0).getY().get();
        minZ = _vertices.get(0).getZ().get();
        for (Point3D vertex : _vertices) {
            if (vertex.getX().get() < minX)
                minX = vertex.getX().get();
            if (vertex.getY().get() < minY)
                minY = vertex.getY().get();
            if (vertex.getZ().get() < minZ)
                minZ = vertex.getZ().get();
        }
        return new Point3D(minX, minY, minZ);

    }

    @Override
    public Point3D getMax() {
// 3DDDA algorithm to improve rendering performance
        double maxX, maxY, maxZ;
        maxX = _vertices.get(0).getX().get();
        maxY = _vertices.get(0).getY().get();
        maxZ = _vertices.get(0).getZ().get();
        for (Point3D vertex : _vertices) {
            if (vertex.getX().get() > maxX)
                maxX = vertex.getX().get();
            if (vertex.getY().get() > maxY)
                maxY = vertex.getY().get();
            if (vertex.getZ().get() > maxZ)
                maxZ = vertex.getZ().get();
        }
        return new Point3D(maxX, maxY, maxZ);
    }

    @Override
    public Vector getNormal(Point3D point) {
        return _plane.getNormal();
    }


    @Override
    public List<GeoPoint> findIntersections(Ray ray, double max) {

        List<GeoPoint> list = _plane.findIntersections(ray, max);

        if (list == null) return null;

        List<GeoPoint> polyList = new LinkedList<>();
        for (GeoPoint geoPoint : list) {
            polyList.add(new GeoPoint(this, geoPoint._point));
        }

        List<Vector> pList = new LinkedList<>();
        for (Point3D vertex : _vertices) {
            pList.add(vertex.subtract(ray.getPoint())); //p0 = ray.getPoint()
        }

        List<Vector> nList = new LinkedList<>();
        for (int i = 0; i < pList.size() - 1; ++i) {
            nList.add((pList.get(i).crossProduct(pList.get(i + 1))).normalize());
        }
        nList.add((pList.get(pList.size() - 1).crossProduct(pList.get(0))).normalize());

        Vector v = ray.getVector();

        double d = alignZero(v.dotProduct(nList.get(0)));
        boolean positive = d > 0;
        if (d == 0) return null;

        for (int i = 1; i < nList.size(); ++i) {
            d = alignZero(v.dotProduct(nList.get(i)));
            if (positive != (d > 0) || d == 0)
                return null;
        }
        return polyList;
    }
}
