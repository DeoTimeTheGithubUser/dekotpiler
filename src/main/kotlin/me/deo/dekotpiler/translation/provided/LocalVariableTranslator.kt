package me.deo.dekotpiler.translation.provided

import me.deo.dekotpiler.model.KtExpression
import me.deo.dekotpiler.model.KtStatement
import me.deo.dekotpiler.model.KtVariable
import me.deo.dekotpiler.translation.Translation
import me.deo.dekotpiler.translation.Translator
import org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement
import org.benf.cfr.reader.bytecode.analysis.parse.lvalue.LocalVariable
import org.benf.cfr.reader.bytecode.analysis.structured.statement.StructuredExpressionStatement
import org.springframework.stereotype.Component

@Component
class LocalVariableTranslator : Translator<LocalVariable, KtVariable> {
    override val type = LocalVariable::class
    // does this need a seperate statement?... maybe
    override fun Translation.translation(value: LocalVariable): KtVariable = KtVariable(
        value.name.stringName,
        true, // todo postprocessing for this
        value.inferredJavaType.javaTypeInstance
    )
}