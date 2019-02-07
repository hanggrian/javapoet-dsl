package com.hendraanggrian.javapoet.internal

interface ControlFlowManager {

    fun beginControlFlow(format: String, vararg args: Any)

    fun nextControlFlow(format: String, vararg args: Any)

    fun endControlFlow()

    fun endControlFlow(format: String, vararg args: Any)
}