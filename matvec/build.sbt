import AssemblyKeys._ 

assemblySettings

name := "matrixVectorCalculation"
 
version := "1.0"

scalaVersion := "2.9.1"

javaOptions in run += "-Xbatch -server -Xmx1G -Xms1G -XX:PermSize=64m -XX:MaxPermSize=64m"

resolvers ++= Seq(
  "Akka Snapshots"           at "http://akka.io/snapshots",
  "Typesafe Snapshots"       at "http://repo.typesafe.com/typesafe/snapshots",
  "Typesafe Releases"        at "http://repo.typesafe.com/typesafe/releases",
  "Scala-Tools Snapshots"    at "http://scala-tools.org/repo-snapshots",
  "Scala Tools Releases"     at "http://scala-tools.org/repo-releases",
  "Sonatype Nexus Releases"  at "https://oss.sonatype.org/content/repositories/releases",
  "Sonatype Legacy Releases" at "https://oss.sonatype.org/content/repositories/releases",
  "Sonatype staging"         at "https://oss.sonatype.org/content/repositories/staging",
  "Sonatype Snapshots"       at "https://oss.sonatype.org/content/repositories/snapshots"
)

libraryDependencies ++= Seq(
  "com.typesafe.akka"             %  "akka-actor"           % "2.0"       withSources(),
  "com.github.scala-incubator.io" %  "scala-io-core_2.9.1"  % "0.3.0"     withSources(),
  "com.github.scala-incubator.io" %  "scala-io-file_2.9.1"  % "0.3.0"     withSources(),
  "org.apache.httpcomponents"     %  "httpclient"           % "4.1.2"     withSources(),
  "org.scala-tools"               % "scala-stm_2.9.1"       % "0.6"       withSources(),
  "org.scala-tools.time"          %  "time_2.9.1"           % "0.5"       ,
  "org.scala-lang"                %  "scala-swing"          % "2.9.1-1"   withSources(),
  "org.clapper"                   %% "grizzled-scala"       % "1.0.13"    withSources(),
  "org.jfree"                     %  "jfreechart"           % "1.0.14"    withSources(),
  "com.typesafe.akka" 			  % "akka-testkit" 	        % "2.0.1",
  "junit" 						  % "junit" 				% "4.0"
)