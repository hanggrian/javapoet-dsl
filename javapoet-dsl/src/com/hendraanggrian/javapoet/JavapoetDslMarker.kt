package com.hendraanggrian.javapoet

/**
 * Delimits spec builders' DSL.
 * TODO: Should this annotation be internal?
 */
@DslMarker
@Target(AnnotationTarget.CLASS)
annotation class JavapoetDslMarker