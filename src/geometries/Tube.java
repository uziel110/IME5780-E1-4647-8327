package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * class that implements Tube
 */
public class Tube extends RadialGeometry {
    Ray _axisRay;

    /**
     * constructor of Tube
     * @param Point3D start point
     * @param Vector direction
     * @param double radius of the Tube
     */
    public Tube( Point3D point,Vector vector, double radius){
        super(radius);
        _axisRay = new Ray(point, vector);
    }

    /**
     * constructor of Tube
     * @param Ray axisRay
     * @param double radius of the Tube
     */
    public Tube(Ray axisRay, double radius){
        super(radius);
        _axisRay = new Ray(axisRay);
    }

    /**
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
                ", radius=" + _radius +
                '}';
    }

    // "first the hinges" make problem
    @Override
    public Vector getNormal(Point3D point) {
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
        return null;
    }
}
