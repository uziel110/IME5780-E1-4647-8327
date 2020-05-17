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

        double t = alignZero(v.dotProduct(qp));
        // if t == 0 v is orthogonal to qp so return qp
        if (t == 0)
            return qp;
        Point3D o = _axisRay.getPoint().add(v.scale(t));
        return point.subtract(o).normalize();
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {

        double radius = this.getRadius();

        Point3D rayPoint = new Point3D(ray.getPoint());
        Vector rayVector = new Vector(ray.getVector());
        Point3D axisRayPoint = new Point3D(_axisRay.getPoint());
        Vector axisRayVector = new Vector(_axisRay.getVector());

        double alfa = PointNultPoint(axisRayVector.getEnd(), rayPoint);
        alfa -= PointNultPoint(axisRayVector.getEnd(), axisRayPoint);
        double beta = rayVector.dotProduct(axisRayVector);


        /*double rayPointX, rayPointY, rayPointZ;
        rayPointX = ray.getPoint().getX().get();
        rayPointY = ray.getPoint().getY().get();
        rayPointZ = ray.getPoint().getZ().get();

        double rayVectorX, rayVectorY, rayVectorZ;
        rayVectorX = ray.getVector().getEnd().getX().get();
        rayVectorY = ray.getVector().getEnd().getY().get();
        rayVectorZ = ray.getVector().getEnd().getZ().get();

        double axisRayPointX, axisRayPointY, axisRayPointZ;
        axisRayPointX = _axisRay.getPoint().getX().get();
        axisRayPointY = _axisRay.getPoint().getY().get();
        axisRayPointZ = _axisRay.getPoint().getZ().get();

        double axisRayVectorX, axisRayVectorY, axisRayVectorZ;
        axisRayVectorX = _axisRay.getVector().getEnd().getX().get();
        axisRayVectorY = _axisRay.getVector().getEnd().getY().get();
        axisRayVectorZ = _axisRay.getVector().getEnd().getZ().get();

        // alfa = Ray's point(x, y, z) * arrow's point(x, y, z)
        double alfa = rayPointX * axisRayVectorX
                + rayPointY * axisRayVectorY
                + rayPointZ * axisRayVectorZ;

        alfa = alfa - (axisRayPointX * axisRayVectorX
                + axisRayPointY * axisRayVectorY
                + axisRayPointZ * axisRayVectorZ);

        final double beta = rayVectorX * axisRayVectorX
                + rayVectorY * axisRayVectorY
                + rayVectorZ * axisRayVectorZ;*/

        Point3D a = subtract(subtract(ray.getPoint(),_axisRay.getPoint()),(Point3DScale(axisRayVector.getEnd(),alfa)));
        Point3D b = subtract(rayVector.getEnd(),(Point3DScale(axisRayVector.getEnd(),beta)));

       /* double a1, a2, a3;

        a1 = rayPointX - axisRayPointX - (alfa * axisRayVectorX);
        a2 = rayPointY - axisRayPointY - (alfa * axisRayVectorY);
        a3 = rayPointZ - axisRayPointZ - (alfa * axisRayVectorZ);

        double b1, b2, b3;
        b1 = rayVectorX - (beta * axisRayVectorX);
        b2 = rayVectorY - (beta * axisRayVectorY);
        b3 = rayVectorZ - (beta * axisRayVectorZ);*/


        double w1, w2, w3;
        w1 = alignZero(PointNultPoint(b,b));
        w2 = 2 * PointNultPoint(a,b);
        w3 = PointNultPoint(a,a) - radius * radius;

        /*w1 = alignZero(b1 * b1 + b2 * b2 + b3 * b3);
        w2 = 2 * a1 * b1 + 2 * a2 * b2 + 2 * a3 * b3;
        w3 = a1 * a1 + a2 * a2 + a3 * a3 - radius * radius;*/

        if (w1 == 0)
            return null;

        double sq = alignZero(w2 * w2 - 4 * w1 * w3);

        if (sq < 0)
            return null;

        double t1, t2;
        sq = Math.sqrt(sq);

        t1 = alignZero((-w2 + sq) / (2 * w1));
        t2 = alignZero((-w2 - sq) / (2 * w1));

        if (isZero(t1 - t2) || (t1 <= 0 && t2 <= 0))
            return null;

        if (t1 > t2) {
            double temp = t1;
            t1 = t2;
            t2 = temp;
        }

        List<GeoPoint> points = new LinkedList<>();

        if (t1 > 0)
            points.add(new GeoPoint(this, ray.getPoint(t1)));

        if (t2 > 0)
            points.add(new GeoPoint(this, ray.getPoint(t2)));

        return points;
    }

    /**
     *  multiplying a point3D by other point3D
     * @param point Point3D
     * @param otherPoint other Point3D
     * @return double value - the result of multiplying a point3D by other point3D
     */
    private double PointNultPoint(Point3D point, Point3D otherPoint)
    {
        return point.getX().get() * otherPoint.getX().get()
                + point.getY().get() * otherPoint.getY().get()
                + point.getZ().get() * otherPoint.getZ().get();
    }


    /**
     * multiply point3D by scalar
     * @param point Point3D
     * @param scale double value to scale by
     * @return new Point3D the result of the multiply
     */
    private Point3D Point3DScale(Point3D point, double scale)
    {
        return new Point3D(point.getX().get() * scale,
               point.getY().get() * scale, point.getZ().get() * scale);
    }

    /**
     * Subtraction between two points
     * @param point Point3D
     * @param otherPoint other Point3D
     * @return new Point3D the result of the subtract
     */
    private Point3D subtract(Point3D point, Point3D otherPoint )
    {
        return new Point3D(point.getX().get() - otherPoint.getX().get(),
                point.getY().get() - otherPoint.getY().get(),
                point.getZ().get() - otherPoint.getZ().get());
    }
}
