@file:Suppress("SpellCheckingInspection")

package com.hendraanggrian.javapoet

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeSpec
import com.squareup.javapoet.TypeVariableName
import javax.lang.model.element.Modifier

internal const val NO_GETTER: String = "Property does not have a getter."

/** Some mutable backing fields are only used to set value. */
@PublishedApi
internal fun noGetter(): Nothing = throw UnsupportedOperationException(NO_GETTER)

/** Don't forget to add inline reified function. */
internal interface AnnotableSpecBuilder {

    /** Add annotation to this spec builder. */
    fun annotation(spec: AnnotationSpec)

    /** Add annotation to this spec builder. */
    fun annotation(name: ClassName, builder: (AnnotationSpecBuilder.() -> Unit)? = null) =
        annotation(buildAnnotationSpec(name, builder))

    /** Add annotation to this spec builder. */
    fun annotation(type: Class<*>, builder: (AnnotationSpecBuilder.() -> Unit)? = null) =
        annotation(buildAnnotationSpec(type, builder))
}

internal interface CodableSpecBuilder {

    /** Add a code to this spec builder. */
    fun code(format: String, vararg args: Any)

    /** Add a code to this spec builder. */
    fun code(block: CodeBlock)

    /** Build a code block and add it to this spec builder. */
    fun code(builder: CodeBlockBuilder.() -> Unit) =
        code(buildCodeBlock(builder))

    /** Add a statement to this spec builder. */
    fun statement(format: String, vararg args: Any)

    /** Add a statement to this spec builder. */
    fun statement(block: CodeBlock)

    /** Build a statement block and add it to this spec builder. */
    fun statement(builder: CodeBlockBuilder.() -> Unit) =
        statement(buildCodeBlock(builder))
}

internal interface TypableSpecBuilder {

    /** Add type to this spec builder. */
    fun type(spec: TypeSpec)

    /** Add type to this spec builder. */
    fun type(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null) =
        type(buildTypeSpec(name, builder))

    /** Add type to this spec builder. */
    fun type(name: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null) =
        type(buildTypeSpec(name, builder))

    /** Add interface to this spec builder. */
    fun interfaceType(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null) =
        type(buildInterfaceTypeSpec(name, builder))

    /** Add interface to this spec builder. */
    fun interfaceType(name: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null) =
        type(buildInterfaceTypeSpec(name, builder))

    /** Add enum to this spec builder. */
    fun enumType(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null) =
        type(buildEnumTypeSpec(name, builder))

    /** Add enum to this spec builder. */
    fun enumType(name: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null) =
        type(buildEnumTypeSpec(name, builder))

    /** Add anonymous type to this spec builder. */
    fun anonymousType(typeArgumentsFormat: String, vararg args: Any, builder: (TypeSpecBuilder.() -> Unit)? = null) =
        type(buildAnonymousTypeSpec(typeArgumentsFormat, *args, builder = builder))

    /** Add anonymous type to this spec builder. */
    fun anonymousType(typeArguments: CodeBlock, builder: (TypeSpecBuilder.() -> Unit)? = null) =
        type(buildAnonymousTypeSpec(typeArguments, builder))

    /** Add annotation interfaceType to this spec builder. */
    fun annotationType(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null) =
        type(buildAnnotationTypeSpec(name, builder))

    /** Add annotation interfaceType to this spec builder. */
    fun annotationType(name: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null) =
        type(buildAnnotationTypeSpec(name, builder))
}

internal interface JavadocableSpecBuilder {

    /** Add javadoc to this spec builder. */
    fun javadoc(format: String, vararg args: Any)

    /** Add javadoc to this spec builder. */
    fun javadoc(block: CodeBlock)

    /** Build javadoc and add it to this spec builder. */
    fun javadoc(builder: CodeBlockBuilder.() -> Unit) =
        javadoc(buildCodeBlock(builder))
}

internal interface TypeVariableSpecBuilder {

    /** Add single [TypeVariableName] to this spec builder. */
    fun typeVariable(name: TypeVariableName)

    /**
     * Add multiple [TypeVariableName] to this spec builder.
     * A preferable way to achieve this is to use [plus] operator (e.g.: `typeVariables = name1 + name2`).
     */
    fun typeVariables(names: Iterable<TypeVariableName>)
}

internal interface ControlFlowableSpecBuilder {

    /** Begin a control flow of this spec builder. */
    fun beginControlFlow(format: String, vararg args: Any)

    /** Begin a control flow of this spec builder. */
    fun nextControlFlow(format: String, vararg args: Any)

    /** End a control flow of this spec builder. */
    fun endControlFlow()

    /** End a control flow of this spec builder. */
    fun endControlFlow(format: String, vararg args: Any)
}

internal interface ModifierableSpecBuilder {

    /**
     * Add single/multiple [Modifier] to this spec builder.
     * A preferable way to achieve this is to use [plus] operator (e.g.: `modifiers = public + static`).
     */
    var modifiers: Collection<Modifier>

    /** Instead of recreating a collection every [Collection.plus], add the item to this collection. */
    operator fun MutableCollection<Modifier>.plus(others: MutableCollection<Modifier>): MutableCollection<Modifier> =
        also { it += others }
}