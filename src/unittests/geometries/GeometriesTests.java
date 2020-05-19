package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Testing Geometries
 */
public class GeometriesTests {

    @Test
    public void add() {
        Geometries geometries = new Geometries();
        geometries.add(new Sphere(1, new Point3D(0, 0, 0)));
        assertEquals("Intersections with some shapes , Wrong number of points", 1, geometries.getShapes().size());
    }

    @Test
    public void findIntersections() {
        Geometries geometries = new Geometries(new Sphere(1, new Point3D(1, 0, 0)),
                new Plane(new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0, 0, 0)),
                new Polygon(new Point3D(3, 0, 0), new Point3D(3, 2, 0), new Point3D(3, 2, 4), new Point3D(3, 0, 4)));
        List<GeoPoint> result;

        // ============ Equivalence Partitions Tests ==============
        // TC01:
        result = geometries.findIntersections(new Ray(new Point3D(0, 0, -1), new Vector(2, 0, 4)));
        assertEquals("Intersections with some shapes , Wrong number of points", 3, result.size());

        // =============== Boundary Values Tests ==================
        // TC01:
        Geometries emptyGeometries = new Geometries();
        assertNull("Empty collection of shapes",
                emptyGeometries.findIntersections(new Ray(new Point3D(0, -1, 1), new Vector(1, 0, 0))));

        // TC02:
        assertNull("No intersections with the shapes",
                geometries.findIntersections(new Ray(new Point3D(0, -1, 1), new Vector(1, 0, 0))));


        // TC03:
        result = geometries.findIntersections(new Ray(new Point3D(0, -1, 1), new Vector(3, 2, 0)));
        assertEquals("Only one shape intersections, Wrong number of points", 1, result.size());

        // TC04:
        result = geometries.findIntersections(new Ray(new Point3D(-1, 0, -0.5), new Vector(4, 1, 1)));
        assertEquals("All the shapes intersections with the ray , Wrong number of points", 4, result.size());
/*
        // TC05:
        result = geometries.findIntersections(new Ray(new Point3D(0, 0, -1), new Vector(2, 0, 1)));
        assertEquals("same point of intersections with two shapes , Wrong number of points", 2, result.size());
 */
    }
}