@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.MemberContainer
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import kotlin.reflect.KClass

/*inline operator fun AnnotationSpec.invoke(builder: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    toBuilder()(builder)

inline operator fun AnnotationSpec.Builder.invoke(builder: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    AnnotationSpecBuilder(this).apply(builder).build()*/

inline fun buildAnnotationSpec(
    type: ClassName,
    noinline builder: (AnnotationSpecBuilder.() -> Unit)? = null
): AnnotationSpec = AnnotationSpecBuilder(AnnotationSpec.builder(type))
    .also { builder?.invoke(it) }
    .build()

inline fun buildAnnotationSpec(
    type: KClass<*>,
    noinline builder: (AnnotationSpecBuilder.() -> Unit)? = null
): AnnotationSpec = AnnotationSpecBuilder(AnnotationSpec.builder(type.java))
    .also { builder?.invoke(it) }
    .build()

inline fun <reified T> buildAnnotationSpec(
    noinline builder: (AnnotationSpecBuilder.() -> Unit)? = null
): AnnotationSpec = buildAnnotationSpec(T::class, builder)

class AnnotationSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: AnnotationSpec.Builder) {

    val members: MemberContainer = object : MemberContainer() {
        override fun add(name: String, format: String, vararg args: Any) {
            nativeBuilder.addMember(name, format, *args)
        }

        override fun add(name: String, block: CodeBlock): CodeBlock = block.also { nativeBuilder.addMember(name, it) }
    }

    fun build(): AnnotationSpec = nativeBuilder.build()
}