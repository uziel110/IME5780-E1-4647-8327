package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;

public class CylinderTests {

    /**
     * Test method for {@link Cylinder#getNormal(Point3D) (geometries.Cylinder)}
     */
    @Test
    public void getNormal() {
        Cylinder c = new Cylinder(new Point3D(0,0,0), new Vector(0,0,1) , 1,5);
        // ============ Equivalence Partitions Tests ==============
        // TC01:
        assertEquals("wrong normal on the cylinder's side",new Vector(0,1,0),c.getNormal(new Point3D(0,1,1)));
        // TC02:
        // on the top base
        assertEquals("wrong normal on the cylinder's top base",new Vector(0,0,1),c.getNormal(new Point3D(0,0.5,5)));
        // TC03:
        // on the bottom base
        assertEquals("wrong normal on the cylinder's bottom base",new Vector(0,0,1),c.getNormal(new Point3D(-0.5,0,0)));

        // =============== Boundary Values Tests ==================
        // TC01:
        // intersection of the top base with the side
        assertEquals("wrong normal intersection of the top cylinder base with the side",new Vector(0,0,1),c.getNormal(new Point3D(1,0,5)));
        // TC02:
        // intersection of the bottom base with the side
        assertEquals("wrong normal intersection of the bottom cylinder base with the side",new Vector(0,0,1),c.getNormal(new Point3D(0,1,0)));
    }

    /**
     * Test method for {@link Cylinder#findIntsersections(Ray)} (geometries.Cylinder)}.
     */
    @Test
    public void findIntsersections() {
    }
}