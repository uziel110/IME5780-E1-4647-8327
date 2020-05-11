package geometries;

import primitives.*;

import java.util.List;

/**
 * class that implements Tube
 */
public class Tube extends RadialGeometry {
    private Ray _axisRay;

    /**
     * constructor of Tube that receive
     * point on the center axis the direction
     * vector of the ray from that point
     * the radius of the tube and his color
     *
     * @param emission Color emission color of the Tube
     * @param point    Point3D of start of axis ray
     * @param vector   vector direction
     * @param radius   double value of Tube radius
     */
    public Tube(Color emission, Point3D point, Vector vector, double radius) {
        super(emission, radius);
        _axisRay = new Ray(point, vector);
    }

    /**
     * constructor of Tube that receive
     * point on the center axis the direction
     * vector of the ray from that point
     * and the radius of the tube
     * set the emission color to Black
     *
     * @param point  Point3D of start of axis ray
     * @param vector vector direction
     * @param radius double value of Tube radius
     */
    public Tube(Point3D point, Vector vector, double radius) {
        this(Color.BLACK, point, vector, radius);
    }

    /**
     * constructor of Tube
     * ray of the tube direction
     * the radius of the tube and his color
     *
     * @param emission Color emission color of the Tube
     * @param axisRay  Ray of Tube axis
     * @param radius   double value of Tube radius
     */
    public Tube(Color emission, Ray axisRay, double radius) {
        super(emission, radius);
        _axisRay = new Ray(axisRay);
    }

    /**
     * constructor of Tube
     * ray of the tube direction
     * and the radius of the tube
     * set the emission color to Black
     *
     * @param axisRay Ray of Tube axis
     * @param radius  double value of Tube radius
     */
    public Tube(Ray axisRay, double radius) {
        this(Color.BLACK, axisRay, radius);
    }

    /**
     * return axis ray
     *
     * @return axis ray
     */
    public Ray getAxisRay() {
        return _axisRay;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + _axisRay +
                ", radius=" + getRadius() +
                '}';
    }

    @Override
    public Vector getNormal(Point3D point) {
        //v: vector of the ray
        //p: point of the ray
        //q: point on the surface
        //qp: q - p
        Vector v = new Vector(_axisRay.getVector());
        Vector qp = point.subtract(_axisRay.getPoint());

        double t = v.dotProduct(qp);
        // if t == 0 v is orthogonal to qp so return qp
        if (Util.isZero(t))
            return qp;
        Point3D o = _axisRay.getPoint().add(v.scale(t));
        return point.subtract(o).normalize();
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {

        return null;
    }
}