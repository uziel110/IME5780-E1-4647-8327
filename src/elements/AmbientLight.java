package elements;

import primitives.Color;

public class AmbientLight {

    private Color _intensity;

    public AmbientLight(Color ia, double ka) {
        _intensity = ia.scale(ka);
    }

    public Color getIntensity() {
        return _intensity;
    }
}
