import Dependencies._
lazy val root = (project in file(".")).settings(
  inThisBuild(List(
    organization := "com.framex",
    scalaVersion := "2.12.3",
    version      := "0.1.0-SNAPSHOT"
  )),
  name := "framex",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.2.22"

//  libraryDependencies ++= Seq(
//    "com.typesafe.akka" %% "akka-actor" % "2.5.11",
//    "com.typesafe.akka" %% "akka-testkit" % "2.5.11" % Test
//  ),
//  libraryDependencies ++= Seq(
//    "com.typesafe.akka" %% "akka-stream" % "2.5.11",
//    "com.typesafe.akka" %% "akka-remote" % "2.5.11",
//    "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.11" % Test
//  )
)