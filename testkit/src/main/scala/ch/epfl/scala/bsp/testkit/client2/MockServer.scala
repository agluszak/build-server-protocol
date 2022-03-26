package ch.epfl.scala.bsp.testkit.client2

import ch.epfl.scala.bsp4j.{BuildServer, CppBuildServer, JavaBuildServer, JvmBuildServer, ScalaBuildServer}

trait MockServer
    extends BuildServer
    with ScalaBuildServer
    with JavaBuildServer
    with JvmBuildServer
    with CppBuildServer
