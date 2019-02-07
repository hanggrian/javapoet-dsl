package com.hendraanggrian.javapoet

import org.junit.Test
import javax.lang.model.element.Modifier
import kotlin.test.assertEquals

class StringsTest {

    @Test
    fun dollarSign() {
        assertEquals(
            """
                package com.example;

                import java.lang.String;

                public final class HelloWorld {
                  String slimShady() {
                    return "slimShady";
                  }

                  String eminem() {
                    return "eminem";
                  }

                  String marshallMathers() {
                    return "marshallMathers";
                  }
                }

            """.trimIndent(),
            buildJavaFile("com.example") {
                type("HelloWorld") {
                    modifiers(Modifier.PUBLIC, Modifier.FINAL)
                    nameMethod("slimShady")
                    nameMethod("eminem")
                    nameMethod("marshallMathers")
                }
            }.toString()
        )
    }

    private fun TypeSpecBuilder.nameMethod(name: String) {
        method(name) {
            returns<String>()
            statement("return \$S", name)
        }
    }
}