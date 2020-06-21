package unitests;

import elements.*;
import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Tube;
import org.junit.Before;
import org.junit.Test;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;
import statistics.Statistics;

import java.awt.*;

/**
 * test for depthOfField operation
 */
public class DepthOfFieldTest {
    long startAddGeometries;

    @Before
    public void setUp() {
        startAddGeometries = System.currentTimeMillis();
    }

    /**
     * test some balls DOF Test
     * Produce a picture of some Balls with depthOfFieldTest option on
     */
    @Test
    public void fourBallsDepthOfFieldTest_one() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, 0), 800, 0.5, 1, Math.PI));
        scene.getCamera().setDepthOfField(400, 32, 100);
        scene.setDistance(400);
        scene.setBackground(primitives.Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new primitives.Color(java.awt.Color.WHITE), 0.15));
        double kd = 0.3, ks = 1 - kd, kt = 0, kr = 1;
        primitives.Color sphereColor = new primitives.Color(Color.blue);
        scene.addGeometries(
                new Sphere(sphereColor, new Material(kd, ks, 30, kt, kr),
                        100, new Point3D(300, 0, 0)),
                new Sphere(sphereColor, new Material(kd, ks, 30, kt, kr),
                        100, new Point3D(0, 0, 0)),
                new Sphere(sphereColor, new Material(kd, ks, 30, kt, kr),
                        100, new Point3D(-300, 0, 0)),
                new Sphere(sphereColor, new Material(kd, ks, 30, kt, kr),
                        100, new Point3D(-600, 0, 0)),
                new Sphere(sphereColor, new Material(kd, ks, 30, kt, kr),
                        100, new Point3D(-900, 0, 0)));

        scene.addLights(new DirectionalLight(new primitives.Color(200, 200, 200), new Vector(-1, 1, -1)));

        Statistics.runAndPrintStatistics(startAddGeometries, scene, 600, 600, 800, 800,3,4);
    }

    /**
     * test some balls DOF Test
     * Produce number of pictures of some Balls from multiple angles with depthOfFieldTest option on
     */
/*
    @Test
    public void fourBallsDepthOfFieldTest() {
        Scene scene = new Scene("Test scene");
        scene.setDistance(400);
        scene.setBackground(primitives.Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new primitives.Color(java.awt.Color.WHITE), 0.15));
        double kd = 0.3, ks = 1 - kd, kt = 0, kr = 1;
        primitives.Color sphereColor = new primitives.Color(Color.blue);
        scene.addGeometries(
                new Sphere(sphereColor, new Material(KD, KS, 30, KT, KR),
                        100, new Point3D(300, 0, 0)),
                new Sphere(sphereColor, new Material(KD, KS, 30, KT, KR),
                        100, new Point3D(0, 0, 0)),
                new Sphere(sphereColor, new Material(KD, KS, 30, KT, KR),
                        100, new Point3D(-300, 0, 0)),
                new Sphere(sphereColor, new Material(KD, KS, 30, KT, KR),
                        100, new Point3D(-600, 0, 0)));

        scene.addLights(new DirectionalLight(new primitives.Color(200, 200, 200), new Vector(-1, 1, -1)));

        int numPictures = 50;
        for (int i = 0; i < numPictures; ++i) {
            double theta = Math.PI * 2 * (i * 1.0 / numPictures);
            scene.setCamera(new Camera(new Point3D(0, 0, 0), 800, theta, Math.PI / 3, Math.PI));
            scene.getCamera().setDepthOfField(400, 1000, 100);
            scene.getCamera().setDepthOfFieldEnabled();
            ImageWriter imageWriter = new ImageWriter("fourBallsDepthOfFieldTestEnabled" + i, 600, 600, 1200, 1200);
            Render render = new Render(imageWriter, scene);
            render.renderImage();
            render.writeToImage();
        }
    }
*/

    /**
     * Produce a picture with 10 tubes mirror and sphere
     * with reflection retraction and DOF
     * lighted by point lights directional light
     */
    @Test
    public void tubesMirrorAndSphereDOFTest() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, 500), 2500, 0.3, 1.5, Math.PI));
        scene.getCamera().setDepthOfField(2100, 60, 100);
        scene.setDistance(400);
        scene.setBackground(primitives.Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new primitives.Color(java.awt.Color.WHITE), 0.15));
        double kd = 0.3, ks = 1 - kd, kt = 0, kr = 1;
        primitives.Color mirrorColor = new primitives.Color(java.awt.Color.black);
        scene.addGeometries(
                new Plane(new primitives.Color(java.awt.Color.black), new Material(0.5, 0.5, 30, 0, 0),
                        new Point3D(0, 0, 0), new Vector(0, 0, 1)),
                new Sphere(new primitives.Color(java.awt.Color.blue), new Material(0.2, 0.2, 30, 0.6, 0.4),
                        300, new Point3D(0, 0, 300)));
        double tubeRadius = 1000;
        int numOfTubes = 10;
        for (int i = 0; i < numOfTubes; ++i) {
            double theta = Math.PI * 2 * (i * 1.0 / numOfTubes);
            scene.addGeometries(
                    new Tube(new primitives.Color(109, 82, 16), new Material(0.3, 0.7, 50, 0, 0),
                            new Point3D(tubeRadius * Math.cos(theta), tubeRadius * Math.sin(theta), 10), new Vector(0, 0, 1), 100));
        }
        Point3D[] polygonPoints = new Point3D[numOfTubes];
        for (int i = 0; i < numOfTubes; ++i) {
            double theta = Math.PI * 2 * (i * 1.0 / numOfTubes);
            polygonPoints[i] = new Point3D(tubeRadius * Math.cos(theta), tubeRadius * Math.sin(theta), 10);
        }
        scene.addGeometries(new Polygon(mirrorColor, new Material(kd, ks, 30, kt, kr), polygonPoints));

        scene.addLights(new PointLight(new primitives.Color(654, 495, 96),
                new Point3D(0, 0, 1500), 1, 4E-5, 2E-7));
        scene.addLights(new DirectionalLight(new primitives.Color(200, 200, 200), new Vector(-1, 1, -1)));
        double lightRadius = tubeRadius + 130;
        for (int i = 0; i < numOfTubes; ++i) {
            double theta = Math.PI * 2 * (i * 1.0 / numOfTubes);
            Point3D downPoint = new Point3D(lightRadius * Math.cos(theta), lightRadius * Math.sin(theta), 5);
            Point3D upPoint = new Point3D(tubeRadius * Math.cos(theta), tubeRadius * Math.sin(theta), 250);
            scene.addLights(new SpotLight(new primitives.Color(9500, 1500, 1500), downPoint,
                    upPoint.subtract(downPoint), 10,
                    1, 0.0001, 0.000005));
        }

        ImageWriter imageWriter = new ImageWriter("tubes mirror and sphere DOF", 500, 500, 1000, 1000);
        Render render = new Render(imageWriter, scene);
        render.renderImage();
        render.writeToImage();

        scene.getCamera().setDepthOfField(2100, 0, 1);
        imageWriter = new ImageWriter("tubes mirror and sphere without DOF", 500, 500, 1000, 1000);
        render = new Render(imageWriter, scene);
        render.renderImage();
        render.writeToImage();
    }
}
