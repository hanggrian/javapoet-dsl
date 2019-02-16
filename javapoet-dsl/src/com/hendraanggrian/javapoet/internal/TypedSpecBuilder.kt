package com.hendraanggrian.javapoet.internal

import com.hendraanggrian.javapoet.TypeSpecBuilder
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock

internal interface TypedSpecBuilder {

    /** Add class to this spec builder. */
    fun `class`(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null)

    /** Add class to this spec builder. */
    fun `class`(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null)

    /** Add interface to this spec builder. */
    fun `interface`(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null)

    /** Add interface to this spec builder. */
    fun `interface`(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null)

    /** Add enum to this spec builder. */
    fun `enum`(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null)

    /** Add enum to this spec builder. */
    fun `enum`(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null)

    /** Add anonymous class to this spec builder. */
    fun anonymousClass(typeArgumentsFormat: String, vararg args: Any, builder: (TypeSpecBuilder.() -> Unit)? = null)

    /** Add anonymous class to this spec builder. */
    fun anonymousClass(typeArguments: CodeBlock, builder: (TypeSpecBuilder.() -> Unit)? = null)

    /** Add annotation interface to this spec builder. */
    fun annotationInterface(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null)

    /** Add annotation interface to this spec builder. */
    fun annotationInterface(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null)
}