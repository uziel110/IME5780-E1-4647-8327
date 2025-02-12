package elements;

import geometries.Geometry;
import geometries.Intersectable.GeoPoint;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * Testing integration of geometry objects
 */
public class IntegrationTests {

    List<GeoPoint> tests;

    /**
     * Test method for integration of sphere with the camera
     */
    @Test
    public void SphereIntegrationTest() {
        // TC01: view plane 3X3 sphere radius 1 (2 intersections)
        Sphere sphere = new Sphere(1, new Point3D(0, 0, 3));
        Camera camera = new Camera(new Point3D(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0));
        List<GeoPoint> result = tests(sphere, camera);
        assertEquals("Wrong number of intersections", 2, result.size());

        // TC02: view plane 3X3 sphere radius 2.5 (18 intersections)
        sphere = new Sphere(2.5, new Point3D(0, 0, 2.5));
        camera = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));
        result = tests(sphere, camera);
        assertEquals("Wrong number of intersections", 18, result.size());

        // TC03: view plane 3X3 sphere radius 2 (10 intersections)
        sphere = new Sphere(2, new Point3D(0, 0, 2));
        camera = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));
        result = tests(sphere, camera);
        assertEquals("Wrong number of intersections", 10, result.size());

        // TC04: view plane 3X3 sphere radius 4 (9 intersections)
        sphere = new Sphere(4, new Point3D(0, 0, 2));
        camera = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));
        result = tests(sphere, camera);
        assertEquals("Wrong number of intersections", 9, result.size());

        // TC05: view plane 3X3 sphere radius 0.5 (0 intersections)
        sphere = new Sphere(0.5, new Point3D(0, 0, -1));
        camera = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));
        result = tests(sphere, camera);
        assertEquals("Wrong number of intersections", 0, result.size());
    }

    /**
     * Test method for integration of plane with the camera
     */
    @Test
    public void PlaneIntegrationTest() {
        // TC01: view plane 3X3 plane is parallel to view plane (9 intersections)
        Plane plane = new Plane(new Point3D(0, 0, 3), new Vector(0, 0, 1));
        Camera camera = new Camera(new Point3D(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0));
        List<GeoPoint> result = tests(plane, camera);
        assertEquals("Wrong number of intersections", 9, result.size());

        // TC02: view plane 3X3 plane is in inclined to view plane (9 intersections)
        plane = new Plane(new Point3D(0, 0, 3), new Vector(0, 1, 2));
        camera = new Camera(new Point3D(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0));
        result = tests(plane, camera);
        assertEquals("Wrong number of intersections", 9, result.size());

        // TC03: view plane 3X3 plane is in inclined to view plane (6 intersections)
        plane = new Plane(new Point3D(0, 0, 3), new Vector(0, 1, 1));
        camera = new Camera(new Point3D(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0));
        result = tests(plane, camera);
        assertEquals("Wrong number of intersections", 6, result.size());

        // TC04: view plane 3X3 plane is orthogonal to view plane (3 intersections)
        plane = new Plane(new Point3D(0, 3, 0), new Vector(0, 1, 0));
        camera = new Camera(new Point3D(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0));
        result = tests(plane, camera);
        assertEquals("Wrong number of intersections", 3, result.size());

        // TC05: view plane 3X3 plane is in inclined to view plane (0 intersections)
        plane = new Plane(new Point3D(0, 0, -3), new Vector(0, 1, 1));
        camera = new Camera(new Point3D(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0));
        result = tests(plane, camera);
        assertEquals("Wrong number of intersections", 0, result.size());
    }

    /**
     * Test method for integration of triangle with the camera
     */
    @Test
    public void TriangleIntegrationTest() {

        // TC01: view plane 3X3 triangle is parallel to view plane (1 intersections)
        Triangle triangle = new Triangle(new Point3D(1, 1, 2), new Point3D(-1, 1, 2), new Point3D(0, -1, 2));
        Camera camera = new Camera(new Point3D(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0));
        List<GeoPoint> result = tests(triangle, camera);
        assertEquals("Wrong number of intersections", 1, result.size());

        // TC02: view plane 3X3 triangle is parallel to view plane (2 intersections)
        triangle = new Triangle(new Point3D(1, 1, 2), new Point3D(-1, 1, 2), new Point3D(0, -20, 2));
        camera = new Camera(new Point3D(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0));
        result = tests(triangle, camera);
        assertEquals("Wrong number of intersections", 2, result.size());
    }

    /**
     * find integration of the geometry object with the camera
     *
     * @param object geometry object
     * @param camera camera
     * @return list of integrations of the geometry object with ray from the camera
     */
    private List<GeoPoint> tests(Geometry object, Camera camera) {
        List<GeoPoint> result = new LinkedList<>();
        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 3; ++j) {
                tests = object.findIntersections(camera.constructBeamOfRays(3, 3, j, i, 1, 3, 3).get(0));
                if (tests != null)
                    result.addAll(tests);
            }
        return result;
    }
}