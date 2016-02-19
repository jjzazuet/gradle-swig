package net.tribe7.gradle.swig

import net.tribe7.gradle.swig.task.SwigTask
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class SwigPluginSpec extends Specification {
    void "Test Swig plugin invocation"() {
        given:
        Project project = ProjectBuilder.builder().build()
        def task = project.tasks.create('swigTest', SwigTask)

        task.verbose = true
        task.enableCpp = true
        task.symbols = ['SWIGWORDSIZE64']
        task.module = 'groundhog'
        task.packageName = 'net.tribe7.foo'
        task.source = new File('./src/test/resources/foo.h')
        task.includePaths = [new File('./src/test/resources/time')]
        task.javaSourcesPath = new File('./build/resources/test/net/tribe7/foo')
        task.wrapperTargetFile = new File('./build/resources/test/net/tribe7/foo/foo_jni.cpp')

        when:
        project.pluginManager.apply 'net.tribe-seven.swig'
        task.execute()

        then: true
    }
}
