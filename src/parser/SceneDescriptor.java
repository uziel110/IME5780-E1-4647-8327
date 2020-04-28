package parser;

import scene.Scene;

import java.util.List;
import java.util.Map;

public class SceneDescriptor {
    private Map<String, String> _sceneAttributes;
    private Map<String, String> _cameraAttributes;
    private Map<String, String> _ambientLightAttributes;
    private Map<String, String> _imageAttributes;
    private List<Map<String, String>> _spheres;
    private List<Map<String, String>> _triangles;

    public Scene InitializeFromXMLstring(String xmlText) {
        SceneXMLParser XMLParser = new SceneXMLParser();
        return XMLParser.parseXML(xmlText);
    }
}
