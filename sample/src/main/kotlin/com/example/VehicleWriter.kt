package com.example

import com.hanggrian.javapoet.ABSTRACT
import com.hanggrian.javapoet.INT
import com.hanggrian.javapoet.PUBLIC
import com.hanggrian.javapoet.add
import com.hanggrian.javapoet.addClass
import com.hanggrian.javapoet.addInterface
import com.hanggrian.javapoet.buildJavaFile
import com.hanggrian.javapoet.classNamed
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
            types.addInterface("Vehicle") {
                methods {
                    "getName" {
                        addModifiers(PUBLIC, ABSTRACT)
                        setReturns<String>()
                    }
                    "getWheelCount" {
                        addModifiers(PUBLIC, ABSTRACT)
                        returns = INT
                    }
                }
            }
        }.writeTo(Paths.get(SOURCE_PATH))
    }

    fun write(name: String, wheelCount: Int) {
        buildJavaFile(PACKAGE_NAME) {
            types.addClass(name) {
                superinterfaces += classNamed(PACKAGE_NAME, "Vehicle")
                methods {
                    "getName" {
                        addModifiers(PUBLIC)
                        setReturns<String>()
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
        }.writeTo(Paths.get(SOURCE_PATH))
    }
}
