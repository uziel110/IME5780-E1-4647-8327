package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class that implements Camera
 */
public class Camera {
    private Point3D _location;
    private Vector _vTo, _vUp, _vRight;

    /**
     * create a camera by spheroid parametrization
     * @param sceneCenter the center off the scene to direct to it
     * @param r the distance fro the center of the center
     * @param theta rotation angle around z axis 0 to 2*pi
     * @param phi rotation angle around z axis 0 to 2*pi
     * @param roll
     */
    public Camera(Point3D sceneCenter, double r, double theta, double phi, double roll) {
        Vector direction = new Vector(r * Math.sin(phi) * Math.cos(theta),
                r * Math.sin(phi) * Math.sin(theta),
                r * Math.cos(phi));
        _location = sceneCenter.add(direction);
        _vTo = direction.scale(-1).normalize();
        _vUp = direction.crossProduct(new Vector(direction.getEnd().getY().get(),
                -direction.getEnd().getX().get(), 0).normalize());
        _vRight = _vTo.crossProduct(_vUp).normalize();
        double x1 = Math.cos(roll) / _vUp.length();
        double x2 = Math.sin(roll) / _vRight.length();
        if (!isZero(x1))
            _vUp = _vUp.scale(x1);
        if (!isZero(x2))
            _vUp = _vUp.add(_vRight.scale(x2));
        _vUp = _vUp.scale(_vUp.length());
        _vRight = _vTo.crossProduct(_vUp).normalize();
    }

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