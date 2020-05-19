package renderer;

import elements.Camera;
import elements.LightSource;
import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * class that create image from the scene
 */
public class Render {
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    /**
     * Fixed size for moving the beginning of the beam
     * at shading rays transparency and reflection
     */
    private static final double DELTA = 0.1;
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
     * Check if the pixel is shaded or not
     * by checking if a light source is blocked by other objects
     *
     * @param l  vector from the light to that point
     * @param n  vector normal to the geometry in that point
     * @param gp the point that we want to check
     * @return return true if the pixel is unShaded
     */
    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint gp) {
        Vector lightDirection = l.scale(-1); // change direction, from point to lightSource
        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
        Point3D point = gp._point.add(delta);
        Ray lightRay = new Ray(point, lightDirection); // reversed lightRay

        List<GeoPoint> intersections =
                _scene.getGeometries().findIntersections(lightRay, light.getDistance(point));
        if (intersections != null)
            System.out.println(intersections.get(0));
        return intersections == null;
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
                ray = camera.constructRayThroughPixel(nX, nY, j, i, distance, width, height);
                closestPoint = findClosestIntersection(ray);
                _imageWriter.writePixel(j, i, closestPoint == null ?
                        _scene.getBackground().getColor() :
                        calcColor(closestPoint, ray).getColor());
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

    private Color calcColor(GeoPoint geoPoint, Ray inRay) {
        return calcColor(geoPoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0)
                .add(_scene.getAmbientLight().getIntensity());
    }

    /**
     * return color color of this point
     *
     * @param geoPoint Point3D
     * @return Color of this point
     */
    private Color calcColor(GeoPoint geoPoint, Ray inRay, int level, double k) {
        //if (level == 0 || k < MIN_CALC_COLOR_K) return Color.BLACK;
        Color color = geoPoint._geometry.getEmission(); // ie

        Vector v = geoPoint._point.subtract(_scene.getCamera().getLocation()).normalize();
        Vector n = geoPoint._geometry.getNormal(geoPoint._point);
        Material material = geoPoint._geometry.getMaterial();
        int nShininess = material.getNShininess();
        double kD = material.getKD();
        double kS = material.getKS();
        for (LightSource lightSource : _scene.getLights()) {
            Vector l = lightSource.getL(geoPoint._point);
            // both ( n.dotProduct(l)) and (n.dotProduct(v)) with same sign
            if (n.dotProduct(l) * n.dotProduct(v) > 0)
                if (unshaded(lightSource, l, n, geoPoint)) {
                    Color lightIntensity = lightSource.getIntensity(geoPoint._point);
                    color = color.add(calcDiffusive(kD, l, n, lightIntensity),
                            calcSpecular(kS, l, n, v, nShininess, lightIntensity));
                }
        }
        if (level == 1) return Color.BLACK;
        double kR = geoPoint._geometry.getMaterial().getKR(), kkr = k * kR;
        if (kkr > MIN_CALC_COLOR_K) {
            Ray reflectedRay = constructReflectedRay(n, geoPoint._point, inRay);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint != null)
                color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kR));
        }
        double kT = geoPoint._geometry.getMaterial().getKT(), kkt = k * kT;
        if (kkt > MIN_CALC_COLOR_K) {
            Ray refractedRay = constructRefractedRay(geoPoint._point, inRay);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            if (refractedPoint != null)
                color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kT));
        }
        return color;
    }

    /**
     * return new ray refracted from the surface at the point
     *
     * @param point point on the surface of the geometry
     * @param inRay ray that intersect with the geometry
     * @return return new ray refracted from the surface at the point
     */
    private Ray constructRefractedRay(Point3D point, Ray inRay) {
        return new Ray(point, inRay.getVector()); // refracted lightRay
    }

    /**
     * return the closest point to the head of the reflected ray
     *
     * @param reflectedRay the ray that we find closest point on it
     * @return return the closest point to the head of the reflected ray
     */
    private GeoPoint findClosestIntersection(Ray reflectedRay) {
        List<GeoPoint> intersectionPoints = _scene.getGeometries().findIntersections(reflectedRay);
        if(intersectionPoints == null)
            return null;
        return getClosestPoint(reflectedRay.getPoint(), intersectionPoints);
    }

    /**
     * return new ray reflected from the surface at the point
     *
     * @param n     vector normal to the reflected geometry
     * @param point point on the surface of the geometry
     * @param inRay ray that intersect with the geometry
     * @return return new ray reflected from the surface at the point
     */
    private Ray constructReflectedRay(Vector n, Point3D point, Ray inRay) {
        Vector reflectedVector = inRay.getVector().subtract(n.scale(inRay.getVector().dotProduct(n) * 2)); //r
        Vector delta = n.scale(n.dotProduct(reflectedVector) > 0 ? DELTA : -DELTA);
        Point3D movedPoint = point.add(delta);
        return new Ray(movedPoint, reflectedVector); // reflected lightRay
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
        double lDotN = alignZero(l.dotProduct(n));
        if (lDotN < 0)
            lDotN = -lDotN;
        // lightIntensity - iL
        return lightIntensity.scale(lDotN * kD);
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
     * @return the color with specular effect
     */
    private Color calcSpecular(double kS, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(l.dotProduct(n) * 2));
        double minusVDotR = alignZero(r.dotProduct(v.scale(-1)));
        if (minusVDotR <= 0)
            return Color.BLACK;
        return lightIntensity.scale(Math.pow(minusVDotR, nShininess) * kS);
    }

    /**
     * return GeoPoint the point with minimal distance from the ray begin point
     *
     * @param geoPoints list of Point3D
     * @return GeoPoint the closest point to the ray begin point
     */
    //for the test the access permission needs to be changed to public
    private GeoPoint getClosestPoint(List<GeoPoint> geoPoints) {
        return getClosestPoint(_scene.getCamera().getLocation(), geoPoints);
    }

    /**
     * return GeoPoint the point with minimal distance from the ray begin point
     *
     * @param point     point to measure distance from
     * @param geoPoints list of Point3D
     * @return GeoPoint the closest point to the ray begin point
     */
    private GeoPoint getClosestPoint(Point3D point, List<GeoPoint> geoPoints) {
        GeoPoint closestPoint = null;
        double distance,
                minDistance = Double.MAX_VALUE;
        for (GeoPoint geoPoint : geoPoints) {
            distance = geoPoint._point.distanceSquared(point);
            if (distance < minDistance) {
                minDistance = distance;
                closestPoint = geoPoint;
            }
        }
        return closestPoint;
    }

    /**
     * call to writeToImage in imageWriter class
     */
    public void writeToImage() {
        _imageWriter.writeToImage();
    }
}
