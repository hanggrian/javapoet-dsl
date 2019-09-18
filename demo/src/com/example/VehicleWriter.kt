package com.example

import com.hendraanggrian.javapoet.abstract
import com.hendraanggrian.javapoet.buildJavaFile
import com.hendraanggrian.javapoet.classNameOf
import com.hendraanggrian.javapoet.int
import com.hendraanggrian.javapoet.public
import com.squareup.javapoet.TypeName
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
            addInterface("Vehicle") {
                methods {
                    "getName" {
                        addModifiers(public, abstract)
                        returns<String>()
                    }
                    "getWheelCount" {
                        addModifiers(public, abstract)
                        returns = int
                    }
                }
            }
        }.writeTo(Paths.get("demo/src"))
    }

    fun write(name: String, wheelCount: Int) {
        buildJavaFile(PACKAGE_NAME) {
            addClass(name) {
                addSuperInterface(VEHICLE_NAME)
                methods {
                    "getName" {
                        addModifiers(public)
                        returns<String>()
                        annotations.add<Override>()
                        appendln("return %S", name)
                    }
                    "getWheelCount" {
                        addModifiers(public)
                        returns = TypeName.INT
                        annotations.add<Override>()
                        appendln("return %L", wheelCount)
                    }
                }
            }
        }.writeTo(Paths.get("demo/src"))
    }
}