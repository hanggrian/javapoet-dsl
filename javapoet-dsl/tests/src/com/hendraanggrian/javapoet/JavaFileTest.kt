package com.hendraanggrian.javapoet

import org.junit.Test

class JavaFileTest {

    @Test
    fun helloWorld() {
        buildJavaFile("com.hendraanggrian") {
            type("HelloWorld") {
                method("main") {

                }
            }
        }
    }
}