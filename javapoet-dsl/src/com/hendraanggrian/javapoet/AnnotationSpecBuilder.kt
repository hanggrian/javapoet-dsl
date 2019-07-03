@file:JvmMultifileClass
@file:JvmName("SpecBuildersKt")

package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.MemberContainer
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock

/** Configure [this] spec with DSL. */
inline operator fun AnnotationSpec.invoke(builder: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    toBuilder()(builder)

/** Configure [this] builder with DSL. */
inline operator fun AnnotationSpec.Builder.invoke(builder: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    AnnotationSpecBuilder(this).apply(builder).build()

/** Wrapper of [AnnotationSpec.Builder], providing DSL support as a replacement to Java builder. */
class AnnotationSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: AnnotationSpec.Builder) {

    val members: MemberContainer = object : MemberContainer() {
        override fun add(name: String, format: String, vararg args: Any) {
            nativeBuilder.addMember(name, format, *args)
        }

        override fun add(name: String, block: CodeBlock): CodeBlock = block.also { nativeBuilder.addMember(name, it) }
    }

    fun build(): AnnotationSpec = nativeBuilder.build()
}