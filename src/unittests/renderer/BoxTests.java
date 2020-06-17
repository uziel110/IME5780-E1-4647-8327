package renderer;

import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
import elements.PointLight;
import geometries.Geometries;
import geometries.Sphere;
import org.junit.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
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
}

