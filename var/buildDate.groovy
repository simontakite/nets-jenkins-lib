#!/usr/bin/env groovy

/*
 * buildDate returns build job date
 * @return build date
 *
*/

def call() {
    sh(returnStdout: true, script: "date").trim()
}