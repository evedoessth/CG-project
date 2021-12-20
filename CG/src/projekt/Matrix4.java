package projekt;

import java.util.ArrayList;
import java.util.List;

//Alle Operationen ändern das Matrixobjekt selbst und geben das eigene Matrixobjekt zurück
//Dadurch kann man Aufrufe verketten, z.B.
//Matrix4 m = new Matrix4().scale(5).translate(0,1,0).rotateX(0.5f);
public class Matrix4 {

	private float[][] matrixArray;

	//------------------Constructoren-----------------

	public Matrix4() {//constructor
		// TODO mit der Identitätsmatrix initialisieren
		this.matrixArray =new float[][] {
				{1,0,0,0},
				{0,1,0,0},
				{0,0,1,0},
				{0,0,0,1}
		};
		//intern[0][0]=1; aber auch mit vorschleife
		//anderes float[] 16 intern[0]=1;
		//float i00, i01, i02, ....
	}

	public Matrix4(Matrix4 copy) {
		// TODO neues Objekt mit den Werten von "copy" initialisieren
		//kann sein dass der hier nicht genutzt wird
		for (int i=0;i<copy.matrixArray.length;i++) {
			for (int j=0;j<copy.matrixArray[i].length;j++) {
				matrixArray[i][j]=copy.matrixArray[i][j];
			}
		}
		//sicherheitscopie aber muss nicht sein
	}

	public Matrix4(float near, float far) {
		// TODO erzeugt Projektionsmatrix mit Abstand zur nahen Ebene "near" und Abstand zur fernen Ebene "far", ggf. weitere Parameter hinzufügen
		this.matrixArray =new float[][] {
				{near,0,0,0},
				{0,near,0,0},
				{0,0,(-far-near)/(far-near),(-2*far*near)/(far-near)},
				{0,0,-1,0}
		};
	}//für später aber wird auch ne matrix


//-----------------------------------------------------------------------------------
	//Richtige Matrixen bauen und dann zu multiply geben
	public Matrix4 multiply(Matrix4 other) {
		// TODO hier Matrizenmultiplikation "this = other * this" einfügen
		float[][]prod = new float[4][4];
		for (int z=0;z<4;z++) {
			for (int s=0;s<4;s++) {
				for(int i=0;i<4;i++) {
					prod[z][s]+= other.matrixArray[z][i] * this.matrixArray[i][s];
				}
			}
		}
		this.matrixArray=prod;
		return this;
		//intern[0][0]=blah*intern[0][0]+....!
		//besser:temp[0][0]=blah*intern[0][0]+....!
		//intern[0][0]=temp[0][0]
		/*this.matrixArray =new float[] {
				0,1,2,3,
				4,5,6,7,
				8,9,10,11,
				12,13,14,15
		};*/
		
	}

	public Matrix4 translate(float x, float y, float z) {
		// TODO Verschiebung um x,y,z zu this hinzufügen
		Matrix4 translationMatrix = new Matrix4();
		translationMatrix.matrixArray=new float[][]{
				{1,0,0,x},
				{0,1,0,y},
				{0,0,1,z},
				{0,0,0,1}
		};
		multiply(translationMatrix);
		return this;
	}

	public Matrix4 scale(float uniformFactor) {
		// TODO gleichmäßige Skalierung um Faktor "uniformFactor" zu this hinzufügen
		Matrix4 uniformScaleMatrix = new Matrix4();
		uniformScaleMatrix.matrixArray = new float[][] {
				{uniformFactor,0,0,0},
				{0,uniformFactor,0,0},
				{0,0,uniformFactor,0},
				{0,0,0,1}
		};
		multiply(uniformScaleMatrix);
		return this;
	}

	public Matrix4 scale(float sx, float sy, float sz) {
		// TODO ungleichförmige Skalierung zu this hinzufügen
		Matrix4 ScaleMatrix = new Matrix4();
		ScaleMatrix.matrixArray = new float[][] {
				{sx,0,0,0},
				{0,sy,0,0},
				{0,0,sz,0},
				{0,0,0,1}
		};
		multiply(ScaleMatrix);
		return this;
	}

	public Matrix4 rotateX(float angle) {
		// TODO Rotation um X-Achse zu this hinzufügen
		Matrix4 rotateXMatrix = new Matrix4();
		rotateXMatrix.matrixArray = new float[][] {
				{1,0,0,0},
				{0,(float) Math.cos(angle),(float) -(Math.sin(angle)),0},
				{0,(float) Math.sin(angle),(float) Math.cos(angle),0},
				{0,0,0,1}
		};
		multiply(rotateXMatrix);
		return this;
	}

	public Matrix4 rotateY(float angle) {
		// TODO Rotation um Y-Achse zu this hinzufügen
		Matrix4 rotateYMatrix = new Matrix4();
		rotateYMatrix.matrixArray = new float[][]{
				{(float) Math.cos(angle),0,(float) -(Math.sin(angle)),0},
				{0,1,0,0},
				{(float) Math.sin(angle),0,(float) Math.cos(angle),0},
				{0,0,0,1}
		};
		multiply(rotateYMatrix);
		return this;
	}

	public Matrix4 rotateZ(float angle) {
		// TODO Rotation um Z-Achse zu this hinzufügen
		Matrix4 rotateZMatrix = new Matrix4();
		rotateZMatrix.matrixArray = new float[][] {
				{(float) Math.cos(angle),(float) -(Math.sin(angle)),0,0},
				{(float) Math.sin(angle),(float) Math.cos(angle),0,0},
				{0,0,1,0},
				{0,0,0,1}};
		multiply(rotateZMatrix);
		return this;
	}
//--------------------------------------
	public float[] getValuesAsArray() {
		// TODO hier Werte in einem Float-Array mit 16 Elementen (spaltenweise gefüllt) herausgeben
		float[] temp=new float[16];
		for(int i=0; i<matrixArray.length;i++) {
			for(int j=0;j<matrixArray[i].length;j++) {
				temp[i+(j*matrixArray.length)]=matrixArray[i][j];
			}
		}

		return temp;
	}
}
