package unitests;

import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
import elements.PointLight;
import geometries.*;
import org.junit.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

import java.util.Random;

public class BoxTests {
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
// scene.makeTree();
        ImageWriter imageWriter = new ImageWriter("Box testRR", 500, 500, 1000, 1000);
        Render render = new Render(imageWriter, scene).setMultithreading(3).setBox(5);
        render.renderImage();
        render.writeToImage();
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
        Render render = new Render(imageWriter, scene).setBox(1).setMultithreading(3).setBox(4);
        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void mendyTest() {
        Scene scene = new Scene("Test scene");
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
// scene.makeTree();
        ImageWriter imageWriter = new ImageWriter("mendyTestBox", 1000, 1000, 2000, 2000);
        Render render = new Render(imageWriter, scene).setMultithreading(3).setBox(4);
        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void testSetMap() {
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
        ImageWriter imageWriter = new ImageWriter("rr", 300, 300, 500, 500);
        Render render = new Render(imageWriter, scene).setDebugPrint().setMultithreading(3).setBox(3);
        render.renderImage();
        render.writeToImage();
    }


    @Test
    public void finalTast() {
        Scene scene = new Scene("test2_1");
        scene.setCamera(new Camera(new Point3D(0, 0, 50), 1000, 1, 1, Math.PI));
        scene.setDistance(800);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));
        double scaleTriangle = 200, centerZ = 50;
        double kr = 1.0, kt = 0.0, ks = 0.8, kd = 1 - ks;
        Color triangleColor = new Color(java.awt.Color.black);

        Color PolygonColor = new Color(java.awt.Color.black);

        scene.addGeometries(
                new Polygon(new Color(210, 20, 20), new Material(kd, ks, 0, kt, 0)
                        , new Point3D(-120, -80, -100), new Point3D(-80, -120, -100), new Point3D(-480, -520, -100),
                        new Point3D(-520, -480, -100)),
                new Polygon(new Color(54, 99, 54), new Material(kd, ks, 30, kt, 0)
                        , new Point3D(-1000, 0, -100), new Point3D(0, 1000, -100), new Point3D(1000, 0, -100),
                        new Point3D(0, -1000, -100)),
                new Polygon(new Color(109, 82, 16)
                        ,new Material(kd, ks, 30, kt, 0),
                        new Point3D(-5, -200, 0), new Point3D(5, -200, 0), new Point3D(5, -230, -100),
                        new Point3D(-5, -230, -100)),
                new Polygon(new Color(109, 82, 16)
                        ,new Material(kd, ks, 30, kt, 0),
                        new Point3D(-200, -5, 0), new Point3D(-200, 5, 0), new Point3D(-230, 5, -100),
                        new Point3D(-230, -5, -100)),
                new Polygon(new Color(109, 82, 16)
                        ,new Material(kd, ks, 30, kt, 0),
                        new Point3D(-5, 200, 0), new Point3D(5, 200, 0), new Point3D(5, 230, -100),
                        new Point3D(-5, 230, -100)),
                new Polygon(new Color(109, 82, 16)
                        ,new Material(kd, ks, 30, kt, 0),
                        new Point3D(200, -5, 0), new Point3D(200, 5, 0), new Point3D(230, 5, -100),
                        new Point3D(230, -5, -100)),
                new Triangle(triangleColor, new Material(kd, ks, 30, kt, kr),
                        new Point3D(0, -scaleTriangle, 0), new Point3D(-scaleTriangle, 0, 0), new Point3D(0, 0, centerZ)),
                new Triangle(triangleColor, new Material(kd, ks, 30, kt, kr),
                        new Point3D(-scaleTriangle, 0, 0), new Point3D(0, scaleTriangle, 0), new Point3D(0, 0, centerZ)),
                new Triangle(triangleColor, new Material(kd, ks, 30, kt, kr),
                        new Point3D(0, scaleTriangle, 0), new Point3D(scaleTriangle, 0, 0), new Point3D(0, 0, centerZ)),
                new Triangle(triangleColor, new Material(kd, ks, 30, kt, kr),
                        new Point3D(scaleTriangle, 0, 0), new Point3D(0, -scaleTriangle, 0), new Point3D(0, 0, centerZ)));

        scene.addLights(new PointLight(new Color(700, 400, 400),
                new Point3D(-60, 50, 200), 1, 4E-5, 2E-7));
        /*scene.addLights(new PointLight(new Color(0, 500, 400),
                new Point3D(0, 0, 50), 1, 4E-5, 2E-7));*/
        /*scene.addLights(new PointLight(new Color(700, 400, 400),
                new Point3D(0, -200, -40), 1, 4E-5, 2E-7));*/
        scene.addLights(new DirectionalLight(new Color(200, 200, 200), new Vector(-1, 1, -1)));


        ImageWriter imageWriter = new ImageWriter("finalTest1", 700, 700, 1024, 1024);
        Render render = new Render(imageWriter, scene).setDebugPrint().setMultithreading(3).setBox(3);
        render.renderImage();
        render.writeToImage();
    }


    @Test
    public void mizbeachTast() {
        Scene scene = new Scene("test2_1");
        scene.setCamera(new Camera(new Point3D(0, 0, 50), 1000, 0.2, 0.4, Math.PI));
        scene.setDistance(500);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        //scene.addLights(new PointLight(new Color(700, 400, 400), new Point3D(-60, 50, 200), 1, 4E-5, 2E-7));
        /*scene.addLights(new PointLight(new Color(0, 500, 400),
                new Point3D(0, 0, 50), 1, 4E-5, 2E-7));
        scene.addLights(new PointLight(new Color(700, 400, 400),
                new Point3D(0, -200, -40), 1, 4E-5, 2E-7));*/
         scene.addLights(new DirectionalLight(new Color(200, 200, 200), new Vector(-1, 1, -1)));
        Color woodColor = new Color(85,60,42);
        Color goldColor = new Color(109,82,16);
        Color redColor = new Color(255,0,0);
        double scaleTriangle = 200, centerZ = 50;
        double kr = 1.0, kt = 0.0, ks = 0.8, kd = 1 - ks;

        scene.addGeometries(

             //scene floor
            new Polygon(new Color(192, 192, 192), new Material(kd, ks, 30, kt, 0)
                    , new Point3D(-1000, -1000, -10), new Point3D(-500, 500, -10),
                    new Point3D(500, 500, -10), new Point3D(500, -1000, -10)),

            //1 down
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(-288, -288, 0), new Point3D(-288, 288, 0),
                    new Point3D(288, 288, 0), new Point3D(288, -288, 0)),
            //1 up
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(-288, -288, 15), new Point3D(-288, 288, 15),
                    new Point3D(288, 288, 15), new Point3D(288, -288, 15)),

            //1 West
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(-288, -288, 0), new Point3D(-288, 288, 0),
                    new Point3D(-288, 288, 15), new Point3D(-288, -288, 15)),
            //1 North
            new Polygon(new Color(109, 82, 16)
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(-288, 288, 0), new Point3D(288, 288, 0),
                    new Point3D(288, 288, 15), new Point3D(-288, 288, 15)),
            //1 East
            new Polygon(new Color(109, 82, 16)
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(288, 288, 0), new Point3D(288, -288, 0),
                    new Point3D(288, -288, 15), new Point3D(288, 288, 15)),
            //1 South
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(288, -288, 0), new Point3D(-288, -288, 0),
                    new Point3D(-288, -288, 15), new Point3D(288, -288, 15)),

            //2 down
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(-273, -273, 15), new Point3D(-273, 273, 15),
                    new Point3D(273, 273, 15), new Point3D(273, -273, 15)),
            //2 up
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(-273, -273, 105), new Point3D(-273, 273, 105),
                    new Point3D(273, 273, 105), new Point3D(273, -273, 105)),

            //2 West
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(-273, -273, 15), new Point3D(-273, 273, 15),
                    new Point3D(-273, 273, 105), new Point3D(-273, -273, 105)),
            //2 North
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(-273, 273, 15), new Point3D(273, 273, 15),
                    new Point3D(273, 273, 105), new Point3D(-273, 273, 105)),
            //2 East
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(273, 273, 15), new Point3D(273, -273, 15),
                    new Point3D(273, -273, 105), new Point3D(273, 273, 105)),
            //2 South
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(273, -273, 15), new Point3D(-273, -273, 15),
                    new Point3D(-273, -273, 105), new Point3D(273, -273, 105)),

            //3 down
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(-258, -258, 105), new Point3D(-258, 258, 105),
                    new Point3D(258, 258, 105), new Point3D(258, -258, 105)),
            //3 up
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(-258, -258, 159), new Point3D(-258, 258, 159),
                    new Point3D(258, 258, 159), new Point3D(258, -258, 159)),

            //3 West
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(-258, -258, 105), new Point3D(-258, 258, 105),
                    new Point3D(-258, 258, 159), new Point3D(-258, -258, 159)),
            //3 North
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(-258, 258, 105), new Point3D(258, 258, 105),
                    new Point3D(258, 258, 159), new Point3D(-258, 258, 159)),
            //3 East
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(258, 258, 105), new Point3D(258, -258, 105),
                    new Point3D(258, -258, 159), new Point3D(258, 258, 159)),
            //3 South
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(258, -258, 105), new Point3D(-258, -258, 105),
                    new Point3D(-258, -258, 159), new Point3D(258, -258, 159)),

            //4 West
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(-258, -258, 105), new Point3D(-258, 258, 105),
                    new Point3D(-258, 258, 159), new Point3D(-258, -258, 159)),
            //4 North
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(-258, 258, 105), new Point3D(258, 258, 105),
                    new Point3D(258, 258, 159), new Point3D(-258, 258, 159)),
            //4 East
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(258, 258, 105), new Point3D(258, -258, 105),
                    new Point3D(258, -258, 159), new Point3D(258, 258, 159)),
            //4 South
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(258, -258, 105), new Point3D(-258, -258, 105),
                    new Point3D(-258, -258, 159), new Point3D(258, -258, 159)),

            //1 WS
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(-258, -258, 159), new Point3D(-258, -240, 159),
                    new Point3D(-258, -240, 174), new Point3D(-258, -258, 174)),
            //2 WS
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(-258, -240, 159), new Point3D(-240, -240, 159),
                    new Point3D(-240, -240, 174), new Point3D(-258, -240, 174)),
            //3 WS
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(-240, -240, 159), new Point3D(-240, -258, 159),
                    new Point3D(-240, -258, 174), new Point3D(-240, -240, 174)),
            //4 WS
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(-240, -258, 159), new Point3D(-258, -258, 159),
            new Point3D(-258, -258, 174),new Point3D(-240, -258, 174)),

            //1 NW
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(-258, 240, 159), new Point3D(-258, 258, 159),
                    new Point3D(-258, 258, 174), new Point3D(-258, 240, 174)),
            //2 NW
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(-258, 258, 159), new Point3D(-240, 258, 159),
                    new Point3D(-240, 258, 174), new Point3D(-258, 258, 174)),
            //3 NW
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(-240, 258, 159), new Point3D(-240, 240, 159),
                    new Point3D(-240, 240, 174), new Point3D(-240, 258, 174)),
            //4 NW
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(-240, 240, 159), new Point3D(-258, 240, 159),
                    new Point3D(-258, 240, 174),new Point3D(-240, 240, 174)),

            //1 EN
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(240, 258, 159), new Point3D(258, 258, 159),
                    new Point3D(258, 258, 174), new Point3D(240, 258, 174)),
            //2 EN
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(258, 258, 159), new Point3D(258, 240, 159),
                    new Point3D(258, 240, 174), new Point3D(258, 258, 174)),
            //3 EN
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(258, 240, 159), new Point3D(240, 240, 159),
                    new Point3D(240, 240, 174), new Point3D(258, 240, 174)),
            //4 EN
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(240, 240, 159), new Point3D(240, 258, 159),
                    new Point3D(240, 258, 174),new Point3D(240, 240, 174)),
            //1 ES
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(258, -240, 159), new Point3D(258, -258, 159),
                    new Point3D(258, -258, 174), new Point3D(258, -240, 174)),
            //2 ES
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(258, -258, 159), new Point3D(240, -258, 159),
                    new Point3D(240, -258, 174), new Point3D(258, -258, 174)),
            //3 ES
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(240, -258, 159), new Point3D(240, -240, 159),
                    new Point3D(240, -240, 174), new Point3D(240, -258, 174)),
            //4 ES
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(240, -240, 159), new Point3D(258, -240, 159),
                    new Point3D(258, -240, 174),new Point3D(240, -240, 174)),

            //main ramp
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
            new Point3D(-144, -258, 0), new Point3D(144, -258, 0),
            new Point3D(144, -834, 0), new Point3D(-144, -834, 0)),
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(-144, -258, 159), new Point3D(144, -258, 159),
                    new Point3D(144, -834, 0), new Point3D(-144, -834, 0)),
            new Triangle(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(-144, -258, 159), new Point3D(-144, -834, 0),
                    new Point3D(-144, -258, 0)),
            new Triangle(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(144, -258, 159), new Point3D(144, -834, 0),
                    new Point3D(144, -258, 0)),
            //east ramp
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(144, -273, 0), new Point3D(169, -273, 0),
                    new Point3D(169, -834, 0), new Point3D(144, -834, 0)),
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(144, -273, 105), new Point3D(169, -273, 105),
                    new Point3D(169, -834, 0), new Point3D(144, -834, 0)),
            new Triangle(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(169, -273, 105), new Point3D(169, -834, 0),
                    new Point3D(169, -273, 0)),
            //west ramp
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(-159, -273, 0), new Point3D(-144, -273, 0),
                    new Point3D(-144, -642, 0), new Point3D(-159, -642, 0)),
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(-159, -273, 105), new Point3D(-144, -273, 105),
                    new Point3D(-144, -642, 40), new Point3D(-159, -642, 40)),

            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(-159, -273, 105), new Point3D(-159, -273, 0),
                    new Point3D(-159, -642, 0), new Point3D(-159, -642, 40)),

            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(-174, -672, 40), new Point3D(-144, -672, 40),
                    new Point3D(-144, -672, 0), new Point3D(-174, -672, 0)),

            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(-174, -642, 40), new Point3D(-144, -642, 40),
                    new Point3D(-144, -672, 40), new Point3D(-174, -672, 40)),
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(-159, -672, 0), new Point3D(-174, -672, 0),
                    new Point3D(-174, -442, 0), new Point3D(-159, -442, 0)),
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(-159, -672, 40), new Point3D(-174, -672, 40),
                    new Point3D(-174, -442, 0), new Point3D(-159, -442, 0)),
            new Polygon(goldColor
                    ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(-174, -672, 40), new Point3D(-174, -672, 0),
                    new Point3D(-174, -442, 0),new Point3D(-174, -642, 40)),

            new Sphere(new Color(44, 56, 74), new Material(kd, ks, 10, kt, kr),
                   50, new Point3D(0,0,144)),
                    //2 West
                new Polygon(redColor ,new Material(kd, ks, 30, kt, kr),
                    new Point3D(-273, -273, 80), new Point3D(-273, 273, 80),
                    new Point3D(-273, 273, 87), new Point3D(-273, -273, 87)),
                    //2 North
                new Polygon(redColor ,new Material(kd, ks, 30, kt, kr),
                            new Point3D(-273, 273, 80), new Point3D(273, 273, 80),
                            new Point3D(273, 273, 87), new Point3D(-273, 273, 87)),
                    //2 East
                new Polygon(redColor ,new Material(kd, ks, 30, kt, kr),
                            new Point3D(273, 273, 80), new Point3D(273, -273, 80),
                            new Point3D(273, -273, 87), new Point3D(273, 273, 87)),
                    //2 South
                new Polygon(redColor ,new Material(kd, ks, 30, kt, kr),
                            new Point3D(273, -273, 80), new Point3D(-273, -273, 80),
                            new Point3D(-273, -273, 87), new Point3D(273, -273, 87)));

        for(int y = 195; y > 55; y-=20) {
            scene.addGeometries( new Polygon(woodColor, new Material(kd, ks, 30, kt, kr),
                    new Point3D(-205, y, 160), new Point3D(-85, y, 160),
                    new Point3D(-85, y-10, 160), new Point3D(-205, y-10, 160)));
        }
        for(int x = -195; x < -95; x+=20) {
            scene.addGeometries( new Polygon(woodColor, new Material(kd, ks, 30, kt, kr),
                    new Point3D(x, 205, 160), new Point3D(x+10, 205, 160),
                    new Point3D(x+10, 45, 160), new Point3D(x, 45, 160)));
        }

        for(int y = -35; y > -195; y-=20) {
            scene.addGeometries( new Polygon(woodColor, new Material(kd, ks, 30, kt, kr),
                    new Point3D(-205, y, 160), new Point3D(-85, y, 160),
                    new Point3D(-85, y-10, 160), new Point3D(-205, y-10, 160)));
        }
        for(int x = -195; x < -95; x+=20) {
            scene.addGeometries( new Polygon(woodColor, new Material(kd, ks, 30, kt, kr),
                    new Point3D(x, -35, 160), new Point3D(x+10, -35, 160),
                    new Point3D(x+10, -195, 160), new Point3D(x, -195, 160)));
        }

        for(int y = 195; y > -195; y-=20) {
            scene.addGeometries( new Polygon(woodColor, new Material(kd, ks, 30, kt, kr),
                    new Point3D(85, y, 160), new Point3D(205, y, 160),
                    new Point3D(205, y-10, 160), new Point3D(85, y-10, 160)));
        }
        for(int x = 85; x < 205; x+=20) {
            scene.addGeometries( new Polygon(woodColor, new Material(kd, ks, 30, kt, kr),
                    new Point3D(x, 195, 160), new Point3D(x+10, 195, 160),
                    new Point3D(x+10, -195, 160), new Point3D(x, -195, 160)));
        }





        ImageWriter imageWriter = new ImageWriter("mizbeach", 700, 700, 2048, 2048);
        Render render = new Render(imageWriter, scene).setDebugPrint().setMultithreading(3).setBox(3);
        render.renderImage();
        render.writeToImage();
    }
}


