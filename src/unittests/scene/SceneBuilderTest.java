package scene;

import org.junit.Test;
import parser.SceneDescriptor;
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
        SceneBuilder sceneBuilder = new SceneBuilder();
        sceneBuilder.loadSceneFromFile(new File("basicRenderTestTwoColors.xml"));
        Render render = new Render(sceneBuilder._imageWriter,sceneBuilder._scene);
        render.renderImage();
        render.getImageWriter().writeToImage();
    }
}