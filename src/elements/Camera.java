package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.alignZero;

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

        if (alignZero(vTo.dotProduct(vUp)) != 0)
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
        double ry = screenHeight / nY;
        double rx = screenWidth / nX;

        double xj = (j - nX / 2.0) * rx + rx / 2.0;
        double yi = (i - nY / 2.0) * ry + ry / 2.0;

        // pij = pc - center of the view plane
        Point3D pij = _location.add(_vTo.scale(screenDistance));
        if (xj != 0)
            pij = pij.add(_vRight.scale(xj));
        if (yi != 0)
            pij = pij.add(_vUp.scale(-yi));

        // pij is always not equal to _location
        return new Ray(_location, pij.subtract(_location).normalized());
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
    public Vector getVTo() {
        return _vTo;
    }

    /**
     * return a vector direction up
     *
     * @return vector direction up
     */
    public Vector getVUp() {
        return _vUp;
    }

    /**
     * return a vector direction right
     *
     * @return vector direction right
     */
    public Vector getVRight() {
        return _vRight;
    }
}