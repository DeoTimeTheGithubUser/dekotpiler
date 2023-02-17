package com.deotime.dekotpiler.translation.provided.statement

import com.deotime.dekotpiler.model.expressions.KtLiteral
import com.deotime.dekotpiler.model.expressions.invoke.KtComparison
import com.deotime.dekotpiler.model.statements.KtForStatement
import com.deotime.dekotpiler.translation.Translation
import com.deotime.dekotpiler.translation.Translator
import com.deotime.dekotpiler.translation.provided.expression.KtRangeExpression
import org.benf.cfr.reader.bytecode.analysis.structured.statement.StructuredFor
import org.springframework.stereotype.Component

@Component
class ForLoopStatementTranslator : Translator<StructuredFor, KtForStatement> {

    override fun Translation.Session.translation(value: StructuredFor) = KtForStatement(
        translateVariable(value.initial.createdLValue),
        KtRangeExpression(
            translateExpression(value.initial.rValue),
            ((translateConditional(value.condition) as KtComparison)
                .comparing as KtLiteral.Int).let { KtLiteral.Int(it.value - 1) }, // to my knowledge kotlin only generates a for loop with ints but i may be wrong
        ),
        translateBlock(value.body)
    )
}