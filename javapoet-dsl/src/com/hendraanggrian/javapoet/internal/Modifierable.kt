package com.hendraanggrian.javapoet.internal

import javax.lang.model.element.Modifier

internal interface Modifierable {

    fun modifiers(vararg modifiers: Modifier)
}