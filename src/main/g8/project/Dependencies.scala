import sbt._

object Dependencies {

  val akkVersion = "2.3.12"
  val doobieVersion = "0.6.0"
  val http4sVersion = "0.20.0-M4"
  val circeVersion = "0.9.3"
  val jsoniterVersion = "0.49.1"
  val monocleVersion = "1.5.0"
  val catsVersion = "1.0.1"
  val fs2Version = "1.0.2"

  val akka = Seq(
    "akka-actor"
  ).map("com.typesafe.akka" %% _ % akkVersion)

  val fs2 = Seq(
    "fs2-core",
    "fs2-io"
  ).map("co.fs2" %% _ % fs2Version)

  val doobie = Seq(
    "doobie-core",
    "doobie-hikari" // HikariCP transactor.
  ).map("org.tpolecat" %% _ % doobieVersion)

  val doobiePostgres = "org.tpolecat" %% "doobie-postgres" % doobieVersion // Postgres driver + type mappings.

  val doobieScalaTest = "org.tpolecat" %% "doobie-scalatest" % doobieVersion // Postgres driver + type mappings.

  val doobieH2 = "org.tpolecat" %% "doobie-h2" % doobieVersion // H2 driver + type mappings.

  val http4s = Seq(
    "http4s-dsl",
    "http4s-blaze-server",
    "http4s-blaze-client"
  ).map("org.http4s" %% _ % http4sVersion)

  val circe = Seq(
    "circe-core",
    "circe-generic",
    "circe-parser",
    "circe-generic-extras"
  ).map("io.circe" %% _ % circeVersion)

  val enumeratumCirce = "com.beachape" %% "enumeratum-circe" % "1.5.17"

  val jsoniter = Seq(
    ("jsoniter-scala-core", Compile),
    ("jsoniter-scala-macros", Provided) // required only in compile-time
  ).map { case (name, scope) =>
    "com.github.plokhotnyuk.jsoniter-scala" %% name % jsoniterVersion % scope
  }

  val monocle = Seq(
    "monocle-core",
    "monocle-state",
    "monocle-macro"
  ).map("com.github.julien-truffaut" %% _ % "1.5.0")

  val enumeratum = "com.beachape" %% "enumeratum" % "1.5.13"

  val cats = "org.typelevel" %% "cats-core" % catsVersion

  val flyway = "org.flywaydb" % "flyway-core" % "5.2.0"

  val logback = "ch.qos.logback" % "logback-classic" % "1.2.3"

  val pureConfig = "com.github.pureconfig" %% "pureconfig" % "0.10.0"

  val scalaMeter = "com.storm-enroute" %% "scalameter" % "0.8.2"

  val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.14.0" % Test

  val scalaTest = "org.scalatest" %% "scalatest" % "3.0.7" % Test

  val mockitoScala = "org.mockito" %% "mockito-scala" % "1.3.1" % Test

  val tests = Seq(scalaTest, scalaCheck)
}
