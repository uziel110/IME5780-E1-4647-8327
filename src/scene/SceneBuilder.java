package scene;

import parser.SceneDescriptor;
import renderer.ImageWriter;

import java.io.File;

public class SceneBuilder {
    SceneDescriptor _sceneDescriptor;
    Scene _scene;
    ImageWriter _imageWriter;
    String _filePath;

    public void loadSceneFromFile(File file) {
        _sceneDescriptor = new SceneDescriptor();
        _scene = _sceneDescriptor.InitializeFromXMLstring(_filePath);
    }
}
