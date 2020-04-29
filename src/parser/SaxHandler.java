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
    public Render getRender() {
        return render;
    }

    String _sceneXmlFileName;
    String tmpValue;
    Scene _scene = null;

    /**
     * ctor
     * @param sceneXmlFileName
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
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (qName.equalsIgnoreCase("Scene")) {

            String backgroundColor = attributes.getValue("background-color");
            String[] RGB = backgroundColor.split(" ");
            String distance = attributes.getValue("screen-distance");

            _scene = new Scene("scene import from XML");
            _scene.setBackground(new Color(Integer.parseInt(RGB[0]), Integer.parseInt(RGB[1]), Integer.parseInt(RGB[2])));
            _scene.setDistance(Integer.parseInt(distance));
        }

        if (qName.equalsIgnoreCase("camera")) {

            String p0 = attributes.getValue("P0");
            String[] p0Det = p0.split(" ");
            Point3D point = new Point3D(Integer.parseInt(p0Det[0]), Integer.parseInt(p0Det[1]), Integer.parseInt(p0Det[2]));

            String vTo = attributes.getValue("Vto");
            String[] vToDet = vTo.split(" ");
            Vector vecTo = new Vector(Integer.parseInt(vToDet[0]), Integer.parseInt(vToDet[1]), Integer.parseInt(vToDet[2]));

            String vUp = attributes.getValue("Vup");
            String[] vUpDet = vUp.split(" ");
            Vector vecUp = new Vector(Integer.parseInt(vUpDet[0]), Integer.parseInt(vUpDet[1]), Integer.parseInt(vUpDet[2]));

            _scene.setCamera(new Camera(point, vecTo, vecUp));
        }

        if (qName.equalsIgnoreCase("ambient-light")) {
            String ambientLight = attributes.getValue("color");
            String[] RGB = ambientLight.split(" ");
            _scene.setAmbientLight(new AmbientLight(new Color(Integer.parseInt(RGB[0]), Integer.parseInt(RGB[1]), Integer.parseInt(RGB[2])), 1));
        }

        if (qName.equalsIgnoreCase("sphere")) {
            String center = attributes.getValue("center");
            String[] centerDet = center.split(" ");
            Double radius = Double.parseDouble(attributes.getValue("radius"));
            _scene.addGeometries(new Sphere(radius, new Point3D(Integer.parseInt(centerDet[0]),
                    Integer.parseInt(centerDet[1]), Integer.parseInt(centerDet[2]))));

        }
        if (qName.equalsIgnoreCase("triangle")) {
            String point0 = attributes.getValue("p0");
            String[] p0Det = point0.split(" ");
            Point3D p0 = (new Point3D(Integer.parseInt(p0Det[0]), Integer.parseInt(p0Det[1]), Integer.parseInt(p0Det[2])));

            String point1 = attributes.getValue("p1");
            String[] p1Det = point1.split(" ");
            Point3D p1 = (new Point3D(Integer.parseInt(p1Det[0]), Integer.parseInt(p1Det[1]), Integer.parseInt(p1Det[2])));

            String point2 = attributes.getValue("p2");
            String[] p2Det = point2.split(" ");
            Point3D p2 = (new Point3D(Integer.parseInt(p2Det[0]), Integer.parseInt(p2Det[1]), Integer.parseInt(p2Det[2])));

            _scene.addGeometries(new Triangle(p0, p1, p2));
        }

        if (qName.equalsIgnoreCase("plane")) {
            String point0 = attributes.getValue("p0");
            String[] p0Det = point0.split(" ");
            Point3D p0 = (new Point3D(Integer.parseInt(p0Det[0]), Integer.parseInt(p0Det[1]), Integer.parseInt(p0Det[2])));

            String point1 = attributes.getValue("p1");
            String[] p1Det = point1.split(" ");
            Point3D p1 = (new Point3D(Integer.parseInt(p1Det[0]), Integer.parseInt(p1Det[1]), Integer.parseInt(p1Det[2])));

            String point2 = attributes.getValue("p2");
            String[] p2Det = point2.split(" ");
            Point3D p2 = (new Point3D(Integer.parseInt(p2Det[0]), Integer.parseInt(p2Det[1]), Integer.parseInt(p2Det[2])));

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

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("scene")) {
            render = new Render(image,_scene);
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        tmpValue = new String(ch, start, length);
        //_data.append(new String(ch, start, length));
    }
}
