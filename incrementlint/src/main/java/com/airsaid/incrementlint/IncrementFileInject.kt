package com.airsaid.incrementlint

import com.android.tools.lint.client.api.LintRequest
import org.gradle.api.Project
import java.io.File

/**
 * @author airsaid
 */
internal object IncrementFileInject {

  private const val FILE_PARAMETER_SEPARATOR = ","

  private val incrementFiles = LinkedHashSet<File>()

  fun inject(project: Project, lintRequest: LintRequest) {
    parse(PropertyProvider.incrementFile(project))

    lintRequest.getProjects()?.forEach { project ->
      incrementFiles.forEach { file ->
        project.addFile(file)
      }
    }
  }

  private fun parse(filesParameter: String) {
    if (filesParameter.isEmpty()) {
      return
    }

    val filePaths = filesParameter.split(FILE_PARAMETER_SEPARATOR)
    incrementFiles.clear()
    incrementFiles.addAll(filePaths.map { File(it) })
  }
}