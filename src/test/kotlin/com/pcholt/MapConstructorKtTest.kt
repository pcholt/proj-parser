package com.pcholt

import com.pcholt.lproj.LProjParser
import com.pcholt.lproj.LProjParser.toMap
import com.pcholt.lproj.exceptions.ErrorNodeException
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.asserter

internal class MapConstructorKtTest {

    @Test
    fun parserTest() {
        LProjParser.parseString(
            """
            aaa="test";
        """.trimIndent()
        ).toMap().let {
            asserter.assertEquals("simplest test", "test", it["aaa"])
        }

    }

    @Test
    fun `multiple comments`() {
        LProjParser.parseString(
            """
            /* leading comment
            "" "\"" 
            * / */
            y = /* llll */ "123" /* inline comment */ ;
            // line comment // alskljh // x = "123";
            
        """.trimIndent()
        ).toMap().let {
            asserter.assertEquals("comments do not remove value", "123", it["y"])
            asserter.assertNotEquals("comments remove value", "123", it["x"])
        }
    }

    @Test
    fun `escape quotes`() {
        LProjParser.parseString("""
            a="thing\"2\"";
        """.trimIndent()).toMap().let {
            asserter.assertEquals("escaping quotes", expected = "thing\"2\"", it["a"])
        }
    }

    @Test
    fun `syntax errors throw exceptions`() {
        asserter.assertEquals(
            message = "Syntax error detection",
            actual = assertFailsWith<ErrorNodeException> { LProjParser.parseString(""" aaa="test;  """).toMap() }.message,
            expected = "<missing ';'>"
        )
        asserter.assertEquals(
            message = "Syntax error detection",
            actual = assertFailsWith<ErrorNodeException> { LProjParser.parseString(""" aaa==  """).toMap() }.message,
            expected = "="
        )

    }

}