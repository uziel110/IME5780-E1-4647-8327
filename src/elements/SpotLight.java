package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import static primitives.Util.alignZero;

/**
 * class that implements the spot light
 */
public class SpotLight extends PointLight {
    private double _thickness;
    private Vector _direction;

    /**
     * constructor of PointLight that receive intensity color his position
     * and factors for attenuation
     *
     * @param intensity Color intensity of the light
     * @param position  Point3D position of the light
     * @param thickness the thickness of the spot > 1
     * @param kC        kC >= 1
     * @param kL        attenuation factor linear
     * @param kQ        attenuation factor quadratic
     * @param direction Vector direction of the light
     */
    public SpotLight(Color intensity, Point3D position, Vector direction, double thickness, double kC, double kL, double kQ) {
        super(intensity, position, kC, kL, kQ);
        _thickness = thickness < 1 ? 1 : thickness;
        _direction = direction.normalized();
    }

    /**
     * constructor of PointLight that receive intensity color his position
     * and factors for attenuation
     * set the angle to 90
     *
     * @param intensity Color intensity of the light
     * @param position  Point3D position of the light
     * @param kC        kC >= 1
     * @param kL        attenuation factor linear
     * @param kQ        attenuation factor quadratic
     * @param direction Direction vector of the light
     */
    public SpotLight(Color intensity, Point3D position, Vector direction, double kC, double kL, double kQ) {
        this(intensity, position, direction, 1, kC, kL, kQ);
    }

    @Override
    public Color getIntensity(Point3D p) {
        Vector vectorFromLight = getL(p);
        if (vectorFromLight == null) return Color.BLACK;

        double projection = alignZero(_direction.dotProduct(vectorFromLight));
        if (projection <= 0) return Color.BLACK;
        projection = Math.pow(projection, _thickness);

        Color pointLightIntensity = super.getIntensity(p);

        return pointLightIntensity.scale(projection);
    }
}