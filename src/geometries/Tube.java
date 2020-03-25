package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * class that implements Tube
 */
public class Tube extends RadialGeometry {
    private Ray _axisRay;

    /**
     * constructor of Tube
     *
     * @param point  Point3D of start of axis ray
     * @param vector vector direction
     * @param radius double value of Tube radius
     */
    public Tube(Point3D point, Vector vector, double radius) {
        super(radius);
        _axisRay = new Ray(point, vector);
    }

    /**
     * constructor of Tube
     *
     * @param axisRay Ray of Tube axis
     * @param radius  double value of Tube radius
     */
    public Tube(Ray axisRay, double radius) {
        super(radius);
        _axisRay = new Ray(axisRay);
    }

    /**
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

    // "first the hinges" make problem
    @Override
    public Vector getNormal(Point3D point) {

        //v: vector of the ray
        //p: point of the ray
        //q: point on the surface
        //qp: q - p
        Vector v = new Vector(_axisRay.getVector());
        Vector qp = point.subtract(_axisRay.getPoint());
        double p = v.dotProduct(qp);
        if (p==0)
            return qp;
        return qp.subtract(v.scale(p)).normalize();

/*
        Vector p = new Vector(point.subtract(_axisRay.getPoint()));
        Vector t = new Vector(_axisRay.getVector());
        double p1 = p.dotProduct(t);
        double t1 = t.dotProduct(t);
        double res = (-p1)/t1;

        Vector r;
            r = new Vector(_axisRay.getVector().scale(res));
        if (!(_axisRay.getPoint().equals(Point3D.ZERO)))
            r = r.add(new Vector(_axisRay.getPoint()));
        return (new Vector(point)).subtract(r);
*/

    }
}
