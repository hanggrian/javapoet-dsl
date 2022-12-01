[![Travis CI](https://img.shields.io/travis/com/hendraanggrian/javapoet-dsl)](https://travis-ci.com/github/hendraanggrian/javapoet-dsl/)
[![Codecov](https://img.shields.io/codecov/c/github/hendraanggrian/javapoet-dsl)](https://codecov.io/gh/hendraanggrian/javapoet-dsl/)
[![Maven Central](https://img.shields.io/maven-central/v/com.hendraanggrian/javapoet-dsl)](https://search.maven.org/artifact/com.hendraanggrian/javapoet-dsl/)
[![Nexus Snapshot](https://img.shields.io/nexus/s/com.hendraanggrian/javapoet-dsl?server=https%3A%2F%2Fs01.oss.sonatype.org)](https://s01.oss.sonatype.org/content/repositories/snapshots/com/hendraanggrian/javapoet-dsl/)
[![OpenJDK](https://img.shields.io/badge/jdk-1.8%2B-informational)](https://openjdk.java.net/projects/jdk8/)

# JavaPoet DSL

Lightweight Kotlin extension of [JavaPoet](https://github.com/square/javapoet/), providing Kotlin
DSL functionality and other convenient solutions.

- Full of convenient methods to achieve minimum code writing possible.
- Options to invoke DSL. For example, `methods.add("main") { ... }` is as good
  as `methods { "main" { ... } }`. Scroll down for more information.
- Smooth transition, existing JavaPoet native specs can still be configured with DSL.

```kotlin
buildJavaFile("com.example.helloworld") {
    addClass("HelloWorld") {
        addModifiers(Modifier.PUBLIC, Modifier.FINAL)
        methods {
            "main" {
                addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                returns = VOID
                parameters.add<Array<String>>("args")
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

JavaPoet uses char prefix `$` when formatting literals (`$L`), strings (`$S`), types (`$T`), an
names (`$N`) within strings. However in Kotlin, `$` in strings is reserved for variable referral.
Avoid using `\$` and instead use `%` as the prefix, this is also the approach taken by [KotlinPoet](https://github.com/square/kotlinpoet/).

```kotlin
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

`KClass<*>` can now be used as format arguments. There is also inline reified type function whenever
possible.

```kotlin
buildMethodSpec("sortList") {
    returns = int
    parameters.add(classNameOf("java.util", "List").parameterizedBy(hoverboard), "list")
    appendLine("%T.sort(list)", Collections::class)
    appendLine("return list")
}

buildFieldSpec<Int>("count") {
    initializer("%L", 0)
}
```

### Optional DSL

Some elements (field, method, parameter, etc.) are wrapped in container class. These containers have
ability to add components with/without invoking DSL.

For example, 2 examples below will produce the same result.

```kotlin
types.addClass("Car") {
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

types.addClass("Car") {
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

### Property delegation

In spirit of [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html#using_kotlin_delegated_properties)
, creating a spec can be done by delegating to a property.

```kotlin
val title by buildingParameterSpec(String::class) {
    annotations.add<NotNull>
}

val message by parameters.adding(String::class) {
    annotations.add<Nullable>
}
```

### Fluent TypeName API

Write `TypeName` and all its subtypes fluently.

```kotlin
val myClass: ClassName = classOf("com.example", "MyClass")
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
