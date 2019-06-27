package com.hendraanggrian.javapoet.scope

import com.hendraanggrian.javapoet.AnnotationSpecBuilder
import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.buildAnnotationSpec
import com.hendraanggrian.javapoet.container.AnnotationContainer
import com.squareup.javapoet.ClassName
import kotlin.reflect.KClass

@JavapoetDslMarker
class AnnotationContainerScope internal constructor(private val container: AnnotationContainer) {

    operator fun ClassName.invoke(builder: (AnnotationSpecBuilder.() -> Unit)? = null) {
        container += buildAnnotationSpec(this, builder)
    }

    operator fun KClass<*>.invoke(builder: (AnnotationSpecBuilder.() -> Unit)? = null) {
        container += buildAnnotationSpec(java, builder)
    }

    inline fun <reified T> add(noinline builder: (AnnotationSpecBuilder.() -> Unit)? = null) =
        T::class.invoke(builder)
}