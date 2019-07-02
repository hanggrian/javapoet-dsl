package com.hendraanggrian.javapoet.fromJavapoetReadme

import com.hendraanggrian.javapoet.buildJavaFile
import com.hendraanggrian.javapoet.dsl.MethodContainerScope
import javax.lang.model.element.Modifier
import kotlin.test.Test
import kotlin.test.assertEquals

@Suppress("ClassName")
class `$SforStringsTest` {

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
                addClass("HelloWorld") {
                    addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    methods {
                        nameMethod("slimShady")
                        nameMethod("eminem")
                        nameMethod("marshallMathers")
                    }
                }
            }.toString()
        )
    }

    private fun MethodContainerScope.nameMethod(name: String) {
        name {
            returns<String>()
            statements.add("return \$S", name)
        }
    }
}