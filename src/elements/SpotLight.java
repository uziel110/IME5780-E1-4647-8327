package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Util;
import primitives.Vector;

/**
 * class that implements the spot light
 */
public class SpotLight extends PointLight {
    private double _angle;
    private Vector _direction;

    /**
     * constructor of PointLight that receive intensity color his position
     * and factors for attenuation
     *
     * @param intensity Color intensity of the light
     * @param position  Point3D position of the light
     * @param angle     the angle of the spot <= 90
     * @param kC        kC >= 1
     * @param kL
     * @param kQ
     * @param direction Vector direction of the light
     */
    public SpotLight(Color intensity, Point3D position, Vector direction, double angle, double kC, double kL, double kQ) {
        super(intensity, position, kC, kL, kQ);
        _angle = angle % 90;
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
     * @param kL
     * @param kQ
     * @param direction Vector direction of the light
     */
    public SpotLight(Color intensity, Point3D position, Vector direction, double kC, double kL, double kQ) {
        this(intensity, position, direction, 90, kC, kL, kQ);
    }

    @Override
    public Color getIntensity(Point3D p) {
        double angle = Util.alignZero(_direction.dotProduct(getL(p)));
        if (angle <= 0) return Color.BLACK;
        return super.getIntensity(p).scale(angle);
    }
}