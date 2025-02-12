package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static org.junit.Assert.*;

/**
 *   Testing Cylinders
 */
public class CylinderTests {

    /**
     * Test method for {@link Cylinder#getNormal(Point3D) (geometries.Cylinder)}
     */
    @Test
    public void getNormal() {
        Cylinder c = new Cylinder(new Point3D(0, 0, 0), new Vector(0, 0, 1), 1, 5);
        // ============ Equivalence Partitions Tests ==============
        // TC01:
        assertEquals("wrong normal on the cylinder's side", new Vector(0, 1, 0), c.getNormal(new Point3D(0, 1, 1)));
        // TC02:
        // on the top base
        assertEquals("wrong normal on the cylinder's top base", new Vector(0, 0, 1), c.getNormal(new Point3D(0, 0.5, 5)));
        // TC03:
        // on the bottom base
        assertEquals("wrong normal on the cylinder's bottom base", new Vector(0, 0, 1), c.getNormal(new Point3D(-0.5, 0, 0)));

        // =============== Boundary Values Tests ==================
        // TC01:
        // intersection of the top base with the side
        assertEquals("wrong normal intersection of the top cylinder base with the side", new Vector(0, 0, 1), c.getNormal(new Point3D(1, 0, 5)));
        // TC02:
        // intersection of the bottom base with the side
        assertEquals("wrong normal intersection of the bottom cylinder base with the side", new Vector(0, 0, 1), c.getNormal(new Point3D(0, 1, 0)));
    }

    /**
     * Test method for
     * {@link geometries.Cylinder#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Cylinder cylinder = new Cylinder( new Ray(new Point3D(2, 0, 0), new Vector(0, 0, 1)),1, 10);
        Ray ray;
        GeoPoint geoP1;
       GeoPoint geoP2;
        // ============ Equivalence Partitions Tests ==============
        // case 01: The ray start under the cylinder and going through 2 bases (2
        // points)
        ray = new Ray(new Point3D(1.5, 0, -1), new Vector(0.1, 0, 1));
        List<GeoPoint> result = cylinder.findIntersections(ray);
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0)._point.getZ().get() > result.get(1)._point.getZ().get())
            result = List.of(result.get(1), result.get(0));
        geoP1=new GeoPoint(cylinder, new Point3D(1.6, 0, 0));
        geoP2= new GeoPoint(cylinder,  new Point3D(2.6, 0, 10));
        assertEquals("Worng points", List.of(geoP1,geoP2), result);
        // case 02: The ray start before the cylinder and intersect (2 points)
        ray = new Ray(new Point3D(0, 0, 2), new Vector(1, 0, 1));
        result = cylinder.findIntersections(ray);
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0)._point.getZ().get() > result.get(1)._point.getZ().get())
            result = List.of(result.get(1), result.get(0));
        geoP1=new GeoPoint(cylinder, new Point3D(1, 0, 3));
        geoP2= new GeoPoint(cylinder,  new Point3D(3, 0,5));
        assertEquals("Worng points", List.of(geoP1,geoP2), result);
        // case 03: The ray start before the cylinder and going through the top (2
        // points)
        ray = new Ray(new Point3D(0, 0, 8), new Vector(1, 0, 1));
        result = cylinder.findIntersections(ray);
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0)._point.getZ().get() > result.get(1)._point.getZ().get())
            result = List.of(result.get(1), result.get(0));
        geoP1=new GeoPoint(cylinder, new Point3D(1 ,0, 9));
        geoP2= new GeoPoint(cylinder,  new Point3D(2, 0, 10));
        assertEquals("Worng points", List.of(geoP1, geoP2), result);
        // case 04: The ray start after the cylinder (0 points)
        ray = new Ray(new Point3D(4, 0, 5), new Vector(1, 0, 1));
        result = cylinder.findIntersections(ray);
        assertNull("should be null", result);
        // case 05: The ray start above the cylinder (0 points)
        ray = new Ray(new Point3D(0, 0, 11), new Vector(1, 0, 1));
        result = cylinder.findIntersections(ray);
        assertNull("should be null", result);
        // case 06: Ray's starts within cylinder and going through the top (1 point)
        ray = new Ray(new Point3D(1.5, 0, 3), new Vector(-0.05, 0, 1));
        result = cylinder.findIntersections(ray);
        geoP1= new GeoPoint(cylinder, new Point3D(1.15, 0, 10));
        assertEquals("Worng point", List.of(geoP1), result);
        // case 07: Ray's starts within cylinder and going through the buttom(1 point)
        ray = new Ray(new Point3D(1.5, 0, 3), new Vector(-0.1, 0, -1));
        result = cylinder.findIntersections(ray);
        geoP1= new GeoPoint(cylinder, new Point3D(1.2, 0, 0));
        assertEquals("Worng point", List.of(geoP1), result);
        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line is parallel to the axis
        // case 11: Ray's starts under cylinder and going through the bases(2 point)
        ray = new Ray(new Point3D(1.5, 0, -1), new Vector(0, 0, 1));
        result = cylinder.findIntersections(ray);
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0)._point.getZ().get() > result.get(1)._point.getZ().get())
            result = List.of(result.get(1), result.get(0));
        geoP1=new GeoPoint(cylinder, new Point3D(1.5, 0, 0));
        geoP2= new GeoPoint(cylinder,  new Point3D(1.5, 0, 10));
        assertEquals("Worng points", List.of(geoP1,geoP2), result);
        // case 12: Ray's starts within cylinder and going through the top (1 point)
        ray = new Ray(new Point3D(1.5, 0, 3), new Vector(0, 0, 1));
        result = cylinder.findIntersections(ray);
        geoP1 =new GeoPoint(cylinder, new Point3D(1.5,0,10));
        assertEquals("Worng point", List.of(geoP1), result);
        // case 13: Ray's starts within cylinder and going through the buttom (1 point)
        ray = new Ray(new Point3D(1.5, 0, 3), new Vector(0, 0, -1));
        result = cylinder.findIntersections(ray);
        geoP1 =new GeoPoint(cylinder, new Point3D(1.5,0,0));
        assertEquals("Worng point", List.of(geoP1), result);
        // case 14: Ray's starts at cylinder buttom and going through the top (1 point)
        ray = new Ray(new Point3D(1.5, 0, 0), new Vector(0, 0, 1));
        result = cylinder.findIntersections(ray);
        geoP1 =new GeoPoint(cylinder, new Point3D(1.5,0,10));
        assertEquals("Worng point", List.of(geoP1), result);
        // case 15: Ray's starts at cylinder buttom and going outside (0 points)
        ray = new Ray(new Point3D(1.5, 0, 0), new Vector(0, 0, -1));
        result = cylinder.findIntersections(ray);
        assertNull("should be null", result);
        // case 16: Ray's starts at cylinder center and going through the top (1 point)
        ray = new Ray(new Point3D(2, 0, 0), new Vector(0, 0, 1));
        result = cylinder.findIntersections(ray);
        geoP1 =new GeoPoint(cylinder, new Point3D(2,0,10));
        assertEquals("Worng point", List.of(geoP1), result);
        // case 17: Ray's starts at cylinder center and going outside (0 points)
        ray = new Ray(new Point3D(2, 0, 0), new Vector(0, 0, -1));
        result = cylinder.findIntersections(ray);
        assertNull("should be null", result);
        // case 18: Ray's starts at cylinder ditection and going through the top (1
        // point)
        ray = new Ray(new Point3D(2, 0, 5), new Vector(0, 0, 1));
        result = cylinder.findIntersections(ray);
        geoP1= new GeoPoint(cylinder, new Point3D(2,0,10));
        assertEquals("Worng point", List.of(geoP1), result);
        // **** Group: Ray is orthogonal to the axis
        // case 21: Ray's starts before cylinder and intersect (2 point)
        ray = new Ray(new Point3D(0, 0, 5), new Vector(1, 0, 0));
        result = cylinder.findIntersections(ray);
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0)._point.getX().get() > result.get(1)._point.getX().get())
            result = List.of(result.get(1), result.get(0));
        geoP1= new GeoPoint(cylinder, new Point3D(1,0,5));
        geoP2=new GeoPoint(cylinder, new Point3D(3,0,5));
        assertEquals("Worng points", List.of(geoP1,geoP2), result);
        // case 22: Ray's starts before and above cylinder (0 points)
        ray = new Ray(new Point3D(0, 0, 11), new Vector(1, 0, 0));
        result = cylinder.findIntersections(ray);
        assertNull("should be null", result);
        // case 23: Ray's starts before and going through the top (0 points)
        ray = new Ray(new Point3D(0, 0, 10), new Vector(1, 0, 0));
        result = cylinder.findIntersections(ray);
        assertNull("should be null", result);
        // case 24: Ray's starts at the cylinder center (0 points)
        ray = new Ray(new Point3D(2, 0, 0), new Vector(1, 0, 0));
        result = cylinder.findIntersections(ray);
        assertNull("should be null", result);
        // case 25: Ray's tangent to cylinder top (0 points)
        ray = new Ray(new Point3D(1, 4, 10), new Vector(0, -1, 0));
        result = cylinder.findIntersections(ray);
        assertNull("should be null", result);
        // **** Group: Ray's line is neither parallel nor orthogonal to the axis
        // case 31: Ray's starts within and exits exactly between the base and cylinder
        // (0 points)
        ray = new Ray(new Point3D(2.5, 0, 9), new Vector(0.5, 0, 1));
        result = cylinder.findIntersections(ray);
        assertNull("should be null", result);
        // case 32: Ray's starts at the cylinder and exits exactly between the base and
        // cylinder (0 points)
        ray = new Ray(new Point3D(1, 0, 8), new Vector(1, 0, 1));
        result = cylinder.findIntersections(ray);
        assertNull("should be null", result);
        // case 33: Ray's through exactly between the buttom and cylinder exits exactly
        // between the top and cylinder (0 points)
        ray = new Ray(new Point3D(0.8, 0, -1), new Vector(0.2, 0, 1));
        result = cylinder.findIntersections(ray);
        assertNull("should be null", result);
        // case 34: Ray's start exactly between the buttom and cylinder exits exactly
        // between the top and cylinder (0 points)
        ray = new Ray(new Point3D(1, 0, 0), new Vector(0.2, 0, 1));
        result = cylinder.findIntersections(ray);
        assertNull("should be null", result);
        // case 35: Ray's starts at cylinder and going through the top (1
        // point)
        ray = new Ray(new Point3D(1, 0, 8), new Vector(0.1, 0, 1));
        result = cylinder.findIntersections(ray);
        geoP1= new GeoPoint(cylinder,new Point3D(1.2, 0, 10));
        assertEquals("Worng point", List.of(geoP1), result);
        // case 36: Ray's tangent the cylinder exactly between the top and
        // cylinder (0 points)
        ray = new Ray(new Point3D(0, 0, 9), new Vector(1, 0, 1));
        result = cylinder.findIntersections(ray);
        assertNull("should be null", result);
    }
}