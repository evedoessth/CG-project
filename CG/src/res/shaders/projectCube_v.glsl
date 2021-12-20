#version 330
layout(location=0) in vec4 eckenAusJava;
layout(location=1) in vec3 colorAusJava;
layout(location=2) in vec3 normaleAusJava;
layout(location=3) in vec2 vertexUV;
out vec3 renderColor;
out vec4 normalen;
out vec3 p;
out vec2 uvKoordinaten;
uniform mat4 meineMatrix;
uniform mat4 projectMatrix;

void main() {
    gl_Position=projectMatrix*meineMatrix*eckenAusJava;
    mat4 normalMatrix = inverse(transpose(meineMatrix));
    normalen = normalMatrix * vec4(normaleAusJava,1);
    p = (meineMatrix * eckenAusJava).xyz;
    //renderColor=colorAusJava;
    uvKoordinaten=vertexUV;
}