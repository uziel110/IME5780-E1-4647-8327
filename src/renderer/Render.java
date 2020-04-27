package renderer;

import elements.AmbientLight;
import elements.Camera;
import geometries.Intersectable;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.ArrayList;
import java.util.List;

public class Render {
    ImageWriter _imageWriter;
    Scene _scene;

    public Render(ImageWriter writer, Scene scene) {
        _imageWriter = writer;
        _scene = scene;
    }

    public void renderImage() {
        Camera camera = _scene.getCamera();
        Intersectable geometries = _scene.getGeometries();
        java.awt.Color background = _scene.getBackground().getColor();
        AmbientLight ambientLight = _scene.getAmbientLight();
        double distance = _scene.getDistance();

        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        double width = _imageWriter.getWidth();
        double height = _imageWriter.getHeight();

        Ray ray;
        List<Point3D> intersectionPoints = new ArrayList<Point3D>();
        Point3D closestPoint;
        for (int i = 0; i < nY; ++i) {
            for (int j = 0; j < nX; ++j) {
                ray = camera.constructRayThroughPixel(nX, nY, j, i, distance, width, height);
                intersectionPoints = geometries.findIntersections(ray);
                if (intersectionPoints == null)
                    _imageWriter.writePixel(j, i, background);
                else {
                    closestPoint = getClosestPoint(intersectionPoints);
                    _imageWriter.writePixel(j, i, calcColor(closestPoint).getColor());
                }
            }
        }
    }

    private Color calcColor(Point3D p) {
        return _scene.getAmbientLight().getIntensity();
    }

    public void printGrid(int interval, java.awt.Color color) {
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();

        for (int i = 1; i < nY; ++i) {
            for (int j = 1; j < nX; ++j) {
                if (i % interval == 0 || j % interval == 0)
                    _imageWriter.writePixel(j, i, color);
            }
        }
    }

    private Point3D getClosestPoint(List<Point3D> points) {
        Point3D cameraLocation = _scene.getCamera().getLocation(),
                closestPoint = null;
        double distance,
                minDistance = Double.MAX_VALUE;
        for (Point3D point : points) {
            distance = point.distanceSquared(cameraLocation);
            if (distance < minDistance) {
                minDistance = distance;
                closestPoint = point;
            }
        }
        return closestPoint;
    }
}
