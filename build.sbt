name := "telescope"

version := "1.0"

scalaVersion := "2.11.8"

resolvers += Resolver.sonatypeRepo("releases")

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats" % "0.6.0"
)

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.8.0")
