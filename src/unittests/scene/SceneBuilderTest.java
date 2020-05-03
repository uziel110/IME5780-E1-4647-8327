package scene;

import org.junit.Test;
import parser.SaxHandler;
import renderer.Render;

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
        render.printGrid(50, java.awt.Color.MAGENTA);
        render.getImageWriter().writeToImage();
    }
}