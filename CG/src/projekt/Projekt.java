package projekt;

import static org.lwjgl.opengl.GL30.*;

import lenz.opengl.AbstractOpenGLBase;
import lenz.opengl.ShaderProgram;
import lenz.opengl.Texture;

import java.io.IOException;

public class Projekt extends AbstractOpenGLBase {

	private int vaoCube;
	private int vaoTetr;
	private int vaoObj;

	private int numTetrEdgePoints;
	private int numCubeEdgePoints;
	private int numObjEdgePoints;

	private ShaderProgram tetrShaderProgram;
	private ShaderProgram cubeShaderProgram;
	private ShaderProgram loadedObjShaderProgram;

	Matrix4 cubeTransformation;
	Matrix4 tetrahedronTransformation;
	Matrix4 loadedObjTransformation;

	Texture sparkleTexture;



	public static void main(String[] args) {
		new Projekt().start("CG Projekt", 700, 700);
	}

	@Override
	protected void init() {
		tetrShaderProgram = new ShaderProgram("projectTetrahedron");
		glUseProgram(tetrShaderProgram.getId());

		cubeShaderProgram = new ShaderProgram("projectCube");
		glUseProgram(cubeShaderProgram.getId());

		loadedObjShaderProgram = new ShaderProgram("projectLoadedObj");
		glUseProgram(loadedObjShaderProgram.getId());

		sparkleTexture = new Texture("sparkles.png");


		Matrix4 projMat = new Matrix4(1.0f,10.0f);
		tetrahedron(projMat);
		cube(projMat);
		object(projMat);

		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
	}

	private void tetrahedron (Matrix4 projMat) {
		float[] tetrahedronCoordinates = new float[] {

				0.0f,1.0f,0.0f, 1.0f,0.0f,0.0f, 0.0f,0.0f,0.0f,

				1.0f,0.0f,0.0f, 0.0f,0.0f,1.0f, 0.0f,0.0f,0.0f,

				0.0f,0.0f,1.0f, 0.0f,1.0f,0.0f, 0.0f,0.0f,0.0f,

				0.0f,1.0f,0.0f, 0.0f,0.0f,1.0f, 1.0f,0.0f,0.0f

		};
		float[] tetrahedronColor = new float[] {
				0.5f,1.0f,1.0f, 1.0f,0.5f,1.0f, 1.0f,1.0f,0.5f,

				0.5f,1.0f,1.0f, 1.0f,0.5f,1.0f, 1.0f,1.0f,0.5f,

				0.5f,1.0f,1.0f, 1.0f,0.5f,1.0f, 1.0f,1.0f,0.5f,

				0.5f,1.0f,1.0f, 1.0f,0.5f,1.0f, 1.0f,1.0f,0.5f
		};


		float[] tetrahedronNormal = new float[] {
				0.0f, 0.0f, -1.0f, 	0.0f, 0.0f, 0.0f, 	0.0f, 0.0f, 0.0f,
				0.0f, -1.0f, 0.0f, 	0.0f, 0.0f, 0.0f, 	0.0f, 0.0f, 0.0f,
				-1.0f, 0.0f, 0.0f, 	0.0f, 0.0f, 0.0f, 	0.0f, 0.0f, 0.0f,
				1.0f, 0.0f, 0.0f, 	0.0f, 1.0f, 0.0f, 	0.0f, 0.0f, 1.0f
		};

		numTetrEdgePoints = (tetrahedronCoordinates.length)/3;
		glUseProgram(tetrShaderProgram.getId());
		//Projektionsmatrix an shader übertragen und das multiplizieren im shader machen


		int loc = glGetUniformLocation(tetrShaderProgram.getId(),"projektionsMatrix");
		glUniformMatrix4fv(loc,false,projMat.getValuesAsArray());

		//TODO Koordinaten, VAO, VBO, ... hier anlegen und im Grafikspeicher ablegen
		vaoTetr = glGenVertexArrays();
		glBindVertexArray(vaoTetr);
		Buffers(tetrahedronCoordinates,0,3);
		Buffers(tetrahedronColor,1,3);
		Buffers(tetrahedronNormal,2,3);
	}
	private void cube (Matrix4 projMat) {
		float c=1.0f;
		float[] cubeCoordinates = new float[] {
				//x,y,z
				-c, c, c,
				-c,-c, c,
				c,-c, c, //11 front

				c, c, c,
				-c, c, c,
				c,-c, c, //12 front

				c, c, c,
				c, c,-c,
				-c, c,-c, //7 top

				c, c, c,
				-c, c,-c,
				-c, c, c, //8 top

				c, c,-c,
				-c,-c,-c,
				-c, c,-c, //6 back

				c, c,-c,
				c,-c,-c,
				-c,-c,-c, //5 back

				c,-c, c,
				-c,-c,-c,
				c,-c,-c, //3 bottom

				c,-c, c,
				-c,-c, c,
				-c,-c,-c, //4 bottom

				-c,-c,-c,
				-c,-c, c,
				-c, c, c, //1 left

				-c,-c,-c,
				-c, c, c,
				-c, c,-c, //2 left

				c, c, c,
				c,-c,-c,
				c, c,-c, //9 right

				c,-c,-c,
				c, c, c,
				c,-c, c, //10 right



		};

		float [] cubeColours = new float[] {
				0.0f,  1.0f,  0.19f,
				0.0f,  1.0f,  0.19f, //green 11 front
				0.0f,  1.0f,  0.19f,

				0.0f,  1.0f,  0.19f,
				0.0f,  1.0f,  0.19f, //green 12 front
				0.0f,  1.0f,  0.19f,

				0.0f,  1.0f,  0.89f, //lightblue 7 top
				0.0f,  1.0f,  0.89f,
				0.0f,  1.0f,  0.89f,

				0.0f,  1.0f,  0.89f, //lightblue 8 top
				0.0f,  1.0f,  0.89f,
				0.0f,  1.0f,  0.89f,

				0.0f,  0.69f,  1.0f, //skyblue 5 back
				0.0f,  0.69f,  1.0f,
				0.0f,  0.69f,  1.0f,

				0.0f,  0.69f,  1.0f, //skyblue 6 back
				0.0f,  0.69f,  1.0f,
				0.0f,  0.69f,  1.0f,

				0.0f,  0.33f,  1.0f, //blue 3 bottom
				0.0f,  0.33f,  1.0f,
				0.0f,  0.33f,  1.0f,

				0.0f,  0.33f,  1.0f, //blue 4 bottom
				0.0f,  0.33f,  1.0f,
				0.0f,  0.33f,  1.0f,

				0.02f,  0.0f,  1.0f, //darkblue 1 left
				0.02f,  0.0f,  1.0f,
				0.02f,  0.0f,  1.0f,

				0.02f,  0.0f,  1.0f, //darkblue 2 left
				0.02f,  0.0f,  1.0f,
				0.02f,  0.0f,  1.0f,

				0.0f,  1.0f,  0.61f, //turquoise 9 right
				0.0f,  1.0f,  0.61f,
				0.0f,  1.0f,  0.61f,

				0.0f,  1.0f,  0.61f, //turquoise 10 right
				0.0f,  1.0f,  0.61f,
				0.0f,  1.0f,  0.61f,


		};

//normals
		float[] cubeNormal = new float[] {
				//front
				0.0f,0.0f,1.0f,
				0.0f,0.0f,1.0f,
				0.0f,0.0f,1.0f,

				0.0f,0.0f,1.0f,
				0.0f,0.0f,1.0f,
				0.0f,0.0f,1.0f,

				//top
				0.0f,1.0f,0.0f,
				0.0f,1.0f,0.0f,
				0.0f,1.0f,0.0f,

				0.0f,1.0f,0.0f,
				0.0f,1.0f,0.0f,
				0.0f,1.0f,0.0f,

				//back
				0.0f,0.0f,-1.0f,
				0.0f,0.0f,-1.0f,
				0.0f,0.0f,-1.0f,

				0.0f,0.0f,-1.0f,
				0.0f,0.0f,-1.0f,
				0.0f,0.0f,-1.0f,

				//bottom
				0.0f,-1.0f,0.0f,
				0.0f,-1.0f,0.0f,
				0.0f,-1.0f,0.0f,

				0.0f,-1.0f,0.0f,
				0.0f,-1.0f,0.0f,
				0.0f,-1.0f,0.0f,

				//left
				-1.0f, 0.0f, 0.0f,
				-1.0f, 0.0f, 0.0f,
				-1.0f, 0.0f, 0.0f,

				-1.0f, 0.0f, 0.0f,
				-1.0f, 0.0f, 0.0f,
				-1.0f, 0.0f, 0.0f,

				//right
				1.0f,0.0f,0.0f,
				1.0f,0.0f,0.0f,
				1.0f,0.0f,0.0f,

				1.0f,0.0f,0.0f,
				1.0f,0.0f,0.0f,
				1.0f,0.0f,0.0f


		};

		float[] cubeTexcoords= new float[] {

				0.0f, 0.34f, 0.0f, 0.667f,  0.25f, 0.667f,
				0.25f, 0.34f, 0.0f, 0.34f,  0.25f, 0.667f,

				0.25f, 0.34f, 0.5f, 0.34f,0.5f, 0.0f,
				0.25f, 0.34f, 0.5f, 0.0f, 0.25f, 0.0f,

				0.5f, 0.34f, 0.75f, 0.667f, 0.75f, 0.34f,
				0.5f, 0.34f, 0.5f, 0.667f,  0.75f, 0.667f,

				0.25f, 0.667f, 0.5f, 1.0f, 0.5f, 0.667f,
				0.25f, 0.667f, 0.25f, 1.0f, 0.5f, 1.0f,

				0.75f, 0.667f, 1.0f, 0.667f, 1.0f, 0.34f,
				0.75f, 0.667f, 1.0f, 0.34f, 0.75f, 0.34f,

				0.25f, 0.34f, 0.5f, 0.667f, 0.5f, 0.34f,
				0.5f, 0.667f, 0.25f, 0.34f, 0.25f, 0.667f,


		};

		numCubeEdgePoints = (cubeCoordinates.length)/3;
		glUseProgram(cubeShaderProgram.getId());
		int location = glGetUniformLocation(cubeShaderProgram.getId(),"projectMatrix");
		glUniformMatrix4fv(location,false,projMat.getValuesAsArray());

		vaoCube = glGenVertexArrays();
		glBindVertexArray(vaoCube);
		Buffers(cubeCoordinates,0,3);
		Buffers(cubeColours,1,3);
		Buffers(cubeNormal,2,3);
		Buffers(cubeTexcoords,3,2);
	}
	private void object (Matrix4 projMat) {
		ObjLoader loadedObject = null;
		try {
			loadedObject = new ObjLoader("src\\res\\rupee.obj");
		} catch (IOException e) {
			e.printStackTrace();
		}


		assert loadedObject != null;
		float[] objCoordinates = loadedObject.vertexArray;
		float[] objColours = new float[objCoordinates.length];
		float[] objNormals = loadedObject.normalArray;
		float[] objTexture = loadedObject.textureArray;

		numObjEdgePoints = (objCoordinates.length)/3;
		glUseProgram(loadedObjShaderProgram.getId());
		//Projektionsmatrix an shader übertragen und das multiplizieren im shader machen

		//z muss zwischen -d und -f liegen
		int loca = glGetUniformLocation(loadedObjShaderProgram.getId(),"projektionsMatrix");
		glUniformMatrix4fv(loca,false,projMat.getValuesAsArray());

		//TODO Koordinaten, VAO, VBO, ... hier anlegen und im Grafikspeicher ablegen
		vaoObj = glGenVertexArrays();
		glBindVertexArray(vaoObj);
		Buffers(objCoordinates,0,3);
		Buffers(objColours,1,3);
		Buffers(objNormals,2,3);
		Buffers(objTexture,3,2);
	}

	private void Buffers(float[] arrayName, int index, int size){
		int vboName = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER,vboName);
		glBufferData(GL_ARRAY_BUFFER,arrayName,GL_STATIC_DRAW);
		glVertexAttribPointer(index,size,GL_FLOAT,false,0,0);
		glEnableVertexAttribArray(index);
	}

	float winkel = 0.1f;
	float winkel2 = 0.2f;
	float scl = 1.0f;
	float sinScl=0.0f;

	@Override
	public void update() {
		winkel+= 0.01f;
		winkel2+=0.02f;

		cubeTransformation = new Matrix4();
		cubeTransformation.scale(0.8f);
		cubeTransformation.rotateX(0.5f).rotateY(winkel).rotateZ(winkel2);
		cubeTransformation.translate(0.0f,0,-8.0f);

		Matrix4 orbit = new Matrix4();
		orbit.rotateY(winkel);
		orbit.translate(0.0f,0,-8.0f);
		tetrahedronTransformation = new Matrix4();
		tetrahedronTransformation.scale(1.0f,1.3f,0.7f);
		tetrahedronTransformation.rotateX(winkel2*2).multiply(orbit).translate(2.0f,2.0f,0.0f).rotateZ(winkel);


		loadedObjTransformation = new Matrix4();
		loadedObjTransformation.scale(sinScl).rotateY(winkel).translate(0.0f,2.5f,-10.0f);
		sinScl= (float) Math.sin(scl);
		scl += 0.02f;
	}

	@Override
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		//TODO Matrix an Shader übertragen
		renderWithoutTexture(tetrShaderProgram,tetrahedronTransformation,vaoTetr,numTetrEdgePoints);

		renderWithoutTexture(cubeShaderProgram,cubeTransformation,vaoCube,numCubeEdgePoints);
		renderWithTexture(loadedObjShaderProgram,loadedObjTransformation,vaoObj,numObjEdgePoints,sparkleTexture);
	}

	private void renderWithoutTexture (ShaderProgram shader, Matrix4 transMat,int vao, int numEdgePoints) {
		glUseProgram(shader.getId());
		float[]matrixVal = transMat.getValuesAsArray();
		int loc = glGetUniformLocation(shader.getId(),"meineMatrix");
		glUniformMatrix4fv(loc,false,matrixVal);

		glBindVertexArray(vao);
		glDrawArrays(GL_TRIANGLES,0, numEdgePoints);
	}

	private void renderWithTexture (ShaderProgram shader, Matrix4 transMat,int vao, int EdgePoints, Texture texture) {
		glUseProgram(shader.getId());
		float[]matrixVal = transMat.getValuesAsArray();
		int loc = glGetUniformLocation(shader.getId(),"meineMatrix");
		glUniformMatrix4fv(loc,false,matrixVal);

		glBindVertexArray(vao);
		glBindTexture(GL_TEXTURE_2D,texture.getId());
		glDrawArrays(GL_TRIANGLES,0, EdgePoints);
	}

	@Override
	public void destroy() {
	}
}
