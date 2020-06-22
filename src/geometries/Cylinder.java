package geometries;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * class that implements cylinder
 */
public class Cylinder extends Tube {
    private double _height;

    /**
     * constructor of Cylinder with emission color
     *
     * @param emission Color emission color of the cylinder
     * @param material the material of the cylinder
     * @param point    Point3D of start of the axis ray
     * @param vector   vector direction
     * @param radius   double value of cylinder radius
     * @param height   double value of Cylinder height
     */
    public Cylinder(Color emission, Material material, Point3D point, Vector vector, double radius, double height) {
        super(emission, material, point, vector, radius);
        _height = height;
    }

    /**
     * constructor of Cylinder with emission color
     * set material to (0,0,0)
     *
     * @param emission Color emission color of the cylinder
     * @param point    Point3D of start of the axis ray
     * @param vector   vector direction
     * @param radius   double value of cylinder radius
     * @param height   double value of Cylinder height
     */
    public Cylinder(Color emission, Point3D point, Vector vector, double radius, double height) {
        super(emission, new Material(0, 0, 0), point, vector, radius);
        _height = height;
    }

    /**
     * constructor of Cylinder
     * set the emission color to Black
     * set material to (0,0,0)
     *
     * @param point  Point3D of start of the axis ray
     * @param vector vector direction
     * @param radius double value of cylinder radius
     * @param height double value of Cylinder height
     */
    public Cylinder(Point3D point, Vector vector, double radius, double height) {
        this(Color.BLACK, new Material(0, 0, 0), point, vector, radius, height);
    }

    /**
     * constructor of Cylinder
     *
     * @param emission Color emission color of the cylinder
     * @param material the material of the cylinder
     * @param axisRay  Ray of cylinder axis
     * @param radius   double value of cylinder radius
     * @param height   double value of cylinder height
     */
    public Cylinder(Color emission, Material material, Ray axisRay, double radius, double height) {
        super(emission, material, axisRay, radius);
        _height = height;
    }

    /**
     * constructor of Cylinder
     * set the emission color to Black
     * set material to (0,0,0)
     *
     * @param axisRay Ray of cylinder axis
     * @param radius  double value of cylinder radius
     * @param height  double value of cylinder height
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        this(Color.BLACK, new Material(0, 0, 0), axisRay, radius, height);
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
        Vector v = new Vector(getAxisRay().getDir());
        Vector qp = point.subtract(getAxisRay().getPoint());

        double t = alignZero(v.dotProduct(qp));

        // if t == 0 v is orthogonal to qp so return qp
        if (t == 0 || Util.isZero(t - _height))
            return getAxisRay().getDir();

        Point3D o = this.getAxisRay().getPoint().add(v.scale(t));
        return point.subtract(o).normalize();
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray,double max) {
        Point3D centerP = _axisRay.getPoint();
        Vector cylinderDir = _axisRay.getDir();
        List<GeoPoint> intersectios = super.findIntersections(ray,max);
        List<GeoPoint> toReturn = null;
        // Check if there are intersections with the bottum of cylinder and/or the top
        // cylinder
        Plane buttomCap = new Plane(centerP, cylinderDir);
        Point3D pointAtTop = new Point3D(centerP.add(cylinderDir.scale(_height)));
        Plane topCap = new Plane(pointAtTop, cylinderDir);
        List<GeoPoint> intsB = buttomCap.findIntersections(ray,max);
        List<GeoPoint> intsT = topCap.findIntersections(ray,max);
        if (intsT != null) {
            GeoPoint topInter = intsT.get(0);
            double d = Util.alignZero(topInter._point.distance(pointAtTop) - _radius);
            if (d < 0) {
                // intersect the top
                if (toReturn == null)
                    toReturn = new LinkedList<GeoPoint>();
                topInter._geometry = this;
                toReturn.add(topInter);
            }
        }
        if (intsB != null) {
            GeoPoint bInter = intsB.get(0);
            double d = Util.alignZero(bInter._point.distance(centerP) - _radius);
            if (d < 0) {
                // intersect the buttom
                if (toReturn == null)
                    toReturn = new LinkedList<GeoPoint>();
                bInter._geometry = this;
                toReturn.add(bInter);
            }
        }
        if (toReturn != null && toReturn.size() == 2) // The maximum intersection points are 2
            return toReturn;
        if (intersectios == null) {
            return toReturn;
        }
        // In this point We knows that we got minimum 1 intersection point from the
        // tube.
        // check if intersection point(s) of tube relevant also for the cylinder
        GeoPoint gPoint = intersectios.get(0);
        gPoint._geometry = this;
        intsT = topCap.findIntersections(new Ray(gPoint._point, cylinderDir),max);
        intsB = buttomCap.findIntersections(new Ray(gPoint._point, cylinderDir.scale(-1)),max);
        if (intsT != null && intsB != null) {
            if (toReturn == null)
                toReturn = new LinkedList<GeoPoint>();
            toReturn.add(gPoint);
        }
        if (intersectios.size() == 2) {
            gPoint = intersectios.get(1);
            gPoint._geometry = this;
            intsT = topCap.findIntersections(new Ray(gPoint._point, cylinderDir),max);
            intsB = buttomCap.findIntersections(new Ray(gPoint._point, cylinderDir.scale(-1)),max);
            if (intsT != null && intsB != null) {
                if (toReturn == null)
                    toReturn = new LinkedList<GeoPoint>();
                toReturn.add(gPoint);
            }
        }
        return toReturn;
    }
}
