package geometries;

import static org.junit.Assert.*;

import org.junit.Test;

import primitives.*;

import java.util.List;

/**
 * Testing Polygons
 * @author Dan
 *
 */
public class PolygonTests {

    /**
     * Test method for
     * {@link Polygon#Polygon(Point3D...)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(0, 1, 0),
                    new Point3D(1, 0, 0), new Point3D(-1, 1, 1));
            fail("Constructed a polygon with wrong order of vertices");
        } catch (IllegalArgumentException e) {}

        // TC03: Not in the same plane
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 2, 2));
            fail("Constructed a polygon with vertices that are not in the same plane");
        } catch (IllegalArgumentException e) {}

        // TC04: Concave quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0.5, 0.25, 0.5));
            fail("Constructed a concave polygon");
        } catch (IllegalArgumentException e) {}

        // =============== Boundary Values Tests ==================

        // TC10: Vertix on a side of a quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0.5, 0.5));
            fail("Constructed a polygon with vertix on a side");
        } catch (IllegalArgumentException e) {}

        // TC11: Last point = first point
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0, 1));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {}

        // TC12: Collocated points
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 1, 0));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {}

    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Polygon pl = new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0),
                new Point3D(-1, 1, 1));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals("Bad normal to trinagle", new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)));
    }

    /**
     * Test method for {@link Polygon#findIntersections(Ray)} (geometries.Polygon)}.
     */
    @Test
    public void findIntsersections() {

        Polygon polygon = new Polygon(new Point3D(0,2,0), new Point3D(2,0,0), new Point3D(1,1,2));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Inside polygon (1 point)
        assertEquals("Inside polygon", List.of(new Point3D(1, 1, 1)),
                polygon.findIntersections(new Ray(new Point3D(0, 0, 1), new Vector(1, 1, 0))));
        // TC02: Outside against edge (0 points)
        assertNull("Outside against edge",
                polygon.findIntersections(new Ray(new Point3D(0, 0, -1), new Vector(1, 1, 0))));
        // TC03: Outside against vertex (0 points)
        assertNull("Outside against vertex",
                polygon.findIntersections(new Ray(new Point3D(0, 4, -1), new Vector(1, 1, 0))));

        // =============== Boundary Values Tests ==================
        // TC04: On edge (0 points)
        assertNull("Ray start before the polygon and go through the edge",
                polygon.findIntersections(new Ray(new Point3D(0, 0, 1), new Vector(0.5, 1.5, 0))));

        // TC05: In vertex
        assertNull("Ray start before the polygon and go through the vertex",
                polygon.findIntersections(new Ray(new Point3D(0, 0, 1), new Vector(0, 2, -1))));
        // TC06: On edge's continuation
        assertNull("Ray start before the polygon and go through and intersects the plane",
                polygon.findIntersections(new Ray(new Point3D(0, 0, 1), new Vector(2, 0, 3))));


    }
}
