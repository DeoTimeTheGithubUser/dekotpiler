package me.deo.dekotpiler.decompile

import org.benf.cfr.reader.api.CfrDriver
import org.springframework.stereotype.Component
import java.io.File

import org.benf.cfr.reader.util.getopt.OptionsImpl.*
import org.benf.cfr.reader.util.getopt.PermittedOptionProvider

@Component
class CFRDecompilerEngineImpl : CFRDecompilerEngine {

    private val config = cfrConfig {
        DECOMPILER_COMMENTS set false
        ANTI_OBF set false
        ALLOW_CORRECTING set false
        TIDY_VARIABLE_NAMES set false
    }

    override fun decompile(file: File) = CfrDriver.Builder()
        .withOptions(config)
        .build()
        .analyse(file.absolutePath)

    private fun cfrConfig(closure: CFRConfig.() -> Unit) =
        CFRConfig().apply(closure).options

    private class CFRConfig {
        val options = mutableMapOf<String, String>()

        infix fun <T> PermittedOptionProvider.ArgumentParam<T, *>.set(value: T) {
            options[name] = value.toString()
        }
    }
}