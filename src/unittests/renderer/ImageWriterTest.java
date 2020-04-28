package renderer;

import elements.AmbientLight;
import elements.Camera;
import org.junit.Before;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;
import scene.Scene;

import java.awt.*;

/**
 * Unit tests for ImageWriter class
 */
public class ImageWriterTest {
    Scene scene;
    ImageWriter imageWriter;
    Render render;

    @Before
    public void setUp() {
        scene = new Scene("Test scene");
        scene.setCamera(new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setBackground(new primitives.Color(25, 96, 20));
    }

    /**
     * Test method for {@link renderer.ImageWriter#writeToImage}.
     */
    @Test
    public void writeToImage() {
        imageWriter = new ImageWriter("Test write to image", 500, 500, 500, 500);
        render = new Render(imageWriter, scene);
        render.renderImage();
        render.printGrid(50, java.awt.Color.YELLOW);
        imageWriter.writeToImage();
    }

    /**
     * Test method for {@link renderer.ImageWriter#writePixel}.
     */
    @Test
    public void writePixel() {
        imageWriter = new ImageWriter("Test write pixel", 500, 500, 500, 500);
        render = new Render(imageWriter, scene);
        render.printGrid(50, java.awt.Color.YELLOW);
        imageWriter.writePixel(10, 10, new Color(159, 147, 0));
        imageWriter.writeToImage();
    }
}