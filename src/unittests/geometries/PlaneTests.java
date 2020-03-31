package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.assertEquals;

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
    }
}