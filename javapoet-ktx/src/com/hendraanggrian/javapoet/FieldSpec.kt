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
import kotlin.reflect.KClass

/** Builds a new [FieldSpec] from [type] supplying its [name] and [modifiers]. */
fun fieldSpecOf(type: TypeName, name: String, vararg modifiers: Modifier): FieldSpec =
    FieldSpec.builder(type, name, *modifiers).build()

/**
 * Builds a new [FieldSpec] from [type] supplying its [name] and [modifiers],
 * by populating newly created [FieldSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildFieldSpec(
    type: TypeName,
    name: String,
    vararg modifiers: Modifier,
    builderAction: FieldSpecBuilder.() -> Unit
): FieldSpec = FieldSpec.builder(type, name, *modifiers).build(builderAction)

/** Builds a new [FieldSpec] from [type] supplying its [name] and [modifiers]. */
fun fieldSpecOf(type: Type, name: String, vararg modifiers: Modifier): FieldSpec =
    FieldSpec.builder(type, name, *modifiers).build()

/** Builds a new [FieldSpec] from [type] supplying its [name] and [modifiers]. */
fun fieldSpecOf(type: KClass<*>, name: String, vararg modifiers: Modifier): FieldSpec =
    fieldSpecOf(type.java, name, *modifiers)

/** Builds a new [FieldSpec] from [T] supplying its [name] and [modifiers]. */
inline fun <reified T> fieldSpecOf(name: String, vararg modifiers: Modifier): FieldSpec =
    fieldSpecOf(T::class, name, *modifiers)

/**
 * Builds a new [FieldSpec] from [type] supplying its [name] and [modifiers],
 * by populating newly created [FieldSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildFieldSpec(
    type: Type,
    name: String,
    vararg modifiers: Modifier,
    builderAction: FieldSpecBuilder.() -> Unit
): FieldSpec = FieldSpec.builder(type, name, *modifiers).build(builderAction)

/**
 * Builds a new [FieldSpec] from [type] supplying its [name] and [modifiers],
 * by populating newly created [FieldSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildFieldSpec(
    type: KClass<*>,
    name: String,
    vararg modifiers: Modifier,
    builderAction: FieldSpecBuilder.() -> Unit
): FieldSpec = buildFieldSpec(type.java, name, *modifiers, builderAction = builderAction)

/**
 * Builds a new [FieldSpec] from [T] supplying its [name] and [modifiers],
 * by populating newly created [FieldSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun <reified T> buildFieldSpec(
    name: String,
    vararg modifiers: Modifier,
    builderAction: FieldSpecBuilder.() -> Unit
): FieldSpec = buildFieldSpec(T::class, name, *modifiers, builderAction = builderAction)

/** Modify existing [FieldSpec.Builder] using provided [builderAction] and then building it. */
inline fun FieldSpec.Builder.build(builderAction: FieldSpecBuilder.() -> Unit): FieldSpec =
    FieldSpecBuilder(this).apply(builderAction).build()

/** Wrapper of [FieldSpec.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetDslMarker
class FieldSpecBuilder(private val nativeBuilder: FieldSpec.Builder) {

    /** Modifiers of this field. */
    val modifiers: MutableList<Modifier> get() = nativeBuilder.modifiers

    /** Javadoc of this field. */
    val javadoc: JavadocContainer = object : JavadocContainer() {
        override fun append(format: String, vararg args: Any): Unit =
            format.formatWith(args) { s, array -> nativeBuilder.addJavadoc(s, *array) }

        override fun append(code: CodeBlock) {
            nativeBuilder.addJavadoc(code)
        }
    }

    /** Configures javadoc for this field. */
    inline fun javadoc(configuration: JavadocContainerScope.() -> Unit) =
        JavadocContainerScope(javadoc).configuration()

    /** Annotations of this field. */
    val annotations: AnnotationSpecList = AnnotationSpecList(nativeBuilder.annotations)

    /** Configures annotations for this field. */
    inline fun annotations(configuration: AnnotationSpecListScope.() -> Unit) =
        AnnotationSpecListScope(annotations).configuration()

    /** Add field modifiers. */
    fun addModifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    /** Initialize field value like [String.format]. */
    fun initializer(format: String, vararg args: Any): Unit =
        format.formatWith(args) { s, array -> nativeBuilder.initializer(s, *array) }

    /** Initialize field value with code. */
    var initializer: CodeBlock
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.initializer(value)
        }

    /** Initialize field value with custom initialization [builderAction]. */
    inline fun initializer(builderAction: CodeBlockBuilder.() -> Unit): CodeBlock =
        buildCodeBlock(builderAction).also { initializer = it }

    /** Returns native spec. */
    fun build(): FieldSpec = nativeBuilder.build()
}
