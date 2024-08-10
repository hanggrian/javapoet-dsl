package com.hanggrian.javapoet

import com.google.common.truth.Truth.assertThat
import com.squareup.javapoet.CodeBlock
import java.util.Collections
import java.util.Date
import kotlin.test.Test

/** From `https://github.com/square/javapoet`. */
class GitHubTest {
    @Test
    fun `Example`() {
        assertThat(
            buildJavaFile("com.example.helloworld") {
                types.addClass("HelloWorld") {
                    addModifiers(PUBLIC, FINAL)
                    methods.add("main") {
                        addModifiers(PUBLIC, STATIC)
                        returns = VOID
                        parameters.add(STRING.array, "args")
                        appendLine("%T.out.println(%S)", System::class, "Hello, JavaPoet!")
                    }
                }
            }.toString(),
        ).isEqualTo(
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
        )
    }

    @Test
    fun `Code & Control Flow`() {
        assertThat(
            buildMethodSpec("main") {
                returns = VOID
                appendLine("int total = 0")
                beginControlFlow("for (int i = 0; i < 10; i++)")
                appendLine("total += i")
                endControlFlow()
            }.toString(),
        ).isEqualTo(
            """
            void main() {
              int total = 0;
              for (int i = 0; i < 10; i++) {
                total += i;
              }
            }

            """.trimIndent(),
        )
        assertThat(
            buildMethodSpec("multiply10to20") {
                returns = INT
                appendLine("int result = 1")
                beginControlFlow("for (int i = 10; i < 20; i++)")
                appendLine("result = result * i")
                endControlFlow()
                appendLine("return result")
            }.toString(),
        ).isEqualTo(
            """
            int multiply10to20() {
              int result = 1;
              for (int i = 10; i < 20; i++) {
                result = result * i;
              }
              return result;
            }

            """.trimIndent(),
        )
        assertThat(
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
        ).isEqualTo(
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
        )
        assertThat(
            buildMethodSpec("main") {
                beginControlFlow("try")
                appendLine("throw new Exception(%S)", "Failed")
                nextControlFlow("catch (%T e)", Exception::class)
                appendLine("throw new %T(e)", RuntimeException::class)
                endControlFlow()
            }.toString(),
        ).isEqualTo(
            """
            void main() {
              try {
                throw new Exception("Failed");
              } catch (java.lang.Exception e) {
                throw new java.lang.RuntimeException(e);
              }
            }

            """.trimIndent(),
        )
    }

    @Test
    fun `$L for Literals`() {
        assertThat(
            buildMethodSpec("computeRange") {
                returns = INT
                appendLine("int result = 0")
                beginControlFlow("for (int i = %L; i < %L; i++)", 0, 10)
                appendLine("result = result %L i", "+=")
                endControlFlow()
                appendLine("return result")
            }.toString(),
        ).isEqualTo(
            """
            int computeRange() {
              int result = 0;
              for (int i = 0; i < 10; i++) {
                result = result += i;
              }
              return result;
            }

            """.trimIndent(),
        )
    }

    @Test
    fun `$S for Strings`() {
        assertThat(
            buildClassTypeSpec("HelloWorld") {
                addModifiers(PUBLIC, FINAL)
                methods {
                    "slimShady" {
                        setReturns<String>()
                        appendLine("return %S", "slimShady")
                    }
                    "eminem" {
                        setReturns<String>()
                        appendLine("return %S", "eminem")
                    }
                    "marshallMathers" {
                        setReturns<String>()
                        appendLine("return %S", "marshallMathers")
                    }
                }
            }.toString(),
        ).isEqualTo(
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
        )
    }

    @Test
    fun `$T for Types`() {
        assertThat(
            buildClassTypeSpec("HelloWorld") {
                addModifiers(PUBLIC, FINAL)
                methods.add("today") {
                    setReturns<Date>()
                    appendLine("return new %T()", Date::class)
                }
            }.toString(),
        ).isEqualTo(
            """
            public final class HelloWorld {
              java.util.Date today() {
                return new java.util.Date();
              }
            }

            """.trimIndent(),
        )
        assertThat(
            buildClassTypeSpec("HelloWorld") {
                addModifiers(PUBLIC, FINAL)
                methods.add("tomorrow") {
                    val hoverboard = classNamed("com.mattel", "Hoverboard")
                    returns = hoverboard
                    appendLine("return new %T()", hoverboard)
                }
            }.toString(),
        ).isEqualTo(
            """
            public final class HelloWorld {
              com.mattel.Hoverboard tomorrow() {
                return new com.mattel.Hoverboard();
              }
            }

            """.trimIndent(),
        )
        assertThat(
            buildClassTypeSpec("HelloWorld") {
                addModifiers(PUBLIC, FINAL)
                methods.add("beyond") {
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
        ).isEqualTo(
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
        )
        assertThat(
            buildJavaFile("com.example.helloworld") {
                val hoverboard = classNamed("com.mattel", "Hoverboard")
                val namedBoards = classNamed("com.mattel", "Hoverboard", "Boards")
                addStaticImport(hoverboard, "createNimbus")
                addStaticImport(namedBoards, "*")
                addStaticImport<Collections>("*")
                types.addClass("HelloWorld") {
                    addModifiers(PUBLIC, FINAL)
                    methods.add("beyond") {
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
        ).isEqualTo(
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
        )
    }

    @Test
    fun `$N for Names`() {
        val hexDigit =
            buildMethodSpec("hexDigit") {
                addModifiers(PUBLIC)
                parameters.add(INT, "i")
                returns = CHAR
                appendLine("return (char) (i < 10 ? i + '0' : i - 10 + 'a')")
            }
        val byteToHex =
            buildMethodSpec("byteToHex") {
                addModifiers(PUBLIC)
                parameters.add(INT, "b")
                setReturns<String>()
                appendLine("char[] result = new char[2]")
                appendLine("result[0] = %N((b >>> 4) & 0xf)", hexDigit)
                appendLine("result[1] = %N(b & 0xf)", hexDigit)
                appendLine("return new String(result)")
            }
        assertThat("$byteToHex\n$hexDigit")
            .isEqualTo(
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
            )
    }

    @Test
    fun `Code block format strings`() {
        assertThat(codeBlockOf("I ate %L %L", 3, "tacos"))
            .isEqualTo(CodeBlock.of("I ate \$L \$L", 3, "tacos"))
        assertThat(codeBlockOf("I ate %2L %1L", "tacos", 3))
            .isEqualTo(CodeBlock.of("I ate \$2L \$1L", "tacos", 3))
        val templates = linkedMapOf<String, Any>()
        templates["food"] = "tacos"
        templates["count"] = 3

        assertThat(buildCodeBlock { appendNamed("I ate %count:L %food:L", templates) })
            .isEqualTo(CodeBlock.builder().addNamed("I ate \$count:L \$food:L", templates).build())
    }

    @Test
    fun `Methods`() {
        assertThat(
            buildClassTypeSpec("HelloWorld") {
                addModifiers(PUBLIC, ABSTRACT)
                methods.add("flux") {
                    addModifiers(PROTECTED, ABSTRACT)
                }
            }.toString(),
        ).isEqualTo(
            """
            public abstract class HelloWorld {
              protected abstract void flux();
            }

            """.trimIndent(),
        )
    }

    @Test
    fun `Constructors`() {
        assertThat(
            buildClassTypeSpec("HelloWorld") {
                addModifiers(PUBLIC)
                fields.add<String>("greeting", PRIVATE, FINAL)
                methods.addConstructor {
                    addModifiers(PUBLIC)
                    parameters.add<String>("greeting")
                    appendLine("this.%N = %N", "greeting", "greeting")
                }
            }.toString(),
        ).isEqualTo(
            """
            public class HelloWorld {
              private final java.lang.String greeting;

              public HelloWorld(java.lang.String greeting) {
                this.greeting = greeting;
              }
            }

            """.trimIndent(),
        )
    }

    @Test
    fun `Parameters`() {
        assertThat(
            buildMethodSpec("welcomeOverlords") {
                parameters {
                    add<String>("android", FINAL)
                    add<String>("robot", FINAL)
                }
            }.toString(),
        ).isEqualTo(
            """
            void welcomeOverlords(final java.lang.String android, final java.lang.String robot) {
            }

            """.trimIndent(),
        )
    }

    @Test
    fun `Fields`() {
        assertThat(
            buildClassTypeSpec("HelloWorld") {
                addModifiers(PUBLIC)
                fields {
                    add<String>("android", PRIVATE, FINAL)
                    add<String>("robot", PRIVATE, FINAL)
                }
            }.toString(),
        ).isEqualTo(
            """
            public class HelloWorld {
              private final java.lang.String android;

              private final java.lang.String robot;
            }

            """.trimIndent(),
        )
        assertThat(
            buildFieldSpec(STRING, "android", PRIVATE, FINAL) {
                setInitializer("\"Lollipop v.\" + 5.0")
            }.toString(),
        ).isEqualTo(
            """
            private final java.lang.String android = "Lollipop v." + 5.0;

            """.trimIndent(),
        )
    }

    @Test
    fun `Interfaces`() {
        assertThat(
            buildInterfaceTypeSpec("HelloWorld") {
                addModifiers(PUBLIC)
                fields.add(String::class, "ONLY_THING_THAT_IS_CONSTANT", PUBLIC, STATIC, FINAL) {
                    setInitializer("%S", "change")
                }
                methods.add("beep") {
                    addModifiers(PUBLIC, ABSTRACT)
                }
            }.toString(),
        ).isEqualTo(
            """
            public interface HelloWorld {
              java.lang.String ONLY_THING_THAT_IS_CONSTANT = "change";

              void beep();
            }

            """.trimIndent(),
        )
    }

    @Test
    fun `Enums`() {
        assertThat(
            buildEnumTypeSpec("Roshambo") {
                addModifiers(PUBLIC)
                addEnumConstant("ROCK")
                addEnumConstant("SCISSORS")
                addEnumConstant("PAPER")
            }.toString(),
        ).isEqualTo(
            """
            public enum Roshambo {
              ROCK,

              SCISSORS,

              PAPER
            }

            """.trimIndent(),
        )
        assertThat(
            buildEnumTypeSpec("Roshambo") {
                addModifiers(PUBLIC)
                enumConstants["ROCK"] =
                    buildAnonymousTypeSpec("%S", "fist") {
                        methods.add("toString") {
                            annotations.add<Override>()
                            addModifiers(PUBLIC)
                            appendLine("return %S", "avalanche!")
                            setReturns<String>()
                        }
                    }
                addEnumConstant("SCISSORS", buildAnonymousTypeSpec("%S", "peace") { })
                addEnumConstant("PAPER", buildAnonymousTypeSpec("%S", "flat") { })
                fields.add<String>("handsign", PRIVATE, FINAL)
                methods.addConstructor {
                    parameters.add<String>("handsign")
                    appendLine("this.%N = %N", "handsign", "handsign")
                }
            }.toString(),
        ).isEqualTo(
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
        )
    }

    @Test
    fun `Anonymous Inner Classes`() {
        assertThat(
            buildMethodSpec("sortByLength") {
                parameters.add(LIST.parameterizedBy<String>(), "strings")
                appendLine(
                    "%T.sort(%N, %L)",
                    Collections::class,
                    "strings",
                    buildAnonymousTypeSpec("") {
                        superinterfaces += Comparator::class.name.parameterizedBy<String>()
                        methods.add("compare") {
                            annotations.add<Override>()
                            addModifiers(PUBLIC)
                            parameters {
                                add<String>("a")
                                add<String>("b")
                            }
                            returns = INT
                            appendLine("return %N.length() - %N.length()", "a", "b")
                        }
                    },
                )
            }.toString(),
        ).isEqualTo(
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
        )
    }

    @Test
    fun `Annotations`() {
        assertThat(
            buildMethodSpec("toString") {
                annotations.add<Override>()
                setReturns<String>()
                addModifiers(PUBLIC)
                appendLine("return %S", "Hoverboard")
            }.toString(),
        ).isEqualTo(
            """
            @java.lang.Override
            public java.lang.String toString() {
              return "Hoverboard";
            }

            """.trimIndent(),
        )

        val headers = classNamed("com.example", "Headers")
        val logRecord = classNamed("com.example", "LogRecord")
        val logReceipt = classNamed("com.example", "LogReceipt")
        assertThat(
            buildMethodSpec("recordEvent") {
                addModifiers(PUBLIC, ABSTRACT)
                annotations.add(headers) {
                    addMember("accept", "%S", "application/json; charset=utf-8")
                    addMember("userAgent", "%S", "Square Cash")
                }
                parameters.add(logRecord, "logRecord")
                returns = logReceipt
            }.toString(),
        ).isEqualTo(
            """
            @com.example.Headers(
                accept = "application/json; charset=utf-8",
                userAgent = "Square Cash"
            )
            public abstract com.example.LogReceipt recordEvent(com.example.LogRecord logRecord);

            """.trimIndent(),
        )

        val header = classNamed("com.example", "Header")
        val headerList = classNamed("com.example", "HeaderList")
        assertThat(
            buildMethodSpec("recordEvent") {
                addModifiers(PUBLIC, ABSTRACT)
                annotations.add(headerList) {
                    addMember(
                        "value",
                        "%L",
                        buildAnnotationSpec(header) {
                            addMember("name", "%S", "Accept")
                            addMember("value", "%S", "application/json; charset=utf-8")
                        },
                    )
                    addMember(
                        "value",
                        "%L",
                        buildAnnotationSpec(header) {
                            addMember("name", "%S", "User-Agent")
                            addMember("value", "%S", "Square Cash")
                        },
                    )
                }
                parameters.add(logRecord, "logRecord")
                returns = logReceipt
            }.toString(),
        ).isEqualTo(
            """
            @com.example.HeaderList({
                @com.example.Header(name = "Accept", value = "application/json; charset=utf-8"),
                @com.example.Header(name = "User-Agent", value = "Square Cash")
            })
            public abstract com.example.LogReceipt recordEvent(com.example.LogRecord logRecord);

            """.trimIndent(),
        )
    }

    @Test
    fun `Javadoc`() {
        val message = classNamed("com.example", "Message")
        val conversation = classNamed("com.example", "Conversation")
        assertThat(
            buildMethodSpec("dismiss") {
                addJavadoc("Hides {@code message} from the caller's history. Other\n")
                addJavadoc("participants in the conversation will continue to see the\n")
                addJavadoc("message in their own history unless they also delete it.\n\n")
                addJavadoc(
                    """
                    <p>Use {@link #delete(%T)} to delete the entire
                    conversation for all participants.
                    """.trimIndent(),
                    conversation,
                )
                addModifiers(PUBLIC, ABSTRACT)
                parameters.add(message, "message")
            }.toString(),
        ).isEqualTo(
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
        )
    }
}
