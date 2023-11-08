package com.hendraanggrian.javapoet

import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class JavaFileBuilderTest {
    enum class MyEnum { A }

    /** A java file may only have one type. */
    @Test
    fun invalidNumberOfType() {
        // multiple types
        assertFails {
            buildJavaFile("com.example") {
                classType("MyClass")
                classType("MyOtherClass")
            }
        }
        // no type
        assertFails {
            buildJavaFile("com.example") { }
        }
    }

    @Test
    fun comments() {
        assertEquals(
            JavaFile.builder("com.example", TypeSpec.classBuilder("MyClass").build())
                .addFileComment("A ")
                .addFileComment("very ")
                .addFileComment("long ")
                .addFileComment("comment")
                .build(),
            buildJavaFile("com.example") {
                classType("MyClass")
                comment("A ")
                comment("very ")
                comment("long ")
                comment("comment")
            },
        )
    }

    @Test
    fun staticImports() {
        // names array cannot be empty
        assertFails {
            buildJavaFile("com.example") {
                classType("MyClass")
                staticImport<String>()
            }
        }
        // same type import using different functions
        assertEquals(
            """
            package com.example;

            import static com.hendraanggrian.javapoet.JavaFileBuilderTest.MyEnum.A;
            import static java.lang.String.toDouble;
            import static java.lang.String.toFloat;
            import static java.lang.String.toInt;

            class MyClass {
            }

            """.trimIndent(),
            buildJavaFile("com.example") {
                classType("MyClass")
                staticImport(MyEnum.A)
                staticImport(String::class.java, "toInt")
                staticImport(String::class, "toDouble")
                staticImport<String>("toFloat")
            }.toString(),
        )
    }

    @Test
    fun skipJavaLangImports() {
        assertEquals(
            """
            package com.example;

            class MyClass {
              MyClass() {
                String s = new String;
              }
            }

            """.trimIndent(),
            buildJavaFile("com.example") {
                classType("MyClass") {
                    constructorMethod {
                        appendLine("%T s = new %T", String::class, String::class)
                    }
                }
                skipJavaLangImports = true
            }.toString(),
        )
    }

    @Test
    fun indent() {
        // custom string
        assertEquals(
            """
            package com.example;

            class MyClass {
            >MyClass() {
            >}
            }

            """.trimIndent(),
            buildJavaFile("com.example") {
                classType("MyClass") {
                    constructorMethod()
                }
                indent = ">"
            }.toString(),
        )
        // use count
        assertEquals(
            """
            package com.example;

            class MyClass {
                MyClass() {
                }
            }

            """.trimIndent(),
            buildJavaFile("com.example") {
                classType("MyClass") {
                    constructorMethod()
                }
                indentSize = 4
            }.toString(),
        )
    }
}
