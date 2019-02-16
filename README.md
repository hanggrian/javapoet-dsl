[![bintray](https://img.shields.io/badge/bintray-maven-brightgreen.svg)](https://bintray.com/hendraanggrian/maven)
[![download](https://api.bintray.com/packages/hendraanggrian/maven/javapoet-dsl/images/download.svg)](https://bintray.com/hendraanggrian/maven/javapoet-dsl/_latestVersion)
[![build](https://travis-ci.com/hendraanggrian/javapoet-dsl.svg)](https://travis-ci.com/hendraanggrian/javapoet-dsl)
[![license](https://img.shields.io/badge/license-Apache--2.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)

JavaPoet DSL
============
Write Java files in Kotlin DSL.


Download
--------
```gradle
repositories {
    jcenter()
}

dependencies {
    implementation "com.hendraanggrian:javapoet-dsl:$version"
}
```

Usage
-----
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
        modifiers = public + final
        method("main") {
            modifiers = public + static
            returns = TypeName.VOID
            parameter(Array<String>::class.java, "args")
            statement("\$T.out.println(\$S)", System::class.java, "Hello, JavaPoet!")
        }
    }
}.writeTo(System.out)
```

License
-------
    Copyright 2019 Hendra Anggrian

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
