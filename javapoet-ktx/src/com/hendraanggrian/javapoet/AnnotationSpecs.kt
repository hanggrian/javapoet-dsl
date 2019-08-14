@file:JvmMultifileClass
@file:JvmName("SpecBuildersKt")

package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.MemberContainer
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import javax.lang.model.element.AnnotationMirror
import kotlin.reflect.KClass

object AnnotationSpecs {

    operator fun get(annotation: Annotation, includeDefaultValues: Boolean = false): AnnotationSpec =
        AnnotationSpec.get(annotation, includeDefaultValues)

    operator fun get(annotation: AnnotationMirror): AnnotationSpec =
        AnnotationSpec.get(annotation)

    fun of(type: ClassName): AnnotationSpec =
        AnnotationSpec.builder(type).build()

    inline fun of(type: ClassName, builder: Builder.() -> Unit): AnnotationSpec =
        Builder(AnnotationSpec.builder(type)).apply(builder).build()

    fun of(type: KClass<*>): AnnotationSpec =
        AnnotationSpec.builder(type.java).build()

    inline fun of(type: KClass<*>, builder: Builder.() -> Unit): AnnotationSpec =
        Builder(AnnotationSpec.builder(type.java)).apply(builder).build()

    inline fun <reified T> of(): AnnotationSpec =
        of(T::class)

    inline fun <reified T> of(builder: Builder.() -> Unit): AnnotationSpec =
        of(T::class, builder)

    /** Wrapper of [AnnotationSpec.Builder], providing DSL support as a replacement to Java builder. */
    class Builder @PublishedApi internal constructor(private val nativeBuilder: AnnotationSpec.Builder) {

        val members: MemberContainer = object : MemberContainer() {
            override fun add(name: String, format: String, vararg args: Any) {
                nativeBuilder.addMember(name, format, *args.mappedKClass)
            }

            override fun add(name: String, block: CodeBlock): CodeBlock =
                block.also { nativeBuilder.addMember(name, it) }
        }

        fun build(): AnnotationSpec = nativeBuilder.build()
    }
}
