@file:JvmMultifileClass
@file:JvmName("SpecBuildersKt")

package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.MemberContainer
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock

inline operator fun AnnotationSpec.invoke(builder: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    toBuilder()(builder)

inline operator fun AnnotationSpec.Builder.invoke(builder: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    AnnotationSpecBuilder(this).apply(builder).build()

class AnnotationSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: AnnotationSpec.Builder) {

    val members: MemberContainer = object : MemberContainer() {
        override fun add(name: String, format: String, vararg args: Any) {
            nativeBuilder.addMember(name, format, *args)
        }

        override fun add(name: String, block: CodeBlock): CodeBlock = block.also { nativeBuilder.addMember(name, it) }
    }

    fun build(): AnnotationSpec = nativeBuilder.build()
}