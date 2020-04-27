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

    private List<Intersectable> _shapes;

    /**
     * default constructor of Geometries
     */
    public Geometries() {
        _shapes = new LinkedList<Intersectable>();
    }

    /**
     * constructor of Geometries that receive list of Intersctable
     *
     * @param geometries list of Intersctable
     */
    public Geometries(Intersectable... geometries) {
        _shapes = new LinkedList<Intersectable>();
        _shapes.addAll(Arrays.asList(geometries));
    }

    /**
     * add list of Intersctable to the list
     *
     * @param geometries list of Intersctable
     */
    public void add(Intersectable... geometries) {
        _shapes.addAll(Arrays.asList(geometries));
    }

    public List<Intersectable> getShapes() {
        return _shapes;
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> returnList = new LinkedList<Point3D>();
        List<Point3D> list;
        for (Intersectable shape : _shapes) {
            list = shape.findIntersections(ray);
            if (list != null)
                returnList.addAll(list);
        }
        if (returnList.size() == 0)
            return null;
        return returnList;
    }
}
