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
     * ctor
     *
     * @param sceneXmlFileName
     */
    public SaxHandler(String sceneXmlFileName) {
        _sceneXmlFileName = sceneXmlFileName;
        parseDocument();
    }

    public Render getRender() {
        return render;
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
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        double[] doubleArr;
        int[] intArr;
        /*if (qName.equalsIgnoreCase("Scene")) {

            _scene = new Scene("scene import from XML");
            intArr = getIntArray(attributes, "background-color");

            _scene.setBackground(new Color(intArr[0], intArr[1], intArr[2]));
            String distance = attributes.getValue("screen-distance");
            _scene.setDistance(Integer.parseInt(distance));
        }*/

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

       /* if (qName.equalsIgnoreCase("ambient-light")) {

            intArr = getIntArray(attributes, "color");
            _scene.setAmbientLight(new AmbientLight(new Color(intArr[0], intArr[1], intArr[2]), 1));
        }*/

        if (qName.equalsIgnoreCase("ambient-light")) {

            doubleArr = getDoubleArray(attributes, "color");
            _scene.setAmbientLight(new AmbientLight(new Color(doubleArr[0], doubleArr[1], doubleArr[2]), 1));
        }

        if (qName.equalsIgnoreCase("sphere")) {

            doubleArr = getDoubleArray(attributes, "center");

            double radius = Double.parseDouble(attributes.getValue("radius"));
            _scene.addGeometries(new Sphere(radius, new Point3D(doubleArr[0], doubleArr[1], doubleArr[2])));

        }
        if (qName.equalsIgnoreCase("triangle")) {

            doubleArr = getDoubleArray(attributes, "p0");
            Point3D p0 = new Point3D(doubleArr[0], doubleArr[1], doubleArr[2]);

            doubleArr = getDoubleArray(attributes, "p1");
            Point3D p1 = new Point3D(doubleArr[0], doubleArr[1], doubleArr[2]);

            doubleArr = getDoubleArray(attributes, "p2");
            Point3D p2 = new Point3D(doubleArr[0], doubleArr[1], doubleArr[2]);

            _scene.addGeometries(new Triangle(p0, p1, p2));
        }

        if (qName.equalsIgnoreCase("plane")) {
            doubleArr = getDoubleArray(attributes, "p0");
            Point3D p0 = new Point3D(doubleArr[0], doubleArr[1], doubleArr[2]);

            doubleArr = getDoubleArray(attributes, "p1");
            Point3D p1 = new Point3D(doubleArr[0], doubleArr[1], doubleArr[2]);

            doubleArr = getDoubleArray(attributes, "p2");
            Point3D p2 = new Point3D(doubleArr[0], doubleArr[1], doubleArr[2]);

            _scene.addGeometries(new Plane(p0, p1, p2));
        }

        /**/
        if (qName.equalsIgnoreCase("image")) {

            double screenWidth = Double.parseDouble(attributes.getValue("screen-width"));
            double screenHeight = Double.parseDouble(attributes.getValue("screen-height"));
            int nX = Integer.parseInt(attributes.getValue("Nx"));
            int nY = Integer.parseInt(attributes.getValue("Ny"));
            image = new ImageWriter("image from XML",
                    screenWidth, screenHeight, nX, nY);

            //_scene.set
        }
        /**/
        // create the data container
        // _data = new StringBuilder();
    }

    private double[] getDoubleArray(Attributes attributes, String str) {
        String num = attributes.getValue(str);
        String[] number = num.split(" ");
        return new double[]{Double.parseDouble(number[0]), Double.parseDouble(number[1]), Double.parseDouble(number[2])};
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("scene")) {
            render = new Render(image, _scene);
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        tmpValue = new String(ch, start, length);
        //_data.append(new String(ch, start, length));
    }
}