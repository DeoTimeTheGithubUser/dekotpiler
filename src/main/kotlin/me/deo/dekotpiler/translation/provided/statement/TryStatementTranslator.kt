package me.deo.dekotpiler.translation.provided.statement

import me.deo.dekotpiler.model.statements.KtTryStatement
import me.deo.dekotpiler.translation.Translation
import me.deo.dekotpiler.translation.Translator
import org.benf.cfr.reader.bytecode.analysis.structured.statement.StructuredCatch
import org.benf.cfr.reader.bytecode.analysis.structured.statement.StructuredTry
import org.springframework.stereotype.Component

@Component
class TryStatementTranslator : Translator<StructuredTry, KtTryStatement> {
    override val type = StructuredTry::class
    override fun Translation.translation(value: StructuredTry) = KtTryStatement(
        translateStatement(value.tryBlock),
        value.catchBlocks.map { it.statement }.filterIsInstance<StructuredCatch>().map {
            KtTryStatement.Catch(
                translateVariable(it.catching),
                translateStatement(it.catchBlock)
            )
        }.toMutableList(),
        value.finallyBlock?.let { translateStatement(it) }
    )
}