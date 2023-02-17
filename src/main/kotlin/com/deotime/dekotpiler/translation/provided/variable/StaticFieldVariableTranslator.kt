package com.deotime.dekotpiler.translation.provided.variable

import com.deotime.dekotpiler.model.variable.KtStaticField
import com.deotime.dekotpiler.translation.Translation
import com.deotime.dekotpiler.translation.Translator
import org.benf.cfr.reader.bytecode.analysis.parse.lvalue.StaticVariable
import org.springframework.stereotype.Component

@Component
class StaticFieldVariableTranslator : Translator<StaticVariable, KtStaticField> {


    override fun Translation.Session.translation(value: StaticVariable) = KtStaticField(
        translateType(value.owningClassType).nullable(false),
        value.rawFieldName,
        value.isFinal,
        translateType(value.inferredJavaType),
        false, // todo?
    )
}