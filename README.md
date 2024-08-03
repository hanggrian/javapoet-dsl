[![CircleCI](https://img.shields.io/circleci/build/gh/hanggrian/javapoet-dsl)](https://app.circleci.com/pipelines/github/hanggrian/javapoet-dsl/)
[![Codecov](https://img.shields.io/codecov/c/gh/hanggrian/javapoet-dsl)](https://app.codecov.io/gh/hanggrian/javapoet-dsl/)
[![Maven Central](https://img.shields.io/maven-central/v/com.hanggrian/javapoet-dsl)](https://repo1.maven.org/maven2/com/hanggrian/javapoet-dsl/)
[![OpenJDK](https://img.shields.io/badge/jdk-11%2B-informational)](https://openjdk.org/projects/jdk/11/)

# JavaPoet DSL

Lightweight Kotlin extension of [JavaPoet](https://github.com/square/javapoet/),
providing Kotlin DSL functionality and other convenient solutions.

- Full of convenient methods to achieve minimum code writing possible.
- Options to invoke DSL. For example, `method("main") { ... }` is as good as
  `methods { "main" { ... } }`. Scroll down for more information.
- Smooth transition, existing JavaPoet native specs can still be configured with
  DSL.

```kt
buildJavaFile("com.example.helloworld") {
    classType("HelloWorld") {
        modifiers(PUBLIC, FINAL)
        methods {
            "main" {
                modifiers(PUBLIC, STATIC)
                returns = VOID
                parameter(STRING.array, "args")
                appendLine("%T.out.println(%S)", System::class, "Hello, JavaPoet!")
            }
        }
    }
}.writeTo(System.out)
```

## Download

```gradle
repositories {
    mavenCentral()
}
dependencies {
    implementation "com.hendraanggrian:javapoet-dsl:$version"
}
```

## Usage

### Use `%` in string formatter

JavaPoet uses char prefix `$` when formatting literals (`$L`), strings (`$S`),
types (`$T`), an names (`$N`) within strings. However in Kotlin, `$` in strings
is reserved for variable referral. Avoid using `\$` and instead use `%` as the
prefix, this is also the approach taken by [KotlinPoet](https://github.com/square/kotlinpoet/).

```kt
buildMethodSpec("getName") {
    returns<String>()
    appendLine("%S", name)
}

buildCodeBlock {
    appendLine("int result = 0")
    beginFlow("for (int i = %L; i < %L; i++)", 0, 10)
    appendLine("result = result %L i", "+=")
    endFlow()
    appendLine("return result")
}
```

### Use `T::class` as parameters

`KClass<*>` can now be used as format arguments. There is also inline reified
type function whenever possible.

```kt
buildMethodSpec("sortList") {
    returns = int
    parameter(classNamed("java.util", "List").parameterizedBy(hoverboard), "list")
    appendLine("%T.sort(list)", Collections::class)
    appendLine("return list")
}

buildFieldSpec("count", INT) {
    initializer("%L", 0)
}
```

### Optional DSL

Some elements (field, method, parameter, etc.) are wrapped in container class.
These containers have ability to add components with/without invoking DSL.

For example, 2 examples below will produce the same result.

```kt
types.addClass("Car") {
    methods {
        "getWheels" {
            returns = INT
            appendLine("return wheels")
        }
        "setWheels" {
            parameter(INT, "wheels")
            appendLine("this.wheels = wheels")
        }
    }
}

types.addClass("Car") {
    method("getWheels") {
        returns = INT
        appendLine("return wheels")
    }
    method("setWheels") {
        parameter(INT, "wheels")
        appendLine("this.wheels = wheels")
    }
}
```

### Property delegation

In spirit of [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html#using_kotlin_delegated_properties),
creating a spec can be done by delegating to a property.

```kt
val setWheels by methoding {
    val wheels by parametering(INT)
    appendLine("this.wheels = wheels")
}
```

### Fluent TypeName API

Write `TypeName` and all its subtypes fluently.

```kt
val customClass: ClassName =
    classNamed("com.example", "MyClass")

val arrayOfString: ArrayTypeName =
    String::class.name.array

val pairOfInteger: ParameterizedTypeName =
    classNamed("android.util", "Pair")
        .parameterizedBy(Integer::class, Integer::class)

val aGenerics: TypeVariableName =
    "T".generics

val subtypeOfCharSequence: WildcardTypeName =
    CharSequence::class.name.subtype
```
