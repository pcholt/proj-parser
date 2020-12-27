package com.pcholt.lproj

import com.pcholt.lproj.exceptions.ErrorNodeException
import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.ParseTreeWalker

class MapConstructor : LProjStringsBaseListener() {
    var string: String? = null
    var key: String? = null
    var map = mutableMapOf<String, String?>()

    override fun exitVariable(ctx: LProjStringsParser.VariableContext?) {
        key = ctx?.text
    }

    override fun exitValue(ctx: LProjStringsParser.ValueContext?) {
        string = ctx?.text?.let {
            if (it.isNotEmpty()) it.substring(1,it.length-1) else it
        }?.replace("\\\"", "\"")
    }

    override fun exitLine(ctx: LProjStringsParser.LineContext?) {
        key?.let {
            map[it] = string
        }
    }

    override fun visitErrorNode(node: ErrorNode?) {
        throw ErrorNodeException(node)
    }

}

object LProjParser {

    fun parseString(program: String): LProjStringsParser = parse { CharStreams.fromString(program) }

    fun parseFile(fileName: String): LProjStringsParser = parse { CharStreams.fromFileName(fileName) }

    private inline fun parse(func: () -> CharStream) =
        LProjStringsParser(
            CommonTokenStream(
                LProjStringsLexer(
                    func()
                )
            )
        )

    fun LProjStringsParser.toMap(): Map<String, String?> {
        val m = MapConstructor()
        ParseTreeWalker().walk(m, lProjStrings())
        return m.map
    }
}

