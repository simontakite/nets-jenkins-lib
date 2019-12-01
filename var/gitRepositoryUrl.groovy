#!/usr/bin/env groovy

/*
 * gitRepositoryUrl returns the commit hash
 * @return the git commit hash //short
*/

def call() {
    sh(returnStdout: true, script: "git config --get remote.origin.url").trim()
}
