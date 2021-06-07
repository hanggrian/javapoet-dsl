[![version](https://img.shields.io/maven-central/v/com.hendraanggrian/javapoet-ktx)](https://search.maven.org/artifact/com.hendraanggrian/javapoet-ktx)
[![build](https://img.shields.io/travis/com/hendraanggrian/javapoet-ktx)](https://travis-ci.com/hendraanggrian/javapoet-ktx)
[![analysis](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081)](https://ktlint.github.io)
[![license](https://img.shields.io/github/license/hendraanggrian/javapoet-ktx)](https://github.com/hendraanggrian/javapoet-ktx/blob/main/LICENSE)

JavaPoet KTX
============

Lightweight Kotlin extension of [JavaPoet](https://github.com/square/javapoet),
providing Kotlin DSL functionality and other convenient solutions.
* Full of convenient methods to achieve minimum code writing possible.
* Options to invoke DSL. For example, `methods.add("main") { ... }` is as good as `methods { "main" { ... } }`. Scroll down for more information.
* Smooth transition, existing JavaPoet native specs can still be configured with DSL.

```kotlin
buildJavaFile("com.example.helloworld") {
    addClass("HelloWorld") {
        addModifiers(Modifier.PUBLIC, Modifier.FINAL)
        methods {
            "main" {
                addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                returns = VOID
                parameters.add<Array<String>>("args")
                appendln("%T.out.println(%S)", System::class, "Hello, JavaPoet!")
            }
        }
    }
}.writeTo(System.out)
```

Download
--------

```gradle
repositories {
    mavenCentral()
}

dependencies {
    implementation "com.hendraanggrian:javapoet-ktx:${version}"
}
```

Snapshots of the development version are available in [Sonatype's snapshots repository](https://s01.oss.sonatype.org/content/repositories/snapshots/).

Usage
-----

### Use `%` in string formatter
JavaPoet uses char prefix `$` when formatting literals (`$L`), strings (`$S`), types (`$T`), an names (`$N`) within strings.
However in Kotlin, `$` in strings is reserved for variable referral. Avoid using `\$` and instead use `%` as the prefix, this is also the approach taken by [KotlinPoet](https://github.com/square/kotlinpoet).

```kotlin
buildMethodSpec("getName") {
    returns<String>()
    appendln("%S", name)
}

buildCodeBlock {
    appendln("int result = 0")
    beginFlow("for (int i = %L; i < %L; i++)", 0, 10)
    appendln("result = result %L i", "+=")
    endFlow()
    appendln("return result")
}
```

### Use `T::class` as parameters
`KClass<*>` can now be used as format arguments. There is also inline reified type function whenever possible.

```kotlin
buildMethodSpec("sortList") {
    returns = int
    parameters.add(classNameOf("java.util", "List").parameterizedBy(hoverboard), "list")
    appendln("%T.sort(list)", Collections::class)
    appendln("return list")
}

buildFieldSpec<Int>("count") {
    initializer("%L", 0)
}
```

### Optional DSL
Some elements (field, method, parameter, etc.) are wrapped in container class. These containers have ability to add components with/without invoking DSL.

For example, 2 examples below will produce the same result.

```kotlin
addClass("Car") {
    annotations {
        SuppressWarnings::class {
            members {
                "value" {
                    add("deprecation")
                }
            }
        }
    }
    fields {
        "wheels"(int) {
            initializer("4")
        }
    }
    methods {
        "getWheels" {
            returns = int
            statements {
                add("return wheels")
            }
        }
        "setWheels" {
            parameters {
                add(int, "wheels")
            }
            statements {
                add("this.wheels = wheels")
            }
        }
    }
}

addClass("Car") {
    annotations.add<SuppressWarnings> {
        members.add("value", "deprecation")
    }
    fields.add("wheels", int) {
        initializer("4")
    }
    methods.add("getWheels") {
        returns = int
        statements.add("return wheels")
    }
    methods.add("setWheels") {
        parameters["wheels"] = int
        statements.add("this.wheels = wheels")
    }
}
```

### Fluent TypeName API
Write `TypeName` and all its subtypes fluently.

```kotlin
val myClass: ClassName = "com.example".classOf("MyClass")
val arrayOfString: ArrayTypeName = "java.lang".classOf("String").arrayOf()
val pairOfInteger: ParameterizedTypeName = "android.util".classOf("Pair").parameterizedBy(Integer::class, Integer::class)
val tVariable: TypeVariableName = "T".typeVarOf()
val subtypeOfCharSequence: WildcardTypeName = "java.lang".classOf("CharSequence").subtypeOf()
```

If you have access to those types, they can also be strongly-typed.

```kotlin
val myClass = com.example.MyClass.asClassName()
val arrayOfString = arrayTypeNameOf<java.lang.String>()
val pairOfInteger = parameterizedTypeNameOf<android.util.Pair>(Integer::class, Integer::class)
val subtypeOfCharSequence = wildcardTypeNameSubtypeOf<java.lang.CharSequence>()
```
