scalaVersion := "2.10.3"


scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

traceLevel := -1

logLevel := Level.Info

// disable printing timing information, but still print [success]
showTiming := false

// disable printing a message indicating the success or failure of running a task
showSuccess := false

offline := true

libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value

libraryDependencies += "org.scala-lang" % "scala-compiler" % scalaVersion.value

libraryDependencies += "org.scalatest" %% "scalatest" % "1.9.1"

addCommandAlias("part1_1", "~ test-only part1_1")

addCommandAlias("part1_2", "~ test-only part1_1")

addCommandAlias("go", "~ test-only HandsOnScala")
