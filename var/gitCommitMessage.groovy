#!/usr/bin/env groovy

/*
 * gitCommitMessage returns the commit message
 * @return the git commit message
*/

def call() {
    sh(returnStdout: true, script: "git log -1 --pretty=%B").trim()
}
