package com.hendraanggrian.javapoet.internal

import com.hendraanggrian.javapoet.TypeSpecBuilder
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock

internal interface TypedSpecBuilder {

    /** Add class to this spec builder. */
    fun type(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null)

    /** Add class to this spec builder. */
    fun type(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null)

    /** Add interface to this spec builder. */
    fun interfaceType(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null)

    /** Add interface to this spec builder. */
    fun interfaceType(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null)

    /** Add enum to this spec builder. */
    fun enumType(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null)

    /** Add enum to this spec builder. */
    fun enumType(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null)

    /** Add anonymous class to this spec builder. */
    fun anonymousType(typeArgumentsFormat: String, vararg args: Any, builder: (TypeSpecBuilder.() -> Unit)? = null)

    /** Add anonymous class to this spec builder. */
    fun anonymousType(typeArguments: CodeBlock, builder: (TypeSpecBuilder.() -> Unit)? = null)

    /** Add annotation interface to this spec builder. */
    fun annotationType(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null)

    /** Add annotation interface to this spec builder. */
    fun annotationType(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null)
}