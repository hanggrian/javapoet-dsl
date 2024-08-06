@file:OptIn(ExperimentalContracts::class)

package com.hanggrian.javapoet

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
public fun buildMethodSpec(name: String, configuration: MethodSpecBuilder.() -> Unit): MethodSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return MethodSpecBuilder(MethodSpec.methodBuilder(name))
        .apply(configuration)
        .build()
}

/**
 * Inserts new [MethodSpec] by populating newly created [MethodSpecBuilder] using provided
 * [configuration].
 */
public fun MethodSpecHandler.method(
    name: String,
    configuration: MethodSpecBuilder.() -> Unit,
): MethodSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return MethodSpecBuilder(MethodSpec.methodBuilder(name))
        .apply(configuration)
        .build()
        .also(::method)
}

/**
 * Inserts new constructor [MethodSpec] by populating newly created [MethodSpecBuilder] using
 * provided [configuration].
 */
public fun MethodSpecHandler.constructorMethod(
    configuration: MethodSpecBuilder.() -> Unit,
): MethodSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return MethodSpecBuilder(MethodSpec.constructorBuilder())
        .apply(configuration)
        .build()
        .also(::method)
}

/**
 * Property delegate for inserting new [MethodSpec] by populating newly created [MethodSpecBuilder]
 * using provided [configuration].
 */
public fun MethodSpecHandler.methoding(
    configuration: MethodSpecBuilder.() -> Unit,
): SpecDelegateProvider<MethodSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        MethodSpecBuilder(MethodSpec.methodBuilder(it))
            .apply(configuration)
            .build()
            .also(::method)
    }
}

/** Invokes DSL to configure [MethodSpec] collection. */
public fun MethodSpecHandler.methods(configuration: MethodSpecHandlerScope.() -> Unit) {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    MethodSpecHandlerScope
        .of(this)
        .configuration()
}

/** Responsible for managing a set of [MethodSpec] instances. */
public interface MethodSpecHandler {
    public fun method(method: MethodSpec)

    public fun method(name: String): MethodSpec = methodSpecOf(name).also(::method)

    public fun methoding(): SpecDelegateProvider<MethodSpec> =
        SpecDelegateProvider { methodSpecOf(it).also(::method) }

    public fun constructorMethod(): MethodSpec = emptyConstructorMethodSpec().also(::method)
}

/**
 * Receiver for the `methods` block providing an extended set of operators for the
 * configuration.
 */
@JavapoetDsl
public open class MethodSpecHandlerScope private constructor(handler: MethodSpecHandler) :
    MethodSpecHandler by handler {
        /**
         * @see method
         */
        public operator fun String.invoke(configuration: MethodSpecBuilder.() -> Unit): MethodSpec =
            method(this, configuration)

        public companion object {
            public fun of(handler: MethodSpecHandler): MethodSpecHandlerScope =
                MethodSpecHandlerScope(handler)
        }
    }

/** Wrapper of [MethodSpec.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetDsl
public class MethodSpecBuilder(private val nativeBuilder: MethodSpec.Builder) :
    AnnotationSpecHandler,
    ParameterSpecHandler {
    public val annotations: MutableList<AnnotationSpec> get() = nativeBuilder.annotations
    public val modifiers: MutableList<Modifier> get() = nativeBuilder.modifiers
    public val typeVariables: MutableList<TypeVariableName> get() = nativeBuilder.typeVariables
    public val parameters: MutableList<ParameterSpec> get() = nativeBuilder.parameters

    public var name: String
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.setName(value)
        }

    public fun javadoc(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 ->
            nativeBuilder.addJavadoc(format2, *args2)
        }

    public fun javadoc(block: CodeBlock) {
        nativeBuilder.addJavadoc(block)
    }

    public override fun annotation(annotation: AnnotationSpec) {
        nativeBuilder.addAnnotation(annotation)
    }

    public fun modifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    public fun typeVariables(typeVariables: Iterable<TypeVariableName>) {
        nativeBuilder.addTypeVariables(typeVariables)
    }

    public fun typeVariable(typeVariable: TypeVariableName) {
        nativeBuilder.addTypeVariable(typeVariable)
    }

    public var returns: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.returns(value)
        }

    public fun returns(type: Class<*>) {
        nativeBuilder.returns(type)
    }

    public fun returns(type: KClass<*>) {
        nativeBuilder.returns(type.java)
    }

    public inline fun <reified T> returns(): Unit = returns(T::class)

    public override fun parameter(parameter: ParameterSpec) {
        nativeBuilder.addParameter(parameter)
    }

    public var isVarargs: Boolean
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.varargs(value)
        }

    public fun exceptions(exceptions: Iterable<TypeName>) {
        nativeBuilder.addExceptions(exceptions)
    }

    public fun exception(exception: TypeName) {
        nativeBuilder.addException(exception)
    }

    public fun exception(exception: Class<*>) {
        nativeBuilder.addException(exception)
    }

    public fun exception(exception: KClass<*>) {
        nativeBuilder.addException(exception.java)
    }

    public inline fun <reified T> exception(): Unit = exception(T::class)

    public fun append(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 -> nativeBuilder.addCode(format2, *args2) }

    public fun appendNamed(format: String, args: Map<String, *>): Unit =
        format.internalFormat(args) { format2, args2 -> nativeBuilder.addNamedCode(format2, args2) }

    public fun append(code: CodeBlock) {
        nativeBuilder.addCode(code)
    }

    public fun comment(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 -> nativeBuilder.addComment(format2, *args2) }

    public fun defaultValue(format: String, vararg args: Any) {
        defaultValue = codeBlockOf(format, *args)
    }

    public var defaultValue: CodeBlock
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.defaultValue(value)
        }

    public fun beginControlFlow(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 ->
            nativeBuilder.beginControlFlow(format2, *args2)
        }

    public fun beginControlFlow(code: CodeBlock) {
        nativeBuilder.beginControlFlow(code)
    }

    public fun nextControlFlow(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 ->
            nativeBuilder.nextControlFlow(format2, *args2)
        }

    public fun nextControlFlow(code: CodeBlock) {
        nativeBuilder.nextControlFlow(code)
    }

    public fun endControlFlow() {
        nativeBuilder.endControlFlow()
    }

    public fun endControlFlow(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 ->
            nativeBuilder.endControlFlow(format2, *args2)
        }

    public fun endControlFlow(code: CodeBlock) {
        nativeBuilder.endControlFlow(code)
    }

    public fun appendLine(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 ->
            nativeBuilder.addStatement(format2, *args2)
        }

    public fun appendLine(code: CodeBlock) {
        nativeBuilder.addStatement(code)
    }

    public fun build(): MethodSpec = nativeBuilder.build()
}
