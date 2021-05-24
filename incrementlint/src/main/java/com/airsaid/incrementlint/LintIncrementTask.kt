package com.airsaid.incrementlint

import com.android.annotations.NonNull
import com.android.build.api.variant.impl.VariantPropertiesImpl
import com.android.build.gradle.internal.scope.GlobalScope
import com.android.build.gradle.tasks.LintGlobalTask
import org.gradle.api.file.FileCollection

/**
 * Incremental Lint Task implementation.
 *
 * The Task receives the "Files" parameter, which is a string of the full path of the file,
 * and the full path of multiple files is divided by ",".
 *
 * For example:
 *
 * ./gradlew lintIncrement -PFiles=file_path1,file_path2
 *
 * @author airsaid
 */
internal abstract class LintIncrementTask : LintGlobalTask() {

  override fun runLint(descriptor: LintBaseTaskDescriptor) {
    val lintClassPath: FileCollection? = lintClassPath
    if (lintClassPath != null) {
      OKReflectiveLintRunner().runLint(project.gradle, descriptor, lintClassPath.files)
    }
  }

  class LintIncrementCreationAction(
      @NonNull globalScope: GlobalScope,
      @NonNull variants: Collection<VariantPropertiesImpl>
  ) : LintGlobalTask.GlobalCreationAction(globalScope, variants) {
    override fun configure(task: LintGlobalTask?) {
      globalScope.project.dependencies.add(LINT_CLASS_PATH, Constant.DEPENDENCY_INCREMENT_LINT)
      super.configure(task)
    }
  }

}