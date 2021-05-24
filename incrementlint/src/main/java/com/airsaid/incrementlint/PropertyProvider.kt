package com.airsaid.incrementlint

import org.gradle.api.Project

/**
 * @author airsaid
 */
object PropertyProvider {

  private const val FILES_PROPERTY = "Files"

  fun incrementFile(project: Project): String {
    if (project.hasProperty(FILES_PROPERTY)) {
      return project.property(FILES_PROPERTY) as String
    }
    return ""
  }

}