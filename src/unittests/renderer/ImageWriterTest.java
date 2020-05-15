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
        int width = 1600;
        int height = 1000;
        int nX = 800;
        int nY = 500;
        ImageWriter imageWriter = new ImageWriter("Test write to image", width, height, nX, nY);
        for (int column = 0; column < nY; ++column) {
            for (int row = 0; row < nX; ++row) {
                if (column % 50 == 0 || row % 50 == 0)
                    imageWriter.writePixel(row, column, Color.red);
                else
                    imageWriter.writePixel(row, column, Color.YELLOW);
            }
        }
        imageWriter.writeToImage();
    }
}