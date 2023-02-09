package me.deo.dekotpiler.model

import me.deo.dekotpiler.coding.Codable
import me.deo.dekotpiler.model.statements.KtMultiBodyStatement

interface KtStatement : KtTyped, Codable {
    override val type: KtType
        get() = KtType.Unit
}