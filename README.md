# IncrementLint
A Gradle Plugin that supports Lint increment scanning.

NODE: The target support Android Gradle Plugin version is 4.1.0, No guarantee that other versions will be available.

# Usage
Add the following to your build.gradle:
```
buildscript {
    ......
    dependencies {
        ......
        classpath "com.airsaid:incrementlint:1.0.0"
    }
}
```
```
plugins {
    ......
    id 'com.airsaid.incrementlint'
}
```

# Tasks
## lintIncrement
The Task receives the "Files" parameter, which is a string of the full path of the file, and the full path of multiple files is divided by ",".

For example:
```
./gradlew lintIncrement -PFiles=file_path1,file_path2
```

# More
## Git Hooks Scripts
There are also git-hooks scripts in the repository, to see: [git-hooks/pre-push](https://github.com/Airsaid/IncrementLint/blob/master/git-hooks/pre-push).

The execution logic of the script is:

When the push command is executed, the lintIncrement Task will be executed automatically, and the file list of this change will be passed in.
When an Error level error is detected in the code, the push is terminated to avoid pushing the code with errors to the remote repository.

## Copy Git Hooks Scripts Task
Because the git hook script must be in the .git/hooks directory by default. So copyGitHooks task will automatically copy the script to this directory. The copyGitHooks task code is as follows:
```
allprojects {
    ......
    afterEvaluate { project ->
        project.tasks.whenTaskAdded { task ->
            if (task.name == "preDebugBuild") {
                task.dependsOn(rootProject.tasks.named("copyGitHooks"))
            }
        }
    }
}

task copyGitHooks(type: Copy, description: "Copy the git hooks script to the .git/hooks directory.") {
    from "git-hooks"
    into ".git/hooks"
    eachFile { file ->
        file.setMode(0755)
    }
}
```

# License
```
Copyright 2021 Airsaid

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```