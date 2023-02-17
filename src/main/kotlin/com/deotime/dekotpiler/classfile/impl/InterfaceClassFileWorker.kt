package com.deotime.dekotpiler.classfile.impl

import com.deotime.dekotpiler.classfile.ClassFileWorker
import com.deotime.dekotpiler.jar.KotlinJarPool
import org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement
import org.benf.cfr.reader.entities.ClassFile
import org.benf.cfr.reader.entities.Method
import org.springframework.stereotype.Component

@Component
class InterfaceClassFileWorker(
    private val jarPool: KotlinJarPool
) : ClassFileWorker {
    override fun work(classFile: ClassFile): Map<Method, Op04StructuredStatement?> {
        val defaultImpls =
            jarPool.locate("${classFile.classType.rawName}\$DefaultImpls")
                ?.let(jarPool::load) ?: return emptyMap() // FIXME odsafsdaf
        return classFile.methods.associateWith { method ->
            defaultImpls.methods.find { it.name == method.name }?.analysis
        }
    }
}