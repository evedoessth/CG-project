#version 330
out vec3 pixelColor;
in vec3 renderColor;
in vec4 normalen;
in vec3 p;



vec3 lichtposition = vec3( 15,10,9);

in vec2 uvKoordinaten;
uniform sampler2D smplr;

vec2 dimenstion= vec2(0.06,0.07);


void main() {



    vec2 m = mod(uvKoordinaten, 2.0 * dimenstion);
    if(m.x < dimenstion.x && m.y < dimenstion.y || m.x >= dimenstion.x && m.y >= dimenstion.y){
        pixelColor = vec3(0.5,0.8,1.0);
    }
    else{
        pixelColor = vec3(0.5,0.4,0.8);

    }

}
//https://www.shadertoy.com/view/4sGczD