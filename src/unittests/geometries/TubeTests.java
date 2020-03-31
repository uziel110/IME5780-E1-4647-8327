package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

public class TubeTests {

    /**
     * Test method for {@link geometries.Tube#getNormal(geometries.Tube)}.
     */
    @Test
    public void getNormal() {
        Tube t = new Tube(new Point3D(0,0,0), new Vector(0,0,1) , 1);
        // ============ Equivalence Partitions Tests ==============
        // TC01:
        // on the ray direction
        assertEquals("wrong normal on the tube's side in the ray direction",new Vector(0,1,0),t.getNormal(new Point3D(0,1,1)));
        // TC02:
        // opposite the ray direction
        assertEquals("wrong normal on the tube's side opposite the ray direction",new Vector(0,1,0),t.getNormal(new Point3D(0,1,-1)));
        // =============== Boundary Values Tests ==================
        //Normal on the ray's point of the tube
        assertEquals("wrong normal on the bottom",new Vector(0,1,0),t.getNormal(new Point3D(0,1,0)));
    }
}