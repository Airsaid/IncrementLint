package com.airsaid.incrementlint;

import com.android.build.gradle.internal.VariantManager;
import com.android.build.gradle.internal.plugins.AppPlugin;
import com.android.build.gradle.internal.plugins.BasePlugin;
import com.android.build.gradle.internal.scope.GlobalScope;
import com.android.build.gradle.internal.variant.ComponentInfo;
import com.google.common.collect.ImmutableList;

import org.gradle.api.Project;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Reflection to obtain the required parameters.
 *
 * @author airsaid
 */
public class ReflectionProvider {

  private static final String FIELD_GLOBAL_SCOPE = "globalScope";
  private static final String FIELD_VARIANT_MANAGER = "variantManager";

  public static GlobalScope providerGlobalScope(Project project) {
    AppPlugin appPlugin = project.getPlugins().findPlugin(AppPlugin.class);
    if (appPlugin != null) {
      Class<BasePlugin> basePluginClass = BasePlugin.class;
      try {
        Field globalScopeField = basePluginClass.getDeclaredField(FIELD_GLOBAL_SCOPE);
        globalScopeField.setAccessible(true);
        return (GlobalScope) globalScopeField.get(appPlugin);
      } catch (NoSuchFieldException | IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  public static ImmutableList providerVariantPropertiesList(Project project) {
    AppPlugin appPlugin = project.getPlugins().findPlugin(AppPlugin.class);
    if (appPlugin != null) {
      Class<BasePlugin> basePluginClass = BasePlugin.class;
      try {
        Field variantManagerField = basePluginClass.getDeclaredField(FIELD_VARIANT_MANAGER);
        variantManagerField.setAccessible(true);
        VariantManager vm = (VariantManager) variantManagerField.get(appPlugin);
        List<ComponentInfo<?, ?>> list = vm.getMainComponents();
        return list.stream()
            .map(ComponentInfo::getProperties)
            .collect(ImmutableList.toImmutableList());
      } catch (NoSuchFieldException | IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    return null;
  }
}