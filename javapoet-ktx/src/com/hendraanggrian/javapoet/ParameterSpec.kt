package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.AnnotationSpecHandler
import com.hendraanggrian.javapoet.dsl.AnnotationSpecHandlerScope
import com.hendraanggrian.javapoet.dsl.JavadocHandler
import com.hendraanggrian.javapoet.dsl.JavadocHandlerScope
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import java.lang.reflect.Type
import javax.lang.model.element.Modifier
import javax.lang.model.element.VariableElement
import kotlin.reflect.KClass

/** Converts element to [ParameterSpec]. */
fun VariableElement.asParameterSpec(): ParameterSpec = ParameterSpec.get(this)

/** Builds new [ParameterSpec] from [TypeName]. */
fun parameterSpecOf(type: TypeName, name: String, vararg modifiers: Modifier): ParameterSpec =
    ParameterSpec.builder(type, name, *modifiers).build()

/** Builds new [ParameterSpec] from [Type]. */
fun parameterSpecOf(type: Type, name: String, vararg modifiers: Modifier): ParameterSpec =
    ParameterSpec.builder(type, name, *modifiers).build()

/** Builds new [ParameterSpec] from [KClass]. */
fun parameterSpecOf(type: KClass<*>, name: String, vararg modifiers: Modifier): ParameterSpec =
    ParameterSpec.builder(type.java, name, *modifiers).build()

/** Builds new [ParameterSpec] from [T]. */
inline fun <reified T> parameterSpecOf(name: String, vararg modifiers: Modifier): ParameterSpec =
    ParameterSpec.builder(T::class.java, name, *modifiers).build()

/**
 * Builds new [ParameterSpec] from [TypeName],
 * by populating newly created [ParameterSpecBuilder] using provided [configuration].
 */
fun buildParameterSpec(
    type: TypeName,
    name: String,
    vararg modifiers: Modifier,
    configuration: ParameterSpecBuilder.() -> Unit
): ParameterSpec = ParameterSpecBuilder(ParameterSpec.builder(type, name, *modifiers))
    .apply(configuration).build()

/**
 * Builds new [ParameterSpec] from [Type],
 * by populating newly created [ParameterSpecBuilder] using provided [configuration].
 */
fun buildParameterSpec(
    type: Type,
    name: String,
    vararg modifiers: Modifier,
    configuration: ParameterSpecBuilder.() -> Unit
): ParameterSpec = ParameterSpecBuilder(ParameterSpec.builder(type, name, *modifiers))
    .apply(configuration).build()

/**
 * Builds new [ParameterSpec] from [KClass],
 * by populating newly created [ParameterSpecBuilder] using provided [configuration].
 */
fun buildParameterSpec(
    type: KClass<*>,
    name: String,
    vararg modifiers: Modifier,
    configuration: ParameterSpecBuilder.() -> Unit
): ParameterSpec = ParameterSpecBuilder(ParameterSpec.builder(type.java, name, *modifiers))
    .apply(configuration).build()

/**
 * Builds new [ParameterSpec] from [T],
 * by populating newly created [ParameterSpecBuilder] using provided [configuration].
 */
inline fun <reified T> buildParameterSpec(
    name: String,
    vararg modifiers: Modifier,
    noinline configuration: ParameterSpecBuilder.() -> Unit
): ParameterSpec = buildParameterSpec(T::class, name, *modifiers, configuration = configuration)

/** Modify existing [ParameterSpec.Builder] using provided [configuration]. */
fun ParameterSpec.Builder.edit(configuration: ParameterSpecBuilder.() -> Unit): ParameterSpec.Builder =
    ParameterSpecBuilder(this).apply(configuration).nativeBuilder

/**
 * Wrapper of [ParameterSpec.Builder], providing DSL support as a replacement to Java builder.
 * @param nativeBuilder source builder.
 */
@SpecDslMarker
class ParameterSpecBuilder internal constructor(val nativeBuilder: ParameterSpec.Builder) {

    /** Modifiers of this parameter. */
    val modifiers: MutableList<Modifier> get() = nativeBuilder.modifiers

    /** Javadoc of this parameter. */
    val javadoc: JavadocHandler = object : JavadocHandler() {
        override fun append(format: String, vararg args: Any): Unit =
            format.internalFormat(args) { s, array -> nativeBuilder.addJavadoc(s, *array) }

        override fun append(code: CodeBlock) {
            nativeBuilder.addJavadoc(code)
        }
    }

    /** Configures javadoc for this parameter. */
    fun javadoc(configuration: JavadocHandlerScope.() -> Unit): Unit =
        JavadocHandlerScope(javadoc).configuration()

    /** Annotations of this parameter. */
    val annotations: AnnotationSpecHandler = AnnotationSpecHandler(nativeBuilder.annotations)

    /** Configures annotations of this parameter. */
    fun annotations(configuration: AnnotationSpecHandlerScope.() -> Unit): Unit =
        AnnotationSpecHandlerScope(annotations).configuration()

    /** Add parameter modifiers. */
    fun addModifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    /** Returns native spec. */
    fun build(): ParameterSpec = nativeBuilder.build()
}
