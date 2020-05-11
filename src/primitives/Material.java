package primitives;

/**
 * class that implements Materials for the geometries
 */
public class Material {
    private double _kD, _kS;
    private int _nShininess;

    /**
     * constructor for Material class that receive 3 parameters
     *
     * @param kD
     * @param kS
     * @param nShininess
     */
    public Material(double kD, double kS, int nShininess) {
        _kD = kD;
        _kS = kS;
        _nShininess = nShininess;
    }

    /**
     * return kD
     *
     * @return double kD
     */
    public double getKD() {
        return _kD;
    }

    /**
     * return kS
     *
     * @return double kS
     */
    public double getKS() {
        return _kS;
    }

    /**
     * return nShininess
     *
     * @return int nShininess
     */
    public int getNShininess() {
        return _nShininess;
    }
}
