package elements;

import primitives.Color;

/**
 * class for implementation of geometries light
 */
class Light {
    protected Color _intensity;

    /**
     * constructor of Light class
     *
     * @param intensity Color intensity of the light
     */
    public Light(Color intensity) {
        _intensity = new Color(intensity);
    }

    /**
     * return Color intensity of the light
     *
     * @return new Color intensity of the light
     */
    public Color getIntensity() {
        return new Color(_intensity);
    }
}
