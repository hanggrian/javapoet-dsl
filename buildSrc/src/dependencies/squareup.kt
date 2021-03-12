const val VERSION_JAVAPOET = "1.13.0"

fun org.gradle.api.artifacts.dsl.DependencyHandler.squareup(module: String, version: String) =
    "com.squareup:$module:$version"
