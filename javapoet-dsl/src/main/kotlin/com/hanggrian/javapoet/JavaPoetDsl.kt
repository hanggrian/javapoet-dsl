package com.hanggrian.javapoet

/**
 * Delimits spec builders' DSL. Code and javadoc builders are not tagged because some specs may
 * implement them.
 */
@DslMarker
@Target(AnnotationTarget.CLASS)
public annotation class JavaPoetDsl
