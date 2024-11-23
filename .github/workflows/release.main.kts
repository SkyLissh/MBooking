#!/usr/bin/env kotlin

@file:Repository("https://repo.maven.apache.org/maven2/")
@file:DependsOn("io.github.typesafegithub:github-workflows-kt:3.0.1")
@file:Repository("https://bindings.krzeminski.it")
@file:DependsOn("actions:checkout:v4")
@file:DependsOn("actions:setup-java:v4")
@file:DependsOn("actions:upload-artifact:v4")
@file:DependsOn("softprops:action-gh-release:v2")

import io.github.typesafegithub.workflows.actions.actions.Checkout
import io.github.typesafegithub.workflows.actions.actions.SetupJava
import io.github.typesafegithub.workflows.actions.actions.UploadArtifact
import io.github.typesafegithub.workflows.actions.softprops.ActionGhRelease
import io.github.typesafegithub.workflows.domain.JobOutputs
import io.github.typesafegithub.workflows.domain.RunnerType.UbuntuLatest
import io.github.typesafegithub.workflows.domain.triggers.Push
import io.github.typesafegithub.workflows.domain.triggers.WorkflowDispatch
import io.github.typesafegithub.workflows.dsl.expressions.Contexts
import io.github.typesafegithub.workflows.dsl.expressions.expr
import io.github.typesafegithub.workflows.dsl.workflow

workflow(
  name = "release",
  sourceFile = __FILE__,
  on = listOf(
    WorkflowDispatch(
      inputs = mapOf(
        "name" to WorkflowDispatch.Input(
          description = "Release-Builds",
          default = "Generate Release Builds",
          required = true,
          type = WorkflowDispatch.Type.String
        )
      )
    ),
    Push(branches = listOf("main"), tags = listOf("v*"))
  )
) {

  val KEYSTORE_ENCODED by Contexts.secrets
  val KEYSTORE_PROPERTIES by Contexts.secrets
  val SECRET_PROPERTIES by Contexts.secrets
  val GITHUB_OUTPUT by Contexts.env

  val apkJob = job(
    id = "apk",
    name = "Generate Release Builds",
    runsOn = UbuntuLatest,
    env = mapOf(
      "KEYSTORE_ENCODED_ENV" to expr { KEYSTORE_ENCODED },
      "KEYSTORE_PROPERTIES_ENV" to expr { KEYSTORE_PROPERTIES },
      "SECRET_PROPERTIES_ENV" to expr { SECRET_PROPERTIES }
    ),
    outputs = object : JobOutputs() {
      var apk by output()
    }) {
    val KEYSTORE_ENCODED_ENV by Contexts.env
    val KEYSTORE_PROPERTIES_ENV by Contexts.env
    val SECRET_PROPERTIES_ENV by Contexts.env

    uses(name = "Checking out branch", action = Checkout())
    uses(
      name = "Setup Java",
      action = SetupJava(javaVersion = "17", distribution = SetupJava.Distribution.Temurin)
    )
    run(
      name = "Decode Keystore",
      command = "echo $KEYSTORE_ENCODED_ENV | base64 -d > keystore.jks"
    )
    run(
      name = "Decode Keystore Properties",
      command = "echo $KEYSTORE_PROPERTIES_ENV | base64 -d > keystore.properties"
    )
    run(
      name = "Decode Secret Properties",
      command = "echo $SECRET_PROPERTIES_ENV | base64 -d > secret.properties"
    )
    run(
      name = "Build APK",
      command = "./gradlew assembleRelease --stacktrace"
    )
    run(
      name = "Output APK",
      command = "echo 'apk=$(find app/build/outputs/apk/release -name *.apk)' >> $GITHUB_OUTPUT"
    ).let { jobOutputs.apk = it.outputs["apk"] }
  }

  job(
    id = "upload-artifact",
    name = "Upload APK",
    runsOn = UbuntuLatest,
    needs = listOf(apkJob)
  ) {
    uses(
      name = "Upload APK",
      action = UploadArtifact(name = "release-apk", path = listOf(expr { apkJob.outputs.apk }))
    )
  }

  job(
    id = "github-release",
    name = "Create GitHub Release",
    runsOn = UbuntuLatest,
    needs = listOf(apkJob),
    `if` = "startsWith(github.ref, 'refs/tags/v')"
  ) {
    uses(
      name = "Release",
      action = ActionGhRelease(files = listOf(expr { apkJob.outputs.apk }))
    )
  }
}

