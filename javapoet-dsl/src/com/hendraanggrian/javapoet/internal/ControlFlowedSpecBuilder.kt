package com.hendraanggrian.javapoet.internal

internal interface ControlFlowedSpecBuilder {

    /** Begin a control flow of this spec builder. */
    fun beginControlFlow(format: String, vararg args: Any)

    /** Begin a control flow of this spec builder. */
    fun nextControlFlow(format: String, vararg args: Any)

    /** End a control flow of this spec builder. */
    fun endControlFlow()

    /** End a control flow of this spec builder. */
    fun endControlFlow(format: String, vararg args: Any)
}