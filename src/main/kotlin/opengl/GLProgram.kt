package opengl

import org.lwjgl.opengl.GL20.*


fun Boolean.toInt() = if (this) 1 else 0

class GLProgram(vertexName: String, fragmentName: String) {
    private val programID: Int = glCreateProgram()

    init {
        val classLoader: ClassLoader = Thread.currentThread().contextClassLoader
        val vertexStr = classLoader.getResourceAsStream(vertexName)?.reader()?.readText()
        val fragmentStr = classLoader.getResourceAsStream(fragmentName)?.reader()?.readText()

        val vertexShader = glCreateShader(GL_VERTEX_SHADER)
        glShaderSource(vertexShader, vertexStr!!)
        glCompileShader(vertexShader)
        checkCompileErrors(vertexShader, "SHADER")

        val fragmentShader = glCreateShader(GL_FRAGMENT_SHADER)
        glShaderSource(fragmentShader, fragmentStr!!)
        glCompileShader(fragmentShader)
        checkCompileErrors(fragmentShader, "SHADER")

        glAttachShader(programID, vertexShader)
        glAttachShader(programID, fragmentShader)
        glLinkProgram(programID)
        checkCompileErrors(programID, "PROGRAM")

    }

    fun use() {
        glUseProgram(programID)
    }

    fun setBool(name: String, value: Boolean) = glUniform1i(glGetUniformLocation(programID, name), value.toInt())

    fun setInt(name: String, value: Int) = glUniform1i(glGetUniformLocation(programID, name), value)

    fun setFloat(name: String, value: Float) = glUniform1f(glGetUniformLocation(programID, name), value)

    fun setVec3Float(name: String, value: FloatArray) = glUniform3fv(glGetUniformLocation(programID, name), value)

    fun setVec4Float(name: String, value: FloatArray) = glUniform4fv(glGetUniformLocation(programID, name), value)

    private fun checkCompileErrors(shader: Int, type: String) {
        val success = IntArray(1)
        if (type == "PROGRAM") {
            glGetProgramiv(shader, GL_LINK_STATUS, success)
            if (success[0] == GL_FALSE) {
                val info = glGetProgramInfoLog(shader)
                System.err.println("Error compiling program\n$info\n------------------------------------------")
            }
        } else {
            glGetShaderiv(shader, GL_COMPILE_STATUS, success)
            if (success[0] == GL_FALSE) {
                val info = glGetShaderInfoLog(shader)
                System.err.println("Error compiling shader of type $type\n$info\n------------------------------------------")
            }
        }
    }
}