package com.hendraanggrian.javapoet.internal

import com.hendraanggrian.javapoet.MethodSpecBuilder

interface MethodSpecManager {

    fun method(name: String, builder: (MethodSpecBuilder.() -> Unit)? = null)

    fun constructor(builder: (MethodSpecBuilder.() -> Unit)? = null)
}