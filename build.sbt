ThisBuild / version := "0.1.0-SNAPSHOT"
lazy val root = (project in file("."))
  .settings(
    name := "asmd-testing",
      Compile / unmanagedSourceDirectories ++= Seq(
          baseDirectory.value / "src" / "model" / "java",
          baseDirectory.value / "src" / "view" / "java",
          baseDirectory.value / "src" / "controller" / "java"
      ),
      libraryDependencies ++= Seq(
          "net.aichler" % "jupiter-interface" % JupiterKeys.jupiterVersion.value % Test,
          //"org.mockito" % "mockito-core" % "5.21.0" % Test)
          "org.mockito" % "mockito-core" % "3.+" % Test)
)
