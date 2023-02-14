package me.deo.dekotpiler.translation.provided.expression

import me.deo.dekotpiler.model.expressions.KtInstanceOfExpression
import me.deo.dekotpiler.translation.Translation
import me.deo.dekotpiler.translation.Translator
import org.benf.cfr.reader.bytecode.analysis.parse.expression.InstanceOfExpression
import org.springframework.stereotype.Component

@Component
class InstanceOfExpressionTranslator : Translator<InstanceOfExpression, KtInstanceOfExpression> {
    
    override fun Translation.Session.translation(value: InstanceOfExpression) =
        KtInstanceOfExpression(
            translateExpression(value.lhs),
            translateType(value.typeInstance)
        )
}