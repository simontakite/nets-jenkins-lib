#!/usr/bin/env groovy
/*
 * Notify the teams services about the status of a build based from a
 * git repository.
 */

def call(endpoint = "https://outlook.office.com/webhook/ffaa6dbe-27b0-4e34-ad59-774fc7573c5c@df41dac6-47ab-4982-97ab-60c8af4a7dc0/IncomingWebhook/830ed6b257374b3b8d8e11714d02fa7d/008bd7a8-2a05-43ca-9969-48191de5d673") {

    script {
        def jobName = env.JOB_NAME
        def buildName = env.BUILD_DISPLAY_NAME
        def jobBaseName = env.JOB_BASE_NAME
        def buildNumber = env.BUILD_NUMBER
        def buildDate = buildDate()
        def gitCommitAuthor = gitCommitAuthor()
        def gitCommitHash = gitCommitHash()
        def gitCommitMessage = gitCommitMessage()
        def gitRepositoryUrl = gitRepositoryUrl()
        def gitTag = gitTag()

        /*
        if (gitRepositoryUrl.contains('github.com')) {
            gitRepositoryUrl = gitRepositoryUrl.replace('.git', '') + "/commit/${env.DOCKER_TAG}"
        }
        */

        def payload = """
                        {
                            "@type": "MessageCard",
                            "@context": "https://schema.org/extensions",
                            "summary": "success message card",
                            "themeColor": "32a852",
                            "title": "Success",
                            "sections": [
                                {
                                    "activityTitle": "**${JOB_NAME}** build [${BUILD_DISPLAY_NAME}](http://vm-stbuild-5:9998/job/${JOB_BASE_NAME}/${BUILD_NUMBER}/console) (SUCCEEDED) [Build logs](http://vm-stbuild-5:9998/job/${JOB_BASE_NAME}/${BUILD_NUMBER}/consoleText)",
                                    "activitySubtitle": "Finished: ${DATE_RUN} Changes by **${GIT_COMMIT_AUTHOR}**",
                                    "activityImage": "https://cdn.pixabay.com/photo/2017/01/13/01/22/ok-1976099_960_720.png",
                                    "facts": [
                                        {
                                            "name": "Hash:",
                                            "value": "${GIT_COMMIT_HASH}"
                                        },
                                        {
                                            "name": "Message:",
                                            "value": "${GIT_COMMIT_MESSAGE}"
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
                                            "uri": "${GIT_URL}"
                                        }
                                    ]
                                },
                                {
                                    "@type": "OpenUri",
                                    "name": "Jenkins",
                                    "targets": [
                                        {
                                            "os": "default",
                                            "uri": "http://192.168.33.10:8080/job/${JOB_NAME}"
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