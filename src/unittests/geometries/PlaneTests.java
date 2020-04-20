package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PlaneTests {
    private Plane p = new Plane(new Point3D(0, 0, 0), new Point3D(1, 0, 0), new Point3D(0, 1, 0));

    /**
     * Test method for {@link Plane#getNormal() (geometries.Plane)}.
     */
    @Test
    public void getNormal() {
        assertEquals("wrong plane normal without parameters", new Vector (0,0,1), p.getNormal());
    }

    /**
     * Test method for {@link Plane#getNormal()} (geometries.Plane)}.
     */
    @Test
    public void testGetNormal() {
        assertEquals("wrong plane normal with point", new Vector (0,0,1), p.getNormal(new Point3D(1, 1, 0)));
    }

    /**
     * Test method for {@link Plane#findIntsersections(Ray)} (geometries.Plane)}.
     */
    @Test
    public void findIntsersections() {

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray intersects the plane

        // TC02: Ray does not intersect the plane

        // =============== Boundary Values Tests ==================

        // **** Group: Ray is parallel to the plane
        // TC03:  ray included  in the plane
        // TC04:  ray not included in the plane

        // **** Group: Ray is orthogonal to the plane (according to p0)
        // TC05: before the plane
        // TC06: in the plane
        // TC07: after the plane

        // **** Group: Special cases
        // TC08: Ray is neither orthogonal nor parallel to and begins at the plane (𝑃0 is in the plane, but not the ray)
        // TC09: Ray is neither orthogonal nor parallel to the plane and begins in the same point which appears as reference point in the plane (Q)

    }
}