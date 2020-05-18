package scene;

import elements.AmbientLight;
import elements.Camera;
import elements.LightSource;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.Color;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * class that implements scene
 */
public class Scene {
    private String _name;
    private Color _background;
    private AmbientLight _ambientLight;
    private Geometries _geometries;
    private Camera _camera;
    private double _distance;
    private List<LightSource> _lights;

    /**
     * Constructor of scene class, get parameter - name of the scene
     *
     * @param name string
     */
    public Scene(String name) {
        _name = name;
        _geometries = new Geometries();
        _lights = new LinkedList<>();
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
     * func to add light sources
     */
    public void addLights(LightSource... lights) {
        _lights.addAll(Arrays.asList(lights));
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
     *
     * @param background Color
     */
    public void setBackground(Color background) {
        _background = new Color(background);
    }

    /**
     * return ambientLight of the scene
     *
     * @return AmbientLight of the scene
     */
    public AmbientLight getAmbientLight() {
        return _ambientLight;
    }

    /**
     * set new AmbientLight to the scene
     *
     * @param ambientLight AmbientLight
     */
    public void setAmbientLight(AmbientLight ambientLight) {
        _ambientLight = new AmbientLight(ambientLight.getIntensity(), 1);
    }

    /**
     * return geometries
     *
     * @return geometries
     */
    public Geometries getGeometries() {
        return _geometries;
    }

    /**
     * return camera
     *
     * @return Camera
     */
    public Camera getCamera() {
        return _camera;
    }

    /**
     * set camera
     *
     * @param camera
     */
    public void setCamera(Camera camera) {
        _camera = new Camera(camera.getLocation(), camera.getVTo(), camera.getVUp());
    }

    /**
     * return distance
     *
     * @return double distance
     */
    public double getDistance() {
        return _distance;
    }

    /**
     * set distance
     *
     * @param distance double
     */
    public void setDistance(double distance) {
        _distance = distance;
    }

    /**
     * return List of the lights in the scene
     *
     * @return List of the lights in the scene
     */
    public List<LightSource> getLights() {
        return _lights;
    }
}
