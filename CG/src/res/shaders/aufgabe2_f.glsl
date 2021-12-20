#version 330
out vec3 pixelColor;
in vec3 renderColor;

void main() {
    pixelColor= renderColor;
}