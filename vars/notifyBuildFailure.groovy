#!/usr/bin/env groovy
/*
 * Notify the NETS teams services about the status of a build based from a
 * git repository.
 */

def call(endpoint = "https://outlook.office.com/webhook/ffaa6dbe-27b0-4e34-ad59-774fc7573c5c@df41dac6-47ab-4982-97ab-60c8af4a7dc0/IncomingWebhook/830ed6b257374b3b8d8e11714d02fa7d/008bd7a8-2a05-43ca-9969-48191de5d673") {

    jobName = env.JOB_NAME
    buildName = env.BUILD_DISPLAY_NAME
    jobBaseName = env.JOB_BASE_NAME
    buildNumber = env.BUILD_NUMBER
    buildDate = buildDate()
    gitCommitAuthor = gitCommitAuthor()
    gitCommitHash = gitCommitHash()
    gitCommitMessage = gitCommitMessage()
    gitRepositoryUrl = gitRepositoryUrl()

    /*
    if (gitRepositoryUrl.contains('github.com')) {
        gitRepositoryUrl = gitRepositoryUrl.replace('.git', '') + "/commit/${env.DOCKER_TAG}"
    } */

    script {

        def payload = """
                        {
                            "@type": "MessageCard",
                            "@context": "https://schema.org/extensions",
                            "summary": "Fail message card",
                            "themeColor": "eb4034",
                            "title": "Build failure!",
                            "sections": [
                                {
                                    "activityTitle": "**${jobName}** build [${buildName}](http://vm-stbuild-5:9998/job/${jobBaseName}/${buildNumber}/console) (FAILED) [Build logs](http://vm-stbuild-5:9998/job/${jobBaseName}/${buildNumber}/consoleText)",
                                    "activitySubtitle": "Finished: ${buildDate} Changes by **${gitCommitAuthor}**",
                                    "activityImage": "https://cdn.pixabay.com/photo/2017/02/12/21/29/false-2061131_960_720.png",
                                    "facts": [
                                        {
                                            "name": "Hash:",
                                            "value": "${gitCommitHash}"
                                        },
                                        {
                                            "name": "Message:",
                                            "value": "${gitCommitMessage}"
                                        },
                                        {
                                            "name": "Branch:",
                                            "value": "${GIT_BRANCH}"
                                        }
                                    ]
                                }
                            ],
                            "potentialAction": [
                                {
                                    "@type": "OpenUri",
                                    "name": "Bitbucket",
                                    "targets": [
                                        {
                                            "os": "default",
                                            "uri": "${gitRepositoryUrl}"
                                        }
                                    ]
                                },
                                {
                                    "@type": "OpenUri",
                                    "name": "Jenkins",
                                    "targets": [
                                        {
                                            "os": "default",
                                            "uri": "http://192.168.33.10:8080/job/${jobName}"
                                        }
                                    ]
                                },
                                {
                                    "@type": "OpenUri",
                                    "name": "SonarQube",
                                    "targets": [
                                        {
                                            "os": "default",
                                            "uri": "http://..."
                                        }
                                    ]
                                },
                                {
                                    "@type": "OpenUri",
                                    "name": "Kibana",
                                    "targets": [
                                        {
                                            "os": "default",
                                            "uri": "http://..."
                                        }
                                    ]
                                },
                                {
                                    "@type": "OpenUri",
                                    "name": "Rollback",
                                    "targets": [
                                        {
                                            "os": "default",
                                            "uri": "http://..."
                                        }
                                    ]
                                }
                            ]
                        }
                        """
        httpRequest httpMode: 'POST',
                acceptType: 'APPLICATION_JSON',
                contentType: 'APPLICATION_JSON',
                url: "${endpoint}",
                requestBody: payload
    }
}