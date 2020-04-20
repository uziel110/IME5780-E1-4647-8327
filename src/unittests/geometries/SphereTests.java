package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;

/**
 *
 */
public class SphereTests {

    /**
     * Test method for {@link Sphere#getNormal(Point3D)(geometries.Sphere)}.
     */
    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        Sphere sph = new Sphere(1d,new Point3D(1,0,0));
        Point3D p = new Point3D(1,1,0);
        assertEquals("wrong normal on the sphere",new Vector(0,1,0),sph.getNormal(p));
    }

    /**
     * Test method for {@link Sphere#findIntersections(primitives.Ray) }
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(1d, new Point3D(1, 0, 0));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertEquals("Ray's line out of sphere", null,
                sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))));

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
        List<Point3D> result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0).getX().get() > result.get(1).getX().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses sphere", List.of(p1, p2), result);

        // TC03: Ray starts inside the sphere (1 point)
        assertEquals("Ray starts inside the sphere",List.of(new Point3D(1, 1, 0)),
                sphere.findIntersections(new Ray(new Point3D(1, 0.5, 0), new Vector(0, 1, 0))));
        // TC04: Ray starts after the sphere (0 points)
        assertEquals("Ray starts after the sphere", null,
                sphere.findIntersections(new Ray(new Point3D(3, 0, 0), new Vector(1, 0, 0))));

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        assertEquals("Ray starts at sphere and goes inside", List.of(new Point3D(1, 1, 0)),
                sphere.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(-1, 1, 0))));
        // TC12: Ray starts at sphere and goes outside (0 points)
        assertEquals("Ray starts at sphere and goes outside", null,
                sphere.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(1, 0, 0))));

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        p1 = new Point3D(1, 1, 0);
        p2 = new Point3D(1, -1, 0);
        result = sphere.findIntersections(new Ray(new Point3D(1, 2, 0), new Vector(0, -1, 0)));
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0).getX().get() > result.get(1).getX().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses sphere", List.of(p1, p2), result);
        // TC14: Ray starts at sphere and goes inside (1 points)
        assertEquals("Ray starts at sphere and goes inside", List.of(new Point3D(1, -1, 0)),
                sphere.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(0, -1, 0))));
        // TC15: Ray starts inside (1 points)
        assertEquals("Ray starts inside the sphere",List.of( new Point3D(1, 1, 0)),
                sphere.findIntersections(new Ray(new Point3D(1, -0.5, 0), new Vector(0, 1, 0))));
        // TC16: Ray starts at the center (1 points)
        assertEquals("Ray starts inside the sphere",List.of( new Point3D(1, 1, 0)),
                sphere.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(0, 1, 0))));
        // TC17: Ray starts at sphere and goes outside (0 points)
        assertEquals("Ray starts at sphere and goes outside", null,
                sphere.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(0, 1, 0))));
        // TC18: Ray starts after sphere (0 points)
        assertEquals("Ray starts at sphere and goes outside", null,
                sphere.findIntersections(new Ray(new Point3D(1, 2, 0), new Vector(0, 1, 0))));

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertEquals("Ray starts at sphere and goes outside", null,
                sphere.findIntersections(new Ray(new Point3D(0, 1, 0), new Vector(1, 0, 0))));
        // TC20: Ray starts at the tangent point
        assertEquals("Ray starts at sphere and goes outside", null,
                sphere.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(1, 0, 0))));
        // TC21: Ray starts after the tangent point
        assertEquals("Ray starts at sphere and goes outside", null,
                sphere.findIntersections(new Ray(new Point3D(2, 1, 0), new Vector(1, 0, 0))));

        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertEquals("Ray starts at sphere and goes outside", null,
                sphere.findIntersections(new Ray(new Point3D(0, 2, 0), new Vector(1, 0, 0))));
    }
}