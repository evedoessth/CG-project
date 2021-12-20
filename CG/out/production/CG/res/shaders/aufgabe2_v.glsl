#version 330
layout(location=0) in vec2 eckenAusJava;
layout(location=1) in vec3 colorAusJava;
out vec3 renderColor;

void main() {
    float w = 0.0;
    float c = cos(w);
    float s = sin(w);
    mat2 rotMat = mat2(c,s,-s,c);
    vec2 roti = eckenAusJava* rotMat;
    gl_Position =  vec4(roti,0.0,1.0);
    renderColor= colorAusJava;
}