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
    private Point3D _location;
    private Vector _vTo, _vUp, _vRight;
    // parameters for depth of field effect
    private double _focalLenDistance = 500;
    private int _apertureSize = 250;
    private int _rayAmount = 50;

    /**
     * create a camera by spheroid parametrization
     * set default parameters for depth of field effect
     * focalLenDistance = 500
     * apertureSize = 250
     * rayAmount = 50
     *
     * @param sceneCenter the center off the scene to direct to it
     * @param r           the distance fro the center of the center
     * @param theta       rotation angle around z axis 0 to 2*pi
     * @param phi         rotation angle around z axis 0 to 2*pi
     * @param roll        rotation angle around camera direction vector (0 to 2*pi)
     */
    public Camera(Point3D sceneCenter, double r, double theta, double phi, double roll) {
        if (isZero(r)) // to avoid vector 0
            throw new IllegalArgumentException();
        Vector direction = new Vector(r * Math.sin(phi) * Math.cos(theta),
                r * Math.sin(phi) * Math.sin(theta),
                r * Math.cos(phi));
        _location = sceneCenter.add(direction);
        _vTo = direction.scale(-1).normalize();
        // calculation for roll the camera
        Vector tempUp;
        if (isZero(direction.getEnd().getX().get()))
            tempUp = direction.crossProduct(new Vector(0, direction.getEnd().getZ().get(),
                    -direction.getEnd().getY().get()).normalize());
        else if (isZero(direction.getEnd().getY().get()))
            tempUp = direction.crossProduct(new Vector(direction.getEnd().getZ().get(),
                    0, -direction.getEnd().getX().get()).normalize());
        else
            tempUp = direction.crossProduct(new Vector(direction.getEnd().getY().get(),
                    -direction.getEnd().getX().get(), 0)).normalize();

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
     * set default parameters for depth of field effect
     * focalLenDistance = 500
     * apertureSize = 250
     * rayAmount = 50
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
     * return ray from point location on the camera and goes through a certain pixel on the view plane
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

    /**
     * return list of rays from point pij on view plane and goes through focal point
     *
     * @param nX             number of pixels in a row
     * @param nY             number of pixels in the column
     * @param j              pixel row index
     * @param i              pixel column index
     * @param screenDistance distance between location point and the screen
     * @param screenWidth    width of the screen
     * @param screenHeight   height of the screen
     * @return list of rays from point pij on view plane and goes through focal point
     */
    public List<Ray> constructDOFRays(int nX, int nY,
                                      int j, int i, double screenDistance,
                                      double screenWidth, double screenHeight) {
        List<Ray> focalRays = new LinkedList<>();
        // find point on the VP
        Point3D pijVP = getPoint3DPij(nX, nY, j, i, screenDistance, screenWidth, screenHeight);
        // vector from camera to point on the VP
        Vector pijVPVector = pijVP.subtract(_location).normalized();
        // add the main ray start from the VP to the list
        focalRays.add(new Ray(pijVP, pijVPVector));
        // find point on the Focal plane
        Point3D focalPoint = pijVP.add(pijVPVector.scale(_focalLenDistance));
        // create _rayAmount vectors from VP to Focal Plane in uniform distribution

        Random random = new Random();
        int numRaysInWidth = (int) Math.sqrt(_rayAmount);
        double ry = alignZero((double) _apertureSize / numRaysInWidth);
        double rx = alignZero((double) _apertureSize / numRaysInWidth);
        for (int k = 0; k < _rayAmount; k++) {
            Point3D pijDOF = getHeadFocalRay(numRaysInWidth, random.nextInt(numRaysInWidth), random.nextInt(numRaysInWidth), rx, ry, pijVP);
            focalRays.add(new Ray(pijDOF, focalPoint.subtract(pijDOF)));
        }
        return focalRays;
    }

    /**
     * return point3D a certain pixel on the view plane
     *
     * @param nX             number of pixels in a row
     * @param nY             number of pixels in the column
     * @param j              pixel row index
     * @param i              pixel column index
     * @param screenDistance distance between location point and the screen
     * @param screenWidth    width of the screen
     * @param screenHeight   height of the screen
     * @return point3D a certain pixel on the view plane
     */
    private Point3D getPoint3DPij(int nX, int nY, int j, int i, double screenDistance, double screenWidth, double screenHeight) {
        double ry = alignZero(screenHeight / nY);
        double rx = alignZero(screenWidth / nX);

        double xj = alignZero((j - nX / 2.0) * rx + rx / 2.0);
        double yi = alignZero((i - nY / 2.0) * ry + ry / 2.0);

        // pij = pc - center of the view plane
        Point3D pij = _location.add(_vTo.scale(screenDistance));
        return movePoint(xj, yi, pij);
    }

    /**
     * return point3D - the starting point of ray from the view plane that goes through focal point
     * `
     *
     * @param numRaysInWidth points number in the width / height
     * @param j              pixel row index
     * @param i              pixel column index
     * @param rx             the ratio between screen width and points number in the width
     * @param ry             the ratio between screen height and points number in the height
     * @param pij            pixel on view plane
     * @return the starting point of tay from the view plane that goes through focal point
     */
    private Point3D getHeadFocalRay(int numRaysInWidth, int j, int i, double rx, double ry, Point3D pij) {
        double xj = alignZero((j - numRaysInWidth / 2.0) * rx + rx / 2.0);
        double yi = alignZero((i - numRaysInWidth / 2.0) * ry + ry / 2.0);

        Point3D pijMoved = new Point3D(pij);
        return movePoint(xj, yi, pijMoved);
    }

    /**
     * move point xj and yi units
     *
     * @param xj  how much to move to right
     * @param yi  how much to move to down
     * @param pij the point to move it
     * @return the point moved xj and yi units
     */
    private Point3D movePoint(double xj, double yi, Point3D pij) {
        if (xj != 0)
            pij = pij.add(_vRight.scale(xj));
        if (yi != 0)
            pij = pij.add(_vUp.scale(-yi));
        return pij;
    }

    /**
     * set to the camera the parameters of DepthOfField
     *
     * @param focalLenDistance distance between view plane and focal plane
     * @param apertureSize     size of the aperture
     * @param rayAmount        amount of rays
     */
    public void setDepthOfField(double focalLenDistance, int apertureSize, int rayAmount) {
        _focalLenDistance = focalLenDistance;
        _apertureSize = apertureSize;
        _rayAmount = rayAmount;
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

    /**
     * return distance between view plane and focal plane
     *
     * @return distance between view plane and focal plane
     */
    public double getFocalLenDistance() {
        return _focalLenDistance;
    }

    /**
     * return size of the aperture
     *
     * @return size of the aperture
     */
    public int getApertureSize() {
        return _apertureSize;
    }

    /**
     * return the number of rays exiting the aperture
     *
     * @return number of rays exiting the aperture
     */
    public int getRayAmount() {
        return _rayAmount;
    }

}