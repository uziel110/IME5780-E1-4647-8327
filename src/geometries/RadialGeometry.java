package geometries;

import primitives.Point3D;
import primitives.Vector;

public abstract class RadialGeometry implements Geometry {
    protected double _radius;

    public RadialGeometry(double radius) {
        _radius = radius;
    }
    public RadialGeometry(RadialGeometry geometry) {
        _radius = geometry._radius;
    }

    public double get_radius() {
        return _radius;
    }
}
