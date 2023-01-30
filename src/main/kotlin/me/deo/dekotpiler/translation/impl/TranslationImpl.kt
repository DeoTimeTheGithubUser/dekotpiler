package me.deo.dekotpiler.translation.impl

import me.deo.dekotpiler.model.KtConditional
import me.deo.dekotpiler.model.KtExpression
import me.deo.dekotpiler.model.KtStatement
import me.deo.dekotpiler.model.KtUnknown
import me.deo.dekotpiler.model.KtLocal
import me.deo.dekotpiler.model.KtType
import me.deo.dekotpiler.translation.Translation
import me.deo.dekotpiler.translation.Translator
import me.deo.dekotpiler.util.CFRExpression
import me.deo.dekotpiler.util.CFRStatement
import me.deo.dekotpiler.util.CFRLocal
import org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement
import org.benf.cfr.reader.bytecode.analysis.parse.expression.ConditionalExpression
import org.benf.cfr.reader.bytecode.analysis.types.JavaTypeInstance
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
class TranslationImpl(
    translators: List<Translator<*, *>>
) : Translation {
    private val translatorsByType = translators.associateBy {
        it.type.java
    }

    override fun <C : Any, K> translator(type: KClass<out C>) =
        (translatorsByType[type.java] as? Translator<C, K>)

    private fun <K> translate(value: Any) =
        translator<Any, K>(value::class)?.run { translation(value) }

    override fun translateExpression(expression: CFRExpression): KtExpression = translate(expression) ?: KtUnknown(expression.toString())
    override fun translateStatement(statement: CFRStatement): KtStatement = translate(statement) ?: KtUnknown(statement.toString())
    override fun translateStatement(statement: Op04StructuredStatement): KtStatement = translate(statement) ?: KtUnknown(statement.toString())
    override fun translateVariable(variable: CFRLocal): KtLocal = translate(variable) ?: error("what")
    override fun translateType(type: JavaTypeInstance) = KtType(type, true) // TODO
    override fun translateConditional(conditional: ConditionalExpression) = KtConditional(translateExpression(conditional), null)
}