package renderer;

import elements.*;
import geometries.*;
import org.junit.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));
        scene.addGeometries(
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.4, 0.3, 100, 0.3, 0), 50,
                        new Point3D(0, 0, 50)),
                new Sphere(new Color(java.awt.Color.RED), new Material(0.5, 0.5, 100), 25, new Point3D(0, 0, 50)));

        scene.addLights(new SpotLight(new Color(1000, 600, 0), new Point3D(-100, 100, -500), new Vector(-1, 1, 2), 1,
                0.0004, 0.0000006));

        ImageWriter imageWriter = new ImageWriter("twoSpheres", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -10000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(5000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        scene.addGeometries(
                new Sphere(new Color(0, 0, 100), new Material(0.25, 0.25, 20, 0.5, 0), 400, new Point3D(-950, 900, 1000)),
                new Sphere(new Color(100, 20, 20), new Material(0.25, 0.25, 20), 200, new Point3D(-950, 900, 1000)),
                new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 1), new Point3D(1500, 1500, 1500),
                        new Point3D(-1500, -1500, 1500), new Point3D(670, -670, -3000)),
                new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 0.5), new Point3D(1500, 1500, 1500),
                        new Point3D(-1500, -1500, 1500), new Point3D(-1500, 1500, 2000)));

        scene.addLights(new SpotLight(new Color(1020, 400, 400), new Point3D(-750, 750, 150),
                new Vector(-1, 1, 4), 1, 0.00001, 0.000005));

        ImageWriter imageWriter = new ImageWriter("twoSpheresMirrored", 1250, 1250, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a partially transparent Sphere
     * producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries( //
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150)), //
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 150)), //
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0.6, 0), // )
                        30, new Point3D(60, -50, 50)));

        scene.addLights(new SpotLight(new Color(700, 400, 400), //
                new Point3D(60, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7));

        ImageWriter imageWriter = new ImageWriter("shadow with transparency", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * our image
     * Produce a picture of a reflection triangles
     * and a partially transparent Sphere and tube lighted by point lights
     * producing partial shadow and reflection
     */
    @Test
    public void ourTest() {
        Scene scene = new Scene("Test scene");
        int distance = 500;
        scene.setCamera(new Camera(new Point3D(0, 0, 50), 1000, 0.5, 1, Math.PI));
        scene.setDistance(distance);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
        double scaleTriangle = 200, centerZ = 50;
        double kr = 1.0, kt = 0.0, ks = 0.8, kd = 1 - ks;
        Color triangleColor = new Color(java.awt.Color.black);
        scene.addGeometries(
                new Triangle(triangleColor, new Material(kd, ks, 30, kt, kr),
                        new Point3D(0, -scaleTriangle, 0), new Point3D(-scaleTriangle, 0, 0), new Point3D(0, 0, centerZ)),
                new Triangle(triangleColor, new Material(kd, ks, 30, kt, kr),
                        new Point3D(-scaleTriangle, 0, 0), new Point3D(0, scaleTriangle, 0), new Point3D(0, 0, centerZ)),
                new Triangle(triangleColor, new Material(kd, ks, 30, kt, kr),
                        new Point3D(0, scaleTriangle, 0), new Point3D(scaleTriangle, 0, 0), new Point3D(0, 0, centerZ)),
                new Triangle(triangleColor, new Material(kd, ks, 30, kt, kr),
                        new Point3D(scaleTriangle, 0, 0), new Point3D(0, -scaleTriangle, 0), new Point3D(0, 0, centerZ)),
                new Sphere(new Color(java.awt.Color.blue), new Material(0.2, 0.2, 30, 0.6, 0.4), // )
                        30, new Point3D(0, 0, 80)),
                new Tube(new Color(java.awt.Color.red), new Material(0.5, 0.2, 30, 0, 0.2),
                        new Point3D(0, 0, 80), new Vector(0, 1, 0), 10));

        scene.addLights(new PointLight(new Color(700, 400, 400),
                new Point3D(60, -50, 100), 1, 4E-5, 2E-7));
        scene.addLights(new PointLight(new Color(0, 500, 400),
                new Point3D(0, 0, 100), 1, 4E-5, 2E-7));

        ImageWriter imageWriter = new ImageWriter("ourTest", 100, 100, 600, 600);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture with 10 tubes mirror and sphere
     * with reflection retraction
     * lighted by point lights and directional light
     */
    @Test
    public void bonusTestBestCameraLocation() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, 500), 2500, 0.3, 1.5, Math.PI));
        scene.setDistance(400);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
        double kd = 0.3, ks = 1 - kd, kt = 0, kr = 1;
        Color triangleColor = new Color(java.awt.Color.black);
        scene.addGeometries(
                new Plane(new Color(java.awt.Color.black), new Material(0.5, 0.5, 30, 0, 0),
                        new Point3D(0, 0, 0), new Vector(0, 0, 1)),
                new Sphere(new Color(java.awt.Color.blue), new Material(0.2, 0.2, 30, 0.6, 0.4),
                        300, new Point3D(0, 0, 300)));
        double tubeRadius = 1000;
        int numOfTubes = 10;
        for (int i = 0; i < numOfTubes; ++i) {
            double theta = Math.PI * 2 * (i * 1.0 / numOfTubes);
            scene.addGeometries(
                    new Tube(new Color(109, 82, 16), new Material(0.3, 0.7, 50, 0, 0),
                            new Point3D(tubeRadius * Math.cos(theta), tubeRadius * Math.sin(theta), 10), new Vector(0, 0, 1), 100));
        }
        Point3D[] polygonPoints = new Point3D[numOfTubes];
        for (int i = 0; i < numOfTubes; ++i) {
            double theta = Math.PI * 2 * (i * 1.0 / numOfTubes);
            polygonPoints[i] = new Point3D(tubeRadius * Math.cos(theta), tubeRadius * Math.sin(theta), 10);
        }
        scene.addGeometries(new Polygon(triangleColor, new Material(kd, ks, 30, kt, kr), polygonPoints));

        scene.addLights(new PointLight(new Color(654, 495, 96),
                new Point3D(0, 0, 1500), 1, 4E-5, 2E-7));

        scene.addLights(new DirectionalLight(new Color(200, 200, 200), new Vector(-1, 1, -1)));

        ImageWriter imageWriter = new ImageWriter("bonusTestBestCameraLocation", 1200, 600, 3600, 1800);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture with 10 tubes mirror and sphere
     * with reflection retraction
     * lighted by point lights and directional light
     */
    @Test
    public void bonusTestAnotherCameraLocation() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, 500), 2500, 0.4, 0.7, Math.PI));
        scene.setDistance(400);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
        double kd = 0.3, ks = 1 - kd, kt = 0, kr = 1;
        Color PolygonColor = new Color(java.awt.Color.black);
        scene.addGeometries(
                new Plane(new Color(java.awt.Color.black), new Material(0.5, 0.5, 30, 0, 0),
                        new Point3D(0, 0, 0), new Vector(0, 0, 1)),
                new Sphere(new Color(java.awt.Color.blue), new Material(0.2, 0.2, 30, 0.6, 0.4),
                        300, new Point3D(0, 0, 300)));
        double tubeRadius = 1000;
        int numOfTubes = 10;
        for (int i = 0; i < numOfTubes; ++i) {
            double theta = Math.PI * 2 * (i * 1.0 / numOfTubes);
            scene.addGeometries(
                    new Tube(new Color(109, 82, 16), new Material(0.3, 0.7, 50, 0, 0),
                            new Point3D(tubeRadius * Math.cos(theta), tubeRadius * Math.sin(theta), 10), new Vector(0, 0, 1), 100));
        }
        Point3D[] polygonPoints = new Point3D[numOfTubes];
        for (int i = 0; i < numOfTubes; ++i) {
            double theta = Math.PI * 2 * (i * 1.0 / numOfTubes);
            polygonPoints[i] = new Point3D(tubeRadius * Math.cos(theta), tubeRadius * Math.sin(theta), 10);
        }
        scene.addGeometries(new Polygon(PolygonColor, new Material(kd, ks, 30, kt, kr), polygonPoints));

        scene.addLights(new PointLight(new Color(654, 495, 96),
                new Point3D(0, 0, 1500), 1, 4E-5, 2E-7));

        scene.addLights(new DirectionalLight(new Color(200, 200, 200), new Vector(-1, 1, -1)));
        double lightRadius = tubeRadius + 130;
        for (int i = 0; i < numOfTubes; ++i) {
            double theta = Math.PI * 2 * (i * 1.0 / numOfTubes);
            Point3D downPoint = new Point3D(lightRadius * Math.cos(theta), lightRadius * Math.sin(theta), 5);
            Point3D upPoint = new Point3D(tubeRadius * Math.cos(theta), tubeRadius * Math.sin(theta), 250);
            scene.addLights(new SpotLight(new primitives.Color(9500, 1500, 1500), downPoint,
                    upPoint.subtract(downPoint), 10,
                    1, 0.0001, 0.000005));
        }
        ImageWriter imageWriter = new ImageWriter("bonusTestAnotherCameraLocation", 600, 600, 1800, 1800);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture with 10 tubes mirror and sphere
     * with reflection retraction
     * lighted by point lights directional light and spot lights
     */

    @Test
    public void tubesMirrorAndSphereWithSpotTest() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, 500), 2500, 0.3, 1.5, Math.PI));
        scene.setDistance(400);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
        double kd = 0.3, ks = 1 - kd, kt = 0, kr = 1;
        Color triangleColor = new Color(java.awt.Color.black);
        scene.addGeometries(
                new Plane(new Color(java.awt.Color.black), new Material(0.5, 0.5, 30, 0, 0),
                        new Point3D(0, 0, 0), new Vector(0, 0, 1)),
                new Sphere(new Color(java.awt.Color.blue), new Material(0.2, 0.2, 30, 0.6, 0.4),
                        300, new Point3D(0, 0, 300)));
        double tubeRadius = 1000;
        int numOfTubes = 10;
        for (int i = 0; i < numOfTubes; ++i) {
            double theta = Math.PI * 2 * (i * 1.0 / numOfTubes);
            scene.addGeometries(
                    new Tube(new Color(109, 82, 16), new Material(0.3, 0.7, 50, 0, 0),
                            new Point3D(tubeRadius * Math.cos(theta), tubeRadius * Math.sin(theta), 10), new Vector(0, 0, 1), 100));
        }
        Point3D[] polygonPoints = new Point3D[numOfTubes];
        for (int i = 0; i < numOfTubes; ++i) {
            double theta = Math.PI * 2 * (i * 1.0 / numOfTubes);
            polygonPoints[i] = new Point3D(tubeRadius * Math.cos(theta), tubeRadius * Math.sin(theta), 10);
        }
        scene.addGeometries(new Polygon(triangleColor, new Material(kd, ks, 30, kt, kr), polygonPoints));

        scene.addLights(new PointLight(new Color(654, 495, 96),
                new Point3D(0, 0, 1500), 1, 4E-5, 2E-7));
        scene.addLights(new DirectionalLight(new Color(200, 200, 200), new Vector(-1, 1, -1)));
        double lightRadius = tubeRadius + 130;

        for (int i = 0; i < numOfTubes; ++i) {
            double theta = Math.PI * 2 * (i * 1.0 / numOfTubes);
            Point3D downPoint = new Point3D(lightRadius * Math.cos(theta), lightRadius * Math.sin(theta), 5);
            Point3D upPoint = new Point3D(tubeRadius * Math.cos(theta), tubeRadius * Math.sin(theta), 250);
            scene.addLights(new SpotLight(new Color(9500, 1500, 1500), downPoint,
                    upPoint.subtract(downPoint), 10,
                    1, 0.0001, 0.000005));
        }

        ImageWriter imageWriter = new ImageWriter("bonusTestWithSpot", 1200, 600, 3600, 1800);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }
}