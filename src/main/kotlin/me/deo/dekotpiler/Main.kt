package me.deo.dekotpiler

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import me.deo.dekotpiler.decompile.CFRDecompilerEngine
import me.deo.dekotpiler.decompile.DecompilerEngine
import me.deo.dekotpiler.metadata.MetadataReader
import me.deo.dekotpiler.metadata.MetadataResolver
import me.deo.dekotpiler.model.KtBlock
import me.deo.dekotpiler.translation.Translation
import me.deo.dekotpiler.util.taskAsync
import me.deo.dekotpiler.util.exportTasks
import me.deo.dekotpiler.util.getValue
import me.deo.dekotpiler.util.helper.FileSelector
import me.deo.dekotpiler.util.task
import org.springframework.boot.Banner
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.stereotype.Component
import java.io.File

@Component
class Main(
    private val metadataResolver: MetadataResolver,
    private val fileSelector: FileSelector,
    private val reader: MetadataReader,
    private val engines: List<DecompilerEngine>,
    private val translation: Translation
) : CommandLineRunner {
    // This will eventually be replaced by a CLI
    override fun run(vararg args: String): Unit = runBlocking(Dispatchers.Default) {
        // using cfr for testing
        val cfr = engines.filterIsInstance<CFRDecompilerEngine>().first()

        // testing
        val file = File(File("").absolutePath, "/build/classes/kotlin/main/me/deo/dekotpiler/Test.class")

        val metadata by taskAsync("Metadata") { reader.read(metadataResolver.resolve(file)) }
        val clazz by taskAsync("CFR") { cfr.decompile(file) ?: error("Couldn't read class.") }

        task("Kotlin Decompilation") {
            clazz.methods.forEach { cfrMethod ->
                cfrMethod.analysis.statement.let { stmt ->
                    val translated = translation.translateStatement(stmt)
                    println("------------${cfrMethod.name}---------------")
                    println(translated.code())
                    println("------------${cfrMethod.name}---------------")
                    println((translated as KtBlock).statements.lastOrNull()?.let { it::class })
                }
            }
        }
        // testing only
//        (metadata as KotlinClassMetadata.Class).let { meta ->
//            (meta.toKmClass().toSpec()) {
//                source.funSpecs.replaceAll { func ->
//                    func.invoke {
//                        code {
//
//                            // add code here
//                        }
//                    }
//                }
//            }
//        }

    }

    private companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val app = task("Spring Initialization") {
                SpringApplicationBuilder(App::class.java)
                    .web(WebApplicationType.NONE)
                    .bannerMode(Banner.Mode.OFF)
                    .logStartupInfo(false)
                    .headless(false)
                    .application()
            }
            app.run()
            exportTasks()
        }

        @SpringBootApplication
        class App

    }
}