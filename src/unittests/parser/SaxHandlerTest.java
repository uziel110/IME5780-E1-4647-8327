package parser;

import org.junit.Test;
import renderer.Render;

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
}