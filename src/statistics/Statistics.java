package statistics;

import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

public class Statistics {
    /**
     * run the test 3 times without improves with multithreading and with box
     * and print the execution time of all the renders and the ratio between them
     * <p>
     * add this before your function "long startAddGeometries = System.currentTimeMillis();"
     *
     * @param startAddGeometries the time of starting
     * @param scene              the scene we render
     */
    public static void runAndPrintStatistics(long startAddGeometries, Scene scene, double width, double height, int nX, int nY, int threads, int lambda) {

        long endAddGeometries = System.currentTimeMillis();
        double endAddGeometriesDuration = (endAddGeometries - startAddGeometries) / 1000d;

        print(endAddGeometriesDuration, "Add geometries time: ");
        System.out.println("Number of geometries: " + scene.getGeometries().getGeometries().size());
        System.out.println("Number of lights: " + scene.getLights().size());

        //---------------

        long startRenderWithoutMultithreading = System.currentTimeMillis();
        ImageWriter imageWriter = new ImageWriter(scene.getName() + " WithoutMultithreading", width, height, nX, nY);
        Render render = new Render(imageWriter, scene).setMultithreading(1);
        /*render.renderImage();
        render.writeToImage();*/

        long endRenderWithoutMultithreading = System.currentTimeMillis();
        double renderWithoutMultithreadingDuration = (endRenderWithoutMultithreading - startRenderWithoutMultithreading + endAddGeometriesDuration) / 1000d;
        print(renderWithoutMultithreadingDuration, "Render time without multithreading:");

        //---------------

        long startRenderWithoutBox = System.currentTimeMillis();
        imageWriter = new ImageWriter(scene.getName() + " WithMultithreading", width, height, nX, nY);
        render = new Render(imageWriter, scene).setMultithreading(threads);
        render.renderImage();
        render.writeToImage();

        long endRenderWithoutBox = System.currentTimeMillis();
        double renderWithMultithreadingDuration = (endRenderWithoutBox - startRenderWithoutBox + endAddGeometriesDuration) / 1000d;
        print(renderWithMultithreadingDuration, "Render time with multithreading:");

        //---------------

        long startRenderWithBox = System.currentTimeMillis();
        imageWriter = new ImageWriter(scene.getName() + " WithBox", width, height, nX, nY);
        render = new Render(imageWriter, scene).setMultithreading(threads).setBox(lambda);
        render.renderImage();
        render.writeToImage();

        long endRenderWithBox = System.currentTimeMillis();
        double renderWithBoxDuration = (endRenderWithBox - startRenderWithBox + endAddGeometriesDuration) / 1000d;
        print(renderWithBoxDuration, "Render time with box when density = " + scene.getBox().getDensity() + " is:");

        //---------------

        System.out.printf("Normal render/multithreading render ratio is: " + "%.1f\n", 1.0 * renderWithoutMultithreadingDuration / renderWithMultithreadingDuration);
        System.out.printf("Normal render/box render ratio is: " + "%.1f\n", 1.0 * renderWithoutMultithreadingDuration / renderWithBoxDuration);
        System.out.printf("Multithreading render/box render ratio is: " + "%.1f\n", 1.0 * renderWithMultithreadingDuration / renderWithBoxDuration);
    }

    /**
     * print duration time formatted
     * @param durationTime duration time to print
     * @param s text to print
     */
    private static void print(double durationTime, String s) {
        if (durationTime < 1)
            System.out.printf(s + " %.3f Ms\n", durationTime);
        else {
            System.out.print(s);
            if (((int) durationTime) / 60 > 0)
                System.out.print(" " + (((int) durationTime) / 60) + " minutes and");
            System.out.printf(" %.1f seconds\n", durationTime % 60);
        }
    }
}
