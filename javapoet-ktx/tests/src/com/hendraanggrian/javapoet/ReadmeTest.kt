package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.MethodContainerScope
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeName
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
            JavaFiles.of("com.example.helloworld") {
                addClass("HelloWorld") {
                    addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    methods.add("main") {
                        addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        returns = TypeName.VOID
                        parameters.add<Array<String>>("args")
                        codes.appendln("%T.out.println(%S)", System::class, "Hello, JavaPoet!")
                    }
                }
            }.toString()
        )
    }

    @Test
    fun `code&ControlFlow`() {
        val expected =
            """
                void main() {
                  int total = 0;
                  for (int i = 0; i < 10; i++) {
                    total += i;
                  }
                }
            
            """.trimIndent()
        assertEquals(
            expected,
            MethodSpecs.of("main") {
                returns = TypeName.VOID
                codes.append(
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
            expected,
            MethodSpecs.of("main") {
                returns = TypeName.VOID
                codes {
                    appendln("int total = 0")
                    beginControlFlow("for (int i = 0; i < 10; i++)")
                    appendln("total += i")
                    endControlFlow()
                }
            }.toString()
        )
        assertEquals(
            """
                int multiply10to20() {
                  int result = 1;
                  for (int i = 10; i < 20; i++) {
                    result = result * i;
                  }
                  return result;
                }

            """.trimIndent(),
            MethodSpecs.of("multiply10to20") {
                returns = TypeName.INT
                codes {
                    appendln("int result = 1")
                    beginControlFlow("for (int i = 10; i < 20; i++)")
                    appendln("result = result * i")
                    endControlFlow()
                    appendln("return result")
                }
            }.toString()
        )
        assertEquals(
            """
                void main() {
                  long now = java.lang.System.currentTimeMillis();
                  if (java.lang.System.currentTimeMillis() < now) {
                    java.lang.System.out.println("Time travelling, woo hoo!");
                  } else if (java.lang.System.currentTimeMillis() == now) {
                    java.lang.System.out.println("Time stood still!");
                  } else {
                    java.lang.System.out.println("Ok, time still moving forward");
                  }
                }

            """.trimIndent(),
            MethodSpecs.of("main") {
                codes {
                    appendln("long now = %T.currentTimeMillis()", System::class)
                    beginControlFlow("if (%T.currentTimeMillis() < now)", System::class)
                    appendln("%T.out.println(%S)", System::class, "Time travelling, woo hoo!")
                    nextControlFlow("else if (%T.currentTimeMillis() == now)", System::class)
                    appendln("%T.out.println(%S)", System::class, "Time stood still!")
                    nextControlFlow("else")
                    appendln("%T.out.println(%S)", System::class, "Ok, time still moving forward")
                    endControlFlow()
                }
            }.toString()
        )
        assertEquals(
            """
                void main() {
                  try {
                    throw new Exception("Failed");
                  } catch (java.lang.Exception e) {
                    throw new java.lang.RuntimeException(e);
                  }
                }
                
            """.trimIndent(),
            MethodSpecs.of("main") {
                codes {
                    beginControlFlow("try")
                    appendln("throw new Exception(%S)", "Failed")
                    nextControlFlow("catch (%T e)", Exception::class)
                    appendln("throw new %T(e)", RuntimeException::class)
                    endControlFlow()
                }
            }.toString()
        )
    }

    @Test
    fun `$SForStrings`() {
        assertEquals(
            """
                public final class HelloWorld {
                  java.lang.String slimShady() {
                    return "slimShady";
                  }

                  java.lang.String eminem() {
                    return "eminem";
                  }

                  java.lang.String marshallMathers() {
                    return "marshallMathers";
                  }
                }

            """.trimIndent(),
            TypeSpecs.classOf("HelloWorld") {
                addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                methods {
                    nameMethod("slimShady")
                    nameMethod("eminem")
                    nameMethod("marshallMathers")
                }
            }.toString()
        )
    }

    @Test
    fun `$TForTypes`() {
        assertEquals(
            """
                public final class HelloWorld {
                  java.util.Date today() {
                    return new java.util.Date();
                  }
                }

            """.trimIndent(),
            TypeSpecs.classOf("HelloWorld") {
                addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                methods {
                    "today" {
                        returns<Date>()
                        codes.appendln("return new %T()", Date::class)
                    }
                }
            }.toString()
        )
        assertEquals(
            """
                public final class HelloWorld {
                  com.mattel.Hoverboard tomorrow() {
                    return new com.mattel.Hoverboard();
                  }
                }

            """.trimIndent(),
            TypeSpecs.classOf("HelloWorld") {
                addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                methods {
                    "tomorrow" {
                        val hoverboard = ClassName.get("com.mattel", "Hoverboard")
                        returns = hoverboard
                        codes.appendln("return new %T()", hoverboard)
                    }
                }
            }.toString()
        )
        assertEquals(
            """
                public final class HelloWorld {
                  java.util.List<com.mattel.Hoverboard> beyond() {
                    java.util.List<com.mattel.Hoverboard> result = new java.util.ArrayList<>();
                    result.add(new com.mattel.Hoverboard());
                    result.add(new com.mattel.Hoverboard());
                    result.add(new com.mattel.Hoverboard());
                    return result;
                  }
                }

            """.trimIndent(),
            TypeSpecs.classOf("HelloWorld") {
                addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                methods {
                    "beyond" {
                        val hoverboard = ClassName.get("com.mattel", "Hoverboard")
                        val list = ClassName.get("java.util", "List");
                        val arrayList = ClassName.get("java.util", "ArrayList")
                        val listOfHoverboards = ParameterizedTypeName.get(list, hoverboard)
                        returns = listOfHoverboards
                        codes {
                            appendln("%T result = new %T<>()", listOfHoverboards, arrayList)
                            appendln("result.add(new %T())", hoverboard)
                            appendln("result.add(new %T())", hoverboard)
                            appendln("result.add(new %T())", hoverboard)
                            appendln("return result")
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
            JavaFiles.of("com.example.helloworld") {
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
                                appendln("%T result = new %T<>()", listOfHoverboards, arrayList)
                                appendln("result.add(%T.createNimbus(2000))", hoverboard)
                                appendln("result.add(%T.createNimbus(\"2001\"))", hoverboard)
                                appendln("result.add(%T.createNimbus(%T.THUNDERBOLT))", hoverboard, namedBoards)
                                appendln("%T.sort(result)", Collections::class)
                                appendln(
                                    "return result.isEmpty() ? %T.emptyList() : result", Collections::class
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
        val hexDigit = MethodSpecs.of("hexDigit") {
            addModifiers(Modifier.PUBLIC)
            parameters.add(ClassName.INT, "i")
            returns = ClassName.CHAR
            codes.appendln("return (char) (i < 10 ? i + '0' : i - 10 + 'a')")
        }
        val byteToHex = MethodSpecs.of("byteToHex") {
            addModifiers(Modifier.PUBLIC)
            parameters.add(ClassName.INT, "b")
            returns<String>()
            codes {
                appendln("char[] result = new char[2]")
                appendln("result[0] = %N((b >>> 4) & 0xf)", hexDigit)
                appendln("result[1] = %N(b & 0xf)", hexDigit)
                appendln("return new String(result)")
            }
        }
        assertEquals(
            """
                public java.lang.String byteToHex(int b) {
                  char[] result = new char[2];
                  result[0] = hexDigit((b >>> 4) & 0xf);
                  result[1] = hexDigit(b & 0xf);
                  return new String(result);
                }

                public char hexDigit(int i) {
                  return (char) (i < 10 ? i + '0' : i - 10 + 'a');
                }

            """.trimIndent(),
            "$byteToHex\n$hexDigit"
        )
    }

    @Test
    fun codeBlockFormatStrings() {
        assertEquals(
            CodeBlock.builder().add("I ate \$L \$L", 3, "tacos").build(),
            CodeBlocks["I ate %L %L", 3, "tacos"]
        )
        assertEquals(
            CodeBlock.builder().add("I ate \$2L \$1L", "tacos", 3).build(),
            CodeBlocks["I ate %2L %1L", "tacos", 3]
        )
    }

    @Test
    fun methods() {
        assertEquals(
            """
                public abstract class HelloWorld {
                  protected abstract void flux();
                }
                
            """.trimIndent(),
            TypeSpecs.classOf("HelloWorld") {
                addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                methods.add("flux") {
                    addModifiers(Modifier.PROTECTED, Modifier.ABSTRACT)
                }
            }.toString()
        )
    }

    @Test
    fun constructors() {
        assertEquals(
            """
                public class HelloWorld {
                  private final java.lang.String greeting;

                  public HelloWorld(java.lang.String greeting) {
                    this.greeting = greeting;
                  }
                }
                
            """.trimIndent(),
            TypeSpecs.classOf("HelloWorld") {
                addModifiers(Modifier.PUBLIC)
                fields.add<String>("greeting") {
                    addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                }
                methods.addConstructor {
                    addModifiers(Modifier.PUBLIC)
                    parameters.add<String>("greeting")
                    codes.appendln("this.%N = %N", "greeting", "greeting")
                }
            }.toString()
        )
    }

    @Test
    fun parameters() {
        /*assertEquals(
            """
                void welcomeOverlords(final String android, final String robot) {
                }

            """.trimIndent(),
            (ParameterSpec.builder())
        )*/
    }

    private fun MethodContainerScope.nameMethod(name: String) {
        name {
            returns<String>()
            codes.appendln("return %S", name)
        }
    }
}