package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.assertEquals;

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
        assertEquals("wrong normal on the tube's side in the ray direction", new Vector(0, 1, 0), t.getNormal(new Point3D(0, 1, 1)));
        // TC02:
        // opposite the ray direction
        assertEquals("wrong normal on the tube's side opposite the ray direction", new Vector(0, 1, 0), t.getNormal(new Point3D(0, 1, -1)));
        // =============== Boundary Values Tests ==================
        //Normal on the ray's point of the tube
        assertEquals("wrong normal on the bottom", new Vector(0, 1, 0), t.getNormal(new Point3D(0, 1, 0)));
    }

    /**
     * Test method for {@link Tube#findIntersections(Ray)} (geometries.Tube)}.
     */
    /**
     @Test public void findIntsersections() {
     Tube tube = new Tube(new Point3D(0, 2, 0), new Vector(0, 0, 1), 1);

     // ============ Equivalence Partitions Tests ==============

     // **** Group: Ray's line not go through the center

     // TC01:
     // Ray's line is outside the tube (0 points)
     assertNull("Ray's line out of tube", tube.findIntersections(new Ray(new Point3D(2, 0, 1), new Vector(0, 1, 0))));

     // TC02:
     // Ray's line is outside the tube and parallel to the tube(0 points)
     assertNull("Ray's line out of tube", tube.findIntersections(new Ray(new Point3D(2, 2, 0), new Vector(0, 0, 1))));


     //
     // TC01:
     // Ray's line is outside the tube (0 points)
     assertNull("Ray's line out of tube", tube.findIntersections(new Ray(new Point3D(2, 0, 1), new Vector(0, 1, 0))));

     // TC01:
     // Ray's line is outside the tube (0 points)
     assertNull("Ray's line out of tube", tube.findIntersections(new Ray(new Point3D(2, 0, 1), new Vector(0, 1, 0))));


     // TC02:
     // Ray starts inside the tube (1 point)
     assertEquals("Ray starts inside the tube", List.of(new Point3D(-1, 2, 1)),
     tube.findIntersections(new Ray(new Point3D(-0.5, 1.5, 1), new Vector(-1, 1, 0))));


     // **** Group: Ray's line goes through the center

     // TC03:
     // Ray starts before and crosses the tube (2 points)
     Point3D p1 = new Point3D(0, 1, 1);
     Point3D p2 = new Point3D(0, 3, 1);
     List<Point3D> result = tube.findIntersections(new Ray(new Point3D(0, 0, 1), new Vector(0, 1, 0)));
     assertEquals("Wrong number of points", 2, result.size());
     if (result.get(0).getX().get() > result.get(1).getX().get())
     result = List.of(result.get(1), result.get(0));
     assertEquals("Ray crosses tube from the side", List.of(p1, p2), result);

     // TC04:
     // Ray starts inside the tube (1 point)
     assertEquals("Ray starts inside the sphere", List.of(new Point3D(1, 1, 0)),
     tube.findIntersections(new Ray(new Point3D(0.5, 2, 1), new Vector(-1, 0, 0))));

     // =============== Boundary Values Tests ==================

     // **** Group: Ray's line crosses the tube (but not the center)

     // TC05:
     // Ray starts before from the side and crosses the tube (2 points)
     p1 = new Point3D(0, 1, 1);
     p2 = new Point3D(-1, 2, 1);
     result = tube.findIntersections(new Ray(new Point3D(1, 0, 1), new Vector(-2, 2, 0)));
     assertEquals("Wrong number of points", 2, result.size());
     if (result.get(0).getX().get() > result.get(1).getX().get())
     result = List.of(result.get(1), result.get(0));
     assertEquals("Ray crosses tube from the side", List.of(p1, p2), result);

     // TC06:
     // Ray starts on the surface of the tube (1 point)
     assertEquals("Ray starts inside the sphere", List.of(new Point3D(0.6, 1.2, 1)),
     tube.findIntersections(new Ray(new Point3D(-1, 2, 1), new Vector(2, -1, 0))));

     // **** Group: Ray's line goes through the center

     // TC07:
     // Ray starts on the surface of the tube and goes inside(1 point)
     assertEquals("Ray starts inside the sphere", List.of(new Point3D(1, 2, 1)),
     tube.findIntersections(new Ray(new Point3D(-1, 2, 1), new Vector(1, 0, 0))));

     // TC08:
     // Ray starts on the surface of the tube and goes outside(0 point)
     assertNull("Ray starts inside the sphere",
     tube.findIntersections(new Ray(new Point3D(1, 2, 1), new Vector(1, 0, 0))));

     // TC09:
     // Ray starts after the tube (0 point)
     assertNull("Ray starts inside the sphere",
     tube.findIntersections(new Ray(new Point3D(0, 4, 1), new Vector(0, 1, 0))));
     }
     **/
}