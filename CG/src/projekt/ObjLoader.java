package projekt;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ObjLoader {
    float[] vertexArray;
    float[] textureArray;
    float[] normalArray;

    public ObjLoader(String filePath) throws IOException {
        ArrayList<Float> vertices = new ArrayList<>();
        ArrayList<Float> textures = new ArrayList<>();
        ArrayList<Float> normals = new ArrayList<>();
        ArrayList<String> faces = new ArrayList<>();

        File myObj = new File(filePath);
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            String[] currentLine = data.split(" ");
            if (data.startsWith("v ")) {
                for (int i = 1; i <= 3; i++)
                    vertices.add(Float.parseFloat(currentLine[i]));
            }
            else if (data.startsWith("vt ")) {
                for (int i = 1; i <= 2; i++)
                    textures.add(Float.parseFloat(currentLine[i]));
            }
            else if (data.startsWith("vn ")) {
                for (int i = 1; i <= 3; i++)
                    normals.add(Float.parseFloat(currentLine[i]));
            }
            else if (data.startsWith("f ")) {
                for (int i = 1; i <= 3; i++)
                    faces.add(currentLine[i]);
            }
        }

        vertexArray = new float[faces.size()*3];
        textureArray = new float[faces.size()*2];
        normalArray = new float[faces.size()*3];

        for (int i=0; i<faces.size(); i++) {
            String[] numberStrs = faces.get(i).split("/");
            int[] currentLine = new int[numberStrs.length];
            for(int j=0; j<numberStrs.length; j++)
                currentLine[j] = Integer.parseInt(numberStrs[j]);

            for (int j=0; j<3; j++) {
                vertexArray[3*i+j]= vertices.get(3*(currentLine[0]-1)+j);
                normalArray[3*i+j]= normals.get(3*(currentLine[2]-1)+j);
            }
            for (int j=0; j<2; j++) {
                textureArray[2*i+j]= textures.get(2*(currentLine[1]-1)+j);
            }
        }
    }

}
