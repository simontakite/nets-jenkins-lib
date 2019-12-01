#!/usr/bin/env groovy

/*
 * gitCommitAuthor returns the commit author
 * @return the git commit author
*/

def call() {
    sh(returnStdout: true, script: "git log -1 --pretty=format:'%an'").trim()
}
