#version 330
out vec3 pixelColor;
in vec3 renderColor;
in vec4 normalen;
in vec3 p;

vec3 lichtposition = vec3(-3,15,0);

in vec2 uvKoordinaten;
uniform sampler2D smplr;

void main() {

    vec3 N = normalize(normalen.xyz);
    vec3 L = normalize(lichtposition - p);
    vec3 R = reflect(-L,N);
    vec3 V = normalize(-p);
    float I = dot(L,N) + dot(R,V);

    vec4 texel = texture(smplr, uvKoordinaten);
    pixelColor= texel.rgb*I;
    //pixelColor=vec3(1.0f,0.0f,0.0f);
}