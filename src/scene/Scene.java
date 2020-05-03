package scene;

import elements.AmbientLight;
import elements.Camera;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.Color;

/**
 * class that implements scene
 */
public class Scene {
    String _name;
    Color _background;
    AmbientLight _ambientLight;
    Geometries _geometries;
    Camera _camera;
    double _distance;

    /**
     * Constructor of scene class, get parameter - name of the scene
     *
     * @param name string
     */
    public Scene(String name) {
        _name = name;
        _geometries = new Geometries();
    }

    /**
     * add list of Intersctable to the list
     *
     * @param geometries list of Intersctable
     */
    public void addGeometries(Intersectable... geometries) {
        _geometries.add(geometries);
    }

    /**
     * return name of the scene
     *
     * @return string name of the scene
     */
    public String getName() {
        return _name;
    }

    /**
     * return background color of the scene
     *
     * @return Color background of the scene
     */
    public Color getBackground() {
        return _background;
    }

    /**
     * set new background color to the scene
     * @param background Color
     */
    public void setBackground(Color background) {
        _background = new Color(background);
    }

    /**
     *  return ambientLight of the scene
     *  @return AmbientLight of the scene
     */
    public AmbientLight getAmbientLight() {
        return _ambientLight;
    }

    /**
     * set new AmbientLight to the scene
     * @param ambientLight AmbientLight
     */
    public void setAmbientLight(AmbientLight ambientLight) {
        _ambientLight = new AmbientLight(ambientLight.getIntensity(), 1);
    }

    /**
     * return geometries
     * @return geometries
     */
    public Geometries getGeometries() {
        return _geometries;
    }

    /**
     * return camera
     * @return Camera
     */
    public Camera getCamera() {
        return _camera;
    }

    /**
     * set camera
     * @param camera
     */
    public void setCamera(Camera camera) {
        _camera = new Camera(camera.getLocation(), camera.getvTo(), camera.getvUp());
    }

    /**
     * return distance
     * @return double distance
     */
    public double getDistance() {
        return _distance;
    }

    /**
     * set distance
     * @param distance double
     */
    public void setDistance(double distance) {
        _distance = distance;
    }
}
