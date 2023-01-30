package me.deo.dekotpiler.model

import me.deo.dekotpiler.translation.codeWriter

data class KtBlock(
    val statements: List<KtStatement>
) : KtStatement {

    override val type: KtType
        get() = statements.lastOrNull()?.type ?: super.type

    override fun writeCode() = codeWriter {
        statements.fold(false) { prev, value ->
            if (prev) newline()
            write(value)
            true
        }
    }

}