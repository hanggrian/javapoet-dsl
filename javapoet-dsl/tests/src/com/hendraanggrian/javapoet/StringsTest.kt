package com.hendraanggrian.javapoet

import org.junit.Test
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
                    modifiers = public + final
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