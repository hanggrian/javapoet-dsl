package com.example

import com.hanggrian.javapoet.ABSTRACT
import com.hanggrian.javapoet.INT
import com.hanggrian.javapoet.PUBLIC
import com.hanggrian.javapoet.annotation
import com.hanggrian.javapoet.buildJavaFile
import com.hanggrian.javapoet.classNamed
import com.hanggrian.javapoet.classType
import com.hanggrian.javapoet.interfaceType
import com.hanggrian.javapoet.methods
import java.nio.file.Paths

class VehicleWriter {
    companion object {
        private const val PACKAGE_NAME = "com.example.output"
        private const val SOURCE_PATH = "sample/src/main/kotlin"

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
        }.writeTo(Paths.get(SOURCE_PATH))
    }

    fun write(name: String, wheelCount: Int) {
        buildJavaFile(PACKAGE_NAME) {
            classType(name) {
                superinterfaces += classNamed(PACKAGE_NAME, "Vehicle")
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
        }.writeTo(Paths.get(SOURCE_PATH))
    }
}
