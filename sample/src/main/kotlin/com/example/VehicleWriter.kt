package com.example

import com.hendraanggrian.javapoet.ABSTRACT
import com.hendraanggrian.javapoet.INT
import com.hendraanggrian.javapoet.PUBLIC
import com.hendraanggrian.javapoet.annotation
import com.hendraanggrian.javapoet.buildJavaFile
import com.hendraanggrian.javapoet.classNameOf
import com.hendraanggrian.javapoet.classType
import com.hendraanggrian.javapoet.interfaceType
import com.hendraanggrian.javapoet.methods
import java.nio.file.Paths

class VehicleWriter {

    companion object {
        private const val PACKAGE_NAME = "com.example.output"
        private val VEHICLE_NAME = classNameOf(PACKAGE_NAME, "Vehicle")

        @JvmStatic
        fun main(args: Array<String>) {
            val writer = VehicleWriter()
            writer.prepare()
            writer.write("Bike", 2)
            writer.write("Car", 4)
        }
    }

    fun prepare() {
        buildJavaFile(PACKAGE_NAME) {
            interfaceType("Vehicle") {
                methods {
                    "getName" {
                        modifiers(PUBLIC, ABSTRACT)
                        returns<String>()
                    }
                    "getWheelCount" {
                        modifiers(PUBLIC, ABSTRACT)
                        returns = INT
                    }
                }
            }
        }.writeTo(Paths.get("sample/src"))
    }

    fun write(name: String, wheelCount: Int) {
        buildJavaFile(PACKAGE_NAME) {
            classType(name) {
                superinterfaces += VEHICLE_NAME
                methods {
                    "getName" {
                        modifiers(PUBLIC)
                        returns<String>()
                        annotation<Override>()
                        appendLine("return %S", name)
                    }
                    "getWheelCount" {
                        modifiers(PUBLIC)
                        returns = INT
                        annotation<Override>()
                        appendLine("return %L", wheelCount)
                    }
                }
            }
        }.writeTo(Paths.get("sample/src"))
    }
}
