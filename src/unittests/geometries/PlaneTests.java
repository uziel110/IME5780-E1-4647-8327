package geometries;

import geometries.Intersectable.GeoPoint;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PlaneTests {
    private Plane p = new Plane(new Point3D(0, 0, 0), new Point3D(1, 0, 0), new Point3D(0, 1, 0));

    /**
     * Test method for {@link Plane#getNormal() (geometries.Plane)}.
     */
    @Test
    public void getNormal() {
        assertEquals("wrong plane normal without parameters", new Vector(0, 0, 1), p.getNormal());
    }

    /**
     * Test method for {@link Plane#getNormal()} (geometries.Plane)}.
     */
    @Test
    public void testGetNormal() {
        assertEquals("wrong plane normal with point", new Vector(0, 0, 1), p.getNormal(new Point3D(1, 1, 0)));
    }

    /**
     * Test method for {@link Plane#findIntersections(Ray)} (geometries.Plane)}.
     */
    @Test
    public void findIntsersections() {

        Plane plane = new Plane(new Point3D(0, 1, 0), new Vector(-1, -1, 1));

        // ============ Equivalence Partitions Tests ==============


        // TC01: Ray intersects the plane (1 points)
        assertEquals("Ray intersects the plane, wrong point ", List.of(new GeoPoint(plane, new Point3D(1, 0, 0))),
                plane.findIntersections(new Ray(new Point3D(0.5, 0, 0), new Vector(1, 0, 0))));
        // TC02: Ray does not intersect the plane (0 points)
        assertNull("Ray does not intersect the plane",
                plane.findIntersections(new Ray(new Point3D(0.5, 0, 0), new Vector(-1, 1, 0))));

        // =============== Boundary Values Tests ==================

        // **** Group: Ray is parallel to the plane
        // TC03:  ray included  in the plane (0 points)
        assertNull("Ray is parallel to the plane, ray included in the plane",
                plane.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(1, -1, 0))));
        // TC04:  ray not included in the plane (0 points)
        assertNull("Ray is parallel to the plane, ray not included in the plane",
                plane.findIntersections(new Ray(new Point3D(0.5, 0, 0), new Vector(1, -1, 0))));

        plane = new Plane(new Point3D(0, 1, 0), new Vector(-1, -1, 0));

        // **** Group: Ray is orthogonal to the plane (according to p0)
        // TC05: before the plane (1 points)
        assertEquals("Ray is orthogonal to the plane and start before the plane, worng point",
                List.of(new GeoPoint(plane, new Point3D(0.5, 0.5, 1))),
                plane.findIntersections(new Ray(new Point3D(1, 1, 1), new Vector(-1, -1, 0))));
        // TC06: in the plane (0 points)
        assertNull("Ray is orthogonal to the plane and start in the plane",
                plane.findIntersections(new Ray(new Point3D(0.5, 0.5, 1), new Vector(1, 1, 0))));
        // TC07: after the plane (0 points)
        assertNull("Ray is orthogonal to the plane and start after the plane",
                plane.findIntersections(new Ray(new Point3D(1, 1, 1), new Vector(1, 1, 0))));

        // **** Group: Special cases
        // TC08: Ray is neither orthogonal nor parallel to and begins at the plane (p0 is in the plane, but not the ray)
        assertNull("Ray is neither orthogonal nor parallel to and begins at the plane",
                plane.findIntersections(new Ray(new Point3D(0.5, 0.5, 1), new Vector(1, 2, 3))));
        // TC09: Ray is neither orthogonal nor parallel to the plane and begins in the same point which appears as reference point in the plane (Q)
        assertNull("Ray is neither orthogonal nor parallel to the plane and begins in the same point which appears as reference point in the plane",
                plane.findIntersections(new Ray(new Point3D(0, 1, 0), new Vector(1, 2, 3))));
    }
}