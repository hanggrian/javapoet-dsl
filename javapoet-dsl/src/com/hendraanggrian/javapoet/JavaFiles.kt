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
        type = buildTypeSpec(name, builder).build()
    }

    override fun type(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildTypeSpec(className, builder).build()
    }

    override fun interfaceType(name: String, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildInterfaceTypeSpec(name, builder).build()
    }

    override fun interfaceType(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildInterfaceTypeSpec(className, builder).build()
    }

    override fun enumType(name: String, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildEnumTypeSpec(name, builder).build()
    }

    override fun enumType(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildEnumTypeSpec(className, builder).build()
    }

    override fun anonymousType(typeArgumentsFormat: String, vararg args: Any, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildAnonymousTypeSpec(typeArgumentsFormat, *args, builder = builder).build()
    }

    override fun anonymousType(typeArguments: CodeBlock, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildAnonymousTypeSpec(typeArguments, builder).build()
    }

    override fun annotationType(name: String, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildAnnotationTypeSpec(name, builder).build()
    }

    override fun annotationType(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildAnnotationTypeSpec(className, builder).build()
    }
}

@PublishedApi
internal class JavaFileBuilderImpl : JavaFileBuilder {

    override var type: TypeSpec? = null
}

inline fun buildJavaFile(packageName: String, builder: JavaFileBuilder.() -> Unit): JavaFile = JavaFile
    .builder(packageName, checkNotNull(JavaFileBuilderImpl().apply(builder).type) { "A type must be initialized" })
    .build()