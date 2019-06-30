package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.MemberContainer
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import kotlin.reflect.KClass

@JavapoetDslMarker
class AnnotationSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: AnnotationSpec.Builder) :
    SpecBuilder<AnnotationSpec> {

    @PublishedApi
    @Suppress("NOTHING_TO_INLINE")
    internal companion object {
        inline fun of(type: ClassName, noinline builder: (AnnotationSpecBuilder.() -> Unit)? = null): AnnotationSpec =
            AnnotationSpecBuilder(AnnotationSpec.builder(type))
                .also { builder?.invoke(it) }
                .build()

        inline fun of(type: KClass<*>, noinline builder: (AnnotationSpecBuilder.() -> Unit)? = null): AnnotationSpec =
            AnnotationSpecBuilder(AnnotationSpec.builder(type.java))
                .also { builder?.invoke(it) }
                .build()
    }

    val members: MemberContainer = object : MemberContainer() {
        override fun add(name: String, format: String, vararg args: Any) {
            nativeBuilder.addMember(name, format, *args)
        }

        override fun add(name: String, block: CodeBlock): CodeBlock = block.also { nativeBuilder.addMember(name, it) }
    }

    override fun build(): AnnotationSpec = nativeBuilder.build()
}