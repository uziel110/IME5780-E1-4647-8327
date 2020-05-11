package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * interface for light source
 */
public interface LightSource {
    /**
     * return Color of received point
     *
     * @param p Point3D required point for his color
     * @return Color of the point
     */
    Color getIntensity(Point3D p);

    /**
     * return direction vector of the light
     *
     * @param p Point3D required point for his color
     * @return Vector direction vector of the light
     */
    Vector getL(Point3D p);
}