package parser;

import elements.AmbientLight;
import elements.Camera;
import elements.PointLight;
import org.junit.Test;
import primitives.Color;
import primitives.Point3D;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

/**
 * Testing SaxHandler class
 */
public class SaxHandlerTest {
    /**
     * Test method for {@link SaxHandler#parseDocument()} (parser.SaxHandler)}.
     */
    @Test
    public void parseDocumentTest() {
        SaxHandler Handler = new SaxHandler("basicRenderTestTwoColors.xml");
        Render render = Handler.parseDocument();
        if (render != null) {
            render.renderImage();
            render.printGrid(50, java.awt.Color.MAGENTA);
            render.writeToImage();
        }
    }
    @Test
    public void offTest() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, 0), 500, 0, 1, Math.PI/2));
        scene.setDistance(50);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries(TextReader.readOff("Apple",3));

        scene.addLights(new PointLight(new Color(654, 495, 96),
                new Point3D(0, 0, 1500), 1, 4E-5, 2E-7));
        ImageWriter imageWriter = new ImageWriter("Apple", 500, 500, 500, 500);
        Render render = new Render(imageWriter, scene).setMultithreading(3).setDebugPrint();

        render.renderImage();
        render.writeToImage();
    }
}