package parser;

import geometries.Geometry;
import geometries.Triangle;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class TextReader {
    public static Geometry[] readOff(String fileName, double scale) {

        Scanner myReader = getScanner(fileName);

        //reads the name of oxtention
        String name = myReader.nextLine();
        //System.out.println("Name: " + name);

        //Gets num of vertices and num on faces
        String docInformations = myReader.nextLine();
        String[] informationData = docInformations.split(" ");
        int nv = parseInt(informationData[0]),
                nf = parseInt(informationData[1]);

        //Gets all points (vertices)
        String pointData;
        double[] pointNums = new double[3];
        Point3D[] points = new Point3D[nv];

        for (int i = 0; i < nv; i++) {
            pointData = myReader.nextLine();
            int index = 0;
            for (String text : pointData.split(" ")) {
                pointNums[index] = (parseDouble(text));
                index++;
            }
            points[i] = new Point3D(pointNums[0] * scale, pointNums[1] * scale, pointNums[2] * scale);
            // System.out.println(i + ": " + points[i]);
        }
        List<Geometry> geometries = new LinkedList<>();

        String geometryData;
        int[] geometryDataNums;

        for (int i = 0; i < nf; i++) {

            geometryData = myReader.nextLine();

            geometryDataNums = new int[parseInt(geometryData.split(" ")[0]) + 1];
            System.out.println();
            int index = 0;
            for (String text : geometryData.split(" ")) {
                geometryDataNums[index] = (parseInt(text));
                index++;
            }

            Point3D[] geometryPoints = new Point3D[geometryDataNums[0]];
            for (int j = 1; j <= geometryDataNums[0]; j++) {
                geometryPoints[j - 1] = (points[geometryDataNums[j]]);//gets the point on the position on teh array
            }
            if (geometryDataNums[0] == 3) {
                geometries.add(new Triangle(
                                new Color(109, 82, 16),
                                new Material(0.3, 0.7, 50, 0, 0),
                                geometryPoints[0],
                                geometryPoints[1],
                                geometryPoints[2]
                        )
                );
            } else if (geometryDataNums[0] == 4){
//                System.out.println(
//                        geometryDataNums[1] + " "
//                        + geometryDataNums[2]+ " "
//                        + geometryDataNums[3]+ " "
//                        + geometryDataNums[4]);
//                System.out.println(
//                        geometryPoints[0] + " "
//                        + geometryPoints[1]+ " "
//                        + geometryPoints[2]+ " "
//                        + geometryPoints[3]);
                geometries.add(new Triangle(
                        new Color(109, 82, 16),
                        new Material(0.3, 0.7, 50, 0, 0),
                        geometryPoints[0],
                        geometryPoints[1],
                        geometryPoints[2]
                ));
                geometries.add(new Triangle(
                                new Color(109, 82, 16),
                                new Material(0.3, 0.7, 50, 0, 0),
                                geometryPoints[0],
                                geometryPoints[2],
                                geometryPoints[3]
                        )
                );
            }

//            System.out.println( new Polygon(geometryPoints).);

        }

        myReader.close();
        return geometries.toArray(Geometry[]::new);
    }

    static Scanner getScanner(String fileName) {
        try {
            File myObj = new File("./off/" + fileName + ".off");
            return new Scanner(myObj);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }
}