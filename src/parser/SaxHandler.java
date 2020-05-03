package parser;

import elements.AmbientLight;
import elements.Camera;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class SaxHandler extends DefaultHandler {

    Render render = null;
    ImageWriter image = null;
    String _sceneXmlFileName;
    String tmpValue;
    Scene _scene = null;

    /**
     * constructor of sceneXmlFileName that receive a file name and create scene from it
     *
     * @param sceneXmlFileName the name of the XML file
     */
    public SaxHandler(String sceneXmlFileName) {
        _sceneXmlFileName = sceneXmlFileName;
        parseDocument();
    }

    private void parseDocument() {
        // parse
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(_sceneXmlFileName, this);
        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfig error");
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        } catch (IOException e) {
            System.out.println("IO error");
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        double[] doubleArr;
        try {

            if (qName.equalsIgnoreCase("Scene")) {

                _scene = new Scene("scene import from XML");
                doubleArr = getDoubleArray(attributes, "background-color");

                _scene.setBackground(new Color(doubleArr[0], doubleArr[1], doubleArr[2]));
                String distance = attributes.getValue("screen-distance");
                _scene.setDistance(Integer.parseInt(distance));
            }

            if (qName.equalsIgnoreCase("camera")) {

                doubleArr = getDoubleArray(attributes, "P0");
                Point3D point = new Point3D(doubleArr[0], doubleArr[1], doubleArr[2]);

                doubleArr = getDoubleArray(attributes, "Vto");
                Vector vecTo = new Vector(doubleArr[0], doubleArr[1], doubleArr[2]);

                doubleArr = getDoubleArray(attributes, "Vup");
                Vector vecUp = new Vector(doubleArr[0], doubleArr[1], doubleArr[2]);

                _scene.setCamera(new Camera(point, vecTo, vecUp));
            }

            if (qName.equalsIgnoreCase("ambient-light")) {

                doubleArr = getDoubleArray(attributes, "color");
                _scene.setAmbientLight(new AmbientLight(new Color(doubleArr[0], doubleArr[1], doubleArr[2]), 1));
            }

            if (qName.equalsIgnoreCase("sphere")) {

                double radius = Double.parseDouble(attributes.getValue("radius"));
                _scene.addGeometries(new Sphere(radius, getPointFromStr(attributes, "center")));

            }
            if (qName.equalsIgnoreCase("triangle")) {

                Point3D p0 = getPointFromStr(attributes, "p0");
                Point3D p1 = getPointFromStr(attributes, "p1");
                Point3D p2 = getPointFromStr(attributes, "p2");

                _scene.addGeometries(new Triangle(p0, p1, p2));
            }

            if (qName.equalsIgnoreCase("plane")) {

                Point3D p0 = getPointFromStr(attributes, "p0");
                Point3D p1 = getPointFromStr(attributes, "p1");
                Point3D p2 = getPointFromStr(attributes, "p2");

                _scene.addGeometries(new Plane(p0, p1, p2));
            }

            if (qName.equalsIgnoreCase("image")) {
                double screenWidth, screenHeight;
                int nX, nY;
                screenWidth = Double.parseDouble(attributes.getValue("screen-width"));
                screenHeight = Double.parseDouble(attributes.getValue("screen-height"));
                nX = Integer.parseInt(attributes.getValue("Nx"));
                nY = Integer.parseInt(attributes.getValue("Ny"));
                image = new ImageWriter("image from XML",
                        screenWidth, screenHeight, nX, nY);
            }
        } catch (NumberFormatException e) {
            System.out.println(e);
            return;
        }
    }

    public Render getRender() {
        return render;
    }

    private double[] getDoubleArray(Attributes attributes, String str) {
        String num = attributes.getValue(str);
        String[] number = num.split(" ");
        double[] doubles = new double[]{Double.parseDouble(number[0]),
                Double.parseDouble(number[1]),
                Double.parseDouble(number[2])};
        return doubles;
    }

    private Point3D getPointFromStr(Attributes attributes, String str) {
        double[] number = getDoubleArray(attributes, str);
        return new Point3D((number[0]), (number[1]), (number[2]));
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equals("scene")) {
            render = new Render(image, _scene);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        tmpValue = new String(ch, start, length);
    }
}