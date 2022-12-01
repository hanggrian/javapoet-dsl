pluginManagement.repositories {
    gradlePluginPortal()
    mavenCentral()
}
dependencyResolutionManagement.repositories.mavenCentral()

rootProject.name = "javapoet-dsl"

include("javapoet-dsl")
include("sample")
include("website")
