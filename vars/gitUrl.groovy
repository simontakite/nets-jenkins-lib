#!/usr/bin/env groovy

/*
 * gitCommitAuthor returns the git url
 * @return the git url
*/

def call() {
    sh(returnStdout: true, script: "git ls-remote --get-url").trim()
}
