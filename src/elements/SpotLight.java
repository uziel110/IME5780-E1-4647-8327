package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class that implements the spot light
 */
public class SpotLight extends PointLight {
    private Vector _direction;

    /**
     * constructor of PointLight that receive intensity color his position
     * and factors for attenuation
     *
     * @param intensity Color intensity of the light
     * @param position  Point3D position of the light
     * @param kC        kC >= 1 for denominator > 1
     * @param kL
     * @param kQ
     * @param direction Vector direction of the light
     */
    public SpotLight(Color intensity, Point3D position, Vector direction, double kC, double kL, double kQ) {
        super(intensity, position, kC, kL, kQ);
        _direction = direction.normalized();
    }

    @Override
    public Color getIntensity(Point3D p) {
        double distance = _position.distanceSquared(p);
        double angle = _direction.dotProduct(getL(p));
        angle = angle < 0 ? 0 : angle;
        return _intensity.scale(angle / (_kC + _kL * distance + _kQ * distance * distance));
    }
}
