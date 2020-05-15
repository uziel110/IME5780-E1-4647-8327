package geometries;

import geometries.Intersectable.GeoPoint;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Tube class tests
 */
public class TubeTests {

    /**
     * Test method for {@link Tube#getNormal(Point3D)(geometries.Tube)}.
     */
    @Test
    public void getNormal() {
        Tube t = new Tube(new Point3D(0, 0, 0), new Vector(0, 0, 1), 1);
        // ============ Equivalence Partitions Tests ==============
        // TC01:
        // on the ray direction
        assertEquals("wrong normal on the tube's side in the ray direction",
                new Vector(0, 1, 0), t.getNormal(new Point3D(0, 1, 1)));
        // TC02:
        // opposite the ray direction
        assertEquals("wrong normal on the tube's side opposite the ray direction",
                new Vector(0, 1, 0), t.getNormal(new Point3D(0, 1, -1)));
        // =============== Boundary Values Tests ==================
        //Normal on the ray's point of the tube
        assertEquals("wrong normal on the bottom", new Vector(0, 1, 0), t.getNormal(new Point3D(0, 1, 0)));
    }

    /**
     * Test method for {@link Tube#findIntersections(Ray)}.
     */
    @Test
    public void testFindIntersectionsRay() {
        Tube tube1 = new Tube(new Ray(new Point3D(1, 0, 0), new Vector(0, 1, 0)), 1d);
        Vector vAxis = new Vector(0, 0, 1);
        Tube tube2 = new Tube(new Ray(new Point3D(1, 1, 1), vAxis), 1d);
        Ray ray;
        List<GeoPoint> result;
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the tube (0 points)
        ray = new Ray(new Point3D(1, 1, 2), new Vector(1, 1, 0));
        assertNull("Must not be intersections", tube1.findIntersections(ray));

        // TC02: Ray's crosses the tube (2 points)
        ray = new Ray(new Point3D(0, 0, 0), new Vector(2, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
            assertEquals("must be 2 intersections", 2, result.size());
        if (result.get(0)._point.getY().get() > result.get(1)._point.getY().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Bad intersections",
                List.of(new GeoPoint(tube2, new Point3D(0.4, 0.2, 0.2)),
                        new GeoPoint(tube2, new Point3D(2, 1, 1))), result);

        // TC03: Ray's starts within tube and crosses the tube (1 point)
        ray = new Ray(new Point3D(1, 0.5, 0.5), new Vector(2, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections", List.of(new GeoPoint(tube2, new Point3D(2, 1, 1))), result);

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line is parallel to the axis (0 points)
        // TC04: Ray is inside the tube (0 points)
        ray = new Ray(new Point3D(0.5, 0.5, 0.5), vAxis);
        assertNull("must not be intersections", tube2.findIntersections(ray));
        // TC05: Ray is outside the tube
        ray = new Ray(new Point3D(0.5, -0.5, 0.5), vAxis);
        assertNull("must not be intersections", tube2.findIntersections(ray));
        // TC06: Ray is at the tube surface
        ray = new Ray(new Point3D(2, 1, 0.5), vAxis);
        assertNull("must not be intersections", tube2.findIntersections(ray));
        // TC07: Ray is inside the tube and starts against axis head
        ray = new Ray(new Point3D(0.5, 0.5, 1), vAxis);
        assertNull("must not be intersections", tube2.findIntersections(ray));
        // TC08: Ray is outside the tube and starts against axis head
        ray = new Ray(new Point3D(0.5, -0.5, 1), vAxis);
        assertNull("must not be intersections", tube2.findIntersections(ray));
        // TC09: Ray is at the tube surface and starts against axis head
        ray = new Ray(new Point3D(2, 1, 1), vAxis);
        assertNull("must not be intersections", tube2.findIntersections(ray));
        // TC10: Ray is inside the tube and starts at axis head
        ray = new Ray(new Point3D(1, 1, 1), vAxis);
        assertNull("must not be intersections", tube2.findIntersections(ray));

        // **** Group: Ray is orthogonal but does not begin against the axis head
        // TC11: Ray starts outside and the line is outside (0 points)
        ray = new Ray(new Point3D(0, 2, 2), new Vector(1, 1, 0));
        assertNull("must not be intersections", tube2.findIntersections(ray));
        // TC12: The line is tangent and the ray starts before the tube (0 points)
        ray = new Ray(new Point3D(0, 2, 2), new Vector(1, 0, 0));
        assertNull("must not be intersections", tube2.findIntersections(ray));
        // TC13: The line is tangent and the ray starts at the tube (0 points)
        ray = new Ray(new Point3D(1, 2, 2), new Vector(1, 0, 0));
        assertNull("must not be intersections", tube2.findIntersections(ray));
        // TC14: The line is tangent and the ray starts after the tube (0 points)
        ray = new Ray(new Point3D(2, 2, 2), new Vector(1, 0, 0));
        assertNull("must not be intersections", tube2.findIntersections(ray));
        // TC15: Ray starts before (2 points)
        ray = new Ray(new Point3D(0, 0, 2), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 2 intersections", 2, result.size());
        if (result.get(0)._point.getY().get() > result.get(1)._point.getY().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Bad intersections",
                List.of(new GeoPoint(tube2, new Point3D(0.4, 0.2, 2)),
                        new GeoPoint(tube2, new Point3D(2, 1, 2))), result);
        // TC16: Ray starts at the surface and goes inside (1 point)
        ray = new Ray(new Point3D(0.4, 0.2, 2), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections", List.of(new GeoPoint(tube2, new Point3D(2, 1, 2))), result);
        // TC17: Ray starts inside (1 point)
        ray = new Ray(new Point3D(1, 0.5, 2), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections", List.of(new GeoPoint(tube2, new Point3D(2, 1, 2))), result);
        // TC18: Ray starts at the surface and goes outside (0 points)
        ray = new Ray(new Point3D(2, 1, 2), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull("Bad intersections", result);
        // TC19: Ray starts after
        ray = new Ray(new Point3D(4, 2, 2), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull("Bad intersections", result);
        // TC20: Ray starts before and crosses the axis (2 points)
        ray = new Ray(new Point3D(1, -1, 2), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 2 intersections", 2, result.size());
        if (result.get(0)._point.getY().get() > result.get(1)._point.getY().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Bad intersections",
                List.of(new GeoPoint(tube2, new Point3D(1, 0, 2)),
                        new GeoPoint(tube2, new Point3D(1, 2, 2))), result);
        // TC21: Ray starts at the surface and goes inside and crosses the axis
        ray = new Ray(new Point3D(1, 0, 2), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections", List.of(new GeoPoint(tube2, new Point3D(1, 2, 2))), result);
        // TC22: Ray starts inside and the line crosses the axis (1 point)
        ray = new Ray(new Point3D(1, 0.5, 2), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections", List.of(new GeoPoint(tube2, new Point3D(1, 2, 2))), result);
        // TC23: Ray starts at the surface and goes outside and the line crosses the
        // axis (0 points)
        ray = new Ray(new Point3D(1, 2, 2), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull("Bad intersections", result);
        // TC24: Ray starts after and crosses the axis (0 points)
        ray = new Ray(new Point3D(1, 3, 2), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull("Bad intersections", result);
        // TC25: Ray start at the axis
        ray = new Ray(new Point3D(1, 1, 2), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections", List.of(new GeoPoint(tube2, new Point3D(1, 2, 2))), result);

        // **** Group: Ray is orthogonal to axis and begins against the axis head
        // TC26: Ray starts outside and the line is outside (
        ray = new Ray(new Point3D(0, 2, 1), new Vector(1, 1, 0));
        assertNull("must not be intersections", tube2.findIntersections(ray));
        // TC27: The line is tangent and the ray starts before the tube
        ray = new Ray(new Point3D(0, 2, 1), new Vector(1, 0, 0));
        assertNull("must not be intersections", tube2.findIntersections(ray));
        // TC28: The line is tangent and the ray starts at the tube
        ray = new Ray(new Point3D(1, 2, 1), new Vector(1, 0, 0));
        assertNull("must not be intersections", tube2.findIntersections(ray));
        // TC29: The line is tangent and the ray starts after the tube
        ray = new Ray(new Point3D(2, 2, 2), new Vector(1, 0, 0));
        assertNull("must not be intersections", tube2.findIntersections(ray));
        // TC30: Ray starts before
        ray = new Ray(new Point3D(0, 0, 1), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 2 intersections", 2, result.size());
        if (result.get(0)._point.getY().get() > result.get(1)._point.getY().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Bad intersections",
                List.of(new GeoPoint(tube2, new Point3D(0.4, 0.2, 1)),
                        new GeoPoint(tube2, new Point3D(2, 1, 1))), result);
        // TC31: Ray starts at the surface and goes inside
        ray = new Ray(new Point3D(0.4, 0.2, 1), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections", List.of(new GeoPoint(tube2, new Point3D(2, 1, 1))), result);
        // TC32: Ray starts inside
        ray = new Ray(new Point3D(1, 0.5, 1), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections", List.of(new GeoPoint(tube2, new Point3D(2, 1, 1))), result);
        // TC33: Ray starts at the surface and goes outside
        ray = new Ray(new Point3D(2, 1, 1), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull("Bad intersections", result);
        // TC34: Ray starts after
        ray = new Ray(new Point3D(4, 2, 1), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull("Bad intersections", result);
        // TC35: Ray starts before and goes through the axis head
        ray = new Ray(new Point3D(1, -1, 1), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 2 intersections", 2, result.size());
        if (result.get(0)._point.getY().get() > result.get(1)._point.getY().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Bad intersections",
                List.of(new GeoPoint(tube2, new Point3D(1, 0, 1)),
                        new GeoPoint(tube2, new Point3D(1, 2, 1))), result);
        // TC36: Ray starts at the surface and goes inside and goes through the axis
        // head
        ray = new Ray(new Point3D(1, 0, 1), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections", List.of(new GeoPoint(tube2, new Point3D(1, 2, 1))), result);
        // TC37: Ray starts inside and the line goes through the axis head
        ray = new Ray(new Point3D(1, 0.5, 1), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections", List.of(new GeoPoint(tube2, new Point3D(1, 2, 1))), result);
        // TC38: Ray starts at the surface and the line goes outside and goes through
        // the axis head
        ray = new Ray(new Point3D(1, 2, 1), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull("Bad intersections", result);
        // TC39: Ray starts after and the line goes through the axis head
        ray = new Ray(new Point3D(1, 3, 1), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull("Bad intersections", result);
        // TC40: Ray start at the axis head
        ray = new Ray(new Point3D(1, 1, 1), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections", List.of(new GeoPoint(tube2, new Point3D(1, 2, 1))), result);

        // **** Group: Ray's line is neither parallel nor orthogonal to the axis and
        // begins against axis head
        Point3D p0 = new Point3D(0, 2, 1);
        // TC41: Ray's line is outside the tube
        ray = new Ray(p0, new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNull("Bad intersections", result);
        // TC42: Ray's line crosses the tube and begins before
        ray = new Ray(p0, new Vector(2, -1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 2 intersections", 2, result.size());
        if (result.get(0)._point.getY().get() > result.get(1)._point.getY().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Bad intersections",
                List.of(new GeoPoint(tube2, new Point3D(2, 1, 2)),
                        new GeoPoint(tube2, new Point3D(0.4, 1.8, 1.2))), result);
        // TC43: Ray's line crosses the tube and begins at surface and goes inside
        ray = new Ray(new Point3D(0.4, 1.8, 1), new Vector(2, -1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections", List.of(new GeoPoint(tube2, new Point3D(2, 1, 1.8))), result);
        // TC44: Ray's line crosses the tube and begins inside
        ray = new Ray(new Point3D(1, 1.5, 1), new Vector(2, -1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections", List.of(new GeoPoint(tube2, new Point3D(2, 1, 1.5))), result);
        // TC45: Ray's line crosses the tube and begins at the axis head
        ray = new Ray(new Point3D(1, 1, 1), new Vector(0, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections", List.of(new GeoPoint(tube2, new Point3D(1, 2, 2))), result);
        // TC46: Ray's line crosses the tube and begins at surface and goes outside
        ray = new Ray(new Point3D(2, 1, 1), new Vector(2, -1, 1));
        result = tube2.findIntersections(ray);
        assertNull("Bad intersections", result);
        // TC47: Ray's line is tangent and begins before
        ray = new Ray(p0, new Vector(0, 2, 1));
        result = tube2.findIntersections(ray);
        assertNull("Bad intersections", result);
        // TC48: Ray's line is tangent and begins at the tube surface
        ray = new Ray(new Point3D(1, 2, 1), new Vector(1, 0, 1));
        result = tube2.findIntersections(ray);
        assertNull("Bad intersections", result);
        // TC49: Ray's line is tangent and begins after
        ray = new Ray(new Point3D(2, 2, 1), new Vector(1, 0, 1));
        result = tube2.findIntersections(ray);
        assertNull("Bad intersections", result);

        // **** Group: Ray's line is neither parallel nor orthogonal to the axis and
        // does not begin against axis head
        double sqrt2 = Math.sqrt(2);
        double denomSqrt2 = 1 / sqrt2;
        double value1 = 1 - denomSqrt2;
        double value2 = 1 + denomSqrt2;
        // TC50: Ray's crosses the tube and the axis
        ray = new Ray(new Point3D(0, 0, 2), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 2 intersections", 2, result.size());
        if (result.get(0)._point.getY().get() > result.get(1)._point.getY().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Bad intersections",
                List.of(new GeoPoint(tube2, new Point3D(value1, value1, 2 + value1)),
                        new GeoPoint(tube2, new Point3D(value2, value2, 2 + value2))), result);
        // TC51: Ray's crosses the tube and the axis head
        ray = new Ray(new Point3D(0, 0, 0), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 2 intersections", 2, result.size());
        if (result.get(0)._point.getY().get() > result.get(1)._point.getY().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Bad intersections",
                List.of(new GeoPoint(tube2, new Point3D(value1, value1, value1)),
                        new GeoPoint(tube2, new Point3D(value2, value2, value2))), result);
        // TC52: Ray's begins at the surface and goes inside
        // TC53: Ray's begins at the surface and goes inside crossing the axis
        ray = new Ray(new Point3D(value1, value1, 2 + value1), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections",
                List.of(new GeoPoint(tube2, new Point3D(value2, value2, 2 + value2))), result);
        // TC54: Ray's begins at the surface and goes inside crossing the axis head
        ray = new Ray(new Point3D(value1, value1, value1), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections",
                List.of(new GeoPoint(tube2, new Point3D(value2, value2, value2))), result);
        // TC55: Ray's begins inside and the line crosses the axis
        ray = new Ray(new Point3D(0.5, 0.5, 2.5), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections",
                List.of(new GeoPoint(tube2, new Point3D(value2, value2, 2 + value2))), result);
        // TC56: Ray's begins inside and the line crosses the axis head
        ray = new Ray(new Point3D(0.5, 0.5, 0.5), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections",
                List.of(new GeoPoint(tube2, new Point3D(value2, value2, value2))), result);
        // TC57: Ray's begins at the axis
        ray = new Ray(new Point3D(1, 1, 3), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections",
                List.of(new GeoPoint(tube2, new Point3D(value2, value2, 2 + value2))), result);
        // TC58: Ray's begins at the surface and goes outside
        ray = new Ray(new Point3D(2, 1, 2), new Vector(2, 1, 1));
        result = tube2.findIntersections(ray);
        assertNull("Bad intersections", result);
        // TC59: Ray's begins at the surface and goes outside and the line crosses the
        // axis
        ray = new Ray(new Point3D(value2, value2, 2 + value2), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNull("Bad intersections", result);
        // TC60: Ray's begins at the surface and goes outside and the line crosses the
        // axis head
        ray = new Ray(new Point3D(value2, value2, value2), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNull("Bad intersections", result);
    }
}