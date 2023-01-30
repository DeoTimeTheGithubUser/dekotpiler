package me.deo.dekotpiler.model.expressions

import me.deo.dekotpiler.model.KtExpression
import me.deo.dekotpiler.model.KtType
import me.deo.dekotpiler.translation.codeWriter

data class KtInstanceOfExpression(
    var expression: KtExpression,
    var typeCheck: KtType
) : KtExpression {
    override val type = KtType.Boolean
    override fun writeCode() = codeWriter {
        write(expression, " is ", typeCheck)
    }
}