package io.github.hendraanggrian.javapoet

import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeName
import io.github.hendraanggrian.javapoet.collections.AnnotationSpecList
import io.github.hendraanggrian.javapoet.collections.AnnotationSpecListScope
import io.github.hendraanggrian.javapoet.collections.JavadocContainer
import io.github.hendraanggrian.javapoet.collections.JavadocContainerScope
import java.lang.reflect.Type
import javax.lang.model.element.Modifier
import kotlin.reflect.KClass

/** Builds new [FieldSpec] from [TypeName] supplying its name and modifiers. */
fun fieldSpecOf(type: TypeName, name: String, vararg modifiers: Modifier): FieldSpec =
    FieldSpec.builder(type, name, *modifiers).build()

/** Builds new [FieldSpec] from [Type] supplying its name and modifiers. */
fun fieldSpecOf(type: Type, name: String, vararg modifiers: Modifier): FieldSpec =
    FieldSpec.builder(type, name, *modifiers).build()

/** Builds new [FieldSpec] from [KClass] supplying its name and modifiers. */
fun fieldSpecOf(type: KClass<*>, name: String, vararg modifiers: Modifier): FieldSpec =
    FieldSpec.builder(type.java, name, *modifiers).build()

/** Builds new [FieldSpec] from [T] supplying its name and modifiers. */
inline fun <reified T> fieldSpecOf(name: String, vararg modifiers: Modifier): FieldSpec =
    FieldSpec.builder(T::class.java, name, *modifiers).build()

/**
 * Builds new [FieldSpec] from [TypeName] supplying its name and modifiers,
 * by populating newly created [FieldSpecBuilder] using provided [builderAction].
 */
inline fun buildFieldSpec(
    type: TypeName,
    name: String,
    vararg modifiers: Modifier,
    builderAction: FieldSpecBuilder.() -> Unit
): FieldSpec = FieldSpecBuilder(FieldSpec.builder(type, name, *modifiers)).apply(builderAction).build()

/**
 * Builds new [FieldSpec] from [Type] supplying its name and modifiers,
 * by populating newly created [FieldSpecBuilder] using provided [builderAction].
 */
inline fun buildFieldSpec(
    type: Type,
    name: String,
    vararg modifiers: Modifier,
    builderAction: FieldSpecBuilder.() -> Unit
): FieldSpec = FieldSpecBuilder(FieldSpec.builder(type, name, *modifiers)).apply(builderAction).build()

/**
 * Builds new [FieldSpec] from [KClass] supplying its name and modifiers,
 * by populating newly created [FieldSpecBuilder] using provided [builderAction].
 */
inline fun buildFieldSpec(
    type: KClass<*>,
    name: String,
    vararg modifiers: Modifier,
    builderAction: FieldSpecBuilder.() -> Unit
): FieldSpec = FieldSpecBuilder(FieldSpec.builder(type.java, name, *modifiers)).apply(builderAction).build()

/**
 * Builds new [FieldSpec] from [T] supplying its name and modifiers,
 * by populating newly created [FieldSpecBuilder] using provided [builderAction].
 */
inline fun <reified T> buildFieldSpec(
    name: String,
    vararg modifiers: Modifier,
    builderAction: FieldSpecBuilder.() -> Unit
): FieldSpec = FieldSpecBuilder(FieldSpec.builder(T::class.java, name, *modifiers)).apply(builderAction).build()

/** Modify existing [FieldSpec.Builder] using provided [builderAction]. */
inline fun FieldSpec.Builder.edit(
    builderAction: FieldSpecBuilder.() -> Unit
): FieldSpec.Builder = FieldSpecBuilder(this).apply(builderAction).nativeBuilder

/**
 * Wrapper of [FieldSpec.Builder], providing DSL support as a replacement to Java builder.
 * @param nativeBuilder source builder.
 */
@SpecDslMarker
class FieldSpecBuilder(val nativeBuilder: FieldSpec.Builder) {

    /** Modifiers of this field. */
    val modifiers: MutableList<Modifier> get() = nativeBuilder.modifiers

    /** Javadoc of this field. */
    val javadoc: JavadocContainer = object : JavadocContainer() {
        override fun append(format: String, vararg args: Any): Unit =
            format.internalFormat(args) { s, array -> nativeBuilder.addJavadoc(s, *array) }

        override fun append(code: CodeBlock) {
            nativeBuilder.addJavadoc(code)
        }
    }

    /** Configures javadoc for this field. */
    inline fun javadoc(builderAction: JavadocContainerScope.() -> Unit): Unit =
        JavadocContainerScope(javadoc).builderAction()

    /** Annotations of this field. */
    val annotations: AnnotationSpecList = AnnotationSpecList(nativeBuilder.annotations)

    /** Configures annotations for this field. */
    inline fun annotations(builderAction: AnnotationSpecListScope.() -> Unit): Unit =
        AnnotationSpecListScope(annotations).builderAction()

    /** Add field modifiers. */
    fun addModifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    /** Initialize field value like [String.format]. */
    fun initializer(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { s, array -> nativeBuilder.initializer(s, *array) }

    /** Initialize field value with code. */
    var initializer: CodeBlock
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.initializer(value)
        }

    /** Initialize field value with custom initialization [builderAction]. */
    inline fun initializer(builderAction: CodeBlockBuilder.() -> Unit) {
        initializer = buildCodeBlock(builderAction)
    }

    /** Returns native spec. */
    fun build(): FieldSpec = nativeBuilder.build()
}
