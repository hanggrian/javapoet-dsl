package com.hendraanggrian.javapoet

/**
 * Delimits spec builders' DSL. Code and javadoc builders are not tagged because some specs may
 * implement them.
 */
@DslMarker
@Target(AnnotationTarget.CLASS)
annotation class JavapoetDsl
