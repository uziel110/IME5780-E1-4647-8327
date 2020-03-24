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
    }

    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    public void add() {
    }

    /**
     * Test method for {@link primitives.Vector#scale(primitives.Vector)}.
     */
    @Test
    public void scale() {
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    public void dotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);

        if (!isZero(v1.dotProduct(v3)))
            out.println("ERROR: dotProduct() for orthogonal vectors is not zero");
        if (!isZero(v1.dotProduct(v2) + 28))
            out.println("ERROR: dotProduct() wrong value");
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
        } catch (Exception e) {}
    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared(primitives.Vector)}.
     */
    @Test
    public void lengthSquared() {
        // test length..
        if (!isZero(new Vector(1, 2, 3).lengthSquared() - 14))
            out.println("ERROR: lengthSquared() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#length(primitives.Vector)}.
     */
    @Test
    public void length() {
        // test length..
        if (!isZero(new Vector(0, 3, 4).length() - 5))
            out.println("ERROR: length() wrong value");
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
        if (vCopy != vCopyNormalize)
            out.println("ERROR: normalize() function creates a new vector");
        if (!isZero(vCopyNormalize.length() - 1))
            out.println("ERROR: normalize() result is not a unit vector");
    }

    /**
     * Test method for {@link primitives.Vector#normalized(primitives.Vector)}.
     */
    @Test
    public void normalized() {
        // test vector normalization vs vector length and cross-product
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalized();
        if (u == v)
            out.println("ERROR: normalized() function does not create a new vector");
    }
}