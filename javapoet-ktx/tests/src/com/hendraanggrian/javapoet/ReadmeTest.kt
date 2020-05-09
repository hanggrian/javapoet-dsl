package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.MethodSpecContainerScope
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.MethodSpec
import java.util.Collections
import java.util.Date
import javax.lang.model.element.Modifier
import kotlin.test.Test
import kotlin.test.assertEquals

/** From `https://github.com/square/javapoet`. */
class ReadmeTest {

    @Test fun example() {
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
                    methods.add("main") {
                        addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        returns = VOID
                        parameters.add<Array<String>>("args")
                        appendln("%T.out.println(%S)", System::class, "Hello, JavaPoet!")
                    }
                }
            }.toString()
        )
    }

    @Test fun `code&ControlFlow`() {
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
            buildMethodSpec("main") {
                returns = VOID
                append(
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
            buildMethodSpec("main") {
                returns = VOID
                appendln("int total = 0")
                beginFlow("for (int i = 0; i < 10; i++)")
                appendln("total += i")
                endFlow()
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
            buildMethodSpec("multiply10to20") {
                returns = INT
                appendln("int result = 1")
                beginFlow("for (int i = 10; i < 20; i++)")
                appendln("result = result * i")
                endFlow()
                appendln("return result")
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
            buildMethodSpec("main") {
                appendln("long now = %T.currentTimeMillis()", System::class)
                beginFlow("if (%T.currentTimeMillis() < now)", System::class)
                appendln("%T.out.println(%S)", System::class, "Time travelling, woo hoo!")
                nextFlow("else if (%T.currentTimeMillis() == now)", System::class)
                appendln("%T.out.println(%S)", System::class, "Time stood still!")
                nextFlow("else")
                appendln("%T.out.println(%S)", System::class, "Ok, time still moving forward")
                endFlow()
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
            buildMethodSpec("main") {
                beginFlow("try")
                appendln("throw new Exception(%S)", "Failed")
                nextFlow("catch (%T e)", Exception::class)
                appendln("throw new %T(e)", RuntimeException::class)
                endFlow()
            }.toString()
        )
    }

    @Test fun `$LForLiterals`() {
        assertEquals(
            """
                int computeRange() {
                  int result = 0;
                  for (int i = 0; i < 10; i++) {
                    result = result += i;
                  }
                  return result;
                }

            """.trimIndent(),
            buildMethodSpec("computeRange") {
                returns = INT
                appendln("int result = 0")
                beginFlow("for (int i = %L; i < %L; i++)", 0, 10)
                appendln("result = result %L i", "+=")
                endFlow()
                appendln("return result")
            }.toString()
        )
    }

    @Test fun `$SForStrings`() {
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
            buildClassTypeSpec("HelloWorld") {
                addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                methods {
                    whatsMyName("slimShady")
                    whatsMyName("eminem")
                    whatsMyName("marshallMathers")
                }
            }.toString()
        )
    }

    @Test fun `$TForTypes`() {
        assertEquals(
            """
                public final class HelloWorld {
                  java.util.Date today() {
                    return new java.util.Date();
                  }
                }

            """.trimIndent(),
            buildClassTypeSpec("HelloWorld") {
                addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                methods.add("today") {
                    returns<Date>()
                    appendln("return new %T()", Date::class)
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
            buildClassTypeSpec("HelloWorld") {
                addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                methods.add("tomorrow") {
                    val hoverboard = "com.mattel".classOf("Hoverboard")
                    returns = hoverboard
                    appendln("return new %T()", hoverboard)
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
            buildClassTypeSpec("HelloWorld") {
                addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                methods.add("beyond") {
                    val hoverboard = "com.mattel".classOf("Hoverboard")
                    val arrayList = "java.util".classOf("ArrayList")
                    val listOfHoverboards = "java.util".classOf("List").parameterizedBy(hoverboard)
                    returns = listOfHoverboards
                    appendln("%T result = new %T<>()", listOfHoverboards, arrayList)
                    appendln("result.add(new %T())", hoverboard)
                    appendln("result.add(new %T())", hoverboard)
                    appendln("result.add(new %T())", hoverboard)
                    appendln("return result")
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
                val hoverboard = "com.mattel".classOf("Hoverboard")
                val namedBoards = "com.mattel".classOf("Hoverboard", "Boards")
                addStaticImport(hoverboard, "createNimbus")
                addStaticImport(namedBoards, "*")
                addStaticImport<Collections>("*")
                addClass("HelloWorld") {
                    addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    methods.add("beyond") {
                        val arrayList = "java.util".classOf("ArrayList")
                        val listOfHoverboards = "java.util".classOf("List").parameterizedBy(hoverboard)
                        returns = listOfHoverboards
                        appendln("%T result = new %T<>()", listOfHoverboards, arrayList)
                        appendln("result.add(%T.createNimbus(2000))", hoverboard)
                        appendln("result.add(%T.createNimbus(\"2001\"))", hoverboard)
                        appendln("result.add(%T.createNimbus(%T.THUNDERBOLT))", hoverboard, namedBoards)
                        appendln("%T.sort(result)", Collections::class)
                        appendln("return result.isEmpty() ? %T.emptyList() : result", Collections::class)
                    }
                }
            }.toString()
        )
    }

    @Test fun `$NForNames`() {
        val hexDigit = buildMethodSpec("hexDigit") {
            addModifiers(Modifier.PUBLIC)
            parameters.add(INT, "i")
            returns = CHAR
            appendln("return (char) (i < 10 ? i + '0' : i - 10 + 'a')")
        }
        val byteToHex = buildMethodSpec("byteToHex") {
            addModifiers(Modifier.PUBLIC)
            parameters.add(INT, "b")
            returns<String>()
            appendln("char[] result = new char[2]")
            appendln("result[0] = %N((b >>> 4) & 0xf)", hexDigit)
            appendln("result[1] = %N(b & 0xf)", hexDigit)
            appendln("return new String(result)")
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

    @Test fun codeBlockFormatStrings() {
        assertEquals(
            CodeBlock.of("I ate \$L \$L", 3, "tacos"),
            codeBlockOf("I ate %L %L", 3, "tacos")
        )
        assertEquals(
            CodeBlock.of("I ate \$2L \$1L", "tacos", 3),
            codeBlockOf("I ate %2L %1L", "tacos", 3)
        )
    }

    @Test fun methods() {
        assertEquals(
            """
                public abstract class HelloWorld {
                  protected abstract void flux();
                }
                
            """.trimIndent(),
            buildClassTypeSpec("HelloWorld") {
                addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                methods.add("flux") {
                    addModifiers(Modifier.PROTECTED, Modifier.ABSTRACT)
                }
            }.toString()
        )
    }

    @Test fun constructors() {
        assertEquals(
            """
                public class HelloWorld {
                  private final java.lang.String greeting;

                  public HelloWorld(java.lang.String greeting) {
                    this.greeting = greeting;
                  }
                }
                
            """.trimIndent(),
            buildClassTypeSpec("HelloWorld") {
                addModifiers(Modifier.PUBLIC)
                fields.add<String>("greeting", Modifier.PRIVATE, Modifier.FINAL)
                methods.addConstructor {
                    addModifiers(Modifier.PUBLIC)
                    parameters.add<String>("greeting")
                    appendln("this.%N = %N", "greeting", "greeting")
                }
            }.toString()
        )
    }

    @Test fun parameters() {
        assertEquals(
            """
                void welcomeOverlords(final java.lang.String android, final java.lang.String robot) {
                }

            """.trimIndent(),
            buildMethodSpec("welcomeOverlords") {
                parameters {
                    add<String>("android", Modifier.FINAL)
                    add<String>("robot", Modifier.FINAL)
                }
            }.toString()
        )
    }

    @Test fun fields() {
        assertEquals(
            """
                public class HelloWorld {
                  private final java.lang.String android;

                  private final java.lang.String robot;
                }
                
            """.trimIndent(),
            buildClassTypeSpec("HelloWorld") {
                addModifiers(Modifier.PUBLIC)
                fields {
                    add<String>("android", Modifier.PRIVATE, Modifier.FINAL)
                    add<String>("robot", Modifier.PRIVATE, Modifier.FINAL)
                }
            }.toString()
        )
        assertEquals(
            "private final java.lang.String android = \"Lollipop v.\" + 5.0;\n",
            buildFieldSpec<String>("android", Modifier.PRIVATE, Modifier.FINAL) {
                initializer("\"Lollipop v.\" + 5.0")
            }.toString()
        )
    }

    @Test fun interfaces() {
        assertEquals(
            """
                public interface HelloWorld {
                  java.lang.String ONLY_THING_THAT_IS_CONSTANT = "change";

                  void beep();
                }
                
            """.trimIndent(),
            buildInterfaceTypeSpec("HelloWorld") {
                addModifiers(Modifier.PUBLIC)
                fields.add<String>("ONLY_THING_THAT_IS_CONSTANT", Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL) {
                    initializer("%S", "change")
                }
                methods.add("beep") {
                    addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                }
            }.toString()
        )
    }

    @Test fun enums() {
        assertEquals(
            """
                public enum Roshambo {
                  ROCK,

                  SCISSORS,

                  PAPER
                }
                
            """.trimIndent(),
            buildEnumTypeSpec("Roshambo") {
                addModifiers(Modifier.PUBLIC)
                addEnumConstant("ROCK")
                addEnumConstant("SCISSORS")
                addEnumConstant("PAPER")
            }.toString()
        )
        assertEquals(
            """
                public enum Roshambo {
                  ROCK("fist") {
                    @java.lang.Override
                    public java.lang.String toString() {
                      return "avalanche!";
                    }
                  },

                  SCISSORS("peace"),

                  PAPER("flat");

                  private final java.lang.String handsign;

                  Roshambo(java.lang.String handsign) {
                    this.handsign = handsign;
                  }
                }
                
            """.trimIndent(),
            buildEnumTypeSpec("Roshambo") {
                addModifiers(Modifier.PUBLIC)
                addEnumConstant("ROCK", buildAnonymousTypeSpec("%S", "fist") {
                    methods.add("toString") {
                        annotations.add<Override>()
                        addModifiers(Modifier.PUBLIC)
                        appendln("return %S", "avalanche!")
                        returns<String>()
                    }
                })
                addEnumConstant("SCISSORS", anonymousTypeSpecOf("%S", "peace"))
                addEnumConstant("PAPER", anonymousTypeSpecOf("%S", "flat"))
                fields.add<String>("handsign", Modifier.PRIVATE, Modifier.FINAL)
                methods.addConstructor {
                    parameters.add<String>("handsign")
                    appendln("this.%N = %N", "handsign", "handsign")
                }
            }.toString()
        )
    }

    @Test fun anonymousInnerClasses() {
        lateinit var sortByLength: MethodSpec
        buildClassTypeSpec("HelloWorld") {
            sortByLength = methods.add("sortByLength") {
                parameters.add(List::class.parameterizedBy(String::class), "strings")
                appendln("%T.sort(%N, %L)", Collections::class, "strings", buildAnonymousTypeSpec("") {
                    addSuperinterface(Comparator::class.parameterizedBy(String::class))
                    methods.add("compare") {
                        annotations.add<Override>()
                        addModifiers(Modifier.PUBLIC)
                        parameters {
                            add<String>("a")
                            add<String>("b")
                        }
                        returns = INT
                        appendln("return %N.length() - %N.length()", "a", "b")
                    }
                })
            }
        }
        assertEquals(
            """
                void sortByLength(java.util.List<java.lang.String> strings) {
                  java.util.Collections.sort(strings, new java.util.Comparator<java.lang.String>() {
                    @java.lang.Override
                    public int compare(java.lang.String a, java.lang.String b) {
                      return a.length() - b.length();
                    }
                  });
                }
                
            """.trimIndent(),
            sortByLength.toString()
        )
    }

    @Test fun annotations() {
        assertEquals(
            """
                @java.lang.Override
                public java.lang.String toString() {
                  return "Hoverboard";
                }
                
            """.trimIndent(),
            buildMethodSpec("toString") {
                annotations.add<Override>()
                returns<String>()
                addModifiers(Modifier.PUBLIC)
                appendln("return %S", "Hoverboard")
            }.toString()
        )
        assertEquals(
            """
                @com.hendraanggrian.javapoet.ReadmeTest.Headers(
                    accept = "application/json; charset=utf-8",
                    userAgent = "Square Cash"
                )
                public abstract com.hendraanggrian.javapoet.ReadmeTest.LogReceipt recordEvent(
                    com.hendraanggrian.javapoet.ReadmeTest.LogRecord logRecord);
                
            """.trimIndent(),
            buildMethodSpec("recordEvent") {
                addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                annotations.add<Headers> {
                    addMember("accept", "%S", "application/json; charset=utf-8")
                    addMember("userAgent", "%S", "Square Cash")
                }
                parameters.add<LogRecord>("logRecord")
                returns<LogReceipt>()
            }.toString()
        )
        assertEquals(
            """
                @com.hendraanggrian.javapoet.ReadmeTest.HeaderList({
                    @com.hendraanggrian.javapoet.ReadmeTest.Header(name = "Accept", value = "application/json; charset=utf-8"),
                    @com.hendraanggrian.javapoet.ReadmeTest.Header(name = "User-Agent", value = "Square Cash")
                })
                public abstract com.hendraanggrian.javapoet.ReadmeTest.LogReceipt recordEvent(
                    com.hendraanggrian.javapoet.ReadmeTest.LogRecord logRecord);
                
            """.trimIndent(),
            buildMethodSpec("recordEvent") {
                addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                annotations.add<HeaderList> {
                    addMember("value", "%L", buildAnnotationSpec<Header> {
                        addMember("name", "%S", "Accept")
                        addMember("value", "%S", "application/json; charset=utf-8")
                    })
                    addMember("value", "%L", buildAnnotationSpec<Header> {
                        addMember("name", "%S", "User-Agent")
                        addMember("value", "%S", "Square Cash")
                    })
                }
                parameters.add<LogRecord>("logRecord")
                returns<LogReceipt>()
            }.toString()
        )
    }

    @Test fun javadoc() {
        assertEquals(
            """
                /**
                 * Hides {@code message} from the caller's history. Other
                 * participants in the conversation will continue to see the
                 * message in their own history unless they also delete it.
                 *
                 * <p>Use {@link #delete(com.hendraanggrian.javapoet.ReadmeTest.Conversation)} to delete the entire
                 * conversation for all participants.
                 */
                public abstract void dismiss(com.hendraanggrian.javapoet.ReadmeTest.Message message);

            """.trimIndent(),
            buildMethodSpec("dismiss") {
                javadoc {
                    appendln("Hides {@code message} from the caller's history. Other")
                    appendln("participants in the conversation will continue to see the")
                    appendln("message in their own history unless they also delete it.")
                    appendln()
                    append(
                        "<p>Use {@link #delete(%T)} to delete the entire\n"
                            + "conversation for all participants.\n", Conversation::class
                    )
                }
                addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                parameters.add<Message>("message")
            }.toString()
        )
    }

    private fun MethodSpecContainerScope.whatsMyName(name: String) {
        name {
            returns<String>()
            appendln("return %S", name)
        }
    }

    private class Headers
    private class Header
    private class HeaderList
    private class LogRecord
    private class LogReceipt

    private class Message
    private class Conversation
}