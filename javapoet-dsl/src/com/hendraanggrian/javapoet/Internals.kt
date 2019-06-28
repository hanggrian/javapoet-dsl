@file:Suppress("SpellCheckingInspection")

package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.AnnotationContainer
import com.hendraanggrian.javapoet.dsl.CodeContainer
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeSpec
import com.squareup.javapoet.TypeVariableName
import javax.lang.model.element.Modifier

internal const val NO_GETTER: String = "Property does not have a getter."

/** Some mutable backing fields are only used to set value. */
@PublishedApi
internal fun noGetter(): Nothing = throw UnsupportedOperationException(NO_GETTER)

internal interface AnnotatedSpecBuilder {

    val annotations: AnnotationContainer
}

internal interface CodedSpecBuilder {

    val codes: CodeContainer

    val statements: CodeContainer
}

internal interface TypedSpecBuilder {

    /** Add type to this spec builder. */
    fun addType(spec: TypeSpec)

    /** Add class type to this spec builder. */
    fun classType(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null) =
        addType(buildClassTypeSpec(name, builder))

    /** Add class type to this spec builder. */
    fun classType(type: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null) =
        addType(buildClassTypeSpec(type, builder))

    /** Add interface type to this spec builder. */
    fun interfaceType(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null) =
        addType(buildInterfaceTypeSpec(name, builder))

    /** Add interface type to this spec builder. */
    fun interfaceType(type: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null) =
        addType(buildInterfaceTypeSpec(type, builder))

    /** Add enum type to this spec builder. */
    fun enumType(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null) =
        addType(buildEnumTypeSpec(name, builder))

    /** Add enum type to this spec builder. */
    fun enumType(type: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null) =
        addType(buildEnumTypeSpec(type, builder))

    /** Add anonymous type to this spec builder. */
    fun anonymousType(format: String, vararg args: Any, builder: (TypeSpecBuilder.() -> Unit)? = null) =
        addType(buildAnonymousTypeSpec(format, *args, builder = builder))

    /** Add anonymous type to this spec builder. */
    fun anonymousType(block: CodeBlock, builder: (TypeSpecBuilder.() -> Unit)? = null) =
        addType(buildAnonymousTypeSpec(block, builder))

    /** Add annotation type to this spec builder. */
    fun annotationType(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null) =
        addType(buildAnnotationTypeSpec(name, builder))

    /** Add annotation type to this spec builder. */
    fun annotationType(type: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null) =
        addType(buildAnnotationTypeSpec(type, builder))
}

internal interface JavadocedSpecBuilder {

    val javadocs: CodeContainer
}

internal interface TypeVariabledSpecBuilder {

    /** Add single [TypeVariableName] to this spec builder. */
    fun addTypeVariable(name: TypeVariableName)

    /**
     * Add multiple [TypeVariableName] to this spec builder.
     * A preferable way to achieve this is to use [plus] operator (e.g.: `addTypeVariables = name1 + name2`).
     */
    fun addTypeVariables(names: Iterable<TypeVariableName>)
}

internal interface ControlFlowedSpecBuilder {

    /** Begin a control flow of this spec builder. */
    fun beginControlFlow(format: String, vararg args: Any)

    /** Begin a control flow of this spec builder. */
    fun nextControlFlow(format: String, vararg args: Any)

    /** End a control flow of this spec builder. */
    fun endControlFlow()

    /** End a control flow of this spec builder. */
    fun endControlFlow(format: String, vararg args: Any)
}

internal interface ModifieredSpecBuilder {

    /**
     * Add single/multiple [Modifier] to this spec builder.
     * A preferable way to achieve this is to use [plus] operator (e.g.: `modifiers = public + static`).
     */
    var modifiers: Collection<Modifier>

    /** Instead of recreating a collection every [Collection.plus], add the item to this collection. */
    operator fun MutableCollection<Modifier>.plus(others: MutableCollection<Modifier>): MutableCollection<Modifier> =
        also { it += others }
}