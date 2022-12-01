package aoc2022.utils

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainInOrder

class Input private constructor(private val contents: String) {

    fun asList() = contents.split("\n")
    
    fun asString() = contents

    companion object {
        fun fromResource(fileName: String): Input {
            val contents = this::class.java.classLoader.getResource(fileName)?.readText().orEmpty()
            return Input(contents)
        }

        fun fromString(value: String): Input = Input(value)
    }
}

class InputSpec : StringSpec({

    "read multi-line file from resources" {
        Input.fromResource("test").asList() shouldContainInOrder listOf("This", "is", "a", "Test")
    }

    "read multi-line string" {
        val input = """
            This
            is
            a
            Test
        """.trimIndent()
        Input.fromString(input).asList() shouldContainInOrder listOf("This", "is", "a", "Test")
    }

})