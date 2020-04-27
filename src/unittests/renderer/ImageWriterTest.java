package renderer;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class ImageWriterTest {

    @Test
    public void writeToImage() {
        ImageWriter image = new ImageWriter("OurImage", 1000, 1600, 500, 800);
        image.writeToImage();
    }

    @Test
    public void writePixel() {
        ImageWriter image = new ImageWriter("OurImage", 1000, 1600, 500, 800);
        image.writePixel(0, 0, new Color(159, 147, 0));
        image.writeToImage();
    }
}