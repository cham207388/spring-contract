package com.abc.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

class RenameStubArtifactPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        // Retrieve the stubVersion (can be passed as an extension property if needed)
        def stubVersion = '0.0.1-RELEASE'

        // Define the task to rename the artifact
        Task renameStubArtifactTask = project.tasks.register('renameStubArtifact') {
            inputs.file("${project.buildDir}/libs/${project.name}-${project.version}-stubs.jar") // Original artifact
            outputs.file("${project.buildDir}/libs/${project.name}-${stubVersion}-stubs.jar") // Renamed artifact
            
            doFirst {
                println "Renaming artifact from ${project.name}-${project.version}-stubs.jar to ${project.name}-${stubVersion}-stubs.jar"
            }
            
            doLast {
                def sourceFile = project.file("${project.buildDir}/libs/${project.name}-${project.version}-stubs.jar")
                def targetDir = project.file("${project.buildDir}/libs")
                
                def targetFile = project.file("${targetDir}/${project.name}-${stubVersion}-stubs.jar")

                if (!targetDir.exists()) {
                    targetDir.mkdirs()
                }
                sourceFile.renameTo(targetFile)

                println "Renaming stub artifact completed!"
            }
        }.get()

        // Ensure the 'publishStubsPublicationToMavenLocalRepository' task depends on the rename task
        project.tasks.named('publishStubsPublicationToMavenLocalRepository') {
            dependsOn renameStubArtifactTask
        }
    }
}