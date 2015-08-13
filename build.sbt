name := "play-ebean-bean-persist-listener"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean
)     

compile in Test <<= PostCompile(Test)

play.Project.playJavaSettings
