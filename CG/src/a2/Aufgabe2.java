package a2;

import static org.lwjgl.opengl.GL30.*;

import lenz.opengl.AbstractOpenGLBase;
import lenz.opengl.ShaderProgram;

public class Aufgabe2 extends AbstractOpenGLBase {

	private int vaoId;
	private int vboId;
	private int colorId;
	private int anzEcken;

	public Aufgabe2() {
	}

	public static void main(String[] args) {
		new Aufgabe2().start("CG Aufgabe 2", 700, 700);
	}

	@Override
	protected void init() {
		// folgende Zeile läd automatisch "aufgabe2_v.glsl" (vertex) und "aufgabe2_f.glsl" (fragment)
		ShaderProgram shaderProgram = new ShaderProgram("aufgabe2");
		glUseProgram(shaderProgram.getId());

		//TODO Koordinaten, VAO, VBO, ... hier anlegen und im Grafikspeicher ablegen
		float[]triangles = new float[]{
				//x0,y0,x1,y1,...
				-0.0025f,0.5f, -0.0025f,-0.2f ,-0.55f,-0.5f, //Dreieck 1
				0.0025f,0.5f, 0.0025f,-0.2f ,0.55f,-0.5f  //Dreieck 2
		};
		float[] color = new float[] {
				//r0,g0,b0, r1,g1,b1,...
				0.9f,0.5f,0.7f, 1.0f,1.0f,1.0f, 0.5f,0.3f,1.0f,
				0.4f,0.9f,0.8f, 1.0f,1.0f,1.0f, 0.1f,0.9f,0.2f,
		};
		this.anzEcken = 6;
		vaoId= glGenVertexArrays();
		glBindVertexArray(vaoId);

		Buffers(vboId, triangles,0,2);
		Buffers(colorId, color,1,3);
	}

	private void Buffers(int vboName, float[] arrayName, int index, int size){
		vboName = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER,vboName);
		glBufferData(GL_ARRAY_BUFFER,arrayName,GL_STATIC_DRAW);
		glVertexAttribPointer(index,size,GL_FLOAT,false,0,0);
		glEnableVertexAttribArray(index);
	}
	
	@Override
	public void update() {
	}

	@Override
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT); // Zeichenfläche leeren

		glBindVertexArray(vaoId);
		glDrawArrays(GL_TRIANGLES,0,anzEcken);
		glDrawArrays(GL_COLOR,0,anzEcken);
		//TODO hier vorher erzeugte VAOs zeichnen
	}

	@Override
	public void destroy() {
	}
}
