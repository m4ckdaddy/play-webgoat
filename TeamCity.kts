package _Self.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object Build : BuildType({
    name = "Build"

    vcs {
        root(HttpsGithubComTfaheyPlayWebgoatRefsHeads28x)
    }
    steps {
        step {
            type = "SBT"
            param("sbt.args", """; clean; set scalacOptions += "-g:vars"; compile; test; package""")
        }
        step {
            name = "Veracode Upload and Scan"
            type = "teamcity-veracode-plugin"
            param("include", "*.jar")
            param("uploadIncludePattern", """\target\scala-2.13\*.jar""")
            param("appName", "%env.TEAMCITY_PROJECT_NAME%")
            param("createProfile", "true")
            param("criticality", "VeryHigh")
            param("waitForScan", "false")
            param("sandboxName", "TeamCity")
            param("useGlobalCredentials", "true")
            param("createSandbox", "true")
            param("version", "%env.BUILD_NUMBER%")
        }
    }
    triggers {
        vcs {
        }
    }
})
