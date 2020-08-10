#!/usr/bin/env groovy

def JDK_VERSION = 'amazon-corretto-8.262.10.1'
def ANDROID_VERSION = 'r28.0.3'

def PROGRAMER_FEISHU_CHANNEL = 'AdWorks'

feishuNotify "Started"

pipeline {
    agent {
        label 'adworks:android'
    }
    options {
        skipDefaultCheckout()
        disableConcurrentBuilds()
        buildDiscarder(logRotator(
            daysToKeepStr: '15',
            artifactNumToKeepStr: '20'
        ))
        ansiColor('xterm')
    }
    parameters {
        booleanParam(name: 'CLEAN_WS',
            defaultValue: false,
            description: 'When checked, call function cleanWs to clean workspace.')
    }
    stages {
        stage('Clean') {
            steps {
                script {
                    if (params.CLEAN_WS) {
                        cleanWs()
                    }
                    sh 'rm -vf *.apk'
                }
            }
        }
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Gradle') {
            tools {
                jdk JDK_VERSION
            }
            steps {
                withAndroidSdk(ANDROID_VERSION) {
                    sh './gradlew clean build'
                }
            }
        }
        stage('Archive') {
            environment {
                ARCHIVE_DIR = "${env.WORKSPACE}/app/build/outputs/apk"
                DEBUG_APK = artifactName(name: "android-demo", suffix: "-debug", extension: "apk")
                RELEASE_APK = artifactName(name: "android-demo", suffix: "-release", extension: "apk")
            }
            steps {
                sh "find ${env.ARCHIVE_DIR}/debug -name '*.apk' -exec mv {} ${env.WORKSPACE}/${env.DEBUG_APK} \\;"
                sh "find ${env.ARCHIVE_DIR}/release -name '*.apk' -exec mv {} ${env.WORKSPACE}/${env.RELEASE_APK} \\;"
                archiveArtifacts artifacts: '*.apk', onlyIfSuccessful: true
            }
        }
    }
    post {
        success {
            feishuNotify what: "Success", withDuration: true, withSummary: true, withChanges: true
        }
        failure {
            feishuNotify what: "Failure", withDuration: true, withSummary: true, withChanges: true
            feishuNotify what: "Failure", channel: PROGRAMER_FEISHU_CHANNEL
        }
    }
}
