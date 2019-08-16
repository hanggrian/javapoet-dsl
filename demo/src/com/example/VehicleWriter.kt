package com.example

import com.hendraanggrian.javapoet.buildJavaFile
import com.hendraanggrian.javapoet.toClassName
import com.squareup.javapoet.TypeName
import java.nio.file.Paths
import javax.lang.model.element.Modifier

class VehicleWriter {

    companion object {
        private const val PACKAGE_NAME = "com.example.output"
        private val VEHICLE_NAME = PACKAGE_NAME.toClassName("Vehicle")

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
                        addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                        returns<String>()
                    }
                    "getWheelCount" {
                        addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                        returns = TypeName.INT
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
                        addModifiers(Modifier.PUBLIC)
                        returns<String>()
                        annotations.add<Override>()
                        appendln("return %S", name)
                    }
                    "getWheelCount" {
                        addModifiers(Modifier.PUBLIC)
                        returns = TypeName.INT
                        annotations.add<Override>()
                        appendln("return %L", wheelCount)
                    }
                }
            }
        }.writeTo(Paths.get("demo/src"))
    }
}