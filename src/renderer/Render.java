package renderer;

import elements.Camera;
import elements.LightSource;
import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import scene.Scene;

import java.util.List;

/**
 * class that create image from the scene
 */
public class Render {

    private ImageWriter _imageWriter;
    private Scene _scene;

    /**
     * Constructor of Render class, get two parameters - imageWriter and scene
     *
     * @param writer ImageWriter - image parameters
     * @param scene  Scene - scene details
     */
    public Render(ImageWriter writer, Scene scene) {
        _imageWriter = writer;
        _scene = scene;
    }

    /**
     * return imageWriter
     *
     * @return imageWriter
     */
    public ImageWriter getImageWriter() {
        return _imageWriter;
    }

    /**
     * return scene
     *
     * @return scene
     */
    public Scene getScene() {
        return _scene;
    }

    /**
     * create image from the scene
     */
    public void renderImage() {
        // scene parameters
        Camera camera = _scene.getCamera();
        Intersectable geometries = _scene.getGeometries();
        java.awt.Color background = _scene.getBackground().getColor();
        double distance = _scene.getDistance();

        // imageWriter parameters
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        double width = _imageWriter.getWidth();
        double height = _imageWriter.getHeight();

        Ray ray;
        List<GeoPoint> intersectionPoints;
        GeoPoint closestPoint;
        for (int i = 0; i < nY; ++i) {
            for (int j = 0; j < nX; ++j) {
                //todo check the order of nx ny, i j, width height
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

    /**
     * print grid on the image
     *
     * @param interval distance between the lines of the grid
     * @param color    Color
     */
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

    /**
     * return color color of this point
     *
     * @param intersection Point3D
     * @return Color of this point
     */
    private Color calcColor(GeoPoint intersection) {
        Color color = _scene.getAmbientLight().getIntensity(); // ip = ia.dotProduct(ka)
        color = color.add(intersection._geometry.getEmission()); // ie

        Vector v = intersection._point.subtract(_scene.getCamera().getLocation()).normalize();
        Vector n = intersection._geometry.getNormal(intersection._point);
        Material material = intersection._geometry.getMaterial();
        int nShininess = material.getNShininess();
        double kD = material.getKD();
        double kS = material.getKS();
        for (LightSource lightSource : _scene.getLights()) {
            Vector l = lightSource.getL(intersection._point);
            if ((n.dotProduct(l) > 0 && n.dotProduct(v) > 0) || (n.dotProduct(l) < 0 && n.dotProduct(v) < 0)) {
                Color lightIntensity = lightSource.getIntensity(intersection._point);
                color = color.add(calcDiffusive(kD, l, n, lightIntensity),
                        calcSpecular(kS, l, n, v, nShininess, lightIntensity));
            }
        }
        return color;
    }

    /**
     * return the diffusive color
     *
     * @param kD             factor of the diffusive
     * @param l              vector direction from the light
     * @param n              vector normal to the geometry surface
     * @param lightIntensity intensity of the light
     * @return the diffusive color
     */
    private Color calcDiffusive(double kD, Vector l, Vector n, Color lightIntensity) {
        double x = l.dotProduct(n);
        x = x > 0 ? x : -x;
        x = x * kD;
        // lightIntensity - iL
        return lightIntensity.scale(x);
    }

    /**
     * return the specular color
     *
     * @param kS             factor of the specular
     * @param l              vector direction from the light
     * @param n              vector normal to the geometry surface
     * @param v              vector direction from the camera
     * @param nShininess     the shininess of the surface
     * @param lightIntensity intensity of the light
     * @return
     */
    private Color calcSpecular(double kS, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(l.dotProduct(n) * 2));
        double x = r.dotProduct(v.scale(-1));
        x = Math.pow(x > 0 ? x : 0, nShininess) * kS;
        return lightIntensity.scale(x);
    }

    /**
     * return Point3D the point with minimal distance from the ray begin point
     *
     * @param geoPoints list of Point3D
     * @return Point3D the closest point to the ray begin point
     */
    //for the test the access permission needs to be changed to public
    private GeoPoint getClosestPoint(List<GeoPoint> geoPoints) {
        Point3D cameraLocation = _scene.getCamera().getLocation();
        GeoPoint closestPoint = null;
        double distance,
                minDistance = Double.MAX_VALUE;
        for (GeoPoint geoPoint : geoPoints) {
            distance = geoPoint._point.distanceSquared(cameraLocation);
            if (distance < minDistance) {
                minDistance = distance;
                closestPoint = geoPoint;
            }
        }
        return closestPoint;
    }
}
