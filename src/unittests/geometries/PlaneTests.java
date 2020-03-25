package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

public class PlaneTests {

    /**
     * Test method for {@link Plane#getNormal()} (geometries.Plane)}.
     */
    @Test
    public void getNormal() {
        Plane p = new Plane(new Point3D(0, 0, 0), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
        Vector n = p.getNormal();
    }

    /**
     * Test method for {@link Plane#getNormal()} (geometries.Plane)}.
     */
    @Test
    public void testGetNormal() {
        Plane p = new Plane(new Point3D(0, 0, 0), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
        Vector n = p.getNormal(new Point3D(1, 1, 0));
    }
}