package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class that implements Camera
 */
public class Camera {
    Boolean _depthOfFieldEnabled;
    private Point3D _location;
    private Vector _vTo, _vUp, _vRight;
    private double _focalLenDistance;
    private int _apertureSize;
    private int _rayAmount;
    /**
     * create a camera by spheroid parametrization
     *
     * @param sceneCenter the center off the scene to direct to it
     * @param r           the distance fro the center of the center
     * @param theta       rotation angle around z axis 0 to 2*pi
     * @param phi         rotation angle around z axis 0 to 2*pi
     * @param roll
     */
    public Camera(Point3D sceneCenter, double r, double theta, double phi, double roll) {
        Vector direction = new Vector(r * Math.sin(phi) * Math.cos(theta),
                r * Math.sin(phi) * Math.sin(theta),
                r * Math.cos(phi));
        _location = sceneCenter.add(direction);
        _vTo = direction.scale(-1).normalize();
        Vector tempUp = direction.crossProduct(new Vector(direction.getEnd().getY().get(),
                -direction.getEnd().getX().get(), 0).normalize());
        Vector tempRight = _vTo.crossProduct(tempUp).normalize();
        double x1 = Math.cos(roll) / tempUp.length();
        double x2 = Math.sin(roll) / tempRight.length();
        if (!isZero(x1))
            tempUp = tempUp.scale(x1);
        if (!isZero(x2))
            tempUp = tempUp.add(tempRight.scale(x2));
        _vUp = tempUp.scale(tempUp.length());
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

    public void setDepthOfField(double focalLenDistance, int apertureSize, int rayAmount) {
        _apertureSize = apertureSize;
        _focalLenDistance = focalLenDistance;
        _rayAmount = rayAmount;
    }

    public Boolean getDepthOfFieldState() {
        return _depthOfFieldEnabled;
    }

    public void setDepthOfFieldEnabled() {
        _depthOfFieldEnabled = true;
    }

    public void setDepthOfFieldDisabled() {
        _depthOfFieldEnabled = false;
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
        Point3D pij = getPoint3DPij(nX, nY, j, i, screenDistance, screenWidth, screenHeight);
        // pij is always not equal to _location
        return new Ray(_location, pij.subtract(_location).normalized());
    }

    private Point3D getPoint3DPij(int nX, int nY, int j, int i, double screenDistance, double screenWidth, double screenHeight) {
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
        return pij;
    }

    public List<Ray> constructDepthOfFieldRays(int nX, int nY,
                                               int j, int i, double screenDistance,
                                               double screenWidth, double screenHeight) {
        Point3D pij = getPoint3DPij(nX, nY, j, i, screenDistance, screenWidth, screenHeight);
        Vector pijVector = pij.subtract(_location).normalized();
        Point3D focalPoint = pij.add(pijVector.scale(_focalLenDistance));
        List<Ray> focalRays = new LinkedList<>();
        Random random = new Random();
        int numRaysInWidth = (int) Math.sqrt(_rayAmount);
        for (int k = 0; k < _rayAmount; k++) {
            Point3D pijMoved = getHeadFocalRay(pij, random.nextInt(numRaysInWidth), random.nextInt(numRaysInWidth));
            focalRays.add(new Ray(pijMoved, focalPoint.subtract(pijMoved)));
        }
        // pij is always not equal to _location
        return focalRays;
    }

    private Point3D getHeadFocalRay(Point3D pij, int j, int i) {
        int numRaysInWidth = (int) Math.sqrt(_rayAmount);
        int focalLenSize = (int) Math.sqrt(_apertureSize);
        double ry = focalLenSize / numRaysInWidth;
        double rx = focalLenSize / numRaysInWidth;

        double xj = (j - numRaysInWidth / 2.0) * rx + rx / 2.0;
        double yi = (i - numRaysInWidth / 2.0) * ry + ry / 2.0;

        Point3D pijMoved = new Point3D(pij);
        if (xj != 0)
            pijMoved = pijMoved.add(_vRight.scale(xj));
        if (yi != 0)
            pijMoved = pijMoved.add(_vUp.scale(-yi));
        return pijMoved;
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

    public double getFocalLenDistance() {
        return _focalLenDistance;
    }

    public int getApertureSize() {
        return _apertureSize;
    }

    public int getRayAmount() {
        return _rayAmount;
    }
}