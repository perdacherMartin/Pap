import AssemblyKeys._ 

assemblySettings


name := "RemoteActorApp"

version := "1.0"

scalaVersion := "2.9.1"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "com.typesafe.akka" % "akka-actor" % "2.0.2"
		
libraryDependencies += "com.typesafe.akka" % "akka-kernel" % "2.0.2"

