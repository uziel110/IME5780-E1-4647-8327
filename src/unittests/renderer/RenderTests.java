package renderer;

import elements.AmbientLight;
import elements.Camera;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test rendering abasic image
 *
 * @author Dan
 */
public class RenderTests {

    /**
     * Produce a scene with basic 3D model and render it into a jpeg image with a
     * grid
     */
    @Test
    public void basicRenderTwoColorTest() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(100);
        scene.setBackground(new Color(75, 127, 90));
        scene.setAmbientLight(new AmbientLight(new Color(255, 191, 191), 1));

        scene.addGeometries(new Sphere(50, new Point3D(0, 0, 100)));

        scene.addGeometries(
                new Triangle(new Point3D(100, 0, 100), new Point3D(0, 100, 100), new Point3D(100, 100, 100)),
                new Triangle(new Point3D(100, 0, 100), new Point3D(0, -100, 100), new Point3D(100, -100, 100)),
                new Triangle(new Point3D(-100, 0, 100), new Point3D(0, 100, 100), new Point3D(-100, 100, 100)),
                new Triangle(new Point3D(-100, 0, 100), new Point3D(0, -100, 100), new Point3D(-100, -100, 100)));

        ImageWriter imageWriter = new ImageWriter("base render test", 500, 500, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.printGrid(50, java.awt.Color.YELLOW);
        // ._imageWriter is my change
        render.getImageWriter().writeToImage();
    }

    /**
     * Produce a scene with basic 3D model and render it into a jpeg image with a
     * grid
     */
    @Test
    public void ourImage() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(100);
        scene.setBackground(new Color(56, 21, 240));
        scene.setAmbientLight(new AmbientLight(new Color(255, 191, 191), 1));

        scene.addGeometries(new Sphere(50, new Point3D(75, -50, 150)),
                new Sphere(50, new Point3D(-75, -50, 150)));

        scene.addGeometries(new Triangle(new Point3D(-30, 50, 150), new Point3D(30, 50, 150), new Point3D(0, 0, 150)),
                new Triangle(new Point3D(100, 100, 150), new Point3D(-100, 100, 150), new Point3D(0, 150, 150)));

        ImageWriter imageWriter = new ImageWriter("ourImage", 500, 500, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.printGrid(75, java.awt.Color.MAGENTA);
        // ._imageWriter is my change
        render.getImageWriter().writeToImage();
    }

    /**
     * test for getClosestPoint. check if the function returns the closest intersection
     * point of the geometry shape to the camera
     */
    /*@Test
    public void getClosestPoint() {
        Scene scene = new Scene("ClosestPoint test");
        scene.setCamera(new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.addGeometries(new Sphere(1d, new Point3D(0, 0, 2)));
        ImageWriter imageWriter = new ImageWriter("testImage", 500, 500, 500, 500);
        Render render = new Render(imageWriter, scene);
        assertEquals("Wrong closestPoint", new Point3D(0, 0, 1),
              render.getClosestPoint(List.of(new Point3D(0, 0, 1),new Point3D(0, 0, 3))));

    }*/

}
