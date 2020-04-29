package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * class that implements Camera
 */
public class Camera {
    private Point3D _location;
    private Vector _vTo, _vUp, _vRight;

    /**
     * constructor of Camera
     *
     * @param location Point3D on the camera
     * @param vTo      vector direction forward
     * @param vUp      vector direction up
     * @throws IllegalArgumentException if vTo and vUp aren't orthogonal
     */
    public Camera(Point3D location, Vector vTo, Vector vUp) {

        if (vTo.dotProduct(vUp) != 0)
            throw new IllegalArgumentException();

        _location = new Point3D(location);
        _vTo = vTo.normalized();
        _vUp = vUp.normalized();
        _vRight = vTo.crossProduct(vUp).normalize();
    }

    /**
     * return ray from point location on the camera and goes through a certain pixel on the screen
     *
     * @param nX             number of pixels in a row
     * @param nY             number of pixels in the column
     * @param j              pixel row index
     * @param i              pixel column index
     * @param screenDistance distance between location point and the screen
     * @param screenWidth    width of the screen
     * @param screenHeight   height of the screen
     * @return ray from point location on the camera and goes through a certain pixel on the screen
     */
    public Ray constructRayThroughPixel(int nX, int nY,
                                        int j, int i, double screenDistance,
                                        double screenWidth, double screenHeight) {
        Point3D pc = new Point3D(_location);
        if (!isZero(screenDistance))
            pc = _location.add(_vTo.scale(screenDistance));

        // to avoid zero view plane
        if (nY == 0 || nX == 0)
            return null;
        double ry = screenHeight / nY;
        double rx = screenWidth / nX;

        Point3D pij;
        boolean y = isZero(j - (nX - 1) / 2.0);
        boolean x = isZero(i - (nY - 1) / 2.0);
        if (y && x)
            pij = new Point3D(pc);
        else if (y)
            pij = pc.add((Point3D.ZERO).subtract((_vUp.scale((i - ((nY - 1) / 2.0)) * ry)).getEnd()));
        else if (x)
            pij = pc.add(_vRight.scale((j - ((nX - 1) / 2.0)) * rx));
        else
            pij = pc.add(_vRight.scale((j - ((nX - 1) / 2.0)) * rx).subtract(_vUp.scale((i - ((nY - 1) / 2.0)) * ry)));

        // pij is always not equal to _location
        Vector vij = pij.subtract(_location);

        return new Ray(_location, vij.normalized());
    }

    /**
     * return a point Location of the camera
     *
     * @return Point3D Location of the camera
     */
    public Point3D getLocation() {
        return _location;
    }

    /**
     * return a vector direction forward
     *
     * @return vector direction forward
     */
    public Vector getvTo() {
        return _vTo;
    }

    /**
     * return a vector direction up
     *
     * @return vector direction up
     */
    public Vector getvUp() {
        return _vUp;
    }

    /**
     * return a vector direction right
     *
     * @return vector direction right
     */
    public Vector getvRight() {
        return _vRight;
    }
}