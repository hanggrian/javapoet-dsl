package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.MemberContainer
import com.hendraanggrian.javapoet.internal.SpecBuilder
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import kotlin.reflect.KClass

/** Returns an annotation with custom initialization block. */
fun buildAnnotationSpec(name: ClassName, builder: (AnnotationSpecBuilder.() -> Unit)? = null): AnnotationSpec =
    AnnotationSpecBuilder(AnnotationSpec.builder(name))
        .also { builder?.invoke(it) }
        .build()

/** Returns an annotation with custom initialization block. */
fun buildAnnotationSpec(type: KClass<*>, builder: (AnnotationSpecBuilder.() -> Unit)? = null): AnnotationSpec =
    AnnotationSpecBuilder(AnnotationSpec.builder(type.java))
        .also { builder?.invoke(it) }
        .build()

/** Returns an annotation with custom initialization block. */
inline fun <reified T> buildAnnotationSpec(noinline builder: (AnnotationSpecBuilder.() -> Unit)? = null): AnnotationSpec =
    buildAnnotationSpec(T::class, builder)

@JavapoetDslMarker
class AnnotationSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: AnnotationSpec.Builder) :
    SpecBuilder<AnnotationSpec>() {

    val members: MemberContainer = object : MemberContainer() {
        override fun add(name: String, block: CodeBlock) {
            nativeBuilder.addMember(name, block)
        }

        override fun add(name: String, format: String, vararg args: Any) {
            nativeBuilder.addMember(name, format, *args)
        }
    }

    override fun build(): AnnotationSpec = nativeBuilder.build()
}