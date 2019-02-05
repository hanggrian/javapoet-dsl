package com.hendraanggrian.javapoet

import org.junit.Test
import javax.lang.model.element.Modifier

class JavaFileTest {

    @Test
    fun helloWorld() {
        buildJavaFile("com.example.helloworld") {
            type("HelloWorld") {
                modifiers(Modifier.PUBLIC, Modifier.FINAL)
                method("main") {
                    modifiers(Modifier.PUBLIC, Modifier.STATIC)
                    returns(Void::class.java)
                    // parameter(Array<String>::class.java, "args")
                    statement("\$T.out.println(\$S)", System::class.java, "Hello, JavaPoet!")
                }
            }
        }.writeTo(System.out)
    }
}