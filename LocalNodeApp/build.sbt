import AssemblyKeys._ 

assemblySettings

name := "Matrix * Vector : Calculation"
 
version := "1.0"

scalaVersion := "2.9.1"
 
resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
 
libraryDependencies += "com.typesafe.akka" % "akka-actor" % "2.0"

libraryDependencies += "junit" % "junit" % "4.10"

libraryDependencies += "com.typesafe.akka" % "akka-kernel" % "2.0.2"

libraryDependencies += "com.typesafe.akka" % "akka-testkit" % "2.0.2"

libraryDependencies += "com.typesafe.akka" %  "akka-remote-tests_2.10.0-M7" % "2.1-M2"

libraryDependencies += "org.scalatest" % "scalatest_2.9.0" % "1.7.2"
