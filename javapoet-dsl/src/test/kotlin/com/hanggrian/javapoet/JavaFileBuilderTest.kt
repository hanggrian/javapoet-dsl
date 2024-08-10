package com.hanggrian.javapoet

import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class JavaFileBuilderTest {
    /** A java file may only have one type. */
    @Test
    fun `Invalid number of types`() {
        // Multiple types.
        assertFails {
            buildJavaFile("com.example") {
                types {
                    addClass("MyClass")
                    addClass("MyOtherClass")
                }
            }
        }
        // No type.
        assertFails {
            buildJavaFile("com.example") { }
        }
    }

    @Test
    fun addComment() {
        assertEquals(
            buildJavaFile("com.example") {
                types.addClass("MyClass")
                addComment("A ")
                addComment("very ")
                addComment("long ")
                addComment("comment")
            },
            JavaFile
                .builder("com.example", TypeSpec.classBuilder("MyClass").build())
                .addFileComment("A ")
                .addFileComment("very ")
                .addFileComment("long ")
                .addFileComment("comment")
                .build(),
        )
    }

    @Test
    fun addStaticImports() {
        // Names array cannot be empty.
        assertFails {
            buildJavaFile("com.example") {
                types.addClass("MyClass")
                addStaticImport<String>()
            }
        }
        // Same type import using different functions.
        assertEquals(
            """
            package com.example;

            import static com.hanggrian.javapoet.JavaFileBuilderTest.MyEnum.A;
            import static java.lang.String.toDouble;
            import static java.lang.String.toFloat;

            class MyClass {
            }

            """.trimIndent(),
            buildJavaFile("com.example") {
                types.addClass("MyClass")
                addStaticImport(MyEnum.A)
                addStaticImport(String::class, "toDouble")
                addStaticImport<String>("toFloat")
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
                types.addClass("MyClass") {
                    methods.addConstructor {
                        appendLine("%T s = new %T", String::class, String::class)
                    }
                }
                isSkipJavaLangImports = true
            }.toString(),
        )
    }

    @Test
    fun indent() {
        // Custom string.
        assertEquals(
            """
            package com.example;

            class MyClass {
            >MyClass() {
            >}
            }

            """.trimIndent(),
            buildJavaFile("com.example") {
                types.addClass("MyClass") {
                    methods.addConstructor()
                }
                indent = ">"
            }.toString(),
        )
        // Use count.
        assertEquals(
            """
            package com.example;

            class MyClass {
                MyClass() {
                }
            }

            """.trimIndent(),
            buildJavaFile("com.example") {
                types.addClass("MyClass") {
                    methods.addConstructor()
                }
                indentSize = 4
            }.toString(),
        )
    }

    enum class MyEnum { A }
}
