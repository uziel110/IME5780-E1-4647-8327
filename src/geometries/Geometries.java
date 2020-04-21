package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * class that implements collection of shapes
 */
public class Geometries implements Intersectable {
    private List<Intersectable> intersections;

    /**
     * default constructor of Geometries
     */
    public Geometries() {
        intersections = new LinkedList<Intersectable>();
    }

    /**
     * constructor of Geometries that receive list of Intersctable
     *
     * @param geometries list of Intersctable
     */
    public Geometries(Intersectable... geometries) {
        intersections = new LinkedList<Intersectable>();
        intersections.addAll(Arrays.asList(geometries));
    }

    /**
     * add list of Intersctable to the list
     *
     * @param geometries list of Intersctable
     */
    public void add(Intersectable... geometries) {
        ;
    }


    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> returnList = new LinkedList<Point3D>();
        List<Point3D> list;
        for (Intersectable shape : intersections) {
            list = shape.findIntersections(ray);
            if (list != null)
                returnList.addAll(list);
        }
        if (returnList.size() == 0)
            return null;
        return returnList;
    }
}
