name := """mechanic-api"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"
val reactiveMongoVer = "0.11.14"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.reactivemongo" %% "play2-reactivemongo" % reactiveMongoVer,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

