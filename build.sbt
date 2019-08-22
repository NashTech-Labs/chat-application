name := "chat-application"

version := "1.0"

scalaVersion := "2.11.8"

import Dependencies._
import ProjectSettings._

lazy val commonUtil = BaseProject("common-util").settings(
  libraryDependencies ++= compileDependencies(akkaHttp.value ++ akka.value ++
    json4sNative.value ++ logback.value ++ typesafeConfig.value ++ slf4j.value ++ log4j.value ++
    logback.value ++ json4sNative.value ++ json4sEx.value ++ jodaDate.value)
    ++ testDependencies(mockito.value ++ scalaTest.value ++ spec2.value),

  parallelExecution in Test := false
)

lazy val messagingService = BaseProject("messaging-service").settings(
  libraryDependencies ++= compileDependencies(akkaHttp.value ++ akka.value ++ akkaClusterSharding.value ++
    akkaRemote.value)
    ++ testDependencies(Nil),
  parallelExecution in Test := false).dependsOn(commonUtil)
