#!/usr/bin/env groovy

/*
 * gitClean removes untracked files
 * Cleans the working tree by recursively removing files that are not under version control, starting from the current directory.
 * Except excludes.
 *
*/

def call(String excludes) {

    String excludesParam = ""

    if (excludes != null && "" != excludes) {
        excludesParam = " --exclude $excludes"
    }
    // Remove all untracked files
    sh "git clean -df$excludesParam"
    //Clear all unstaged changes
    sh 'git checkout -- .'

}
