#version 330
 out vec3 pixelFarbe;
vec2 pixelXY = gl_FragCoord.xy;
vec2 mittelpunkt = vec2(350,350);
vec2 mittelpunkt2 = vec2(216,345);
vec2 mittelpunkt3 = vec2(218,340);
vec2 mittelpunkt4 = vec2(176,406);

void rectangle() {
    if (pixelXY.x>50 && pixelXY.x<650 && pixelXY.y>50 && pixelXY.y<650) {
        pixelFarbe = vec3(0.5, 0.37, 0.9);
    }
    else {
        pixelFarbe = vec3(0.1, 0.4, 0.8);
    }
}
/*bool istImKreis(in vec2 mittel, in float radius) {
    return distance(mittel, punkt) <radius;

}

void kreis() {
    pixelFarbe = vec3(0.2,0.4,0.8);
    if(istImKreis(vec2(300.0,400.0),80,pixelXY)){
        pixelFarbe = vec3(0.6,0.92,0.9);
    }



}*/
void navi() {
    pixelFarbe = vec3(1.0,1.0,1.0);
    if (pixelXY.x>250 && pixelXY.x<500 && pixelXY.y>350 && pixelXY.y<570) {
        pixelFarbe = vec3(0.8, 0.8, 1.0);
    }
    if (pixelXY.x>270 && pixelXY.x<450 && pixelXY.y>200 && pixelXY.y<340) {
        pixelFarbe = vec3(0.85, 0.85, 1.0);
    }
    if(distance(pixelXY,mittelpunkt3)<= 101) {
        pixelFarbe = vec3(0.6,0.7,1.0);
    }
    if(distance(pixelXY,mittelpunkt2) <= 100){
        pixelFarbe = vec3(0.6,0.92,0.9);
    }
    if(distance(pixelXY,mittelpunkt2) <= 90){
        pixelFarbe = vec3(0.7,0.92,1.0);
    }
    if(distance(pixelXY,mittelpunkt4)<=10){
        pixelFarbe = vec3(1.0,1.0,1.0);
    }


}
void rotate() {
    pixelFarbe = vec3(0.2,0.4,0.8);
    float w = -0.4;
    float c = cos(w);
    float s = sin(w);
    mat2 rotMat = mat2(c,s,-s,c);
    pixelXY= rotMat*pixelXY;
    if (pixelXY.x>100 && pixelXY.x<600 && pixelXY.y>100 && pixelXY.y<600) {
        pixelFarbe = vec3(0.5, 0.37, 0.9);
    }
}
void main() {

    //rectangle();
    //kreis();
    navi();
    //rotate();
}



