package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;

/**
 *
 */
public class SphereTest {

    /**
     * Test method for {@link geometries.Sphere#getNormal(geometries.Sphere)}.
     */
    @Test
    public void getNormal() {
        //==== Equivalance Partitions ====
        //TC01:
        Sphere sph = new Sphere(1d,new Point3D(1,0,0));
        Point3D p = new Point3D(1,1,0);
        assertEquals("bad normal",new Vector(0,1,0),sph.getNormal(p));


    }
}