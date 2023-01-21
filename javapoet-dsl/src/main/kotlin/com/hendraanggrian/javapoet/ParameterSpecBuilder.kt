@file:OptIn(ExperimentalContracts::class)

package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.collections.AnnotationSpecList
import com.hendraanggrian.javapoet.collections.AnnotationSpecListScope
import com.hendraanggrian.javapoet.collections.JavadocContainer
import com.hendraanggrian.javapoet.collections.JavadocContainerScope
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import java.lang.reflect.Type
import javax.lang.model.element.Modifier
import javax.lang.model.element.VariableElement
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

/** Converts element to [ParameterSpec]. */
inline fun VariableElement.toParameterSpec(): ParameterSpec = ParameterSpec.get(this)

/**
 * Builds new [ParameterSpec] from [TypeName], by populating newly created [ParameterSpecBuilder]
 * using provided [configuration].
 */
inline fun buildParameterSpec(
    type: TypeName,
    name: String,
    vararg modifiers: Modifier,
    configuration: ParameterSpecBuilder.() -> Unit
): ParameterSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return ParameterSpecBuilder(ParameterSpec.builder(type, name, *modifiers)).apply(configuration)
        .build()
}

/**
 * Builds new [ParameterSpec] from [Type], by populating newly created [ParameterSpecBuilder] using
 * provided [configuration].
 */
inline fun buildParameterSpec(
    type: Type,
    name: String,
    vararg modifiers: Modifier,
    configuration: ParameterSpecBuilder.() -> Unit
): ParameterSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return ParameterSpecBuilder(ParameterSpec.builder(type, name, *modifiers)).apply(configuration)
        .build()
}

/**
 * Builds new [ParameterSpec] from [KClass], by populating newly created [ParameterSpecBuilder]
 * using provided [configuration].
 */
inline fun buildParameterSpec(
    type: KClass<*>,
    name: String,
    vararg modifiers: Modifier,
    configuration: ParameterSpecBuilder.() -> Unit
): ParameterSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return ParameterSpecBuilder(ParameterSpec.builder(type.java, name, *modifiers))
        .apply(configuration)
        .build()
}

/**
 * Builds new [ParameterSpec] from [T], by populating newly created [ParameterSpecBuilder] using
 * provided [configuration].
 */
inline fun <reified T> buildParameterSpec(
    name: String,
    vararg modifiers: Modifier,
    configuration: ParameterSpecBuilder.() -> Unit
): ParameterSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return ParameterSpecBuilder(ParameterSpec.builder(T::class.java, name, *modifiers))
        .apply(configuration)
        .build()
}

/**
 * Property delegate for building new [ParameterSpec] from [TypeName], by populating newly
 * created [ParameterSpecBuilder] using provided [configuration].
 */
fun buildingParameterSpec(
    type: TypeName,
    vararg modifiers: Modifier,
    configuration: ParameterSpecBuilder.() -> Unit
): SpecLoader<ParameterSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return createSpecLoader {
        buildParameterSpec(type, it, *modifiers, configuration = configuration)
    }
}

/**
 * Property delegate for building new [ParameterSpec] from [Type], by populating newly
 * created [ParameterSpecBuilder] using provided [configuration].
 */
fun buildingParameterSpec(
    type: Type,
    vararg modifiers: Modifier,
    configuration: ParameterSpecBuilder.() -> Unit
): SpecLoader<ParameterSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return createSpecLoader {
        buildParameterSpec(type, it, *modifiers, configuration = configuration)
    }
}

/**
 * Property delegate for building new [ParameterSpec] from [KClass], by populating newly
 * created [ParameterSpecBuilder] using provided [configuration].
 */
fun buildingParameterSpec(
    type: KClass<*>,
    vararg modifiers: Modifier,
    configuration: ParameterSpecBuilder.() -> Unit
): SpecLoader<ParameterSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return createSpecLoader {
        buildParameterSpec(type, it, *modifiers, configuration = configuration)
    }
}

/**
 * Wrapper of [ParameterSpec.Builder], providing DSL support as a replacement to Java builder.
 *
 * @param nativeBuilder source builder.
 */
@JavapoetSpecDsl
class ParameterSpecBuilder(private val nativeBuilder: ParameterSpec.Builder) {
    val modifiers: MutableList<Modifier> get() = nativeBuilder.modifiers

    /** Javadoc of this parameter. */
    val javadoc: JavadocContainer = object : JavadocContainer {
        override fun append(format: String, vararg args: Any): Unit =
            format.internalFormat(args) { format2, args2 ->
                nativeBuilder.addJavadoc(format2, *args2)
            }

        override fun append(code: CodeBlock) {
            nativeBuilder.addJavadoc(code)
        }
    }

    /** Configures javadoc for this parameter. */
    inline fun javadoc(configuration: JavadocContainerScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        JavadocContainerScope(javadoc).configuration()
    }

    /** Annotations of this parameter. */
    val annotations: AnnotationSpecList = AnnotationSpecList(nativeBuilder.annotations)

    /** Configures annotations of this parameter. */
    inline fun annotations(configuration: AnnotationSpecListScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        AnnotationSpecListScope(annotations).configuration()
    }

    /** Add parameter modifiers. */
    fun addModifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    /** Returns native spec. */
    fun build(): ParameterSpec = nativeBuilder.build()
}
