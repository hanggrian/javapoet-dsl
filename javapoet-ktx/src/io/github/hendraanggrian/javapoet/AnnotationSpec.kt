package io.github.hendraanggrian.javapoet

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import javax.lang.model.element.AnnotationMirror
import kotlin.reflect.KClass

/** Converts [Annotation] to [AnnotationSpec]. */
fun Annotation.asAnnotationSpec(includeDefaultValues: Boolean = false): AnnotationSpec =
    AnnotationSpec.get(this, includeDefaultValues)

/** Converts [AnnotationMirror] to [AnnotationSpec]. */
fun AnnotationMirror.asAnnotationSpec(): AnnotationSpec = AnnotationSpec.get(this)

/** Builds new [AnnotationSpec] from [ClassName]. */
fun annotationSpecOf(type: ClassName): AnnotationSpec = AnnotationSpec.builder(type).build()

/** Builds new [AnnotationSpec] from [Class]. */
fun annotationSpecOf(type: Class<*>): AnnotationSpec = AnnotationSpec.builder(type).build()

/** Builds new [AnnotationSpec] from [KClass]. */
fun annotationSpecOf(type: KClass<*>): AnnotationSpec = AnnotationSpec.builder(type.java).build()

/** Builds new [AnnotationSpec] from [T]. */
inline fun <reified T> annotationSpecOf(): AnnotationSpec = AnnotationSpec.builder(T::class.java).build()

/**
 * Builds new [AnnotationSpec] from [ClassName],
 * by populating newly created [AnnotationSpecBuilder] using provided [configuration].
 */
fun buildAnnotationSpec(type: ClassName, configuration: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    AnnotationSpecBuilder(AnnotationSpec.builder(type)).apply(configuration).build()

/**
 * Builds new [AnnotationSpec] from [Class],
 * by populating newly created [AnnotationSpecBuilder] using provided [configuration].
 */
fun buildAnnotationSpec(type: Class<*>, configuration: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    AnnotationSpecBuilder(AnnotationSpec.builder(type)).apply(configuration).build()

/**
 * Builds new [AnnotationSpec] from [KClass],
 * by populating newly created [AnnotationSpecBuilder] using provided [configuration].
 */
fun buildAnnotationSpec(type: KClass<*>, configuration: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    AnnotationSpecBuilder(AnnotationSpec.builder(type.java)).apply(configuration).build()

/**
 * Builds new [AnnotationSpec] from [T],
 * by populating newly created [AnnotationSpecBuilder] using provided [configuration].
 */
inline fun <reified T> buildAnnotationSpec(noinline configuration: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    buildAnnotationSpec(T::class, configuration)

/** Modify existing [AnnotationSpec.Builder] using provided [configuration]. */
fun AnnotationSpec.Builder.edit(configuration: AnnotationSpecBuilder.() -> Unit): AnnotationSpec.Builder =
    AnnotationSpecBuilder(this).apply(configuration).nativeBuilder

/**
 * Wrapper of [AnnotationSpec.Builder], providing DSL support as a replacement to Java builder.
 * @param nativeBuilder source builder.
 */
@SpecDslMarker
class AnnotationSpecBuilder internal constructor(val nativeBuilder: AnnotationSpec.Builder) {

    /** Members of this annotation. */
    val members: Map<String, List<CodeBlock>> get() = nativeBuilder.members

    /** Add code as a member of this annotation. */
    fun addMember(name: String, format: String, vararg args: Any): Unit =
        format.internalFormat(args) { s, array -> nativeBuilder.addMember(name, s, *array) }

    /** Add code as a member of this annotation. */
    fun addMember(name: String, code: CodeBlock) {
        nativeBuilder.addMember(name, code)
    }

    /** Add code as a member of this annotation with custom initialization [configuration]. */
    fun addMember(name: String, configuration: CodeBlockBuilder.() -> Unit): Unit =
        addMember(name, buildCodeBlock(configuration))

    /** Convenient method to add member with operator function. */
    operator fun String.invoke(configuration: CodeBlockBuilder.() -> Unit): Unit =
        addMember(this, configuration)

    /** Returns native spec. */
    fun build(): AnnotationSpec = nativeBuilder.build()
}
