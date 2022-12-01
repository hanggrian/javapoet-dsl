@file:OptIn(ExperimentalContracts::class)

package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.collections.AnnotationSpecList
import com.hendraanggrian.javapoet.collections.AnnotationSpecListScope
import com.hendraanggrian.javapoet.collections.JavadocContainer
import com.hendraanggrian.javapoet.collections.JavadocContainerScope
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeName
import java.lang.reflect.Type
import javax.lang.model.element.Modifier
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

/**
 * Builds new [FieldSpec] from [TypeName] supplying its name and modifiers, by populating newly
 * created [FieldSpecBuilder] using provided [configuration].
 */
inline fun buildFieldSpec(
    type: TypeName,
    name: String,
    vararg modifiers: Modifier,
    configuration: FieldSpecBuilder.() -> Unit
): FieldSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return FieldSpecBuilder(FieldSpec.builder(type, name, *modifiers)).apply(configuration).build()
}

/**
 * Builds new [FieldSpec] from [Type] supplying its name and modifiers, by populating newly
 * created [FieldSpecBuilder] using provided [configuration].
 */
inline fun buildFieldSpec(
    type: Type,
    name: String,
    vararg modifiers: Modifier,
    configuration: FieldSpecBuilder.() -> Unit
): FieldSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return FieldSpecBuilder(FieldSpec.builder(type, name, *modifiers)).apply(configuration).build()
}

/**
 * Builds new [FieldSpec] from [KClass] supplying its name and modifiers, by populating newly
 * created [FieldSpecBuilder] using provided [configuration].
 */
inline fun buildFieldSpec(
    type: KClass<*>,
    name: String,
    vararg modifiers: Modifier,
    configuration: FieldSpecBuilder.() -> Unit
): FieldSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return FieldSpecBuilder(FieldSpec.builder(type.java, name, *modifiers)).apply(configuration)
        .build()
}

/**
 * Builds new [FieldSpec] from [T] supplying its name and modifiers, by populating newly
 * created [FieldSpecBuilder] using provided [configuration].
 */
inline fun <reified T> buildFieldSpec(
    name: String,
    vararg modifiers: Modifier,
    configuration: FieldSpecBuilder.() -> Unit
): FieldSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return FieldSpecBuilder(FieldSpec.builder(T::class.java, name, *modifiers)).apply(configuration)
        .build()
}

/**
 * Property delegate for building new [FieldSpec] from [TypeName] supplying its name and modifiers,
 * by populating newly created [FieldSpecBuilder] using provided [configuration].
 */
fun buildingFieldSpec(
    type: TypeName,
    vararg modifiers: Modifier,
    configuration: FieldSpecBuilder.() -> Unit
): SpecLoader<FieldSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return createSpecLoader { buildFieldSpec(type, it, *modifiers, configuration = configuration) }
}

/**
 * Property delegate for building new [FieldSpec] from [Type] supplying its name and modifiers, by
 * populating newly created [FieldSpecBuilder] using provided [configuration].
 */
fun buildingFieldSpec(
    type: Type,
    vararg modifiers: Modifier,
    configuration: FieldSpecBuilder.() -> Unit
): SpecLoader<FieldSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return createSpecLoader { buildFieldSpec(type, it, *modifiers, configuration = configuration) }
}

/**
 * Property delegate for building new [FieldSpec] from [KClass] supplying its name and modifiers, by
 * populating newly created [FieldSpecBuilder] using provided [configuration].
 */
fun buildingFieldSpec(
    type: KClass<*>,
    vararg modifiers: Modifier,
    configuration: FieldSpecBuilder.() -> Unit
): SpecLoader<FieldSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return createSpecLoader { buildFieldSpec(type, it, *modifiers, configuration = configuration) }
}

/**
 * Wrapper of [FieldSpec.Builder], providing DSL support as a replacement to Java builder.
 *
 * @param nativeBuilder source builder.
 */
@JavapoetSpecDsl
class FieldSpecBuilder(private val nativeBuilder: FieldSpec.Builder) {
    val modifiers: MutableList<Modifier> get() = nativeBuilder.modifiers

    /** Javadoc of this field. */
    val javadoc: JavadocContainer = object : JavadocContainer {
        override fun append(format: String, vararg args: Any): Unit =
            format.internalFormat(args) { format2, args2 ->
                nativeBuilder.addJavadoc(format2, *args2)
            }

        override fun append(code: CodeBlock) {
            nativeBuilder.addJavadoc(code)
        }
    }

    /** Configures javadoc for this field. */
    fun javadoc(configuration: JavadocContainerScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        JavadocContainerScope(javadoc).configuration()
    }

    /** Annotations of this field. */
    val annotations: AnnotationSpecList = AnnotationSpecList(nativeBuilder.annotations)

    /** Configures annotations for this field. */
    fun annotations(configuration: AnnotationSpecListScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        AnnotationSpecListScope(annotations).configuration()
    }

    /** Add field modifiers. */
    fun addModifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    /** Initialize field value like [String.format]. */
    fun initializer(format: String, vararg args: Any) {
        initializer = codeBlockOf(format, *args)
    }

    /** Initialize field value with code. */
    var initializer: CodeBlock
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.initializer(value)
        }

    /** Returns native spec. */
    fun build(): FieldSpec = nativeBuilder.build()
}
