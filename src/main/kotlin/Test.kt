import opengl.GLContext
import org.lwjgl.opengl.GL11.*
import kotlin.math.abs


fun main() {
    val context = GLContext.createWindow("Hello World!")

    context.use()
    context.show()

    context.drawLoop {
        val color = (System.currentTimeMillis() / 2 % 1000) / 1000f
        glClearColor(1.0f, abs(1 - 2 * color), 0.0f, 1.0f)
        // Clear the framebuffer
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
    }

    context.free()
    GLContext.cleanup()
}
