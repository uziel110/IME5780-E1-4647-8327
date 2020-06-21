package unitests;

import elements.Camera;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Testing Camera Class
 *
 * @author Dan
 */
public class CameraTest {

    /**
     * Test method for
     * {@link elements.Camera#constructBeamOfRays(int, int, int, int, double, double, double)}.
     */
    @Test
    public void testConstructBeamOfRays() {
        Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));

        // ============ Equivalence Partitions Tests ==============
        // TC01: 3X3 Corner (0,0)
        assertEquals("Bad ray", List.of(new Ray(new Point3D(-2, -2, 10), new Vector(-2, -2, 10))),
                camera.constructBeamOfRays(3, 3, 0, 0, 10, 6, 6));

        // TC02: 4X4 Corner (0,0)
        assertEquals("Bad ray", List.of(new Ray(new Point3D(-3, -3, 10), new Vector(-3, -3, 10))),
                camera.constructBeamOfRays(4, 4, 0, 0, 10, 8, 8));

        // TC03: 4X4 Side (0,1)
        assertEquals("Bad ray", List.of(new Ray(new Point3D(-1, -3, 10), new Vector(-1, -3, 10))),
                camera.constructBeamOfRays(4, 4, 1, 0, 10, 8, 8));

        // TC04: 4X4 Inside (1,1)
        assertEquals("Bad ray", List.of(new Ray(new Point3D(-1, -1, 10), new Vector(-1, -1, 10))),
                camera.constructBeamOfRays(4, 4, 1, 1, 10, 8, 8));

        // =============== Boundary Values Tests ==================
        // TC11: 3X3 Center (1,1)
        assertEquals("Bad ray", List.of(new Ray(new Point3D(0, 0, 10), new Vector(0, 0, 10))),
                camera.constructBeamOfRays(3, 3, 1, 1, 10, 6, 6));

        // TC12: 3X3 Center of Upper Side (0,1)
        assertEquals("Bad ray", List.of(new Ray(new Point3D(0, -2, 10), new Vector(0, -2, 10))),
                camera.constructBeamOfRays(3, 3, 1, 0, 10, 6, 6));

        // TC13: 3X3 Center of Left Side (1,0)
        assertEquals("Bad ray", List.of(new Ray(new Point3D(-2, 0, 10), new Vector(-2, 0, 10))),
                camera.constructBeamOfRays(3, 3, 0, 1, 10, 6, 6));
    }
}