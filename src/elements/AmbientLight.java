package elements;

import primitives.Color;

/**
 * class that implements the ambient light by intensity and color light source
 */
public class AmbientLight {

    private Color _intensity;

    /**
     * Constructor of AmbientLight class, get two parameters - color and intensity
     * @param ia color
     * @param ka intensity of the light
     */
    public AmbientLight(Color ia, double ka) {
        _intensity = ia.scale(ka);
    }

    /**
     * return Intensity
     * @return Color Intensity
     */
    public Color getIntensity() {
        return _intensity;
    }
}
