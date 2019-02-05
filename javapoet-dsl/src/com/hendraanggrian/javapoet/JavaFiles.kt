@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.internal.TypeSpecManager
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec

interface JavaFileBuilder : TypeSpecManager {

    var type: TypeSpec?

    override fun type(name: String, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = createType(name, builder)
    }

    override fun type(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = createType(className, builder)
    }

    override fun interfaceType(name: String, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = createInterfaceType(name, builder)
    }

    override fun interfaceType(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = createInterfaceType(className, builder)
    }

    override fun enumType(name: String, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = createEnumType(name, builder)
    }

    override fun enumType(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = createEnumType(className, builder)
    }

    override fun anonymousType(typeArgumentsFormat: String, vararg args: Any, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = createAnonymousType(typeArgumentsFormat, *args, builder = builder)
    }

    override fun anonymousType(typeArguments: CodeBlock, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = createAnonymousType(typeArguments, builder)
    }

    override fun annotationType(name: String, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = createAnnotationType(name, builder)
    }

    override fun annotationType(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = createAnnotationType(className, builder)
    }
}

@PublishedApi
internal class JavaFileBuilderImpl : JavaFileBuilder {

    override var type: TypeSpec? = null
}

inline fun buildJavaFile(packageName: String, builder: JavaFileBuilder.() -> Unit): JavaFile = JavaFile
    .builder(packageName, checkNotNull(JavaFileBuilderImpl().apply(builder).type) { "A type must be initialized" })
    .build()