# proj-parser

## A parser for xcode `.lproj` i18n files

The format of `.lproj` files is pretty simple, they contain a variable, and equals sign, a string value for the presentation of the variable, and a terminating semicolon.

You can read these files into a map, using this parser. The parser is written using ANTLR4 and Kotlin, but is distributed as a single Jar file with ANTLR4 as one of the dependencies.

### Parse a file into a map

```kotlin
fun main() {
    val map = LProjParser.parseFile("Default.lproj").toMap()
    map.forEach { key, value -> 
        println("$key = $value")
    }
}

```
