package com.hanggrian.javapoet

/**
 * Function without inline should not be able to assign a final global variable. For example, the
 * code below should fail.
 *
 * ```
 * val HelloWorld by types.addingClass {
 *     variable = 0
 * }
 * ```
 */
class InliningTest {
    private val variable: Int

    init {
        buildJavaFile("com.example") {
            types.addClass("HelloWorld") {
                variable = 0
            }
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            InliningTest()
        }
    }
}
