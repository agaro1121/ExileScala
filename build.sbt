name := "ExileScala"

version := "1.0"

scalaVersion := "2.12.1"


// Akka Libraries
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.0"
libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.0.5"

// MongoDb
libraryDependencies ++= Seq(
  "org.mongodb" %% "casbah" % "3.1.1",
  "org.slf4j" % "slf4j-simple" % "1.6.4"
)


// Testing Libraries
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1" % "test"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"