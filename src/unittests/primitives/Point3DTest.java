package primitives;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for primitives.Point3D class
 */

public class Point3DTest {

    /**
     * Test method for {@link primitives.Point3D#subtract(primitives.Point3D)}.
     */
    @Test
    public void subtract() {
        Point3D p1 = new Point3D(2, 3, 4);
        Point3D p2 = new Point3D(4, 3, 2);
        assertEquals("subtract failed!", new Vector(2, 0, -2), p2.subtract(p1));
    }

    /**
     * Test method for {@link primitives.Point3D#add(primitives.Vector)}.
     */
    @Test
    public void add() {
        Point3D p1 = new Point3D(2, 3, 4);
        Vector v2 = new Vector(1, 3, 2);
        assertEquals("add failed!", new Point3D(3, 6, 6), p1.add(v2));
    }

    /**
     * Test method for {@link primitives.Point3D#distanceSquared(primitives.Point3D)}.
     */
    @Test
    public void distanceSquared() {
        Point3D p1 = new Point3D(3, 0, 0);
        assertEquals("ERROR: distanceSquared() wrong value",
                25, p1.distanceSquared(new Point3D(0, 4, 0)), 0.00000001);
    }

    /**
     * Test method for {@link primitives.Point3D#distance(primitives.Point3D)}.
     */
    @Test
    public void distance() {
        Point3D p1 = new Point3D(3, 0, 0);
        assertEquals("ERROR: distanceSquared() wrong value",
                5, p1.distance(new Point3D(0, 4, 0)), 0.00000001);
    }
}