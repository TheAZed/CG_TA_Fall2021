#version 430 core

in vec3 color;
in vec2 TexCoord;

out vec4 fragColor;

uniform sampler2D boxTexture;

void main() {
    fragColor = texture(boxTexture, TexCoord);
}
