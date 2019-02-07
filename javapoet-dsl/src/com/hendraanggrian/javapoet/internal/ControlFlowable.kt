package com.hendraanggrian.javapoet.internal

internal interface ControlFlowable {

    fun beginControlFlow(format: String, vararg args: Any)

    fun nextControlFlow(format: String, vararg args: Any)

    fun endControlFlow()

    fun endControlFlow(format: String, vararg args: Any)
}