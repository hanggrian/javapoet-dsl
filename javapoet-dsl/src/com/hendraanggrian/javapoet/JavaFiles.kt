@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.internal.TypeSpecManager
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec

interface JavaFileBuilder : TypeSpecManager {

    var type: TypeSpec?

    override fun type(name: String, builder: TypeSpecBuilder.() -> Unit) {
        type = createType(name, builder)
    }

    override fun type(className: ClassName, builder: TypeSpecBuilder.() -> Unit) {
        type = createType(className, builder)
    }

    override fun iface(name: String, builder: TypeSpecBuilder.() -> Unit) {
        type = createInterface(name, builder)
    }

    override fun iface(className: ClassName, builder: TypeSpecBuilder.() -> Unit) {
        type = createInterface(className, builder)
    }

    override fun enum(name: String, builder: TypeSpecBuilder.() -> Unit) {
        type = createEnum(name, builder)
    }

    override fun enum(className: ClassName, builder: TypeSpecBuilder.() -> Unit) {
        type = createEnum(className, builder)
    }

    override fun anonymous(typeArgumentsFormat: String, vararg args: Any, builder: TypeSpecBuilder.() -> Unit) {
        type = createAnonymous(typeArgumentsFormat, *args, builder = builder)
    }

    override fun anonymous(typeArguments: CodeBlock, builder: TypeSpecBuilder.() -> Unit) {
        type = createAnonymous(typeArguments, builder)
    }

    override fun annotation(name: String, builder: TypeSpecBuilder.() -> Unit) {
        type = createAnnotation(name, builder)
    }

    override fun annotation(className: ClassName, builder: TypeSpecBuilder.() -> Unit) {
        type = createAnnotation(className, builder)
    }
}

class JavaFileBuilderImpl : JavaFileBuilder {

    override var type: TypeSpec? = null
}

inline fun buildJavaFile(packageName: String, builder: JavaFileBuilder.() -> Unit): JavaFile = JavaFile
    .builder(packageName, checkNotNull(JavaFileBuilderImpl().apply(builder).type) { "A type must be initialized" })
    .build()

fun asd() {
    buildJavaFile("cas") {
        annotation("asd") {
        }
        type("asd") {
            type("asd") {

            }
            method("asd") {

            }
        }
    }.writeTo(System.out)
}