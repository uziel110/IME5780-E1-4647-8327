package unitests;

import geometries.Intersectable.GeoPoint;
import geometries.Sphere;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 *Testing Spheres
 */
public class SphereTests {

    /**
     * Test method for {@link Sphere#getNormal(Point3D)(geometries.Sphere)}.
     */
    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        Sphere sph = new Sphere(1d, new Point3D(1, 0, 0));
        Point3D p = new Point3D(1, 1, 0);
        assertEquals("wrong normal on the sphere", new Vector(0, 1, 0), sph.getNormal(p));
    }

    /**
     * Test method for {@link Sphere#findIntersections(primitives.Ray) }
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(1d, new Point3D(1, 0, 0));

        // ============ Equivalence Partitions Tests ==============


        // TC01: Ray's line is outside the sphere (0 points)
        assertNull("Ray's line out of sphere", sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))));

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
        List<GeoPoint> result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0)._point.getX().get() > result.get(1)._point.getX().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses sphere",
                List.of(new GeoPoint(sphere, p1), new GeoPoint(sphere, p2)), result);

        // TC03: Ray starts inside the sphere (1 point)
        assertEquals("Ray starts inside the sphere", List.of(new GeoPoint(sphere, new Point3D(1, 1, 0))),
                sphere.findIntersections(new Ray(new Point3D(1, 0.5, 0), new Vector(0, 1, 0))));

        // TC04: Ray starts after the sphere (0 points)
        assertNull("Ray starts after the sphere",
                sphere.findIntersections(new Ray(new Point3D(3, 0, 0), new Vector(1, 0, 0))));

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC5: Ray starts at sphere and goes inside (1 points)
        assertEquals("Ray starts at sphere and goes inside, worng point", List.of(new GeoPoint(sphere, new Point3D(1, 1, 0))),
                sphere.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(-1, 1, 0))));

        // TC6: Ray starts at sphere and goes outside (0 points)
        assertNull("Ray starts at sphere and goes outside",
                sphere.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(1, 0, 0))));

        // **** Group: Ray's line goes through the center
        // TC7: Ray starts before the sphere (2 points)
        p1 = new Point3D(1, 1, 0);
        p2 = new Point3D(1, -1, 0);
        result = sphere.findIntersections(new Ray(new Point3D(1, 2, 0), new Vector(0, -1, 0)));
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0)._point.getX().get() > result.get(1)._point.getX().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses sphere",
                List.of(new GeoPoint(sphere, p1), new GeoPoint(sphere, p2)), result);

        // TC8: Ray starts at sphere and goes inside (1 points)
        assertEquals("Ray starts at sphere and goes inside, wrong point", List.of(new GeoPoint(sphere, new Point3D(1, -1, 0))),
                sphere.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(0, -1, 0))));

        // TC9: Ray starts inside (1 points)
        assertEquals("Ray starts inside the sphere, wrong point", List.of(new GeoPoint(sphere, new Point3D(1, 1, 0))),
                sphere.findIntersections(new Ray(new Point3D(1, -0.5, 0), new Vector(0, 1, 0))));

        // TC10: Ray starts at the center (1 points)
        assertEquals("Ray starts at the center of the sphere, wrong point", List.of(new GeoPoint(sphere, new Point3D(1, 1, 0))),
                sphere.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(0, 1, 0))));

        // TC11: Ray starts at sphere and goes outside (0 points)
        assertNull("Ray starts at sphere and goes outside",
                sphere.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(0, 1, 0))));

        // TC12: Ray starts after sphere (0 points)
        assertNull("Ray starts after sphere",
                sphere.findIntersections(new Ray(new Point3D(1, 2, 0), new Vector(0, 1, 0))));

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC13: Ray starts before the tangent point
        assertNull("Ray starts before the tangent point",
                sphere.findIntersections(new Ray(new Point3D(0, 1, 0), new Vector(1, 0, 0))));

        // TC14: Ray starts at the tangent point
        assertNull("Ray starts at the tangent point",
                sphere.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(1, 0, 0))));

        // TC15: Ray starts after the tangent point
        assertNull("Ray starts after the tangent point",
                sphere.findIntersections(new Ray(new Point3D(2, 1, 0), new Vector(1, 0, 0))));

        // **** Group: Special cases
        // TC16: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull("Ray's line is outside, ray is orthogonal to ray start to sphere's center line",
                sphere.findIntersections(new Ray(new Point3D(0, 2, 0), new Vector(1, 0, 0))));
    }
}