package scene;

import elements.AmbientLight;
import elements.Camera;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import parser.SaxHandler;
//import parser.SceneDescriptor;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;

import java.io.File;

import static org.junit.Assert.*;

/**
 * tests for SceneBuilder class
 */
public class SceneBuilderTest {

    /**
     * test loadSceneFromFile method
     */
    @Test
    public void loadSceneFromFile() {
        SaxHandler Handler = new SaxHandler("basicRenderTestTwoColors.xml");
        Render render = Handler.getRender();

        render.renderImage();
        render.printGrid(75, java.awt.Color.MAGENTA);
        // ._imageWriter is my change
        render.getImageWriter().writeToImage();

        /*SceneBuilder sceneBuilder = new SceneBuilder();
        sceneBuilder.loadSceneFromFile(new File("basicRenderTestTwoColors.xml"));
        Render render = new Render(sceneBuilder._imageWriter,sceneBuilder._scene);
        render.renderImage();
        render.getImageWriter().writeToImage();*/
    }
}