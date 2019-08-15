plugins {
    kotlin("jvm")
    application
}

application.mainClassName = "com.example.JavaFileWriter"

sourceSets["main"].java.srcDir("src")

dependencies {
    compile(kotlin("stdlib", VERSION_KOTLIN))
    compile(project(":$RELEASE_ARTIFACT"))
}