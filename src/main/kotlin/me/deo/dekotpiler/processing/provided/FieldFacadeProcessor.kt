package me.deo.dekotpiler.processing.provided

import me.deo.dekotpiler.matching.ClassMatcher
import me.deo.dekotpiler.matching.Matcher
import me.deo.dekotpiler.model.expressions.KtJClassExpression
import me.deo.dekotpiler.model.expressions.invoke.KtFieldFacadeInvoke
import me.deo.dekotpiler.model.expressions.invoke.KtGetterInvoke
import me.deo.dekotpiler.model.expressions.invoke.KtMethodInvoke
import me.deo.dekotpiler.model.expressions.invoke.KtSetterInvoke
import me.deo.dekotpiler.model.expressions.invoke.KtStaticInvoke
import me.deo.dekotpiler.processing.PreProcessor
import org.springframework.stereotype.Component
import kotlin.jvm.internal.Reflection

@Component
class FieldFacadeProcessor :
    PreProcessor<KtMethodInvoke>,
    ClassMatcher<KtMethodInvoke> by ClassMatcher() {

    override fun replace(value: KtMethodInvoke) =
        // TODO multiclass processing to confirm if these are actually field facades
        if (value.name.startsWith("get") && value.args.isEmpty()) KtGetterInvoke(value)
        else if (value.name.startsWith("set") && value.args.size == 1) KtSetterInvoke(value)
        else value
}