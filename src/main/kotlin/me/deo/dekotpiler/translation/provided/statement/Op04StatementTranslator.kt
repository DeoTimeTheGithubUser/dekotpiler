package me.deo.dekotpiler.translation.provided.statement

import me.deo.dekotpiler.model.KtStatement
import me.deo.dekotpiler.translation.Translation
import me.deo.dekotpiler.translation.Translator
import org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement
import org.springframework.stereotype.Component

@Component
class Op04StatementTranslator : Translator<Op04StructuredStatement, KtStatement> {
    
    override fun Translation.Session.translation(value: Op04StructuredStatement): KtStatement = translateStatement(value.statement)
}