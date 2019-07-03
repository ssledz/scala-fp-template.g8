lazy val root = (project in file(".")).
  settings(
    commonSettings,
    consoleSettings,
    scalaOptions,
    typeSystemEnhancements,
    dependencies,
    tests
  )
  .enablePlugins(ScalafmtPlugin)

addCommandAlias("fmt", ";scalafmt ;test:scalafmt ;scalafmtSbt")
addCommandAlias("showDep", ";dependencyBrowseGraph")

lazy val commonSettings = Seq(
  name         := "$name$",
  organization := "$organization$",
  scalaVersion := "$scalaVersion$",
  version      := "0.0.1-SNAPSHOT"
)

// Filter out compiler flags to make the repl experience functional...
val badConsoleFlags = Seq("-Xfatal-warnings", "-Ywarn-unused:imports")

val consoleSettings = Seq(
  initialCommands := s"import $defaultImportPath$",
  scalacOptions in (Compile, console) ~= (_.filterNot(badConsoleFlags.contains(_)))
)

lazy val scalaOptions = scalacOptions ++= scalacOptionsForVersion(scalaVersion.value)

def scalacOptionsForVersion(version: String): Seq[String] = {
  // format: off
  val defaultOpts = Seq(
    "-deprecation",                      // Emit warning and location for usages of deprecated APIs.
    "-encoding", "utf-8",                // Specify character encoding used by source files.
    "-explaintypes",                     // Explain type errors in more detail.
    "-feature",                          // Emit warning and location for usages of features that should be imported explicitly.
    "-language:existentials",            // Existential types (besides wildcard types) can be written and inferred
    "-language:experimental.macros",     // Allow macro definition (besides implementation and application)
    "-language:higherKinds",             // Allow higher-kinded types
    "-language:implicitConversions",     // Allow definition of implicit functions called views
    "-unchecked",                        // Enable additional warnings where generated code depends on assumptions.
    "-Xcheckinit",                       // Wrap field accessors to throw an exception on uninitialized access.
    "-Xfuture",                          // Turn on future language features.
    "-Xlint:adapted-args",               // Warn if an argument list is modified to match the receiver.
    "-Xlint:constant",                   // Evaluation of a constant arithmetic expression results in an error.
    "-Xlint:delayedinit-select",         // Selecting member of DelayedInit.
    "-Xlint:doc-detached",               // A Scaladoc comment appears to be detached from its element.
    "-Xlint:inaccessible",               // Warn about inaccessible types in method signatures.
    "-Xlint:infer-any",                  // Warn when a type argument is inferred to be `Any`.
    "-Xlint:missing-interpolator",       // A string literal appears to be missing an interpolator id.
    "-Xlint:nullary-override",           // Warn when non-nullary `def f()' overrides nullary `def f'.
    "-Xlint:nullary-unit",               // Warn when nullary methods return Unit.
    "-Xlint:option-implicit",            // Option.apply used implicit view.
    "-Xlint:package-object-classes",     // Class or object defined in package object.
    "-Xlint:poly-implicit-overload",     // Parameterized overloaded implicit methods are not visible as view bounds.
    "-Xlint:private-shadow",             // A private field (or class parameter) shadows a superclass field.
    "-Xlint:stars-align",                // Pattern sequence wildcard must align with sequence component.
    "-Xlint:type-parameter-shadow",      // A local type parameter shadows a type already in scope.
    "-Ywarn-dead-code",                  // Warn when dead code is identified.
    "-Ywarn-extra-implicit",             // Warn when more than one implicit parameter section is defined.
    "-Ywarn-numeric-widen",              // Warn when numerics are widened.
    "-Ywarn-unused:implicits",           // Warn if an implicit parameter is unused.
    "-Ywarn-unused:imports",             // Warn if an import selector is not referenced.
    "-Ywarn-unused:locals",              // Warn if a local definition is unused.
    "-Ywarn-unused:params",              // Warn if a value parameter is unused.
    "-Ywarn-unused:patvars",             // Warn if a variable bound in a pattern is unused.
    "-Ywarn-unused:privates",            // Warn if a private member is unused.
    "-Ywarn-value-discard"               // Warn when non-Unit expression results are unused.
  )
  val versionOpts: Seq[String] = CrossVersion.partialVersion(version) match {
    case Some((2, major)) if major < 13 => Seq(
      "-Xlint:by-name-right-associative",  // By-name parameter of right associative operator.
      "-Xlint:unsound-match",              // Pattern match may not be typesafe.
      "-Xfatal-warnings",                  // Fail the compilation if there are any warnings.
      "-Yno-adapted-args",                 // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
      "-Ypartial-unification",             // Enable partial unification in type constructor inference
      "-Ywarn-inaccessible",               // Warn about inaccessible types in method signatures.
      "-Ywarn-infer-any",                  // Warn when a type argument is inferred to be `Any`.
      "-Ywarn-nullary-override",           // Warn when non-nullary `def f()' overrides nullary `def f'.
      "-Ywarn-nullary-unit"                // Warn when nullary methods return Unit.
    )
    case _ => Seq()
  }
  defaultOpts ++ versionOpts
  // format: on
}

lazy val typeSystemEnhancements = addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.9")

import Dependencies._

lazy val dependencies = {
  val deps = libraryDependencies ++= cats

  def extraResolvers =
    resolvers ++= Seq(
      Resolver.sonatypeRepo("releases"),
      Resolver.sonatypeRepo("snapshots")
    )

  Seq(deps, extraResolvers)

}

lazy val tests = {

  val deps = libraryDependencies ++= Seq(
    scalaTest,
    scalaCheck
  )

  val frameworks = testFrameworks := Seq(TestFrameworks.ScalaTest)

  Seq(deps, frameworks)
}
