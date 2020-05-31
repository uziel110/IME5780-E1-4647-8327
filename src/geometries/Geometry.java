package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 * interface for geometries shapes
 */
public abstract class Geometry implements Intersectable {

    protected Color _emission;
    protected Material _material;

    // 3DDDA algorithm to improve rendering performance
    protected double _minX, _maxX, _minY, _maxY, _minZ, _maxZ;
    /**
     * constructor for Geometry class
     *
     * @param emission Color emission color of the geometry
     * @param material Material the material of the geometry
     */
    public Geometry(Color emission, Material material) {
        _emission = new Color(emission);
        _material = material;
    }

    /**
     * constructor for Geometry class
     * set material to (0,0,0)
     *
     * @param emission Color emission color of the geometry
     */
    public Geometry(Color emission) {
        this(emission, new Material(0, 0, 0));
    }

    /**
     * constructor for Geometry class
     * set material to (0,0,0)
     * set emission color to Black
     */
    public Geometry() {
        this(Color.BLACK, new Material(0, 0, 0));
    }

    /**
     * return emission light
     *
     * @return emission light Color
     */
    public Color getEmission() {
        return _emission;
    }

    /**
     * return material of the geometry
     *
     * @return material of the geometry
     */
    public Material getMaterial() {
        return _material;
    }

    /**
     * return normal to the geometry shape from the received point
     *
     * @param point point on the surface
     * @return normal to the geometry shape from the received point
     */
    public abstract Vector getNormal(Point3D point);
}
