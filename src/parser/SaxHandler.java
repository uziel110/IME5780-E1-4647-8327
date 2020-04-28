package parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import primitives.Color;
import scene.Scene;

public class SaxHandler extends DefaultHandler {

    boolean bAge = false;
    boolean bName = false;
    boolean bGender = false;
    boolean bRole = false;

    // List to hold Scene objects
    private Scene _scene = null;
    private StringBuilder _data = null;

    // getter method for employee list
    public Scene getScene() {
        return _scene;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (qName.equalsIgnoreCase("Scene")) {
            // create a new Scene and put it in Map
            String backgroundColor = attributes.getValue("background-color");
            String[] RGB = backgroundColor.split(" ");

            String distance = attributes.getValue("screen-distance");

            // initialize Employee object and set id attribute
            _scene = new Scene("scene import from XML");
            _scene.setBackground(new Color(Integer.parseInt(RGB[0]), Integer.parseInt(RGB[1]), Integer.parseInt(RGB[2])));
            _scene.setDistance(Integer.parseInt(distance));

        } else if (qName.equalsIgnoreCase("name")) {
            // set boolean values for fields, will be used in setting Employee variables
            bName = true;
        } else if (qName.equalsIgnoreCase("age")) {
            bAge = true;
        } else if (qName.equalsIgnoreCase("gender")) {
            bGender = true;
        } else if (qName.equalsIgnoreCase("role")) {
            bRole = true;
        }
        // create the data container
        _data = new StringBuilder();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        _data.append(new String(ch, start, length));
    }
}
