@file:OptIn(ExperimentalContracts::class)

package com.hanggrian.javapoet

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

/** Creates new [AnnotationSpec] using parameters. */
public inline fun annotationSpecOf(name: ClassName): AnnotationSpec =
    AnnotationSpec
        .builder(name)
        .build()

/**
 * Builds new [AnnotationSpec] by populating newly created [AnnotationSpecBuilder] using provided
 * [configuration].
 */
public fun buildAnnotationSpec(
    type: ClassName,
    configuration: AnnotationSpecBuilder.() -> Unit,
): AnnotationSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return AnnotationSpecBuilder(AnnotationSpec.builder(type))
        .apply(configuration)
        .build()
}

/**
 * Inserts new [AnnotationSpec] by populating newly created [AnnotationSpecBuilder] using provided
 * [configuration].
 */
public fun AnnotationSpecHandler.annotation(
    type: ClassName,
    configuration: AnnotationSpecBuilder.() -> Unit,
): AnnotationSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return AnnotationSpecBuilder(AnnotationSpec.builder(type))
        .apply(configuration)
        .build()
        .also(::annotation)
}

/**
 * Inserts new [AnnotationSpec] by populating newly created [AnnotationSpecBuilder] using provided
 * [configuration].
 */
public fun AnnotationSpecHandler.annotation(
    type: Class<*>,
    configuration: AnnotationSpecBuilder.() -> Unit,
): AnnotationSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return AnnotationSpecBuilder(AnnotationSpec.builder(type))
        .apply(configuration)
        .build()
        .also(::annotation)
}

/**
 * Inserts new [AnnotationSpec] by populating newly created [AnnotationSpecBuilder] using provided
 * [configuration].
 */
public fun AnnotationSpecHandler.annotation(
    type: KClass<*>,
    configuration: AnnotationSpecBuilder.() -> Unit,
): AnnotationSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return AnnotationSpecBuilder(AnnotationSpec.builder(type.java))
        .apply(configuration)
        .build()
        .also(::annotation)
}

/** Convenient method to insert [AnnotationSpec] using reified type. */
public inline fun <reified T> AnnotationSpecHandler.annotation(): AnnotationSpec =
    AnnotationSpec
        .builder(T::class.java)
        .build()
        .also(::annotation)

/** Invokes DSL to configure [AnnotationSpec] collection. */
public fun AnnotationSpecHandler.annotations(configuration: AnnotationSpecHandlerScope.() -> Unit) {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    AnnotationSpecHandlerScope
        .of(this)
        .configuration()
}

/** Responsible for managing a set of [AnnotationSpec] instances. */
public interface AnnotationSpecHandler {
    public fun annotation(annotation: AnnotationSpec)

    public fun annotation(type: ClassName): AnnotationSpec =
        annotationSpecOf(type).also(::annotation)

    public fun annotation(type: Class<*>): AnnotationSpec =
        annotationSpecOf(type.name2).also(::annotation)

    public fun annotation(type: KClass<*>): AnnotationSpec =
        annotationSpecOf(type.name).also(::annotation)
}

/**
 * Receiver for the `annotations` block providing an extended set of operators for the
 * configuration.
 */
@JavapoetDsl
public open class AnnotationSpecHandlerScope private constructor(handler: AnnotationSpecHandler) :
    AnnotationSpecHandler by handler {
        /**
         * @see annotation
         */
        public operator fun ClassName.invoke(
            configuration: AnnotationSpecBuilder.() -> Unit,
        ): AnnotationSpec = annotation(this, configuration)

        /**
         * @see annotation
         */
        public operator fun Class<*>.invoke(
            configuration: AnnotationSpecBuilder.() -> Unit,
        ): AnnotationSpec = annotation(this, configuration)

        /**
         * @see annotation
         */
        public operator fun KClass<*>.invoke(
            configuration: AnnotationSpecBuilder.() -> Unit,
        ): AnnotationSpec = annotation(this, configuration)

        public companion object {
            public fun of(handler: AnnotationSpecHandler): AnnotationSpecHandlerScope =
                AnnotationSpecHandlerScope(handler)
        }
    }

/** Wrapper of [AnnotationSpec.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetDsl
public class AnnotationSpecBuilder(private val nativeBuilder: AnnotationSpec.Builder) {
    public val members: Map<String, List<CodeBlock>> get() = nativeBuilder.members

    public fun member(name: String, format: String, vararg args: Any): Unit =
        member(name, codeBlockOf(format, *args))

    public fun member(name: String, code: CodeBlock) {
        nativeBuilder.addMember(name, code)
    }

    public fun build(): AnnotationSpec = nativeBuilder.build()
}
