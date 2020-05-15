package geometries;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class that implements Tube
 */
public class Tube extends RadialGeometry {
    private Ray _axisRay;

    /**
     * constructor of Tube that receive
     * point on the center axis the direction
     * vector of the ray from that point
     * the radius of the tube, his material and color
     *
     * @param emission Color emission color of the Tube
     * @param material the material of the tube
     * @param point    Point3D of start of axis ray
     * @param vector   vector direction
     * @param radius   double value of Tube radius
     */
    public Tube(Color emission, Material material, Point3D point, Vector vector, double radius) {
        super(emission, material, radius);
        _axisRay = new Ray(point, vector);
    }

    /**
     * constructor of Tube that receive
     * point on the center axis the direction
     * vector of the ray from that point
     * the radius of the tube and his color
     * set material to (0,0,0)
     *
     * @param emission Color emission color of the Tube
     * @param point    Point3D of start of axis ray
     * @param vector   vector direction
     * @param radius   double value of Tube radius
     */
    public Tube(Color emission, Point3D point, Vector vector, double radius) {
        super(emission, new Material(0, 0, 0), radius);
        _axisRay = new Ray(point, vector);
    }

    /**
     * constructor of Tube that receive
     * point on the center axis the direction
     * vector of the ray from that point
     * and the radius of the tube
     * set the emission color to Black
     * set material to (0,0,0)
     *
     * @param point  Point3D of start of axis ray
     * @param vector vector direction
     * @param radius double value of Tube radius
     */
    public Tube(Point3D point, Vector vector, double radius) {
        this(Color.BLACK, new Material(0, 0, 0), point, vector, radius);
    }

    /**
     * constructor of Tube
     * ray of the tube direction
     * the radius of the tube and his color
     *
     * @param emission Color emission color of the Tube
     * @param material the material of the tube
     * @param axisRay  Ray of Tube axis
     * @param radius   double value of Tube radius
     */
    public Tube(Color emission, Material material, Ray axisRay, double radius) {
        super(emission, material, radius);
        _axisRay = new Ray(axisRay);
    }

    /**
     * constructor of Tube
     * ray of the tube direction
     * the radius of the tube and his color
     * set material to (0,0,0)
     *
     * @param emission Color emission color of the Tube
     * @param axisRay  Ray of Tube axis
     * @param radius   double value of Tube radius
     */
    public Tube(Color emission, Ray axisRay, double radius) {
        super(emission, new Material(0, 0, 0), radius);
        _axisRay = new Ray(axisRay);
    }

    /**
     * constructor of Tube
     * ray of the tube direction
     * and the radius of the tube
     * set the emission color to Black
     * set material to (0,0,0)
     *
     * @param axisRay Ray of Tube axis
     * @param radius  double value of Tube radius
     */
    public Tube(Ray axisRay, double radius) {
        this(Color.BLACK, new Material(0, 0, 0), axisRay, radius);
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
        if (isZero(t))
            return qp;
        Point3D o = _axisRay.getPoint().add(v.scale(t));
        return point.subtract(o).normalize();
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {

        double radius = this.getRadius();

        double a, b, c;
        a = ray.getPoint().getX().get();
        b = ray.getPoint().getY().get();
        c = ray.getPoint().getZ().get();

        double x, y, z;
        x = ray.getVector().getEnd().getY().get();

        y = ray.getVector().getEnd().getY().get();
        z = ray.getVector().getEnd().getZ().get();

        double c1, c2, c3;
        c1 = _axisRay.getPoint().getX().get();
        c2 = _axisRay.getPoint().getY().get();
        c3 = _axisRay.getPoint().getZ().get();
        double v1, v2, v3;
        v1 = _axisRay.getVector().getEnd().getX().get();
        v2 = _axisRay.getVector().getEnd().getY().get();
        v3 = _axisRay.getVector().getEnd().getZ().get();

        // alfa = Ray's point(x, y, z) *  arrow's point(x, y, z)
        double alfa = a * v1
                + b * v2
                + c * v3;

        alfa = alfa - (c1 * v1
                + c2 * v2
                + c3 * v3);

        final double beta = x * v1
                + y * v2
                + z * v3;


        double a1, a2, a3;

        a1 = a - c1 - (alfa * v1);
        a2 = b - c2 - (alfa * v2);
        a3 = c - c3 - (alfa * v3);

        double b1, b2, b3;
        b1 = x - (beta * v1);
        b2 = y - (beta * v2);
        b3 = z - (beta * v3);

        double w1, w2, w3;

        w1 = alignZero(b1 * b1 + b2 * b2 + b3 * b3);
        w2 = 2 * a1 * b1 + 2 * a2 * b2 + 2 * a3 * b3;
        w3 = a1 * a1 + a2 * a2 + a3 * a3 - radius * radius;
        if ( w1==0) {
            return null;
        }
        double sq = alignZero(w2 * w2 - 4 * w1 * w3);

        if (sq<0) {
            return null;
        }

        double t1, t2;
        sq=java.lang.Math.sqrt(sq);

        t1 = alignZero((-w2 + sq) / (2 * w1));
        t2 = alignZero((-w2 - sq) / ( 2 * w1));

        if (isZero(t1 - t2) || (t1 <= 0 && t2 <= 0) ) {
            return null;
        }

        if (t1 > t2) {
            double temp = t1;
            t1 = t2;
            t2 = temp;
        }

        List<GeoPoint> points = new LinkedList<GeoPoint>();
        if (t1 > 0) {
            points.add(new GeoPoint(this, ray.getPoint(t1)));
        }
        if (t2 > 0) {
            points.add(new GeoPoint(this, ray.getPoint(t2)));
        }
        return points;
    }
}
