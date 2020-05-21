package renderer;

import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
import geometries.Sphere;
import org.junit.Test;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import scene.Scene;

import java.awt.*;

public class DepthOfFieldTest {

    @Test
    public void depthOfFieldTest() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, 0), 800, 0.2, Math.PI / 3, Math.PI));
        scene.getCamera().setDepthOfFieldDisabled();
        scene.getCamera().setDepthOfField(300, 1000, 100);
        scene.setDistance(400);
        scene.setBackground(primitives.Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new primitives.Color(java.awt.Color.WHITE), 0.15));
        final double SCALED = 10, WIDTH = 100 * SCALED, CENTER_Z = 0 * SCALED;
        final double KD = 0.3, KS = 1 - KD, KT = 0, KR = 1;
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

        ImageWriter imageWriter = new ImageWriter("depthOfFieldTestDisabled", 600, 600, 1800, 1800);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }
}
