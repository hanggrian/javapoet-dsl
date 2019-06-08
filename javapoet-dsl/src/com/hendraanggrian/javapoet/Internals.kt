package com.hendraanggrian.javapoet

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeVariableName
import javax.lang.model.element.Modifier

internal const val NO_GETTER: String = "Property does not have a getter."

/** Some mutable backing fields are only used to set value. */
internal fun noGetter(): Nothing = throw UnsupportedOperationException(NO_GETTER)

/** Don't forget to add inline reified function. */
internal interface AnnotableSpecBuilder {

    /** Add annotation to this spec builder */
    fun annotation(name: ClassName, builder: (AnnotationSpecBuilder.() -> Unit)? = null)

    /** Add annotation to this spec builder */
    fun annotation(type: Class<*>, builder: (AnnotationSpecBuilder.() -> Unit)? = null)
}

internal interface CodableSpecBuilder {

    /** Add a code to this spec builder. */
    fun code(format: String, vararg args: Any)

    /** Build a code block and add it to this spec builder. */
    fun code(builder: CodeBlockBuilder.() -> Unit)

    /** Add a statement to this spec builder. */
    fun statement(format: String, vararg args: Any)

    /** Build a statement block and add it to this spec builder. */
    fun statement(builder: CodeBlockBuilder.() -> Unit)
}

internal interface TypableSpecBuilder {

    /** Add type to this spec builder. */
    fun type(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null)

    /** Add type to this spec builder. */
    fun type(name: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null)

    /** Add interface to this spec builder. */
    fun interfaceType(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null)

    /** Add interface to this spec builder. */
    fun interfaceType(name: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null)

    /** Add enum to this spec builder. */
    fun enumType(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null)

    /** Add enum to this spec builder. */
    fun enumType(name: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null)

    /** Add anonymous type to this spec builder. */
    fun anonymousType(typeArgumentsFormat: String, vararg args: Any, builder: (TypeSpecBuilder.() -> Unit)? = null)

    /** Add anonymous type to this spec builder. */
    fun anonymousType(typeArguments: CodeBlock, builder: (TypeSpecBuilder.() -> Unit)? = null)

    /** Add annotation interfaceType to this spec builder. */
    fun annotationType(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null)

    /** Add annotation interfaceType to this spec builder. */
    fun annotationType(name: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null)
}

internal interface JavadocableSpecBuilder {

    /** Add javadoc to this spec builder. */
    fun javadoc(format: String, vararg args: Any)

    /** Add javadoc to this spec builder. */
    var javadoc: String
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) = javadoc(value)

    /** Build javadoc and add it to this spec builder. */
    fun javadoc(builder: CodeBlockBuilder.() -> Unit)
}

internal interface TypeVariableSpecBuilder {

    /** Add single [TypeVariableName] to this spec builder. */
    var typeVariable: TypeVariableName

    /**
     * Add multiple [TypeVariableName] to this spec builder.
     * A preferable way to achieve this is to use [plus] operator (e.g.: `typeVariables = name1 + name2 + name3`).
     */
    var typeVariables: Iterable<TypeVariableName>

    /** Combines two initial values. */
    operator fun TypeVariableName.plus(other: TypeVariableName): ArrayList<TypeVariableName> = arrayListOf(this, other)
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
     * Add single [Modifier] to this spec builder.
     * A preferable way to achieve this is to use [plus] operator (e.g.: `typeVariables = name1 + name2 + name3`).
     */
    var modifier: Modifier
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            modifiers = listOf(value)
        }

    /**
     * Add multiple [Modifier] to this spec builder.
     * A preferable way to achieve this is to use [plus] operator (e.g.: `typeVariables = name1 + name2 + name3`).
     */
    var modifiers: Collection<Modifier>

    /** Combines two initial values. */
    operator fun Modifier.plus(other: Modifier): ArrayList<Modifier> = arrayListOf(this, other)
}