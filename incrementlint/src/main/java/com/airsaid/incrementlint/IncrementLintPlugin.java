package com.airsaid.incrementlint;

import com.android.build.gradle.internal.scope.GlobalScope;
import com.google.common.collect.ImmutableList;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

import static com.airsaid.incrementlint.Constant.TASK_NAME;

/**
 * @author airsaid
 */
public class IncrementLintPlugin implements Plugin<Project> {
  @Override
  public void apply(Project target) {
    target.afterEvaluate(project -> {
      GlobalScope globalScope = ReflectionProvider.providerGlobalScope(project);
      if (globalScope == null) {
        project.getLogger().error("globalScope is null.");
        return;
      }

      ImmutableList variantPropertiesList = ReflectionProvider.providerVariantPropertiesList(project);
      if (variantPropertiesList == null) {
        project.getLogger().error("variantPropertiesList is null.");
        return;
      }

      project.getTasks().register(TASK_NAME, LintIncrementTask.class);

      project.getTasks().withType(LintIncrementTask.class).named(TASK_NAME)
          .configure(lintGlobalTask ->
              new LintIncrementTask.LintIncrementCreationAction(globalScope, variantPropertiesList)
                  .configure(lintGlobalTask));
    });
  }
}
