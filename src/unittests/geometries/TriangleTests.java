package geometries;

import geometries.Intersectable.GeoPoint;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TriangleTests {
    /**
     * Test method for {@link Triangle#findIntersections(Ray)} (geometries.Triangle)}.
     */
    @Test
    public void findIntersections() {

        Triangle triangle = new Triangle(new Point3D(0, 2, 0), new Point3D(2, 0, 0), new Point3D(1, 1, 2));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Inside polygon (1 point)
        assertEquals("Inside polygon", List.of(new GeoPoint(triangle, new Point3D(1, 1, 1))),
                triangle.findIntersections(new Ray(new Point3D(0, 0, 1), new Vector(1, 1, 0))));
        // TC02: Outside against edge (0 points)
        assertNull("Outside against edge",
                triangle.findIntersections(new Ray(new Point3D(0, 0, -1), new Vector(1, 1, 0))));
        // TC03: Outside against vertex (0 points)
        assertNull("Outside against vertex",
                triangle.findIntersections(new Ray(new Point3D(0, 4, -1), new Vector(1, 1, 0))));

        // =============== Boundary Values Tests ==================
        // TC04: On edge (0 points)
        assertNull("Ray start before the polygon and go through the edge",
                triangle.findIntersections(new Ray(new Point3D(0, 0, 1), new Vector(0.5, 1.5, 0))));

        // TC05: In vertex
        assertNull("Ray start before the polygon and go through the vertex",
                triangle.findIntersections(new Ray(new Point3D(0, 0, 1), new Vector(0, 2, -1))));
        // TC06: On edge's continuation
        assertNull("Ray start before the polygon and go through and intersects the plane",
                triangle.findIntersections(new Ray(new Point3D(0, 0, 1), new Vector(2, 0, 3))));
    }
}