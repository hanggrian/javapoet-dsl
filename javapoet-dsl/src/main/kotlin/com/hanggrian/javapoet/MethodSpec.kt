@file:OptIn(ExperimentalContracts::class)

package com.hanggrian.javapoet

import com.hanggrian.javapoet.internals.Internals
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeVariableName
import javax.lang.model.element.Modifier
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

/** Creates new [MethodSpec] using parameters. */
public inline fun methodSpecOf(name: String): MethodSpec =
    MethodSpec
        .methodBuilder(name)
        .build()

/** Creates new constructor [MethodSpec]. */
public inline fun emptyConstructorMethodSpec(): MethodSpec =
    MethodSpec
        .constructorBuilder()
        .build()

/**
 * Builds new [MethodSpec] by populating newly created [MethodSpecBuilder] using provided
 * [configuration].
 */
public inline fun buildMethodSpec(
    name: String,
    configuration: MethodSpecBuilder.() -> Unit,
): MethodSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return MethodSpecBuilder(MethodSpec.methodBuilder(name))
        .apply(configuration)
        .build()
}

/**
 * Inserts new [MethodSpec] by populating newly created [MethodSpecBuilder] using provided
 * [configuration].
 */
public inline fun MethodSpecHandler.add(
    name: String,
    configuration: MethodSpecBuilder.() -> Unit,
): MethodSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return MethodSpecBuilder(MethodSpec.methodBuilder(name))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Inserts new constructor [MethodSpec] by populating newly created [MethodSpecBuilder] using
 * provided [configuration].
 */
public inline fun MethodSpecHandler.addConstructor(
    configuration: MethodSpecBuilder.() -> Unit,
): MethodSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return MethodSpecBuilder(MethodSpec.constructorBuilder())
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Property delegate for inserting new [MethodSpec] by populating newly created [MethodSpecBuilder]
 * using provided [configuration].
 */
public fun MethodSpecHandler.adding(
    configuration: MethodSpecBuilder.() -> Unit,
): SpecDelegateProvider<MethodSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        MethodSpecBuilder(MethodSpec.methodBuilder(it))
            .apply(configuration)
            .build()
            .also(::add)
    }
}

/** Responsible for managing a set of [MethodSpec] instances. */
public interface MethodSpecHandler {
    public fun add(method: MethodSpec)

    public fun add(name: String): MethodSpec = methodSpecOf(name).also(::add)

    public fun adding(): SpecDelegateProvider<MethodSpec> =
        SpecDelegateProvider { methodSpecOf(it).also(::add) }

    public fun addConstructor(): MethodSpec = emptyConstructorMethodSpec().also(::add)
}

/**
 * Receiver for the `methods` block providing an extended set of operators for the
 * configuration.
 */
@JavapoetDsl
public open class MethodSpecHandlerScope private constructor(handler: MethodSpecHandler) :
    MethodSpecHandler by handler {
        public inline operator fun String.invoke(
            configuration: MethodSpecBuilder.() -> Unit,
        ): MethodSpec = add(this, configuration)

        public companion object {
            public fun of(handler: MethodSpecHandler): MethodSpecHandlerScope =
                MethodSpecHandlerScope(handler)
        }
    }

/** Wrapper of [MethodSpec.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetDsl
public class MethodSpecBuilder(private val nativeBuilder: MethodSpec.Builder) {
    public val annotations: AnnotationSpecHandler =
        object : AnnotationSpecHandler {
            override fun add(annotation: AnnotationSpec) {
                annotationSpecs += annotation
            }
        }

    public val parameters: ParameterSpecHandler =
        object : ParameterSpecHandler {
            override fun add(parameter: ParameterSpec) {
                parameterSpecs += parameter
            }
        }

    /** Invokes DSL to configure [AnnotationSpec] collection. */
    public inline fun annotations(configuration: AnnotationSpecHandlerScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        AnnotationSpecHandlerScope
            .of(annotations)
            .configuration()
    }

    /** Invokes DSL to configure [ParameterSpec] collection. */
    public inline fun parameters(configuration: ParameterSpecHandlerScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        ParameterSpecHandlerScope
            .of(parameters)
            .configuration()
    }

    public val annotationSpecs: MutableList<AnnotationSpec> get() = nativeBuilder.annotations
    public val modifiers: MutableList<Modifier> get() = nativeBuilder.modifiers
    public val typeVariables: MutableList<TypeVariableName> get() = nativeBuilder.typeVariables
    public val parameterSpecs: MutableList<ParameterSpec> get() = nativeBuilder.parameters

    public var name: String
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.setName(value)
        }

    public fun addJavadoc(format: String, vararg args: Any): Unit =
        Internals.format(format, args) { format2, args2 ->
            nativeBuilder.addJavadoc(format2, *args2)
        }

    public fun addJavadoc(block: CodeBlock) {
        nativeBuilder.addJavadoc(block)
    }

    public fun addModifiers(vararg modifiers: Modifier) {
        this.modifiers += modifiers
    }

    public fun addTypeVariables(vararg typeVariables: TypeVariableName) {
        this.typeVariables += typeVariables
    }

    public var returns: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.returns(value)
        }

    public fun setReturns(type: Class<*>) {
        returns = type.name2
    }

    public fun setReturns(type: KClass<*>) {
        returns = type.name
    }

    public inline fun <reified T> setReturns() {
        returns = T::class.name
    }

    public var isVarargs: Boolean
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.varargs(value)
        }

    public fun addExceptions(exceptions: Iterable<TypeName>) {
        nativeBuilder.addExceptions(exceptions)
    }

    public fun addException(exception: TypeName) {
        nativeBuilder.addException(exception)
    }

    public fun addException(exception: Class<*>) {
        nativeBuilder.addException(exception)
    }

    public fun addException(exception: KClass<*>) {
        nativeBuilder.addException(exception.java)
    }

    public inline fun <reified T> addException(): Unit = addException(T::class)

    public fun append(format: String, vararg args: Any): Unit =
        Internals.format(format, args) { format2, args2 -> nativeBuilder.addCode(format2, *args2) }

    public fun appendNamed(format: String, args: Map<String, *>): Unit =
        Internals.format(format, args) { format2, args2 ->
            nativeBuilder.addNamedCode(format2, args2)
        }

    public fun append(code: CodeBlock) {
        nativeBuilder.addCode(code)
    }

    public fun addComment(format: String, vararg args: Any): Unit =
        Internals.format(format, args) { format2, args2 ->
            nativeBuilder.addComment(format2, *args2)
        }

    public var defaultValue: CodeBlock
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.defaultValue(value)
        }

    public fun setDefaultValue(format: String, vararg args: Any) {
        defaultValue = codeBlockOf(format, *args)
    }

    public fun beginControlFlow(format: String, vararg args: Any): Unit =
        Internals.format(format, args) { format2, args2 ->
            nativeBuilder.beginControlFlow(format2, *args2)
        }

    public fun beginControlFlow(code: CodeBlock) {
        nativeBuilder.beginControlFlow(code)
    }

    public fun nextControlFlow(format: String, vararg args: Any): Unit =
        Internals.format(format, args) { format2, args2 ->
            nativeBuilder.nextControlFlow(format2, *args2)
        }

    public fun nextControlFlow(code: CodeBlock) {
        nativeBuilder.nextControlFlow(code)
    }

    public fun endControlFlow() {
        nativeBuilder.endControlFlow()
    }

    public fun endControlFlow(format: String, vararg args: Any): Unit =
        Internals.format(format, args) { format2, args2 ->
            nativeBuilder.endControlFlow(format2, *args2)
        }

    public fun endControlFlow(code: CodeBlock) {
        nativeBuilder.endControlFlow(code)
    }

    public fun appendLine(format: String, vararg args: Any): Unit =
        Internals.format(format, args) { format2, args2 ->
            nativeBuilder.addStatement(format2, *args2)
        }

    public fun appendLine(code: CodeBlock) {
        nativeBuilder.addStatement(code)
    }

    public fun build(): MethodSpec = nativeBuilder.build()
}
