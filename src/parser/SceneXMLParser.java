package parser;

import scene.Scene;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class SceneXMLParser {
    public SceneXMLParser() {
    }
    public Scene parseXML(){
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser;
        Scene importedScene = null;
        try {
            saxParser = saxParserFactory.newSAXParser();
            SaxHandler handler = new SaxHandler();
            saxParser.parse(new File("/Users/pankaj/employees.xml"), handler);
            importedScene = handler.getScene();
        } catch (Exception e) {
        }
        return importedScene;
    }
}
