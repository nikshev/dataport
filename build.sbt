name := """play-dockerized"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

resolvers += Resolver.bintrayRepo("cakesolutions", "maven")

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "com.datastax.cassandra" % "cassandra-driver-core" % "2.1.6"
)

libraryDependencies += "net.cakesolutions" %% "scala-kafka-client" % "0.11.0.0"
