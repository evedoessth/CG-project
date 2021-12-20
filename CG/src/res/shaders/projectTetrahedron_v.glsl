#version 330
layout(location=0) in vec4 eckenAusJava;
layout(location=1) in vec3 colorAusJava;
layout(location=2) in vec3 normaleAusJava;
out vec3 renderColor;
out vec4 normalen;
out vec3 p;
uniform mat4 meineMatrix;
uniform mat4 projektionsMatrix;

void main() {
    gl_Position=projektionsMatrix*meineMatrix*eckenAusJava;
    mat4 normalMatrix = inverse(transpose(meineMatrix));
    normalen = normalMatrix * vec4(normaleAusJava,1);
    p = (meineMatrix * eckenAusJava).xyz;
    renderColor=colorAusJava;
}