import opengl.GLContext
import opengl.GLProgram
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL33.*
import kotlin.math.abs


fun main() {
    val context = GLContext.createWindow("Hello World!")

    context.use()
    context.show()

    val testProgram = GLProgram("first.vert", "first.frag")
    testProgram.use()

    val data = floatArrayOf(
        -0.5f, -0.5f, 0.0f,        1.0f, 0.0f, 0.0f,
        0.5f, -0.5f, 0.0f,        0.0f, 1.0f, 0.0f,
        0.5f, 0.5f, 0.0f,        0.0f, 0.0f, 1.0f,
        -0.5f, 0.5f, 0.0f,        1.0f, 1.0f, 0.0f,
    )

    val indices = intArrayOf(
        0, 1, 2,
        3, 0, 1,
    )
//    val color = floatArrayOf(1.0f, 0.5f, 0.0f)
//
//    testProgram.setVec3Float(color, "uColor")
    val vao = glGenVertexArrays()
    glBindVertexArray(vao)

    val vbo = glGenBuffers()
    glBindBuffer(GL_ARRAY_BUFFER, vbo)
    glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW)

    val ebo = glGenBuffers()
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo)
    GL15.glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW)


    glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * Float.SIZE_BYTES, 0L)
    glEnableVertexAttribArray(0)
    glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * Float.SIZE_BYTES, (3 * Float.SIZE_BYTES).toLong())
    glEnableVertexAttribArray(1)

    glBindVertexArray(0)
    glBindVertexArray(vao)

    context.drawLoop {
//        val color = (System.currentTimeMillis() / 2 % 1000) / 1000f
//        glClearColor(1.0f, abs(1 - 2 * color), 0.0f, 1.0f)

        // Clear the framebuffer
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0L)
    }

    context.free()
    GLContext.cleanup()
}
