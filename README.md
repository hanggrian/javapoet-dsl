[![bintray](https://img.shields.io/badge/bintray-maven-brightgreen.svg)](https://bintray.com/hendraanggrian/maven)
[![download](https://api.bintray.com/packages/hendraanggrian/maven/javapoet-dsl/images/download.svg)](https://bintray.com/hendraanggrian/maven/javapoet-dsl/_latestVersion)
[![build](https://travis-ci.com/hendraanggrian/javapoet-dsl.svg)](https://travis-ci.com/hendraanggrian/javapoet-dsl)
[![license](https://img.shields.io/badge/license-Apache--2.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)

JavaPoet DSL
============
Lightweight library that provides Kotlin DSL functionality to JavaPort. It's time to replace those old-fashioned Java builders.

 * Full of convenient methods to achieve minimum code writing possible.
 * Options to invoke DSL. For example, `methods.add("main") { ... }` is as good as `methods { "main" { ... } }`. Scroll down for more information.
 * Smooth transition with backwards compatibility. JavaPoet native specs (`TypeSpec`, `FieldSpec`, etc.) are still supported.

```kotlin
buildJavaFile("com.example.helloworld") {
    classType("HelloWorld") {
        modifiers = public + final
        methods {
            "main" {
                modifiers = public + static
                returns = void
                parameters.add<Array<String>>("args")
                statements.add("\$T.out.println(\$S)", System::class.java, "Hello, JavaPoet!")
            }
        }
    }
}.writeTo(System.out)
```

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
Coming soon.

#### Optional DSL

Some elements (field, method, parameter, etc.) are wrapped in container class. These containers have ability to add components with/without invoking DSL.

For example, 2 examples below will produce the same result.

```kotlin
classType("Car") {
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
        "wheels"<Int> {
            initializer = "4"
        }
    }
    methods {
        "getWheels" {
            returns<Int>()
            statements {
                add("return wheels")
            }
        }
        "setWheels" {
            parameters {
                add(Int::class, "wheels")
            }
            statements {
                add("this.wheels = wheels")
            }
        }
    }
}

classType("Car") {
    annotations.add<SuppressWarnings> {
        members.add("value", "deprecation")
    }
    fields.add<Int>("wheels") {
        initializer = "4"
    }
    methods.add("getWheels") {
        returns<Int>()
        statements.add("return wheels")
    }
    methods.add("setWheels") {
        parameters["wheels"] = Int::class
        statements.add("this.wheels = wheels")
    }
}
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
