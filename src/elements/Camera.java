package elements;

import com.sun.source.tree.BreakTree;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Camera {
    private Point3D _location;
    private Vector _vTo, _vUp, _vRight;

    public Camera(Point3D location, Vector vTo, Vector vUp) {

        if (vTo.dotProduct(vUp) != 0)
            throw new IllegalArgumentException();

        _location = new Point3D(location);
        _vTo = vTo.normalized();
        _vUp = vUp.normalized();
        _vRight = vTo.crossProduct(vUp).normalize();
    }

    public Ray constructRayThroughPixel(int nX, int nY,
                                        int j, int i, double screenDistance,
                                        double screenWidth, double screenHeight) {
        Point3D pc = _location.add(_vTo.scale(screenDistance));
        double ry = screenHeight / nY;
        double rx = screenWidth / nX;

        Point3D pij = pc.add(_vRight.scale((j - ((nX - 1) / 2.0)) * rx).subtract(_vUp.scale((i - ((nY - 1) / 2.0))* ry)));
        Vector vij = pij.subtract(_location);

        return new Ray(_location,vij.normalized());
    }

    public Point3D getLocation() {
        return _location;
    }

    public Vector getvTo() {
        return _vTo;
    }

    public Vector getvUp() {
        return _vUp;
    }

    public Vector getvRight() {
        return _vRight;
    }
}