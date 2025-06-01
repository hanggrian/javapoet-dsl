package com.hanggrian.javapoet

import com.google.common.truth.Truth.assertThat
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import kotlin.test.Test
import kotlin.test.assertFails

class JavaFileBuilderTest {
    @Test
    fun `Fails when inserted more than 1 types`() {
        assertFails {
            buildJavaFile("MyClass") {
                types {
                    addClass("Class1")
                    addClass("Class2")
                }
            }
        }
    }

    /** A java file may only have one type. */
    @Test
    fun `Invalid number of types`() {
        // multiple types
        assertFails {
            buildJavaFile("com.example") {
                types {
                    addClass("MyClass")
                    addClass("MyOtherClass")
                }
            }
        }
        // no type
        assertFails {
            buildJavaFile("com.example") { }
        }
    }

    @Test
    fun addComment() =
        assertThat(
            JavaFile
                .builder("com.example", TypeSpec.classBuilder("MyClass").build())
                .addFileComment("A ")
                .addFileComment("very ")
                .addFileComment("long ")
                .addFileComment("comment")
                .build(),
        ).isEqualTo(
            buildJavaFile("com.example") {
                types.addClass("MyClass")
                addComment("A ")
                addComment("very ")
                addComment("long ")
                addComment("comment")
            },
        )

    @Test
    fun addStaticImports() {
        // names array cannot be empty
        assertFails {
            buildJavaFile("com.example") {
                types.addClass("MyClass")
                addStaticImport<String>()
            }
        }
        // same type import using different functions
        assertThat(
            buildJavaFile("com.example") {
                types.addClass("MyClass")
                addStaticImport(MyEnum.A)
                addStaticImport(String::class, "toDouble")
                addStaticImport<String>("toFloat")
            }.toString(),
        ).isEqualTo(
            """
            package com.example;

            import static com.hanggrian.javapoet.JavaFileBuilderTest.MyEnum.A;
            import static java.lang.String.toDouble;
            import static java.lang.String.toFloat;

            class MyClass {
            }

            """.trimIndent(),
        )
    }

    @Test
    fun skipJavaLangImports() =
        assertThat(
            buildJavaFile("com.example") {
                types.addClass("MyClass") {
                    methods.addConstructor {
                        appendLine("%T s = new %T", String::class, String::class)
                    }
                }
                isSkipJavaLangImports = true
            }.toString(),
        ).isEqualTo(
            """
            package com.example;

            class MyClass {
              MyClass() {
                String s = new String;
              }
            }

            """.trimIndent(),
        )

    @Test
    fun indent() =
        assertThat(
            buildJavaFile("com.example") {
                types.addClass("MyClass") {
                    methods.addConstructor()
                }
                indent = ">"
            }.toString(),
        ).isEqualTo(
            """
            package com.example;

            class MyClass {
            >MyClass() {
            >}
            }

            """.trimIndent(),
        )

    @Test
    fun indentSize() =
        assertThat(
            buildJavaFile("com.example") {
                types.addClass("MyClass") {
                    methods.addConstructor()
                }
                indentSize = 4
            }.toString(),
        ).isEqualTo(
            """
            package com.example;

            class MyClass {
                MyClass() {
                }
            }

            """.trimIndent(),
        )

    enum class MyEnum { A }
}
