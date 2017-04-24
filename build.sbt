name := "ExileScala"

version := "1.0"

scalaVersion := "2.12.2"

libraryDependencies += "org.scalaj" %% "scalaj-http" % "2.3.0"
libraryDependencies ++= Seq(
  "org.mongodb" %% "casbah" % "3.1.1",
  "org.slf4j" % "slf4j-simple" % "1.6.4"
)

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1" % "test"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"