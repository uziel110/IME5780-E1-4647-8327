package primitives;

import org.junit.Test;

import static java.lang.System.out;
import static org.junit.Assert.*;
import static primitives.Util.isZero;

public class VectorTests {

    /**
     * Test method for zero vector
     */
    @Test
    public void zero() {
        try { // test zero vector
            new Vector(0, 0, 0);
            out.println("ERROR: zero vector does not throw an exception");
        } catch (Exception e) {
        }
    }

    /**
     * Test method for {@link primitives.Vector#subtract(primitives.Vector)}.
     */
    @Test
    public void subtract() {
        Vector v1 = new Vector(1, 1, 1);
        Vector v2 = new Vector(2, 3, 4);
        assertEquals("subtract failed!", new Vector(1, 2, 3), v2.subtract(v1));
    }

    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    public void add() {
        Vector v1 = new Vector(1, 1, 1);
        Vector v2 = new Vector(1, 2, 3);
        assertEquals("add failed!", new Vector(2, 3, 4), v2.add(v1));
    }

    /**
     * Test method for {@link primitives.Vector#scale(primitives.Vector)}.
     */
    @Test
    public void scale() {
        Vector v1 = new Vector(1, 1, 1);
        double scalar = 2;
        assertEquals("scale failed!", new Vector(2, 2, 2), v1.scale(scalar));
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    public void dotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(0, 3, -2);

        assertEquals("ERROR: dotProduct() for orthogonal vectors is not zero", 0, v1.dotProduct(v2), 0.00000001);
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    public void crossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);

        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v3);

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals("crossProduct() wrong result length", v1.length() * v3.length(), vr.length(), 0.00001);

        // Test cross-product result orthogonality to its operands
        assertTrue("crossProduct() result is not orthogonal to 1st operand", isZero(vr.dotProduct(v1)));
        assertTrue("crossProduct() result is not orthogonal to 2nd operand", isZero(vr.dotProduct(v3)));

        // =============== Boundary Values Tests ==================
        // test zero vector from cross-product of co-lined vectors
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {
        }
    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared(primitives.Vector)}.
     */
    @Test
    public void lengthSquared() {
        assertEquals("ERROR: lengthSquared() wrong value", 14, new Vector(1, 2, 3).lengthSquared(), 0.000001);
    }

    /**
     * Test method for {@link primitives.Vector#length(primitives.Vector)}.
     */
    @Test
    public void length() {
        assertEquals("ERROR: length() wrong value", 5, new Vector(0, 3, 4).length(), 0.00000001);
    }

    /**
     * Test method for {@link primitives.Vector#normalize(primitives.Vector)}.
     */
    @Test
    public void normalize() {
        // test vector normalization vs vector length and cross-product
        Vector v = new Vector(1, 2, 3);
        Vector vCopy = new Vector(v);
        Vector vCopyNormalize = vCopy.normalize();
        assertTrue("ERROR: normalize() function creates a new vector", vCopy == vCopyNormalize);
        assertEquals("ERROR: normalize() result is not a unit vector", 1, vCopyNormalize.length(), 0.00000001);
    }

    /**
     * Test method for {@link primitives.Vector#normalized(primitives.Vector)}.
     */
    @Test
    public void normalized() {
        // test vector normalization vs vector length and cross-product
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalized();
        assertFalse("ERROR: normalized() function does not create a new vector", u == v);
    }
}