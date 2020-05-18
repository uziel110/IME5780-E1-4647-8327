package primitives;

/**
 * class that implements Materials for the geometries
 */
public class Material {
    /**
     * diffusive factor
     */
    private double _kD;
    /**
     * specular factor
     */
    private double _kS;
    /**
     * refraction factor
     */
    private double _kT;
    /**
     * reflection factor
     */
    private double _kR;
    /**
     * shininess of the geometry
     */
    private int _nShininess;

    /**
     * @param kD         factor of the diffusive
     * @param kS         factor of the specular
     * @param nShininess the shininess of the geometry
     * @param kT         refraction  factor
     * @param kR         reflection factor
     */
    public Material(double kD, double kS, int nShininess, double kT, double kR) {
        _kD = kD;
        _kS = kS;
        _nShininess = nShininess;
        _kT = kT;
        _kR = kR;
    }

    /**
     * constructor for Material class that receive 3 parameters
     * set kT and kR to 0
     *
     * @param kD         factor of the diffusive
     * @param kS         factor of the specular
     * @param nShininess the shininess of the geometry
     */
    public Material(double kD, double kS, int nShininess) {
        this(kD, kS, nShininess, 0, 0);
    }

    /**
     * return kD factor of the diffusive
     *
     * @return double kD
     */
    public double getKD() {
        return _kD;
    }

    /**
     * return kS factor of the specular
     *
     * @return double kS
     */
    public double getKS() {
        return _kS;
    }

    /**
     * return kT refraction factor
     *
     * @return kT refraction factor
     */
    public double getKT() {
        return _kT;
    }

    /**
     * return kR reflection factor
     *
     * @return kR reflection factor
     */
    public double getKR() {
        return _kR;
    }

    /**
     * return nShininess
     * the shininess of the geometry
     *
     * @return int nShininess
     */
    public int getNShininess() {
        return _nShininess;
    }
}
