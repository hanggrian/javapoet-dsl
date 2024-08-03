package com.hanggrian.javapoet

import com.squareup.javapoet.CodeBlock
import java.util.Collections
import java.util.Date
import kotlin.test.Test
import kotlin.test.assertEquals

/** From `https://github.com/square/javapoet`. */
class GitHubTest {
    @Test
    fun `Example`() {
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
                classType("HelloWorld") {
                    modifiers(PUBLIC, FINAL)
                    method("main") {
                        modifiers(PUBLIC, STATIC)
                        returns = VOID
                        parameter(STRING.array, "args")
                        appendLine("%T.out.println(%S)", System::class, "Hello, JavaPoet!")
                    }
                }
            }.toString(),
        )
    }

    @Test
    fun `Code & Control Flow`() {
        assertEquals(
            """
            void main() {
              int total = 0;
              for (int i = 0; i < 10; i++) {
                total += i;
              }
            }

            """.trimIndent(),
            buildMethodSpec("main") {
                returns = VOID
                appendLine("int total = 0")
                beginControlFlow("for (int i = 0; i < 10; i++)")
                appendLine("total += i")
                endControlFlow()
            }.toString(),
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
                appendLine("int result = 1")
                beginControlFlow("for (int i = 10; i < 20; i++)")
                appendLine("result = result * i")
                endControlFlow()
                appendLine("return result")
            }.toString(),
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
                appendLine("long now = %T.currentTimeMillis()", System::class)
                beginControlFlow("if (%T.currentTimeMillis() < now)", System::class)
                appendLine("%T.out.println(%S)", System::class, "Time travelling, woo hoo!")
                nextControlFlow("else if (%T.currentTimeMillis() == now)", System::class)
                appendLine("%T.out.println(%S)", System::class, "Time stood still!")
                nextControlFlow("else")
                appendLine("%T.out.println(%S)", System::class, "Ok, time still moving forward")
                endControlFlow()
            }.toString(),
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
                beginControlFlow("try")
                appendLine("throw new Exception(%S)", "Failed")
                nextControlFlow("catch (%T e)", Exception::class)
                appendLine("throw new %T(e)", RuntimeException::class)
                endControlFlow()
            }.toString(),
        )
    }

    @Test
    fun `$L for Literals`() {
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
                appendLine("int result = 0")
                beginControlFlow("for (int i = %L; i < %L; i++)", 0, 10)
                appendLine("result = result %L i", "+=")
                endControlFlow()
                appendLine("return result")
            }.toString(),
        )
    }

    @Test
    fun `$S for Strings`() {
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
                modifiers(PUBLIC, FINAL)
                methods {
                    "slimShady" {
                        returns<String>()
                        appendLine("return %S", "slimShady")
                    }
                    "eminem" {
                        returns<String>()
                        appendLine("return %S", "eminem")
                    }
                    "marshallMathers" {
                        returns<String>()
                        appendLine("return %S", "marshallMathers")
                    }
                }
            }.toString(),
        )
    }

    @Test
    fun `$T for Types`() {
        assertEquals(
            """
            public final class HelloWorld {
              java.util.Date today() {
                return new java.util.Date();
              }
            }

            """.trimIndent(),
            buildClassTypeSpec("HelloWorld") {
                modifiers(PUBLIC, FINAL)
                method("today") {
                    returns<Date>()
                    appendLine("return new %T()", Date::class)
                }
            }.toString(),
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
                modifiers(PUBLIC, FINAL)
                method("tomorrow") {
                    val hoverboard = classNamed("com.mattel", "Hoverboard")
                    returns = hoverboard
                    appendLine("return new %T()", hoverboard)
                }
            }.toString(),
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
                modifiers(PUBLIC, FINAL)
                method("beyond") {
                    val hoverboard = classNamed("com.mattel", "Hoverboard")
                    val arrayList = classNamed("java.util", "ArrayList")
                    val listOfHoverboards =
                        classNamed("java.util", "List")
                            .parameterizedBy(hoverboard)
                    returns = listOfHoverboards
                    appendLine("%T result = new %T<>()", listOfHoverboards, arrayList)
                    appendLine("result.add(new %T())", hoverboard)
                    appendLine("result.add(new %T())", hoverboard)
                    appendLine("result.add(new %T())", hoverboard)
                    appendLine("return result")
                }
            }.toString(),
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
                val hoverboard = classNamed("com.mattel", "Hoverboard")
                val namedBoards = classNamed("com.mattel", "Hoverboard", "Boards")
                staticImport(hoverboard, "createNimbus")
                staticImport(namedBoards, "*")
                staticImport<Collections>("*")
                classType("HelloWorld") {
                    modifiers(PUBLIC, FINAL)
                    method("beyond") {
                        val arrayList = classNamed("java.util", "ArrayList")
                        val listOfHoverboards =
                            classNamed("java.util", "List")
                                .parameterizedBy(hoverboard)
                        returns = listOfHoverboards
                        appendLine("%T result = new %T<>()", listOfHoverboards, arrayList)
                        appendLine("result.add(%T.createNimbus(2000))", hoverboard)
                        appendLine("result.add(%T.createNimbus(\"2001\"))", hoverboard)
                        appendLine(
                            "result.add(%T.createNimbus(%T.THUNDERBOLT))",
                            hoverboard,
                            namedBoards,
                        )
                        appendLine("%T.sort(result)", Collections::class)
                        appendLine(
                            "return result.isEmpty() ? %T.emptyList() : result",
                            Collections::class,
                        )
                    }
                }
            }.toString(),
        )
    }

    @Test
    fun `$N for Names`() {
        val hexDigit =
            buildMethodSpec("hexDigit") {
                modifiers(PUBLIC)
                parameter(INT, "i")
                returns = CHAR
                appendLine("return (char) (i < 10 ? i + '0' : i - 10 + 'a')")
            }
        val byteToHex =
            buildMethodSpec("byteToHex") {
                modifiers(PUBLIC)
                parameter(INT, "b")
                returns<String>()
                appendLine("char[] result = new char[2]")
                appendLine("result[0] = %N((b >>> 4) & 0xf)", hexDigit)
                appendLine("result[1] = %N(b & 0xf)", hexDigit)
                appendLine("return new String(result)")
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
            "$byteToHex\n$hexDigit",
        )
    }

    @Test
    fun `Code block format strings`() {
        assertEquals(
            CodeBlock.of("I ate \$L \$L", 3, "tacos"),
            codeBlockOf("I ate %L %L", 3, "tacos"),
        )
        assertEquals(
            CodeBlock.of("I ate \$2L \$1L", "tacos", 3),
            codeBlockOf("I ate %2L %1L", "tacos", 3),
        )
        val templates = linkedMapOf<String, Any>()
        templates["food"] = "tacos"
        templates["count"] = 3
        assertEquals(
            CodeBlock.builder().addNamed("I ate \$count:L \$food:L", templates).build(),
            buildCodeBlock { appendNamed("I ate %count:L %food:L", templates) },
        )
    }

    @Test
    fun `Methods`() {
        assertEquals(
            """
            public abstract class HelloWorld {
              protected abstract void flux();
            }

            """.trimIndent(),
            buildClassTypeSpec("HelloWorld") {
                modifiers(PUBLIC, ABSTRACT)
                method("flux") {
                    modifiers(PROTECTED, ABSTRACT)
                }
            }.toString(),
        )
    }

    @Test
    fun `Constructors`() {
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
                modifiers(PUBLIC)
                field<String>("greeting", PRIVATE, FINAL)
                constructorMethod {
                    modifiers(PUBLIC)
                    parameter<String>("greeting")
                    appendLine("this.%N = %N", "greeting", "greeting")
                }
            }.toString(),
        )
    }

    @Test
    fun `Parameters`() {
        assertEquals(
            """
            void welcomeOverlords(final java.lang.String android, final java.lang.String robot) {
            }

            """.trimIndent(),
            buildMethodSpec("welcomeOverlords") {
                parameter<String>("android", FINAL)
                parameter<String>("robot", FINAL)
            }.toString(),
        )
    }

    @Test
    fun `Fields`() {
        assertEquals(
            """
            public class HelloWorld {
              private final java.lang.String android;

              private final java.lang.String robot;
            }

            """.trimIndent(),
            buildClassTypeSpec("HelloWorld") {
                modifiers(PUBLIC)
                field<String>("android", PRIVATE, FINAL)
                field<String>("robot", PRIVATE, FINAL)
            }.toString(),
        )
        assertEquals(
            """
            private final java.lang.String android = "Lollipop v." + 5.0;

            """.trimIndent(),
            buildFieldSpec(STRING, "android", PRIVATE, FINAL) {
                initializer("\"Lollipop v.\" + 5.0")
            }.toString(),
        )
    }

    @Test
    fun `Interfaces`() {
        assertEquals(
            """
            public interface HelloWorld {
              java.lang.String ONLY_THING_THAT_IS_CONSTANT = "change";

              void beep();
            }

            """.trimIndent(),
            buildInterfaceTypeSpec("HelloWorld") {
                modifiers(PUBLIC)
                field(String::class, "ONLY_THING_THAT_IS_CONSTANT", PUBLIC, STATIC, FINAL) {
                    initializer("%S", "change")
                }
                method("beep") {
                    modifiers(PUBLIC, ABSTRACT)
                }
            }.toString(),
        )
    }

    @Test
    fun `Enums`() {
        assertEquals(
            """
            public enum Roshambo {
              ROCK,

              SCISSORS,

              PAPER
            }

            """.trimIndent(),
            buildEnumTypeSpec("Roshambo") {
                modifiers(PUBLIC)
                enumConstant("ROCK")
                enumConstant("SCISSORS")
                enumConstant("PAPER")
            }.toString(),
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
                modifiers(PUBLIC)
                enumConstants["ROCK"] =
                    buildAnonymousTypeSpec("%S", "fist") {
                        method("toString") {
                            annotation<Override>()
                            modifiers(PUBLIC)
                            appendLine("return %S", "avalanche!")
                            returns<String>()
                        }
                    }
                enumConstant("SCISSORS", buildAnonymousTypeSpec("%S", "peace") { })
                enumConstant("PAPER", buildAnonymousTypeSpec("%S", "flat") { })
                field<String>("handsign", PRIVATE, FINAL)
                constructorMethod {
                    parameter<String>("handsign")
                    appendLine("this.%N = %N", "handsign", "handsign")
                }
            }.toString(),
        )
    }

    @Test
    fun `Anonymous Inner Classes`() {
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
            buildMethodSpec("sortByLength") {
                parameter(LIST.parameterizedBy<String>(), "strings")
                appendLine(
                    "%T.sort(%N, %L)",
                    Collections::class,
                    "strings",
                    buildAnonymousTypeSpec("") {
                        superinterfaces += Comparator::class.name.parameterizedBy<String>()
                        method("compare") {
                            annotation<Override>()
                            modifiers(PUBLIC)
                            parameter<String>("a")
                            parameter<String>("b")
                            returns = INT
                            appendLine("return %N.length() - %N.length()", "a", "b")
                        }
                    },
                )
            }.toString(),
        )
    }

    @Test
    fun `Annotations`() {
        assertEquals(
            """
            @java.lang.Override
            public java.lang.String toString() {
              return "Hoverboard";
            }

            """.trimIndent(),
            buildMethodSpec("toString") {
                annotation<Override>()
                returns<String>()
                modifiers(PUBLIC)
                appendLine("return %S", "Hoverboard")
            }.toString(),
        )
        val headers = classNamed("com.example", "Headers")
        val logRecord = classNamed("com.example", "LogRecord")
        val logReceipt = classNamed("com.example", "LogReceipt")
        assertEquals(
            """
            @com.example.Headers(
                accept = "application/json; charset=utf-8",
                userAgent = "Square Cash"
            )
            public abstract com.example.LogReceipt recordEvent(com.example.LogRecord logRecord);

            """.trimIndent(),
            buildMethodSpec("recordEvent") {
                modifiers(PUBLIC, ABSTRACT)
                annotation(headers) {
                    member("accept", "%S", "application/json; charset=utf-8")
                    member("userAgent", "%S", "Square Cash")
                }
                parameter(logRecord, "logRecord")
                returns = logReceipt
            }.toString(),
        )
        val header = classNamed("com.example", "Header")
        val headerList = classNamed("com.example", "HeaderList")
        assertEquals(
            """
            @com.example.HeaderList({
                @com.example.Header(name = "Accept", value = "application/json; charset=utf-8"),
                @com.example.Header(name = "User-Agent", value = "Square Cash")
            })
            public abstract com.example.LogReceipt recordEvent(com.example.LogRecord logRecord);

            """.trimIndent(),
            buildMethodSpec("recordEvent") {
                modifiers(PUBLIC, ABSTRACT)
                annotation(headerList) {
                    member(
                        "value",
                        "%L",
                        buildAnnotationSpec(header) {
                            member("name", "%S", "Accept")
                            member("value", "%S", "application/json; charset=utf-8")
                        },
                    )
                    member(
                        "value",
                        "%L",
                        buildAnnotationSpec(header) {
                            member("name", "%S", "User-Agent")
                            member("value", "%S", "Square Cash")
                        },
                    )
                }
                parameter(logRecord, "logRecord")
                returns = logReceipt
            }.toString(),
        )
    }

    @Test
    fun `Javadoc`() {
        val message = classNamed("com.example", "Message")
        val conversation = classNamed("com.example", "Conversation")
        assertEquals(
            """
            /**
             * Hides {@code message} from the caller's history. Other
             * participants in the conversation will continue to see the
             * message in their own history unless they also delete it.
             *
             * <p>Use {@link #delete(com.example.Conversation)} to delete the entire
             * conversation for all participants.
             */
            public abstract void dismiss(com.example.Message message);

            """.trimIndent(),
            buildMethodSpec("dismiss") {
                javadoc("Hides {@code message} from the caller's history. Other\n")
                javadoc("participants in the conversation will continue to see the\n")
                javadoc("message in their own history unless they also delete it.\n\n")
                javadoc(
                    """
                    <p>Use {@link #delete(%T)} to delete the entire
                    conversation for all participants.
                    """.trimIndent(),
                    conversation,
                )
                modifiers(PUBLIC, ABSTRACT)
                parameter(message, "message")
            }.toString(),
        )
    }
}
