import sbt._

object Dependencies {

  val mockitoVersion = "1.9.5"
  val json4sVersion = "3.5.0"
  val scalaTestVersion = "3.0.1"
  val akkaVersion = "2.5.8"
  val akkaHttpVersion = "10.0.11"

  def compileDependencies(deps: List[ModuleID]): Seq[ModuleID] = deps map (_ % Compile)

  def providedDependencies(deps: List[ModuleID]): Seq[ModuleID] = deps map (_ % Provided)

  def testDependencies(deps: List[ModuleID]): Seq[ModuleID] = deps map (_ % Test)

  def testClassifierDependencies(deps: List[ModuleID]) = deps map (_ % "test" classifier "tests")

  def akka = Def.setting {
    "com.typesafe.akka" %% "akka-actor" % akkaVersion :: Nil
  }

  def akkaClusterSharding = Def.setting{
    "com.typesafe.akka" %% "akka-cluster-sharding" % akkaVersion :: Nil
  }

  def akkaRemote = Def.setting {
    "com.typesafe.akka" %% "akka-remote" % akkaVersion :: Nil
  }

  def akkaTestKit = Def.setting {
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion :: Nil
  }

  def akkaHttp = Def.setting {
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion :: Nil
  }

  def akkaHttpTestKit = Def.setting {
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion :: Nil
  }


  def json4sNative = Def.setting {
    "org.json4s" %% "json4s-native" % json4sVersion :: Nil
  }

  def json4sEx = Def.setting {
    ("org.json4s" %% "json4s-ext" % json4sVersion).exclude("joda-time", "joda-time") :: Nil
  }

  def jodaDate = Def.setting {
    "joda-time" % "joda-time" % "2.9.2" :: Nil
  }

  def typesafeConfig = Def.setting {
    "com.typesafe" % "config" % "1.3.1" :: Nil
  }

  def logback = Def.setting {
    "ch.qos.logback" % "logback-classic" % "1.1.7" :: Nil
  }

  def slf4j = Def.setting {
    "org.slf4j" % "slf4j-api" % "1.7.25" :: Nil
  }

  def log4j = Def.setting {
    "log4j" % "log4j" % "1.2.17" :: Nil
  }

  /**
    * Test dependencies
    */
  def mockito = Def.setting {
    "org.mockito" % "mockito-core" % mockitoVersion :: Nil
  }

  def scalaTest = Def.setting {
    ("org.scalatest" %% "scalatest" % scalaTestVersion) :: Nil
  }

  def scalaCheck = Def.setting {
    "org.scalacheck" %% "scalacheck" % "1.13.4" :: Nil
  }

  def specs2Mock = Def.setting {
    "org.specs2" %% "specs2-mock" % "2.4.17" :: Nil
  }

  def spec2 = Def.setting {
    "org.specs2" %% "specs2" % "3.7" :: Nil
  }

}
