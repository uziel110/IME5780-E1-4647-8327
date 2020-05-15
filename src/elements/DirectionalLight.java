package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class that implements the directional light
 */
public class DirectionalLight extends Light implements LightSource {
    private Vector _direction;

    /**
     * constructor of DirectionalLight that receive intensity color and his direction
     *
     * @param intensity Color intensity of the light
     * @param direction Vector direction of the light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        _direction = direction.normalized();
    }

    @Override
    public Color getIntensity(Point3D p) {
        return _intensity;
    }

    @Override
    public Vector getL(Point3D p) {
        return _direction;
    }
}
