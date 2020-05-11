package geometries;

import primitives.*;

import java.util.List;

/**
 * class that implements cylinder
 */
public class Cylinder extends Tube {
    private double _height;

    /**
     * constructor of Cylinder with emission color
     *
     * @param emission Color emission color of the cylinder
     * @param point    Point3D of start of the axis ray
     * @param vector   vector direction
     * @param radius   double value of cylinder radius
     * @param height   double value of Cylinder height
     */
    public Cylinder(Color emission, Point3D point, Vector vector, double radius, double height) {
        super(emission, point, vector, radius);
        _height = height;
    }

    /**
     * constructor of Cylinder
     * set the emission color to Black
     *
     * @param point  Point3D of start of the axis ray
     * @param vector vector direction
     * @param radius double value of cylinder radius
     * @param height double value of Cylinder height
     */
    public Cylinder(Point3D point, Vector vector, double radius, double height) {
        this(Color.BLACK, point, vector, radius, height);
    }

    /**
     * constructor of Cylinder
     *
     * @param emission Color emission color of the cylinder
     * @param axisRay  Ray of cylinder axis
     * @param radius   double value of cylinder radius
     * @param height   double value of cylinder height
     */
    public Cylinder(Color emission, Ray axisRay, double radius, double height) {
        super(emission, axisRay, radius);
        _height = height;
    }

    /**
     * constructor of Cylinder
     * set the emission color to Black
     *
     * @param axisRay Ray of cylinder axis
     * @param radius  double value of cylinder radius
     * @param height  double value of cylinder height
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        this(Color.BLACK, axisRay, radius, height);
    }

    /**
     * return double value the height of the Cylinder
     *
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
        if (Util.isZero(t) || Util.isZero(t - _height))
            return getAxisRay().getVector();

        Point3D o = this.getAxisRay().getPoint().add(v.scale(t));
        return point.subtract(o).normalize();
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        return null;
    }
}
