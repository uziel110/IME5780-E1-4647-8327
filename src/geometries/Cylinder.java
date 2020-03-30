package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

/**
 * class that implements cylinder
 */
public class Cylinder extends Tube{
    private double _height;

    /**
     * constructor of Cylinder
     * @param point Point3D of start of the axis ray
     * @param vector vector direction
     * @param radius double value of cylinder radius
     * @param height double value of Cylinder height
     */
    public Cylinder(Point3D point,Vector vector, double radius, double height){
        super(point,vector, radius);
        _height = height;
    }

    /**
     * constructor of Cylinder
     * @param axisRay Ray of cylinder axis
     * @param radius double value of cylinder radius
     * @param height double value of cylinder height
     */
    public Cylinder(Ray axisRay, double radius, double height){
        super(axisRay,radius);
        _height = height;
    }

    /**
     * @return double height of the Cylinder
     */
    public double getHeight() {
        return _height;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + _height +
                ", axisRay=" + getAxisRay() +
                ", radius=" + getRadius() +
                '}';
    }

    @Override
    public Vector getNormal(Point3D point) {
        Vector v = new Vector(getAxisRay().getVector());
        Vector qp = point.subtract(getAxisRay().getPoint());

        double t = v.dotProduct(qp);

        // if t == 0 v is orthogonal to qp so return qp
        if(Util.isZero(t) || Util.isZero(t-_height))
            return getAxisRay().getVector();
        return  super.getNormal(point);
    }
}
