package parser;

import org.junit.Test;
import renderer.Render;

public class SaxHandlerTest {
    /**
     * test loadSceneFromFile method
     */
    @Test
    public void loadSceneFromFile() {
        SaxHandler Handler = new SaxHandler("basicRenderTestTwoColors.xml");
        Render render = Handler.getRender();
        if (render != null) {
            render.renderImage();
            render.printGrid(50, java.awt.Color.MAGENTA);
            render.getImageWriter().writeToImage();
        }
    }
}