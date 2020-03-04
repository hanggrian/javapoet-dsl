package com.hendraanggrian.javapoet

import com.squareup.javapoet.JavaFile
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class JavaFileTest {

    /** A java file may only have one type. */
    @Test fun invalidNumberOfType() {
        // multiple types
        buildJavaFile("com.example") {
            addClass("MyClass")
            assertFails { addClass("MyOtherClass") }
        }
        // no type
        assertFails { buildJavaFile("com.example") { } }
    }

    @Test fun comments() {
        // stacking addComment
        assertEquals(
            JavaFile.builder("com.example", buildClassType("MyClass"))
                .addFileComment("A ")
                .addFileComment("very ")
                .addFileComment("long ")
                .addFileComment("comment")
                .build(),
            buildJavaFile("com.example") {
                addClass("MyClass")
                addComment("A ")
                addComment("very ")
                addComment("long ")
                addComment("comment")
            }
        )
        // single-line comment
        assertEquals(
            JavaFile.builder("com.example", buildClassType("MyOtherClass"))
                .addFileComment("A ")
                .addFileComment("simple ")
                .addFileComment("comment")
                .build(),
            buildJavaFile("com.example") {
                addClass("MyOtherClass")
                addComment("A very long comment")
                comment = "A simple comment"
            }
        )
    }

    @Test fun staticImports() {
        // names array cannot be empty
        assertFails {
            buildJavaFile("com.example") {
                addClass("MyClass")
                addStaticImport<String>()
            }
        }
        // same type import using different functions
        assertEquals(
            """
                package com.example;

                import static com.hendraanggrian.javapoet.JavaFileTest.MyEnum.A;
                import static java.lang.String.toDouble;
                import static java.lang.String.toFloat;
                import static java.lang.String.toInt;

                class MyClass {
                }
                
            """.trimIndent(),
            buildJavaFile("com.example") {
                addClass("MyClass")
                addStaticImport(MyEnum.A)
                addStaticImport(String::class.java, "toInt")
                addStaticImport(String::class, "toDouble")
                addStaticImport<String>("toFloat")
            }.toString()
        )
    }

    @Test fun skipJavaLangImports() {
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
                addClass("MyClass") {
                    methods.addConstructor {
                        appendln("%T s = new %T", String::class, String::class)
                    }
                }
                skipJavaLangImports = true
            }.toString()
        )
    }

    @Test fun indent() {
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
                addClass("MyClass") {
                    methods.addConstructor()
                }
                indent = ">"
            }.toString()
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
                addClass("MyClass") {
                    methods.addConstructor()
                }
                indentSize = 4
            }.toString()
        )
    }

    enum class MyEnum { A }
}