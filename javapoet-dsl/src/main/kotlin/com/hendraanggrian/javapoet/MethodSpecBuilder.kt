@file:OptIn(ExperimentalContracts::class)

package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.collections.AnnotationSpecList
import com.hendraanggrian.javapoet.collections.AnnotationSpecListScope
import com.hendraanggrian.javapoet.collections.CodeBlockContainer
import com.hendraanggrian.javapoet.collections.JavadocContainer
import com.hendraanggrian.javapoet.collections.JavadocContainerScope
import com.hendraanggrian.javapoet.collections.ParameterSpecList
import com.hendraanggrian.javapoet.collections.ParameterSpecListScope
import com.hendraanggrian.javapoet.collections.TypeNameList
import com.hendraanggrian.javapoet.collections.TypeVariableNameList
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import java.lang.reflect.Type
import javax.lang.model.element.Modifier
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

/**
 * Builds new [MethodSpec], by populating newly created [MethodSpecBuilder] using
 * provided [configuration].
 */
inline fun buildMethodSpec(name: String, configuration: MethodSpecBuilder.() -> Unit): MethodSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return MethodSpecBuilder(MethodSpec.methodBuilder(name)).apply(configuration).build()
}

/**
 * Builds new constructor [MethodSpec], by populating newly created [MethodSpecBuilder] using
 * provided [configuration].
 */
inline fun buildConstructorMethodSpec(configuration: MethodSpecBuilder.() -> Unit): MethodSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return MethodSpecBuilder(MethodSpec.constructorBuilder()).apply(configuration).build()
}

/**
 * Property delegate for building new [MethodSpec], by populating newly created [MethodSpecBuilder]
 * using provided [configuration].
 */
fun buildingMethodSpec(configuration: MethodSpecBuilder.() -> Unit): SpecLoader<MethodSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return createSpecLoader { buildMethodSpec(it, configuration) }
}

/**
 * Wrapper of [MethodSpec.Builder], providing DSL support as a replacement to Java builder.
 *
 * @param nativeBuilder source builder.
 */
@JavapoetSpecDsl
class MethodSpecBuilder(private val nativeBuilder: MethodSpec.Builder) : CodeBlockContainer {
    val modifiers: MutableList<Modifier> get() = nativeBuilder.modifiers

    /** Name of this method. */
    var name: String
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.setName(value)
        }

    /** Javadoc of this method. */
    val javadoc: JavadocContainer = object : JavadocContainer {
        override fun append(format: String, vararg args: Any): Unit =
            format.internalFormat(args) { format2, args2 ->
                nativeBuilder.addJavadoc(format2, *args2)
            }

        override fun append(code: CodeBlock) {
            nativeBuilder.addJavadoc(code)
        }
    }

    /** Configures javadoc of this method. */
    fun javadoc(configuration: JavadocContainerScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        JavadocContainerScope(javadoc).configuration()
    }

    /** Annotations of this method. */
    val annotations: AnnotationSpecList = AnnotationSpecList(nativeBuilder.annotations)

    /** Configures annotations of this method. */
    fun annotations(configuration: AnnotationSpecListScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        AnnotationSpecListScope(annotations).configuration()
    }

    /** Add field modifiers. */
    fun addModifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    /** Type variables of this method. */
    val typeVariables: TypeVariableNameList = TypeVariableNameList(nativeBuilder.typeVariables)

    /** Configures type variables of this method. */
    fun typeVariables(configuration: TypeVariableNameList.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        typeVariables.configuration()
    }

    /** Add return line to type name. */
    var returns: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.returns(value)
        }

    /** Add return line to [type]. */
    fun returns(type: Type) {
        nativeBuilder.returns(type)
    }

    /** Add return line to [type]. */
    fun returns(type: KClass<*>) {
        nativeBuilder.returns(type.java)
    }

    /** Add return line to [T]. */
    inline fun <reified T> returns(): Unit = returns(T::class)

    /** Parameters of this method. */
    val parameters: ParameterSpecList = ParameterSpecList(nativeBuilder.parameters)

    /** Configures parameters of this method. */
    fun parameters(configuration: ParameterSpecListScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        ParameterSpecListScope(parameters).configuration()
    }

    /** Add vararg keyword to array type parameter. */
    var isVarargs: Boolean
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.varargs(value)
        }

    /** Exceptions of this method. */
    val exceptions: TypeNameList = TypeNameList(mutableListOf())

    /** Configures exceptions of this method. */
    fun exceptions(configuration: TypeNameList.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        exceptions.configuration()
    }

    override fun append(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 -> nativeBuilder.addCode(format2, *args2) }

    override fun appendNamed(format: String, args: Map<String, *>): Unit =
        format.internalFormat(args) { format2, args2 -> nativeBuilder.addNamedCode(format2, args2) }

    override fun append(code: CodeBlock) {
        nativeBuilder.addCode(code)
    }

    /** Add comment like [String.format]. */
    fun addComment(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 -> nativeBuilder.addComment(format2, *args2) }

    /** Set default value like [String.format]. */
    fun defaultValue(format: String, vararg args: Any) {
        defaultValue = codeBlockOf(format, *args)
    }

    /** Set default value to code. */
    var defaultValue: CodeBlock
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.defaultValue(value)
        }

    override fun beginControlFlow(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 ->
            nativeBuilder.beginControlFlow(format2, *args2)
        }

    fun beginControlFlow(code: CodeBlock) {
        nativeBuilder.beginControlFlow(code)
    }

    override fun nextControlFlow(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 ->
            nativeBuilder.nextControlFlow(format2, *args2)
        }

    fun nextControlFlow(code: CodeBlock) {
        nativeBuilder.nextControlFlow(code)
    }

    override fun endControlFlow() {
        nativeBuilder.endControlFlow()
    }

    override fun endControlFlow(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 ->
            nativeBuilder.endControlFlow(format2, *args2)
        }

    fun endControlFlow(code: CodeBlock) {
        nativeBuilder.endControlFlow(code)
    }

    override fun appendLine(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 ->
            nativeBuilder.addStatement(format2, *args2)
        }

    override fun appendLine(code: CodeBlock) {
        nativeBuilder.addStatement(code)
    }

    /** Returns native spec. */
    fun build(): MethodSpec = nativeBuilder
        .addExceptions(exceptions)
        .build()
}
