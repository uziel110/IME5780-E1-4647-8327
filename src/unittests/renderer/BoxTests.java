package renderer;

import elements.*;
import geometries.*;
import org.junit.Before;
import org.junit.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import scene.Scene;
import statistics.Statistics;

import java.util.Random;

public class BoxTests {
    private static Random random = new Random();
    long startAddGeometries;

    @Before
    public void setUp() {
        startAddGeometries = System.currentTimeMillis();
    }

    @Test
    public void createSpheres() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(-2000, 0, 0), new Vector(1, 0, 0), new Vector(0, 0, 1)));
        scene.setDistance(300);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(132, 124, 65), 0));

        Random rand = new Random();
        int NUM = 499, MOVE = 100;

        Geometries geometries = new Geometries();
        for (int i = NUM; i >= 0; --i) {
            geometries.add(new Sphere(
                    new Color(Math.abs(rand.nextInt() % 255), Math.abs(rand.nextInt() % 255),
                            Math.abs(rand.nextInt() % 255)),
                    new Material(0.4, 0.7, 100), 50, new Point3D(i * 100 - 200, -MOVE * i, MOVE * i / 2d)));
        }

        for (int i = NUM; i >= 0; --i) {
            geometries.add(new Sphere(
                    new Color(Math.abs(rand.nextInt() % 255), Math.abs(rand.nextInt() % 255),
                            Math.abs(rand.nextInt() % 255)),
                    new Material(0.4, 0.7, 100), 50, new Point3D(i * 100 - 200, MOVE * i, MOVE * i / 2d)));
        }
        for (int i = NUM; i >= 0; --i) {
            geometries.add(new Sphere(
                    new Color(Math.abs(rand.nextInt() % 255), Math.abs(rand.nextInt() % 255),
                            Math.abs(rand.nextInt() % 255)),
                    new Material(0.4, 0.7, 100), 50, new Point3D(i * 100 - 200, -MOVE * i, -MOVE * i / 2d)));
        }
        for (int i = NUM; i >= 0; --i) {
            geometries.add(new Sphere(
                    new Color(Math.abs(rand.nextInt() % 255), Math.abs(rand.nextInt() % 255),
                            Math.abs(rand.nextInt() % 255)),
                    new Material(0.4, 0.7, 100), 50, new Point3D(i * 100 - 200, MOVE * i, -MOVE * i / 2d)));
        }
        scene.addGeometries(geometries);
        scene.addLights(new DirectionalLight(new Color(48, 170, 176), new Vector(0, -1, 0)),
                new PointLight(new Color(103, 110, 13), new Point3D(0, -100, 0), 1, 0, 0));

        Statistics.runAndPrintStatistics(startAddGeometries, scene, 500, 500, 1000, 1000, 3, 4);
    }

    @Test
    public void createSpheres2() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(-2000, 0, 0), new Vector(1, 0, 0), new Vector(0, 0, 1)));
        scene.setDistance(500);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(132, 124, 65), 0));

        Random rand = new Random();
        int NUM = 500;

        Geometries geometries = new Geometries();
        for (int i = NUM; i >= 0; --i) {
            geometries.add(new Sphere(new Color(Math.abs(rand.nextInt() % 255), Math.abs(rand.nextInt() % 255),
                    Math.abs(rand.nextInt() % 255)),
                    new Material(0.4, 0.7, 100), 50, new Point3D(0, rand.nextDouble() * 100 - 50, rand.nextDouble() * 100 - 50)));
        }

        scene.addGeometries(geometries);
        scene.addLights(new DirectionalLight(new Color(48, 170, 176), new Vector(0, -1, 0)),
                new PointLight(new Color(103, 110, 13), new Point3D(0, -100, 0), 1, 0, 0));
        ImageWriter imageWriter = new ImageWriter("Box test3", 500, 500, 10000, 10000);
        Render render = new Render(imageWriter, scene).setMultithreading(3);//.setBoxDensity(10);
        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void createSpheres1() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(-500, 0, 0), new Vector(1, 0, 0), new Vector(0, 0, 1)));
        scene.setDistance(500);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(132, 124, 65), 0));

        Random rand = new Random();
        int MOVE = 75, size = 50;
        Material material = new Material(0.4, 0.7, 100, 0.5, 0.5);
        Geometries geometries = new Geometries();
        geometries.add(new Sphere(
                new Color(Math.abs(rand.nextInt() % 255), Math.abs(rand.nextInt() % 255),
                        Math.abs(rand.nextInt() % 255)),
                material, size, new Point3D(100, MOVE, MOVE)));
        geometries.add(new Sphere(
                new Color(Math.abs(rand.nextInt() % 255), Math.abs(rand.nextInt() % 255),
                        Math.abs(rand.nextInt() % 255)),
                material, size, new Point3D(100, -MOVE, MOVE)));
        geometries.add(new Sphere(
                new Color(Math.abs(rand.nextInt() % 255), Math.abs(rand.nextInt() % 255),
                        Math.abs(rand.nextInt() % 255)),
                material, size, new Point3D(100, MOVE, -MOVE)));
        geometries.add(new Sphere(
                new Color(Math.abs(rand.nextInt() % 255), Math.abs(rand.nextInt() % 255),
                        Math.abs(rand.nextInt() % 255)),
                material, size, new Point3D(100, -MOVE, -MOVE)));
        geometries.add(new Sphere(
                new Color(Math.abs(rand.nextInt() % 255), Math.abs(rand.nextInt() % 255),
                        Math.abs(rand.nextInt() % 255)),
                material, size, new Point3D(100, 0, 0)));

        scene.addGeometries(geometries);
        scene.addLights(new DirectionalLight(new Color(48, 170, 176), new Vector(0, -1, 0)),
                new PointLight(new Color(103, 110, 13), new Point3D(0, -100, 0), 1, 0, 0));
// scene.makeTree();
        ImageWriter imageWriter = new ImageWriter("Box test2", 500, 500, 1000, 1000);
        Render render = new Render(imageWriter, scene).setMultithreading(3);//.setBox(4);
        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void mendyTest() {
        long startAddGeometries = System.currentTimeMillis();

        Scene scene = new Scene("mendy test");
        scene.setCamera(new Camera(new Point3D(-1000, 0, 0), new Vector(1, 0, 0), new Vector(0, 0, 1)));
        scene.setDistance(500);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(132, 124, 65), 0));

        Random rand = new Random();
        final int NUM = 500, MOVE = 50;

        Geometries geometries = new Geometries();
        for (int i = 1; i <= NUM; ++i) {
            geometries.add(new Sphere(
                    new Color(Math.abs(rand.nextInt() % 255), Math.abs(rand.nextInt() % 255),
                            Math.abs(rand.nextInt() % 255)),
                    new Material(0.4, 0.7, 100), 50, new Point3D(i * 100 - 200, -MOVE * i, MOVE * i / 2d)));
        }

        for (int i = NUM; i >= 0; --i) {
            geometries.add(new Sphere(
                    new Color(Math.abs(rand.nextInt() % 255), Math.abs(rand.nextInt() % 255),
                            Math.abs(rand.nextInt() % 255)),
                    new Material(0.4, 0.7, 100), 50, new Point3D(i * 100 - 200, MOVE * i, MOVE * i / 2d)));
        }
        for (int i = 1; i <= NUM; ++i) {
            geometries.add(new Sphere(
                    new Color(Math.abs(rand.nextInt() % 255), Math.abs(rand.nextInt() % 255),
                            Math.abs(rand.nextInt() % 255)),
                    new Material(0.4, 0.7, 100, 0, 0), 50, new Point3D(i * 100 - 200, -MOVE * i, -MOVE * i / 2d)));
        }
        for (int i = NUM; i > 1; i--) {
            geometries.add(new Sphere(
                    new Color(Math.abs(rand.nextInt() % 255), Math.abs(rand.nextInt() % 255),
                            Math.abs(rand.nextInt() % 255)),
                    new Material(0.7, 0.3, 45), 50, new Point3D(i * 100, MOVE * i, -MOVE * i / 2d)));
        }
        scene.addGeometries(geometries);
        scene.addLights(new DirectionalLight(new Color(48, 170, 176), new Vector(0, -1, 0)),
                new PointLight(new Color(103, 110, 13), new Point3D(0, -100, 0), 1, 0, 0));

        Statistics.runAndPrintStatistics(startAddGeometries, scene, 500, 500, 500, 500, 3, 4);
    }

    @Test
    public void testSetMap() {
        long startAddGeometries = System.currentTimeMillis();
        Scene scene = new Scene("test2_1");
        scene.setCamera(new Camera(new Point3D(-1000, 0, 0), new Vector(1, 0, 0), new Vector(0, 0, 1)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));
        Sphere sphere = new Sphere(new Color(100, 100, 100), new Material(0.5, 0.5, 100, 0, 0.5)
                , 50, new Point3D(100, 100, 100));
        scene.addGeometries(sphere);
        Sphere sphere1 = new Sphere(new Color(150, 150, 150), new Material(0.5, 0.5, 100, 0, 0.5)
                , 50, new Point3D(100, -100, 100));
        scene.addGeometries(sphere1);
        Sphere sphere2 = new Sphere(new Color(100, 100, 0), new Material(0.5, 0.5, 100, 0, 0.5)
                , 50, new Point3D(100, 100, -100));
        scene.addGeometries(sphere2);
        Sphere sphere3 = new Sphere(new Color(100, 0, 100), new Material(0.5, 0.5, 100, 0, 0.5)
                , 50, new Point3D(100, -100, -100));
        scene.addGeometries(sphere3);
        Sphere sphere4 = new Sphere(new Color(100, 0, 100), new Material(0.5, 0.5, 100, 0, 0.5)
                , 50, new Point3D(100, 0, 0));
        scene.addGeometries(sphere4);
        scene.addLights(new DirectionalLight(new Color(400, 235, 486), new Vector(1, 0, 0)));

        Statistics.runAndPrintStatistics(startAddGeometries, scene, 300, 300, 500, 500, 3, 4);
    }

    @Test
    public void test() {
        long startAddGeometries = System.currentTimeMillis();
        Camera camera = new Camera(new Point3D(0, 0, 150), new Vector(0, -1, 0), new Vector(0, 0, -1));
        Scene scene = new Scene("malan sphere");
        SpotLight spot = new SpotLight(new Color(100, 100, 100), new Point3D(100, 0, 0),
                new Vector(-100, 0, -250).normalize(), 1, 2, 0.01);
        PointLight pointLight = new PointLight(new Color(77, 0, 0), new Point3D(200, -200, -100), 1, 0.01, 0.01);
        DirectionalLight directionalLight = new DirectionalLight(new Color(1, 1, 0), new Vector(1, 0, 0));

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                Sphere sphere = new Sphere(new Color(100 * (i % 2), 100 * (i % 2 + 1), 0),
                        new Material(500, 20, 100, 0, 0), 10, new Point3D(800 - 25 * i, 800 - 25 * j, -300));
                scene.addGeometries(sphere);
            }
        }
        scene.setAmbientLight(new AmbientLight(new Color(130, 130, 130), 0.1));
        scene.addLights(spot);
        scene.addLights(pointLight);
        scene.addLights(directionalLight);
        scene.setBackground(new Color(0, 0, 0));
        scene.setDistance(149);
        scene.setCamera(camera);
        Statistics.runAndPrintStatistics(startAddGeometries, scene, 500, 500, 500, 500, 3, 4);
    }

    @Test
    public void mizbeachTast() {
        Scene scene = new Scene("mizbeah");
        scene.setCamera(new Camera(new Point3D(0, -273, 0), 2500, 4, 1.1, Math.PI));
        scene.getCamera().setDepthOfField(2500, 32, 100);
        scene.setDistance(300);
        scene.setBackground(Color.BLACK);

        double kr = 0.2, kt = 0.0, ks = 0.3, kd = 1 - ks;

        Color woodColor = new Color(70, 45, 27);
        Color goldColor = new Color(109, 82, 16);
        Color sikraColor = new Color(255, 0, 0);
        Material woodMaterial = new Material(kd, ks, 30, kt, kr);
        Material goldMaterial = new Material(0.2, 0.8, 50, 0, 0);
        Material coalMaterial = new Material(0.2, 0.01, 5, 0, 0);
        Material sikraMaterial = new Material(kd, ks, 30, kt, kr);
        int woodColorRange = 54;
        {scene.addGeometries(

                //scene floor
/*                new Polygon(new Color(java.awt.Color.BLACK), new Material(kd, ks, 30, kt, 0)
                        , new Point3D(-1000, -1000, -10), new Point3D(-1000, 1000, -10),
                        new Point3D(2000, 2000, -10), new Point3D(1000, -1000, -10)),*/

                new Plane(new Color(java.awt.Color.BLACK), new Material(kd, ks, 30, kt, 0)
                        , new Point3D(-1000, -1000, 0), new Point3D(-500, 500, 0),
                        new Point3D(500, 500, 0)),

                //1 down
                new Polygon(goldColor, goldMaterial,
                        new Point3D(-288, -288, 0), new Point3D(-288, 288, 0),
                        new Point3D(288, 288, 0), new Point3D(288, -288, 0)),
                //1 up
                new Polygon(goldColor
                        , new Material(kd, ks, 30, kt, kr),
                        new Point3D(-288, -288, 15), new Point3D(-288, 288, 15),
                        new Point3D(288, 288, 15), new Point3D(288, -288, 15)),

                //1 West
                new Polygon(goldColor, goldMaterial,
                        new Point3D(-288, -288, 0), new Point3D(-288, 288, 0),
                        new Point3D(-288, 288, 15), new Point3D(-288, -288, 15)),
                //1 North
                new Polygon(new Color(109, 82, 16), goldMaterial,
                        new Point3D(-288, 288, 0), new Point3D(288, 288, 0),
                        new Point3D(288, 288, 15), new Point3D(-288, 288, 15)),
                //1 East
                new Polygon(new Color(109, 82, 16), goldMaterial,
                        new Point3D(288, 288, 0), new Point3D(288, -288, 0),
                        new Point3D(288, -288, 15), new Point3D(288, 288, 15)),
                //1 South
                new Polygon(goldColor, goldMaterial,
                        new Point3D(288, -288, 0), new Point3D(-288, -288, 0),
                        new Point3D(-288, -288, 15), new Point3D(288, -288, 15)),

                //2 down
                new Polygon(goldColor, goldMaterial,
                        new Point3D(-273, -273, 15), new Point3D(-273, 273, 15),
                        new Point3D(273, 273, 15), new Point3D(273, -273, 15)),
                //2 up
                new Polygon(goldColor, goldMaterial,
                        new Point3D(-273, -273, 105), new Point3D(-273, 273, 105),
                        new Point3D(273, 273, 105), new Point3D(273, -273, 105)),

                //2 West
                new Polygon(goldColor, goldMaterial,
                        new Point3D(-273, -273, 15), new Point3D(-273, 273, 15),
                        new Point3D(-273, 273, 105), new Point3D(-273, -273, 105)),
                //2 North
                new Polygon(goldColor, goldMaterial,
                        new Point3D(-273, 273, 15), new Point3D(273, 273, 15),
                        new Point3D(273, 273, 105), new Point3D(-273, 273, 105)),
                //2 East
                new Polygon(goldColor, goldMaterial,
                        new Point3D(273, 273, 15), new Point3D(273, -273, 15),
                        new Point3D(273, -273, 105), new Point3D(273, 273, 105)),
                //2 South
                new Polygon(goldColor, goldMaterial,
                        new Point3D(273, -273, 15), new Point3D(-273, -273, 15),
                        new Point3D(-273, -273, 105), new Point3D(273, -273, 105)),

                //3 down
                new Polygon(goldColor, goldMaterial,
                        new Point3D(-258, -258, 105), new Point3D(-258, 258, 105),
                        new Point3D(258, 258, 105), new Point3D(258, -258, 105)),
                //3 up
                new Polygon(goldColor, goldMaterial,
                        new Point3D(-258, -258, 159), new Point3D(-258, 258, 159),
                        new Point3D(258, 258, 159), new Point3D(258, -258, 159)),

                //3 West
                new Polygon(goldColor, goldMaterial,
                        new Point3D(-258, -258, 105), new Point3D(-258, 258, 105),
                        new Point3D(-258, 258, 159), new Point3D(-258, -258, 159)),
                //3 North
                new Polygon(goldColor, goldMaterial,
                        new Point3D(-258, 258, 105), new Point3D(258, 258, 105),
                        new Point3D(258, 258, 159), new Point3D(-258, 258, 159)),
                //3 East
                new Polygon(goldColor, goldMaterial,
                        new Point3D(258, 258, 105), new Point3D(258, -258, 105),
                        new Point3D(258, -258, 159), new Point3D(258, 258, 159)),
                //3 South
                new Polygon(goldColor, goldMaterial,
                        new Point3D(258, -258, 105), new Point3D(-258, -258, 105),
                        new Point3D(-258, -258, 159), new Point3D(258, -258, 159)),

                //4 West
                new Polygon(goldColor, goldMaterial,
                        new Point3D(-258, -258, 105), new Point3D(-258, 258, 105),
                        new Point3D(-258, 258, 159), new Point3D(-258, -258, 159)),
                //4 North
                new Polygon(goldColor, goldMaterial,
                        new Point3D(-258, 258, 105), new Point3D(258, 258, 105),
                        new Point3D(258, 258, 159), new Point3D(-258, 258, 159)),
                //4 East
                new Polygon(goldColor, goldMaterial,
                        new Point3D(258, 258, 105), new Point3D(258, -258, 105),
                        new Point3D(258, -258, 159), new Point3D(258, 258, 159)),
                //4 South
                new Polygon(goldColor, goldMaterial,
                        new Point3D(258, -258, 105), new Point3D(-258, -258, 105),
                        new Point3D(-258, -258, 159), new Point3D(258, -258, 159)),

                //1 WS
                new Polygon(goldColor, goldMaterial,
                        new Point3D(-258, -258, 159), new Point3D(-258, -240, 159),
                        new Point3D(-258, -240, 174), new Point3D(-258, -258, 174)),
                //2 WS
                new Polygon(goldColor, goldMaterial,
                        new Point3D(-258, -240, 159), new Point3D(-240, -240, 159),
                        new Point3D(-240, -240, 174), new Point3D(-258, -240, 174)),
                //3 WS
                new Polygon(goldColor, goldMaterial,
                        new Point3D(-240, -240, 159), new Point3D(-240, -258, 159),
                        new Point3D(-240, -258, 174), new Point3D(-240, -240, 174)),
                //4 WS
                new Polygon(goldColor, goldMaterial,
                        new Point3D(-240, -258, 159), new Point3D(-258, -258, 159),
                        new Point3D(-258, -258, 174), new Point3D(-240, -258, 174)),

                //1 NW
                new Polygon(goldColor, goldMaterial,
                        new Point3D(-258, 240, 159), new Point3D(-258, 258, 159),
                        new Point3D(-258, 258, 174), new Point3D(-258, 240, 174)),
                //2 NW
                new Polygon(goldColor, goldMaterial,
                        new Point3D(-258, 258, 159), new Point3D(-240, 258, 159),
                        new Point3D(-240, 258, 174), new Point3D(-258, 258, 174)),
                //3 NW
                new Polygon(goldColor, goldMaterial,
                        new Point3D(-240, 258, 159), new Point3D(-240, 240, 159),
                        new Point3D(-240, 240, 174), new Point3D(-240, 258, 174)),
                //4 NW
                new Polygon(goldColor, goldMaterial,
                        new Point3D(-240, 240, 159), new Point3D(-258, 240, 159),
                        new Point3D(-258, 240, 174), new Point3D(-240, 240, 174)),

                //1 EN
                new Polygon(goldColor, goldMaterial,
                        new Point3D(240, 258, 159), new Point3D(258, 258, 159),
                        new Point3D(258, 258, 174), new Point3D(240, 258, 174)),
                //2 EN
                new Polygon(goldColor, goldMaterial,
                        new Point3D(258, 258, 159), new Point3D(258, 240, 159),
                        new Point3D(258, 240, 174), new Point3D(258, 258, 174)),
                //3 EN
                new Polygon(goldColor, goldMaterial,
                        new Point3D(258, 240, 159), new Point3D(240, 240, 159),
                        new Point3D(240, 240, 174), new Point3D(258, 240, 174)),
                //4 EN
                new Polygon(goldColor, goldMaterial,
                        new Point3D(240, 240, 159), new Point3D(240, 258, 159),
                        new Point3D(240, 258, 174), new Point3D(240, 240, 174)),
                //1 ES
                new Polygon(goldColor, goldMaterial,
                        new Point3D(258, -240, 159), new Point3D(258, -258, 159),
                        new Point3D(258, -258, 174), new Point3D(258, -240, 174)),
                //2 ES
                new Polygon(goldColor, goldMaterial,
                        new Point3D(258, -258, 159), new Point3D(240, -258, 159),
                        new Point3D(240, -258, 174), new Point3D(258, -258, 174)),
                //3 ES
                new Polygon(goldColor, goldMaterial,
                        new Point3D(240, -258, 159), new Point3D(240, -240, 159),
                        new Point3D(240, -240, 174), new Point3D(240, -258, 174)),
                //4 ES
                new Polygon(goldColor, goldMaterial,
                        new Point3D(240, -240, 159), new Point3D(258, -240, 159),
                        new Point3D(258, -240, 174), new Point3D(240, -240, 174)),

                //main ramp
                new Polygon(goldColor, goldMaterial,
                        new Point3D(-144, -258, 0), new Point3D(144, -258, 0),
                        new Point3D(144, -834, 0), new Point3D(-144, -834, 0)),
                new Polygon(goldColor, goldMaterial,
                        new Point3D(-144, -258, 159), new Point3D(144, -258, 159),
                        new Point3D(144, -834, 0), new Point3D(-144, -834, 0)),
                new Triangle(goldColor, goldMaterial,
                        new Point3D(-144, -258, 159), new Point3D(-144, -834, 0),
                        new Point3D(-144, -258, 0)),
                new Triangle(goldColor, goldMaterial,
                        new Point3D(144, -258, 159), new Point3D(144, -834, 0),
                        new Point3D(144, -258, 0)),
                //east ramp
                new Polygon(goldColor, goldMaterial,
                        new Point3D(144, -273, 0), new Point3D(169, -273, 0),
                        new Point3D(169, -834, 0), new Point3D(144, -834, 0)),
                new Polygon(goldColor, goldMaterial,
                        new Point3D(144, -273, 105), new Point3D(169, -273, 105),
                        new Point3D(169, -834, 0), new Point3D(144, -834, 0)),
                new Triangle(goldColor, goldMaterial,
                        new Point3D(169, -273, 105), new Point3D(169, -834, 0),
                        new Point3D(169, -273, 0)),
                //west ramp
                new Polygon(goldColor, goldMaterial,
                        new Point3D(-159, -273, 0), new Point3D(-144, -273, 0),
                        new Point3D(-144, -642, 0), new Point3D(-159, -642, 0)),
                new Polygon(goldColor, goldMaterial,
                        new Point3D(-159, -273, 105), new Point3D(-144, -273, 105),
                        new Point3D(-144, -642, 40), new Point3D(-159, -642, 40)),

                new Polygon(goldColor, goldMaterial,
                        new Point3D(-159, -273, 105), new Point3D(-159, -273, 0),
                        new Point3D(-159, -642, 0), new Point3D(-159, -642, 40)),

                new Polygon(goldColor, goldMaterial,
                        new Point3D(-174, -672, 40), new Point3D(-144, -672, 40),
                        new Point3D(-144, -672, 0), new Point3D(-174, -672, 0)),

                new Polygon(goldColor, goldMaterial,
                        new Point3D(-174, -642, 40), new Point3D(-144, -642, 40),
                        new Point3D(-144, -672, 40), new Point3D(-174, -672, 40)),
                new Polygon(goldColor, goldMaterial,
                        new Point3D(-159, -672, 0), new Point3D(-174, -672, 0),
                        new Point3D(-174, -442, 0), new Point3D(-159, -442, 0)),
                new Polygon(goldColor, goldMaterial,
                        new Point3D(-159, -672, 40), new Point3D(-174, -672, 40),
                        new Point3D(-174, -442, 0), new Point3D(-159, -442, 0)),
                new Polygon(goldColor, goldMaterial,
                        new Point3D(-174, -672, 40), new Point3D(-174, -672, 0),
                        new Point3D(-174, -442, 0), new Point3D(-174, -642, 40)),

                new Sphere(new Color(21, 27, 31), coalMaterial,
                        50, new Point3D(0, 0, 144)),
                //2 West
                new Polygon(sikraColor, sikraMaterial,
                        new Point3D(-273.001, -273.001, 80), new Point3D(-273.001, 273.001, 80),
                        new Point3D(-273.001, 273.001, 87), new Point3D(-273.001, -273.001, 87)),
                //2 North
                new Polygon(sikraColor, sikraMaterial,
                        new Point3D(-273.001, 273.001, 80), new Point3D(273.001, 273.001, 80),
                        new Point3D(273.001, 273.001, 87), new Point3D(-273.001, 273.001, 87)),
                //2 East
                new Polygon(sikraColor, sikraMaterial,
                        new Point3D(273.001, 273.001, 80), new Point3D(273.001, -273.001, 80),
                        new Point3D(273.001, -273.001, 87), new Point3D(273.001, 273.001, 87)),
                //2 South
                new Polygon(sikraColor, sikraMaterial,
                        new Point3D(273.001, -273.001, 80), new Point3D(-273.001, -273.001, 80),
                        new Point3D(-273.001, -273.001, 87), new Point3D(273.001, -273.001, 87)));}

        for (int y = 195; y > 55; y -= 20) {
            scene.addGeometries(new Polygon(generateWoodColor(woodColor, woodColorRange), woodMaterial,
                    new Point3D(-205, y, 160), new Point3D(-85, y, 160),
                    new Point3D(-85, y - 10, 160), new Point3D(-205, y - 10, 160)));
        }
        for (int x = -195; x < -95; x += 20) {
            scene.addGeometries(new Polygon(generateWoodColor(woodColor, woodColorRange), woodMaterial,
                    new Point3D(x, 205, 160), new Point3D(x + 10, 205, 160),
                    new Point3D(x + 10, 45, 160), new Point3D(x, 45, 160)));
        }

        for (int y = -35; y > -195; y -= 20) {
            scene.addGeometries(new Polygon(generateWoodColor(woodColor, woodColorRange), woodMaterial,
                    new Point3D(-205, y, 160), new Point3D(-85, y, 160),
                    new Point3D(-85, y - 10, 160), new Point3D(-205, y - 10, 160)));
        }
        for (int x = -195; x < -95; x += 20) {
            scene.addGeometries(new Polygon(generateWoodColor(woodColor, woodColorRange), woodMaterial,
                    new Point3D(x, -35, 160), new Point3D(x + 10, -35, 160),
                    new Point3D(x + 10, -195, 160), new Point3D(x, -195, 160)));
        }

        for (int y = 195; y > -195; y -= 20) {
            scene.addGeometries(new Polygon(generateWoodColor(woodColor, woodColorRange), woodMaterial,
                    new Point3D(85, y, 160), new Point3D(205, y, 160),
                    new Point3D(205, y - 10, 160), new Point3D(85, y - 10, 160)));
        }
        for (int x = 85; x < 205; x += 20) {
            scene.addGeometries(new Polygon(generateWoodColor(woodColor, woodColorRange), woodMaterial,
                    new Point3D(x, 195, 160), new Point3D(x + 10, 195, 160),
                    new Point3D(x + 10, -195, 160), new Point3D(x, -195, 160)));
        }

        double lightRadius = 900;
        int numOfSpotes = 10;
        double t = Math.PI * 2 / numOfSpotes / 4;
        for (int i = 0; i < numOfSpotes; ++i) {
            double theta = Math.PI * 2 * (i * 1.0 / numOfSpotes);
            Point3D downPoint = new Point3D(lightRadius * Math.cos(theta), lightRadius * Math.sin(theta)-273, 0);
            scene.addGeometries(new Sphere(new Color(21, 27, 31), coalMaterial,
                    40, downPoint));
            downPoint = new Point3D(lightRadius * Math.cos(theta + t), lightRadius * Math.sin(theta + t)-273, 0);
            scene.addGeometries(new Sphere(new Color(21, 27, 31), coalMaterial,
                    50, downPoint));
            downPoint = new Point3D(lightRadius * Math.cos(theta - t * .8), lightRadius * Math.sin(theta - t * .8)-273, 0);
            scene.addGeometries(new Sphere(new Color(21, 27, 31), coalMaterial,
                    30, downPoint));
        }

        // lights
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        //scene.addLights(new PointLight(new Color(700, 400, 400), new Point3D(-60, 50, 200), 1, 4E-5, 2E-7));
        /*scene.addLights(new PointLight(new Color(0, 500, 400),
                new Point3D(0, 0, 50), 1, 4E-5, 2E-7));*/
        scene.addLights(new SpotLight(new Color(300, 300, 300),
                new Point3D(0, -273, 1500),new Vector(0,0,-1),5, 1, 4E-5, 2E-7));

        scene.addLights(new SpotLight(new Color(440, 0, 0),
                new Point3D(0, 0, 200),new Vector(0,0,-1),5, 1, 4E-5, 2E-7));

        scene.addLights(new DirectionalLight(new Color(150, 150, 150), new Vector(-1, 1, -1)));

        lightRadius = 1200;
        Point3D upPoint = new Point3D(0, -273, 100);
        numOfSpotes = 10;
        for (int i = 0; i < numOfSpotes; ++i) {
            double theta = Math.PI * 2 * (i * 1.0 / numOfSpotes);
            Point3D downPoint = new Point3D(lightRadius * Math.cos(theta), lightRadius * Math.sin(theta)-273, 5);
            scene.addLights(new SpotLight(new primitives.Color(950, 150, 150), downPoint,
                    upPoint.subtract(downPoint), 15, 1, 0.0001, 0.000005));
        }

        //Statistics.runAndPrintStatistics(startAddGeometries,scene,320, 180, 1600, 900,3,4);
        ImageWriter imageWriter = new ImageWriter("mizbeach1234", 320, 180, 1600, 900);
        Render render = new Render(imageWriter, scene).setDebugPrint().setMultithreading(3).setBox(4);
        render.renderImage();
        render.writeToImage();
    }

    private Color generateWoodColor(Color woodColor, int randomRange) {
        int t = random.nextInt(randomRange) - randomRange / 2;
        int x = woodColor.getColor().getRed();
        int y = woodColor.getColor().getGreen();
        int z = woodColor.getColor().getBlue();
        return new Color(x + t, y + t, z + t);
    }
}

