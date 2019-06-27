package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.AnnotationSpecBuilder
import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.buildAnnotationSpec
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import kotlin.reflect.KClass

abstract class AnnotationContainer {

    abstract operator fun plusAssign(spec: AnnotationSpec)

    /** Add annotation to this spec builder. */
    fun add(name: ClassName, builder: (AnnotationSpecBuilder.() -> Unit)? = null) =
        plusAssign(buildAnnotationSpec(name, builder))

    /** Add annotation to this spec builder. */
    fun add(type: KClass<*>, builder: (AnnotationSpecBuilder.() -> Unit)? = null) =
        plusAssign(buildAnnotationSpec(type, builder))

    inline fun <reified T> add(noinline builder: (AnnotationSpecBuilder.() -> Unit)? = null) =
        add(T::class, builder)

    operator fun invoke(configuration: AnnotationContainerScope.() -> Unit) =
        configuration(AnnotationContainerScope(this))
}

@JavapoetDslMarker
class AnnotationContainerScope internal constructor(private val container: AnnotationContainer) {

    operator fun ClassName.invoke(builder: (AnnotationSpecBuilder.() -> Unit)? = null) {
        container += buildAnnotationSpec(this, builder)
    }

    operator fun KClass<*>.invoke(builder: (AnnotationSpecBuilder.() -> Unit)? = null) {
        container += buildAnnotationSpec(this, builder)
    }

    inline fun <reified T> add(noinline builder: (AnnotationSpecBuilder.() -> Unit)? = null) =
        T::class.invoke(builder)
}