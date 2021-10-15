val scala3Version = "3.0.2"

ThisBuild / scalaVersion     := scala3Version
ThisBuild / version          := "0.1.0"
ThisBuild / organization     := "com.criceta"
ThisBuild / organizationName := "taretmch"

val commonSettings = Seq(
  libraryDependencies ++= Seq(
    "org.scalactic" %% "scalactic" % "3.2.9",
    "org.scalatest" %% "scalatest" % "3.2.9" % "test"
  )
)

lazy val chapters = project.in(file("chapters"))
  .settings(name := "chapters")
  .settings(commonSettings: _*)

lazy val tastyInspector = project.in(file("tasty-inspector"))
  .settings(name := "tasty-inspector")
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= Seq(
    "org.scala-lang" %% "scala3-tasty-inspector" % scala3Version,
  ))
  .dependsOn(chapters)

lazy val docs = project.in(file("docs"))
  .settings(name := "have-fun-scala3-docs")
  .enablePlugins(MdocPlugin)
  .settings(mdocIn  := file("docs/src/main"))
  .settings(mdocOut := file("docs/mdoc"))

lazy val exercise = project.in(file("exercise"))
  .settings(name := "exercise")
  .settings(commonSettings: _*)

lazy val answer = project.in(file("answer"))
  .settings(name := "answer")
  .settings(commonSettings: _*)
