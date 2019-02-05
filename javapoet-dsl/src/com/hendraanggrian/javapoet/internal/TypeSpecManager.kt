package com.hendraanggrian.javapoet.internal

import com.hendraanggrian.javapoet.TypeSpecBuilder
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock

interface TypeSpecManager {

    fun type(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null)

    fun type(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null)

    fun interfaceType(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null)

    fun interfaceType(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null)

    fun enumType(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null)

    fun enumType(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null)

    fun anonymousType(typeArgumentsFormat: String, vararg args: Any, builder: (TypeSpecBuilder.() -> Unit)? = null)

    fun anonymousType(typeArguments: CodeBlock, builder: (TypeSpecBuilder.() -> Unit)? = null)

    fun annotationType(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null)

    fun annotationType(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null)
}