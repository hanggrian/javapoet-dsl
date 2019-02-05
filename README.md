javapoet-dsl
============
Write Java files in Kotlin DSL.

#### Example

Here's a boring `HelloWorld` again:

```java
package com.example.helloworld;

public final class HelloWorld {
  public static void main(String[] args) {
    System.out.println("Hello, JavaPoet!");
  }
}
```

Here's how to write it in Kotlin DSL:

```kotlin
buildJavaFile("com.example.helloworld") {
    type("HelloWorld") {
        addModifiers(Modifier.PUBLIC, Modifier.FINAL)
        method("main") {
            addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            returns(Void::class.java)
            addParameter(Array<String>::class.java, "args")
            addStatement("$T.out.println($S)", System::class.java, "Hello, JavaPoet!")
        }
    }
}.writeTo(System.out)
```