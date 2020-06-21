package renderer;

import elements.Camera;
import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import scene.Scene;

import java.util.Collection;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * class that create image from the scene
 */
public class Render {
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private final int SPARE_THREADS = 2;
    private ImageWriter _imageWriter;
    private Scene _scene;
    // ...........
    private int _threads = 3;
    private boolean _print = false;

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
     * This function renders image's pixel color map from the scene included with
     * the Renderer object
     */
    public void renderImage() {
        Color background = _scene.getBackground();
        final int nX = _imageWriter.getNx();
        final int nY = _imageWriter.getNy();
        final double dist = _scene.getDistance();
        final double width = _imageWriter.getWidth();
        final double height = _imageWriter.getHeight();
        final Camera camera = _scene.getCamera();

        final Pixel thePixel = new Pixel(nY, nX);

        // Generate threads
        Thread[] threads = new Thread[_threads];
        for (int i = _threads - 1; i >= 0; --i) {
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel();
                while (thePixel.nextPixel(pixel)) {
                    List<Ray> rays = camera.constructBeamOfRays(nX, nY, pixel.col, pixel.row, //
                            dist, width, height);
                    _imageWriter.writePixel(pixel.col, pixel.row, calcColor(rays, background).getColor());
                }
            });
        }

        // Start threads
        for (Thread thread : threads) thread.start();

        // Wait for all threads to finish
        for (Thread thread : threads)
            try {
                thread.join();
            } catch (Exception e) {
            }
        if (_print) System.out.printf("\r100%%\n");
    }

    /**
     * Calculate the average color of the points that intersections with the rays
     *
     * @param rays list of rays
     * @param background color
     * @return average color
     */
    private Color calcColor(List<Ray> rays, Color background) {
        Color averageColor = Color.BLACK;
        for (Ray ray : rays) {
            GeoPoint closestPoint = findClosestIntersection(ray);
            Color closestPointColor = (closestPoint == null) ?
                    background :
                    calcColor(closestPoint, ray);
            averageColor = averageColor.add(closestPointColor);
        }
        if (rays.size() > 1)
            averageColor = averageColor.reduce(rays.size());
        return averageColor;
    }

    /**
     * Set multithreading <br>
     * - if the parameter is 0 - number of coress less 2 is taken
     *
     * @param threads number of threads
     * @return the Render object itself
     */
    public Render setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading patameter must be 0 or higher");
        if (threads != 0)
            _threads = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            if (cores <= 2)
                _threads = 1;
            else
                _threads = cores;
        }
        return this;
    }

    /**
     * Set debug printing on
     *
     * @return the Render object itself
     */
    public Render setDebugPrint() {
        _print = true;
        return this;
    }

    /**
     * return the factor of light shaded by transparency object
     *
     * @param ls light source
     * @param l  vector from the light to that point
     * @param n  vector normal to the geometry in that point
     * @param gp the point that we want to check
     * @return the factor of light shaded by transparency object
     */
    private double transparency(LightSource ls, Vector l, Vector n, GeoPoint gp) {
        Vector lightDirection = l.scale(-1); // change direction, from point to lightSource
        Ray lightRay = new Ray(gp._point, lightDirection, n);
        List<GeoPoint> intersections =
                _scene.getGeometries().getRelevantPoints(lightRay, _scene.getBox(), true, ls.getDistance(gp._point));
        if (intersections == null) return 1.0;

        double ktr = 1.0;
        for (GeoPoint geoPoint : intersections) {
            ktr *= geoPoint._geometry.getMaterial().getKT();
            if (ktr < MIN_CALC_COLOR_K)
                return 0.0;
        }
        return ktr;
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
        Ray lightRay = new Ray(gp._point, lightDirection, n);
        List<GeoPoint> intersections =
                _scene.getGeometries().getRelevantPoints(lightRay, _scene.getBox(), true, light.getDistance(gp._point));
        if (intersections == null) return true;
        for (GeoPoint geoPoint : intersections)
            if (geoPoint._geometry.getMaterial().getKT() == 0)
                return false;
        return true;
    }

    /**
     * create image from the scene
     * with focus option on or off
     */
    public void renderImageWithoutMultithreading() {
        // scene parameters
        Camera camera = _scene.getCamera();
        Color background = _scene.getBackground();
        double distance = _scene.getDistance();
        // imageWriter parameters
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        double width = _imageWriter.getWidth();
        double height = _imageWriter.getHeight();
        for (int i = 0; i < nY; ++i) {
            for (int j = 0; j < nX; ++j) {
                List<Ray> rays = camera.constructBeamOfRays(nX, nY, j, i, distance, width, height);
                Color averageColor = Color.BLACK;
                for (Ray ray : rays) {
                    GeoPoint closestPoint = findClosestIntersection(ray);
                    Color closestPointColor = (closestPoint == null) ?
                            background :
                            calcColor(closestPoint, ray);
                    averageColor = averageColor.add(closestPointColor);
                }
                if (rays.size() > 1)
                    averageColor = averageColor.reduce(rays.size());
                _imageWriter.writePixel(j, i, averageColor.getColor());
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
     * return color of this point
     * calculate all the reflection and refraction rays
     * start recursive call with MAX_CALC_COLOR_LEVEL and k = 0
     *
     * @param geoPoint the point we check for color
     * @param inRay    the ray the intersect with the geoPoint
     * @return color of this point
     */
    private Color calcColor(GeoPoint geoPoint, Ray inRay) {
        return calcColor(geoPoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0)
                .add(_scene.getAmbientLight().getIntensity());
    }

    /**
     * return color of this point
     * calculate all the reflection and refraction rays
     *
     * @param geoPoint the point we check for color
     * @param inRay    ray that intersect with the geometry
     * @param level    the number of recursive ray
     * @param k        factor of light intensity
     * @return Color of this point
     */
    private Color calcColor(GeoPoint geoPoint, Ray inRay, int level, double k) {
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
            if (alignZero(n.dotProduct(l)) * alignZero(n.dotProduct(v)) > 0) {
                double ktr = transparency(lightSource, l, n, geoPoint);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(geoPoint._point).scale(ktr);
                    color = color.add(calcDiffusive(kD, l, n, lightIntensity),
                            calcSpecular(kS, l, n, v, nShininess, lightIntensity));
                }
            }
        }
        if (level == 1) return Color.BLACK;
        double kR = geoPoint._geometry.getMaterial().getKR(), kkr = k * kR;
        if (kkr > MIN_CALC_COLOR_K)
            color = color.add(calcKK(level, kR, kkr, constructReflectedRay(n, geoPoint, inRay)));
        double kT = geoPoint._geometry.getMaterial().getKT(), kkt = k * kT;
        if (kkt > MIN_CALC_COLOR_K)
            color = color.add(calcKK(level, kT, kkt, constructRefractedRay(n, geoPoint, inRay)));
        return color;
    }

    /**
     * assistant func for reflection or refraction recursive calculation
     *
     * @param level the number of recursive ray
     * @param kX    factor of reflection or refraction intensity
     * @param kkX   factor of reflection or refraction intensity
     * @param ray   cumulative factor
     * @return return Color of this point
     */
    private Color calcKK(int level, double kX, double kkX, Ray ray) {
        GeoPoint refPoint = findClosestIntersection(ray);
        if (refPoint != null)
            return (calcColor(refPoint, ray, level - 1, kkX).scale(kX));
        return Color.BLACK;
    }

    /**
     * return new ray reflected from the surface at the point
     *
     * @param n        vector normal to the reflected geometry
     * @param geoPoint point on the surface of the geometry
     * @param inRay    ray that intersect with the geometry
     * @return return new ray reflected from the surface at the point
     */
    private Ray constructReflectedRay(Vector n, GeoPoint geoPoint, Ray inRay) {
        Vector reflectedVector = inRay.getDir().subtract(n.scale(inRay.getDir().dotProduct(n) * 2)); //r
        return new Ray(geoPoint._point, reflectedVector, n);
    }

    /**
     * return new ray refracted from the surface at the point
     *
     * @param n        vector normal to the reflected geometry
     * @param geoPoint point on the surface of the geometry
     * @param inRay    ray that intersect with the geometry
     * @return return new ray refracted from the surface at the point
     */
    private Ray constructRefractedRay(Vector n, GeoPoint geoPoint, Ray inRay) {
        return new Ray(geoPoint._point, inRay.getDir(), n);// refracted lightRay
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
        if (minusVDotR <= 0) return Color.BLACK;
        return lightIntensity.scale(Math.pow(minusVDotR, nShininess) * kS);
    }

    /**
     * return the closest point to the head of the reflected ray
     *
     * @param ray the ray that we find closest point on it
     * @return return the closest point to the head of the reflected ray
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> relevantPoint = _scene.getGeometries().getRelevantPoints(ray, _scene.getBox(), false,
                Double.POSITIVE_INFINITY);
        if (relevantPoint == null) return null;
        return getClosestPoint(ray.getPoint(), relevantPoint);
    }

    /**
     * return GeoPoint the point with minimal distance from the ray begin point
     *
     * @param point     point to measure distance from
     * @param geoPoints list of Point3D
     * @return GeoPoint the closest point to the ray begin point
     */
    private GeoPoint getClosestPoint(Point3D point, Collection<GeoPoint> geoPoints) {
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
     * call to writeToImage in imageWriter class
     */
    public void writeToImage() {
        _imageWriter.writeToImage();
    }

    /**
     * Pixel is an internal helper class whose objects are associated with a Render object that
     * they are generated in scope of. It is used for multithreading in the Renderer and for follow up
     * its progress.<br/>
     * There is a main follow up object and several secondary objects - one in each thread.
     *
     * @author Dan
     */
    private class Pixel {
        public volatile int row = 0;
        public volatile int col = -1;
        private long _maxRows = 0;
        private long _maxCols = 0;
        private long _pixels = 0;
        private long _counter = 0;
        private int _percents = 0;
        private long _nextCounter = 0;

        /**
         * The constructor for initializing the main follow up Pixel object
         *
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            _maxRows = maxRows;
            _maxCols = maxCols;
            _pixels = maxRows * maxCols;
            _nextCounter = _pixels / 100;
            if (Render.this._print) System.out.printf("\r %02d%%", _percents);
        }

        /**
         * Default constructor for secondary Pixel objects
         */
        public Pixel() {
        }

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object - this function is
         * critical section for all the threads, and main Pixel object data is the shared data of this critical
         * section.<br/>
         * The function provides next pixel number each call.
         *
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print, if it is -1 - the task is
         * finished, any other value - the progress percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col;
            ++_counter;
            if (col < _maxCols) {
                target.row = this.row;
                target.col = this.col;
                if (_counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            ++row;
            if (row < _maxRows) {
                col = 0;
                if (_counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            return -1;
        }

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         *
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percents = nextP(target);
            if (percents > 0)
                if (Render.this._print) System.out.printf("\r %02d%%", percents);
            if (percents >= 0)
                return true;
            if (Render.this._print) System.out.printf("\r %02d%%", 100);
            return false;
        }

        @Override
        public String toString() {
            return "Pixel{" + row + ", " + col + '}';
        }
    }
}