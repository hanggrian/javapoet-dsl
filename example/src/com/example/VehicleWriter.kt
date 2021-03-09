package com.example

import io.github.hendraanggrian.javapoet.ABSTRACT
import io.github.hendraanggrian.javapoet.INT
import io.github.hendraanggrian.javapoet.PUBLIC
import io.github.hendraanggrian.javapoet.buildJavaFile
import io.github.hendraanggrian.javapoet.classOf
import java.nio.file.Paths

class VehicleWriter {

    companion object {
        private const val PACKAGE_NAME = "com.example.output"
        private val VEHICLE_NAME = PACKAGE_NAME.classOf("Vehicle")

        @JvmStatic fun main(args: Array<String>) {
            val writer = VehicleWriter()
            writer.prepare()
            writer.write("Bike", 2)
            writer.write("Car", 4)
        }
    }

    fun prepare() {
        buildJavaFile(PACKAGE_NAME) {
            addInterface("Vehicle") {
                methods {
                    "getName" {
                        addModifiers(PUBLIC, ABSTRACT)
                        returns<String>()
                    }
                    "getWheelCount" {
                        addModifiers(PUBLIC, ABSTRACT)
                        returns = INT
                    }
                }
            }
        }.writeTo(Paths.get("demo/src"))
    }

    fun write(name: String, wheelCount: Int) {
        buildJavaFile(PACKAGE_NAME) {
            addClass(name) {
                superinterfaces += VEHICLE_NAME
                methods {
                    "getName" {
                        addModifiers(PUBLIC)
                        returns<String>()
                        annotations.add<Override>()
                        appendLine("return %S", name)
                    }
                    "getWheelCount" {
                        addModifiers(PUBLIC)
                        returns = INT
                        annotations.add<Override>()
                        appendLine("return %L", wheelCount)
                    }
                }
            }
        }.writeTo(Paths.get("demo/src"))
    }
}