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
    private static final Random random = new Random();
    long startAddGeometries;

    @Before
    public void setUp() {
        startAddGeometries = System.currentTimeMillis();
    }

    /**
     * test for 3DDDA algorithm
     * Produce a picture of mizbeach
     */
    @Test
    public void mizbeachTast() {
        Scene scene = new Scene("mizbeah");
        scene.setCamera(new Camera(new Point3D(0, -273, -100), 2500, 4, 1.1, Math.PI));
        //scene.getCamera().setDepthOfField(2500, 32, 100);
        scene.setDistance(260);
        scene.setBackground(Color.BLACK);

        double kr = 0.2, kt = 0.0, ks = 0.3, kd = 1 - ks;
        Color woodColor = new Color(70, 45, 27).reduce(2);
        Color goldColor = new Color(109, 82, 16);
        Color coalColor = new Color(21, 27, 31);
        Color sikraColor = new Color(255, 0, 0);
        Material woodMaterial = new Material(0.3, 0, 30, 0, 0);
        Material goldMaterial = new Material(0.2, 0.8, 50, 0, 0);
        Material coalMaterial = new Material(0.2, 0.01, 5, 0, 0);
        Material sikraMaterial = new Material(0.5, 0, 30, 0, 0);
        int woodColorRange = 27;

        Geometries geometries = new Geometries();

        //mizbeach
        {
            geometries.add(

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

                    /** Krnot Hmizbeach**/
                    //1 SW
                    new Polygon(goldColor, goldMaterial,
                            new Point3D(-258, -258, 159), new Point3D(-258, -240, 159),
                            new Point3D(-258, -240, 174), new Point3D(-258, -258, 174)),
                    //2 SW
                    new Polygon(goldColor, goldMaterial,
                            new Point3D(-258, -240, 159), new Point3D(-240, -240, 159),
                            new Point3D(-240, -240, 174), new Point3D(-258, -240, 174)),
                    //3 SW
                    new Polygon(goldColor, goldMaterial,
                            new Point3D(-240, -240, 159), new Point3D(-240, -258, 159),
                            new Point3D(-240, -258, 174), new Point3D(-240, -240, 174)),
                    //4 SW
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
                    //1 NE
                    new Polygon(goldColor, goldMaterial,
                            new Point3D(240, 258, 159), new Point3D(258, 258, 159),
                            new Point3D(258, 258, 174), new Point3D(240, 258, 174)),
                    //2 NE
                    new Polygon(goldColor, goldMaterial,
                            new Point3D(258, 258, 159), new Point3D(258, 240, 159),
                            new Point3D(258, 240, 174), new Point3D(258, 258, 174)),
                    //3 NE
                    new Polygon(goldColor, goldMaterial,
                            new Point3D(258, 240, 159), new Point3D(240, 240, 159),
                            new Point3D(240, 240, 174), new Point3D(258, 240, 174)),
                    //4 NE
                    new Polygon(goldColor, goldMaterial,
                            new Point3D(240, 240, 159), new Point3D(240, 258, 159),
                            new Point3D(240, 258, 174), new Point3D(240, 240, 174)),
                    //1 SE
                    new Polygon(goldColor, goldMaterial,
                            new Point3D(258, -240, 159), new Point3D(258, -258, 159),
                            new Point3D(258, -258, 174), new Point3D(258, -240, 174)),
                    //2 SE
                    new Polygon(goldColor, goldMaterial,
                            new Point3D(258, -258, 159), new Point3D(240, -258, 159),
                            new Point3D(240, -258, 174), new Point3D(258, -258, 174)),
                    //3 SE
                    new Polygon(goldColor, goldMaterial,
                            new Point3D(240, -258, 159), new Point3D(240, -240, 159),
                            new Point3D(240, -240, 174), new Point3D(240, -258, 174)),
                    //4 SE
                    new Polygon(goldColor, goldMaterial,
                            new Point3D(240, -240, 159), new Point3D(258, -240, 159),
                            new Point3D(258, -240, 174), new Point3D(240, -240, 174)),

                    /** ramps **/
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
                            new Point3D(-169, -273, 0), new Point3D(-144, -273, 0),
                            new Point3D(-144, -642, 0), new Point3D(-169, -642, 0)),
                    new Polygon(goldColor, goldMaterial,
                            new Point3D(-169, -273, 105), new Point3D(-144, -273, 105),
                            new Point3D(-144, -642, 40), new Point3D(-169, -642, 40)),
                    new Polygon(goldColor, goldMaterial,
                            new Point3D(-169, -273, 105), new Point3D(-169, -273, 0),
                            new Point3D(-169, -642, 0), new Point3D(-169, -642, 40)),
                    new Polygon(goldColor, goldMaterial,
                            new Point3D(-194, -672, 40), new Point3D(-144, -672, 40),
                            new Point3D(-144, -672, 0), new Point3D(-194, -672, 0)),
                    new Polygon(goldColor, goldMaterial,
                            new Point3D(-194, -642, 40), new Point3D(-144, -642, 40),
                            new Point3D(-144, -672, 40), new Point3D(-194, -672, 40)),
                    new Polygon(goldColor, goldMaterial,
                            new Point3D(-169, -672, 0), new Point3D(-194, -672, 0),
                            new Point3D(-194, -442, 0), new Point3D(-169, -442, 0)),
                    new Polygon(goldColor, goldMaterial,
                            new Point3D(-169, -672, 40), new Point3D(-194, -672, 40),
                            new Point3D(-194, -442, 0), new Point3D(-169, -442, 0)),
                    new Polygon(goldColor, goldMaterial,
                            new Point3D(-194, -672, 40), new Point3D(-194, -672, 0),
                            new Point3D(-194, -442, 0), new Point3D(-194, -642, 40)),

                    /** The sikra wire **/
                    //West
                    new Polygon(sikraColor, sikraMaterial,
                            new Point3D(-273.001, -273.001, 80), new Point3D(-273.001, 273.001, 80),
                            new Point3D(-273.001, 273.001, 87), new Point3D(-273.001, -273.001, 87)),
                    //North
                    new Polygon(sikraColor, sikraMaterial,
                            new Point3D(-273.001, 273.001, 80), new Point3D(273.001, 273.001, 80),
                            new Point3D(273.001, 273.001, 87), new Point3D(-273.001, 273.001, 87)),
                    //East
                    new Polygon(sikraColor, sikraMaterial,
                            new Point3D(273.001, 273.001, 80), new Point3D(273.001, -273.001, 80),
                            new Point3D(273.001, -273.001, 87), new Point3D(273.001, 273.001, 87)),
                    //South
                    new Polygon(sikraColor, sikraMaterial,
                            new Point3D(273.001, -273.001, 80), new Point3D(-273.001, -273.001, 80),
                            new Point3D(-273.001, -273.001, 87), new Point3D(273.001, -273.001, 87)),

                    new Sphere(coalColor, coalMaterial,
                            50, new Point3D(0, 0, 144)));
        }

        // balls on the mizbeach
        {
            int radius = 7;
            int j = -273;
            for (int i = 0; j + i < 266; i += 2 * radius)
                geometries.add(new Sphere(goldColor, goldMaterial, radius, new Point3D(j + i, -273, 87 + radius)),
                        new Sphere(goldColor, goldMaterial, radius, new Point3D(j + i, -273, 80 - radius)));
            for (int i = 0; j + i < 283; i += 2 * radius)
                geometries.add(new Sphere(goldColor, goldMaterial, radius, new Point3D(-273, j + i, 87 + radius)),
                        new Sphere(goldColor, goldMaterial, radius, new Point3D(-273, j + i, 80 - radius)));
            for (int i = 0; j + i < 283; i += 2 * radius)
                geometries.add(new Sphere(goldColor, goldMaterial, radius, new Point3D(j + i, 273, 87 + radius)),
                        new Sphere(goldColor, goldMaterial, radius, new Point3D(j + i, 273, 80 - radius)));
            for (int i = 0; j + i < 283; i += 2 * radius)
                geometries.add(new Sphere(goldColor, goldMaterial, radius, new Point3D(273, j + i, 87 + radius)),
                        new Sphere(goldColor, goldMaterial, radius, new Point3D(273, j + i, 80 - radius)));
        }

        //woods
        {
            int radius = 5;
            for (int y = 195; y > 35; y -= 30) {
                geometries.add(new Cylinder(generateWoodColor(woodColor, woodColorRange), woodMaterial,
                        new Point3D(-205, y, 160 + 3 * radius), new Vector(1, 0, 0),
                        radius, 120));
            }
            for (int x = -195; x < -85; x += 20) {
                geometries.add(new Cylinder(generateWoodColor(woodColor, woodColorRange), woodMaterial,
                        new Point3D(x, 205, 160 + radius), new Vector(0, -1, 0),
                        radius, 160));
            }
            for (int y = -35; y > -195; y -= 30) {
                geometries.add(new Cylinder(generateWoodColor(woodColor, woodColorRange), woodMaterial,
                        new Point3D(-205, y, 160 + 3 * radius), new Vector(1, 0, 0),
                        radius, 120));
            }
            for (int x = -195; x < -85; x += 20) {
                geometries.add(new Cylinder(generateWoodColor(woodColor, woodColorRange), woodMaterial,
                        new Point3D(x, -35, 160 + radius), new Vector(0, -1, 0),
                        radius, 160));
            }
            for (int y = 210; y > -145; y -= 30) {
                geometries.add(new Cylinder(generateWoodColor(woodColor, woodColorRange), woodMaterial,
                        new Point3D(85, y, 160 + 3 * radius), new Vector(1, 0, 0),
                        radius, 120));
            }
            for (int x = 85; x < 215; x += 20) {
                geometries.add(new Cylinder(generateWoodColor(woodColor, woodColorRange), woodMaterial,
                        new Point3D(x, 215, 160 + radius), new Vector(0, -1, 0),
                        radius, 350));
            }
        }

        //glass
/*        Material glassMaterial = new Material(0.05, 0.2, 50, 1, 0.1);
            geometries.add(new Sphere(Color.BLACK, glassMaterial, 700, new Point3D(0, -273, 0)));*/

        // lights
        int moveCenterY = -273;
        double lightRadius = 1200;
        {
            scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

            scene.addLights(new SpotLight(new Color(300, 300, 300),
                    new Point3D(0, -273, 1500), new Vector(0, 0, -1), 5, 1, 4E-5, 2E-7));

            scene.addLights(new SpotLight(new Color(440, 0, 0),
                    new Point3D(0, 0, 200), new Vector(0, 0, -1), 5, 1, 4E-5, 2E-7));

            scene.addLights(new DirectionalLight(new Color(150, 150, 150), new Vector(-1, 1, -1)));

            Point3D upPoint = new Point3D(0, -273, 100);
            int numOfSpotes = 50;
            int sphereInRadius = 10;
            double sphereExRadius = lightRadius + sphereInRadius;
            for (int i = 0; i < numOfSpotes; ++i) {
                double theta = Math.PI * 2 * (i * 1.0 / numOfSpotes);
                Point3D downPoint = new Point3D(lightRadius * Math.cos(theta), lightRadius * Math.sin(theta) + moveCenterY, 30);
                scene.addLights(new SpotLight(new primitives.Color(950, 150, 150), downPoint,
                        upPoint.subtract(downPoint), 15, 1, 0.0001, 0.000005));
                downPoint = new Point3D(sphereExRadius * Math.cos(theta), sphereExRadius * Math.sin(theta) + moveCenterY, 30);
                geometries.add(new Sphere(new Color(95, 15, 15), coalMaterial, sphereInRadius, downPoint),
                        new Cylinder(coalColor, coalMaterial, downPoint, new Vector(0, 0, -1), 5, 30));
            }
        }

        // balls on the floor
        {
            int minRadius = 600;
            int maxRadius = ((int) lightRadius);
            for (int i = 0; i < 3000; ++i) {
                double theta = Math.PI * 2 * random.nextDouble();
                double radius = random.nextInt(maxRadius - minRadius) + minRadius;
                Point3D position = new Point3D(radius * Math.cos(theta), radius * Math.sin(theta) + moveCenterY, 0);
                geometries.add(new Sphere(coalColor, coalMaterial, random.nextInt(10) + 10, position));
            }
        }

        geometries.add(new Cylinder(Color.BLACK, new Material(kd, ks, 30, kt, 0),
                new Point3D(0, moveCenterY, 0), new Vector(0, 0, -1), lightRadius + 50, 1));

        scene.addGeometries(geometries);
        //Statistics.runAndPrintStatistics(startAddGeometries, scene, 320, 200, 1600, 1000, 3, 5);

        // another camera angle
        /*scene.setCamera(new Camera(new Point3D(-300, -900, 0), 1300, 1.2, 1.2, Math.PI));
        scene.setDistance(150);*/
        //Statistics.runAndPrintStatistics(startAddGeometries, scene, 320, 200, 1600, 1000, 3, 5);
        ImageWriter imageWriter = new ImageWriter("mizbeach", 320, 200, 3840, 2160);
        scene.setBox(5);
        Render render = new Render(imageWriter, scene).setDebugPrint().setMultithreading(3);
        System.out.println("Density: " + scene.getBox().getDensity());
        render.renderImage();
        render.writeToImage();
    }

    /**
     * generate random wood color
     *
     * @param woodColor   basic color
     * @param randomRange range of random
     * @return new random color
     */
    private Color generateWoodColor(Color woodColor, int randomRange) {
        int t = random.nextInt(randomRange) - randomRange / 2;
        int x = woodColor.getColor().getRed();
        int y = woodColor.getColor().getGreen();
        int z = woodColor.getColor().getBlue();
        return new Color(x + t, y + t, z + t);
    }

    /**
     * test for 3DDDA algorithm
     * Produce a picture with many balls.
     */
    @Test
    public void goodImprove() {
        Scene scene = new Scene("goodImprove");
        scene.setCamera(new Camera(new Point3D(-1000, 0, 0), new Vector(1, 0, 0), new Vector(0, 0, 1)));
        scene.setDistance(500);
        //scene.getCamera().setDepthOfField(2000, 32, 100);

        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(132, 124, 65), 0));

        for (int i = 0; i < 2000; ++i) {
            scene.addGeometries(new Sphere(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)),
                    new Material(0.3, 0.7, 100, 0, .5), random.nextInt(10) + 10,
                    new Point3D(random.nextInt(2000), random.nextInt(2000) - 1000, random.nextInt(2000) - 1000)));
        }

        scene.addLights(new DirectionalLight(new Color(48, 170, 176), new Vector(1, 0.5, 0.5)));

        //Statistics.runAndPrintStatistics(startAddGeometries, scene, 1000, 1000, 1000, 1000, 3, 4);
        ImageWriter imageWriter = new ImageWriter("goodImprove10000", 500, 500, 10000, 10000);
        scene.setBox(4);
        Render render = new Render(imageWriter, scene).setMultithreading(3);
        render.renderImage();
        render.writeToImage();
    }
}