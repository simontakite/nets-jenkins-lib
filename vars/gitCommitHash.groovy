#!/usr/bin/env groovy

/*
 * gitCommitHash returns the commit hash
 * @return the git commit hash
*/

def call() {
    sh(returnStdout: true, script: "git rev-parse --short HEAD").trim()
}
