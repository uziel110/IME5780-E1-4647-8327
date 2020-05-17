package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class that implements the point light
 */
public class PointLight extends Light implements LightSource {
    protected Point3D _position;
    protected double _kC, _kL, _kQ;

    /**
     * constructor of PointLight that receive intensity color his position
     * and factors for attenuation
     *
     * @param intensity Color intensity of the light
     * @param position  Point3D position of the light
     * @param kC        kC >= 1
     * @param kL
     * @param kQ
     */
    public PointLight(Color intensity, Point3D position, double kC, double kL, double kQ) {
        super(intensity);
        _position = new Point3D(position);
        _kC = kC; //kC >= 1 for denominator > 1
        _kL = kL;
        _kQ = kQ;
    }

    @Override
    public Color getIntensity(Point3D p) {
        double distanceSquared = _position.distanceSquared(p);
        double distance = Math.sqrt(distanceSquared);
        return _intensity.scale(1 / (_kC + _kL * distance + _kQ * distanceSquared));
    }

    @Override
    public Vector getL(Point3D p) {
        return p.subtract(_position).normalize();
    }

    @Override
    public double getDistance(Point3D point) {
        return _position.distance(point);
    }
}
