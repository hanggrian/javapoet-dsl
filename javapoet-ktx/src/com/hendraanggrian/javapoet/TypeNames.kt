package com.hendraanggrian.javapoet

import com.squareup.javapoet.TypeName
import java.lang.reflect.Type
import javax.lang.model.type.TypeMirror

object TypeNames {

    operator fun get(mirror: TypeMirror): TypeName = TypeName.get(mirror)

    operator fun get(type: Type): TypeName = TypeName.get(type)
}