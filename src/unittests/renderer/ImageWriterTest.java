package renderer;

import org.junit.Test;

import java.awt.*;

/**
 * Unit tests for ImageWriter class
 */
public class ImageWriterTest {

    /**
     * Test method for {@link renderer.ImageWriter#writeToImage}.
     */
    @Test
    public void writeToImage() {
        ImageWriter image = new ImageWriter("write to image test", 1000, 1600, 500, 800);
        image.writeToImage();
    }

    /**
     * Test method for {@link renderer.ImageWriter#writePixel}.
     */
    @Test
    public void writePixel() {
        ImageWriter image = new ImageWriter("write pixel test", 1000, 1600, 500, 800);
        image.writePixel(0, 0, new Color(159, 147, 0));
        image.writeToImage();
    }
}