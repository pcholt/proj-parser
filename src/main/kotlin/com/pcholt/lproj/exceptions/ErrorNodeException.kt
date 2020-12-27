package com.pcholt.lproj.exceptions

import org.antlr.v4.runtime.tree.ErrorNode

class ErrorNodeException(node: ErrorNode?) : Throwable(message = node?.text ?: "unknown error")
