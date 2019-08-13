package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.MethodContainerScope
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import java.util.Collections
import java.util.Date
import javax.lang.model.element.Modifier
import kotlin.test.Test
import kotlin.test.assertEquals

/** From the original javapoet readme. */
class ReadmeTest {

    @Test
    fun example() {
        assertEquals(
            """
                package com.example.helloworld;

                import java.lang.String;
                import java.lang.System;

                public final class HelloWorld {
                  public static void main(String[] args) {
                    System.out.println("Hello, JavaPoet!");
                  }
                }

            """.trimIndent(),
            buildJavaFile("com.example.helloworld") {
                addClass("HelloWorld") {
                    addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    methods {
                        "main" {
                            addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                            returns = TypeName.VOID
                            parameters.add<Array<String>>("args")
                            codes.addStatement("\$T.out.println(\$S)", System::class.java, "Hello, JavaPoet!")
                        }
                    }
                }
            }.toString()
        )
    }

    @Test
    fun `code&ControlFlow`() {
        val expected1 =
            """
                void main() {
                  int total = 0;
                  for (int i = 0; i < 10; i++) {
                    total += i;
                  }
                }
            
            """.trimIndent()
        val expected2 =
            """
                int multiply10to20() {
                  int result = 1;
                  for (int i = 10; i < 20; i++) {
                    result = result * i;
                  }
                  return result;
                }

            """.trimIndent()
        assertEquals(
            expected1,
            (MethodSpec.methodBuilder("main")) {
                returns = TypeName.VOID
                codes.add(
                    """
                        int total = 0;
                        for (int i = 0; i < 10; i++) {
                          total += i;
                        }
                        
                    """.trimIndent()
                )
            }.toString()
        )
        assertEquals(
            expected1,
            (MethodSpec.methodBuilder("main")) {
                returns = TypeName.VOID
                codes {
                    addStatement("int total = 0")
                    beginControlFlow("for (int i = 0; i < 10; i++)")
                    addStatement("total += i")
                    endControlFlow()
                }
            }.toString()
        )
        assertEquals(
            expected2,
            (MethodSpec.methodBuilder("multiply10to20")) {
                returns = TypeName.INT
                codes {
                    addStatement("int result = 1")
                    beginControlFlow("for (int i = 10; i < 20; i++)")
                    addStatement("result = result * i")
                    endControlFlow()
                    addStatement("return result")
                }
            }.toString()
        )
    }

    @Test
    fun `$SForStrings`() {
        assertEquals(
            """
                package com.example.helloworld;

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
            buildJavaFile("com.example.helloworld") {
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

    @Test
    fun `$TForTypes`() {
        assertEquals(
            """
                package com.example.helloworld;

                import java.util.Date;

                public final class HelloWorld {
                  Date today() {
                    return new Date();
                  }
                }

            """.trimIndent(),
            buildJavaFile("com.example.helloworld") {
                addClass("HelloWorld") {
                    addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    methods {
                        "today" {
                            returns<Date>()
                            codes.addStatement("return new \$T()", Date::class.java)
                        }
                    }
                }
            }.toString()
        )
        assertEquals(
            """
                package com.example.helloworld;

                import com.mattel.Hoverboard;

                public final class HelloWorld {
                  Hoverboard tomorrow() {
                    return new Hoverboard();
                  }
                }

            """.trimIndent(),
            buildJavaFile("com.example.helloworld") {
                addClass("HelloWorld") {
                    addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    methods {
                        "tomorrow" {
                            val hoverboard = ClassName.get("com.mattel", "Hoverboard")
                            returns = hoverboard
                            codes.addStatement("return new \$T()", hoverboard)
                        }
                    }
                }
            }.toString()
        )
        assertEquals(
            """
                package com.example.helloworld;

                import com.mattel.Hoverboard;
                import java.util.ArrayList;
                import java.util.List;

                public final class HelloWorld {
                  List<Hoverboard> beyond() {
                    List<Hoverboard> result = new ArrayList<>();
                    result.add(new Hoverboard());
                    result.add(new Hoverboard());
                    result.add(new Hoverboard());
                    return result;
                  }
                }

            """.trimIndent(),
            buildJavaFile("com.example.helloworld") {
                addClass("HelloWorld") {
                    addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    methods {
                        "beyond" {
                            val hoverboard = ClassName.get("com.mattel", "Hoverboard")
                            val list = ClassName.get("java.util", "List");
                            val arrayList = ClassName.get("java.util", "ArrayList")
                            val listOfHoverboards = ParameterizedTypeName.get(list, hoverboard)
                            returns = listOfHoverboards
                            codes {
                                addStatement("\$T result = new \$T<>()", listOfHoverboards, arrayList)
                                addStatement("result.add(new \$T())", hoverboard)
                                addStatement("result.add(new \$T())", hoverboard)
                                addStatement("result.add(new \$T())", hoverboard)
                                addStatement("return result")
                            }
                        }
                    }
                }
            }.toString()
        )
        assertEquals(
            """
                package com.example.helloworld;

                import static com.mattel.Hoverboard.Boards.*;
                import static com.mattel.Hoverboard.createNimbus;
                import static java.util.Collections.*;

                import com.mattel.Hoverboard;
                import java.util.ArrayList;
                import java.util.List;

                public final class HelloWorld {
                  List<Hoverboard> beyond() {
                    List<Hoverboard> result = new ArrayList<>();
                    result.add(createNimbus(2000));
                    result.add(createNimbus("2001"));
                    result.add(createNimbus(THUNDERBOLT));
                    sort(result);
                    return result.isEmpty() ? emptyList() : result;
                  }
                }

            """.trimIndent(),
            buildJavaFile("com.example.helloworld") {
                val hoverboard = ClassName.get("com.mattel", "Hoverboard")
                val namedBoards = ClassName.get("com.mattel", "Hoverboard", "Boards")
                addStaticImports(hoverboard, "createNimbus")
                addStaticImports(namedBoards, "*")
                addStaticImports(Collections::class, "*")
                addClass("HelloWorld") {
                    addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    methods {
                        "beyond" {
                            val list = ClassName.get("java.util", "List");
                            val arrayList = ClassName.get("java.util", "ArrayList")
                            val listOfHoverboards = ParameterizedTypeName.get(list, hoverboard)
                            returns = listOfHoverboards
                            codes {
                                addStatement("\$T result = new \$T<>()", listOfHoverboards, arrayList)
                                addStatement("result.add(\$T.createNimbus(2000))", hoverboard)
                                addStatement("result.add(\$T.createNimbus(\"2001\"))", hoverboard)
                                addStatement("result.add(\$T.createNimbus(\$T.THUNDERBOLT))", hoverboard, namedBoards)
                                addStatement("\$T.sort(result)", Collections::class.java)
                                addStatement(
                                    "return result.isEmpty() ? \$T.emptyList() : result", Collections::class.java
                                )
                            }
                        }
                    }
                }
            }.toString()
        )
    }

    @Test
    fun `$NForNames`() {
        assertEquals(
            """
                package com.example.helloworld;

                import java.lang.String;

                public final class HelloWorld {
                  public char hexDigit(int i) {
                    return (char) (i < 10 ? i + '0' : i - 10 + 'a');
                  }

                  public String byteToHex(int b) {
                    char[] result = new char[2];
                    result[0] = hexDigit((b >>> 4) & 0xf);
                    result[1] = hexDigit(b & 0xf);
                    return new String(result);
                  }
                }

            """.trimIndent(),
            buildJavaFile("com.example.helloworld") {
                addClass("HelloWorld") {
                    addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    methods {
                        val hexDigit = "hexDigit" {
                            addModifiers(Modifier.PUBLIC)
                            parameters.add(ClassName.INT, "i")
                            returns = ClassName.CHAR
                            codes.addStatement("return (char) (i < 10 ? i + '0' : i - 10 + 'a')")
                        }
                        "byteToHex" {
                            addModifiers(Modifier.PUBLIC)
                            parameters.add(ClassName.INT, "b")
                            returns<String>()
                            codes {
                                addStatement("char[] result = new char[2]")
                                addStatement("result[0] = \$N((b >>> 4) & 0xf)", hexDigit)
                                addStatement("result[1] = \$N(b & 0xf)", hexDigit)
                                addStatement("return new String(result)")
                            }
                        }
                    }
                }
            }.toString()
        )
    }

    @Test
    fun codeBlockFormatStrings() {
        assertEquals(CodeBlock.builder().add("I ate \$L \$L", 3, "tacos").build(),
            (CodeBlock.builder()) { add("I ate \$L \$L", 3, "tacos") })
        assertEquals(CodeBlock.builder().add("I ate \$2L \$1L", "tacos", 3).build(),
            (CodeBlock.builder()) { add("I ate \$2L \$1L", "tacos", 3) })
    }

    @Test
    fun methods() {
        assertEquals(
            """
                public abstract class HelloWorld {
                  protected abstract void flux();
                }
                
            """.trimIndent(),
            (TypeSpec.classBuilder("HelloWorld")) {
                addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                methods.add("flux") {
                    addModifiers(Modifier.PROTECTED, Modifier.ABSTRACT)
                }
            }.toString()
        )
    }

    private fun MethodContainerScope.nameMethod(name: String) {
        name {
            returns<String>()
            codes.addStatement("return \$S", name)
        }
    }
}