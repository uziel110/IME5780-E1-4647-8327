package scene;

import elements.AmbientLight;
import elements.Camera;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.Color;

public class Scene {
    String _name;
    Color _background;
    AmbientLight _ambientLight;
    Geometries _geometries;
    Camera _camera;
    double _distance;

    public Scene(String name) {
        _name = name;
        _geometries = new Geometries();
    }

    public void addGeometries(Intersectable... geometries) {
        _geometries.add(geometries);
    }

    public String getName() {
        return _name;
    }

    public Color getBackground() {
        return _background;
    }

    public void setBackground(Color background) {
        _background = new Color(background);
    }

    public AmbientLight getAmbientLight() {
        return _ambientLight;
    }

    public void setAmbientLight(AmbientLight ambientLight) {
        _ambientLight = new AmbientLight(ambientLight.getIntensity(), 1);
    }

    public Geometries getGeometries() {
        return _geometries;
    }

    public Camera getCamera() {
        return _camera;
    }

    public void setCamera(Camera camera) {
        _camera = new Camera(camera.getLocation(), camera.getvTo(), camera.getvUp());
    }

    public double getDistance() {
        return _distance;
    }

    public void setDistance(double distance) {
        _distance = distance;
    }
}
