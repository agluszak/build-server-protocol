package client2

import ch.epfl.scala.bsp.testkit.client2.{E2ETestClient, Session, SessionResult}
import ch.epfl.scala.bsp4j.{BuildClientCapabilities, BuildServerCapabilities, CleanCacheParams, DependencyModulesParams, DependencySourcesParams, InitializeBuildParams, ResourcesParams, SourcesParams}
import org.eclipse.lsp4j.jsonrpc.ResponseErrorException
import org.scalatest.funsuite.AnyFunSuite

import java.nio.file.Path
import scala.concurrent.duration.DurationInt
import scala.concurrent.{ExecutionContext, Future}
import scala.jdk.CollectionConverters._
import scala.jdk.FutureConverters.CompletionStageOps

/** This file contains test which are not specific to any language or build tool.
  */
class ProtocolSuite(workspacePath: Path) extends AnyFunSuite {
  private implicit val executionContext: ExecutionContext = scala.concurrent.ExecutionContext.global

  val bspVersion = "2.0.0"

  val initializeParamsNoCapabilities = new InitializeBuildParams(
    "TestClient",
    "1.0.0",
    bspVersion,
    workspacePath.toString,
    new BuildClientCapabilities(List.empty.asJava)
  )
  val initializeParamsFullCapabilities = new InitializeBuildParams(
    "TestClient",
    "1.0.0",
    bspVersion,
    workspacePath.toString,
    new BuildClientCapabilities(List("java", "scala", "kotlin").asJava)
  )

  private def withLifetime(initializeParams: InitializeBuildParams, session: Session)(
      f: BuildServerCapabilities => Future[Unit]
  ): Future[Unit] = {
    for {
      initializeResult <- session.server.buildInitialize(initializeParams).asScala
      _ = session.server.onBuildInitialized()
      _ <- f(initializeResult.getCapabilities)
      _ <- session.server.buildShutdown().asScala
      _ = session.server.onBuildExit()
    } yield ()
  }

  test("Before initialization server responds with an error") {
    E2ETestClient.withSession(workspacePath, 10.seconds) { session =>
      session.server.workspaceReload().asScala.failed.map { case e: ResponseErrorException =>
        assert(e.getResponseError.getCode === -32002)
      }
    }
  }

  test("Initialization succeeds") {
    E2ETestClient.withSession(workspacePath, 10.seconds) { session =>
      for {
        initializationResult <- session.server
          .buildInitialize(initializeParamsNoCapabilities)
          .asScala
        _ = session.server.onBuildInitialized()
        _ = session.close()
      } yield {
        println(initializationResult)
      }
    }
  }

  test("Server exits with 0 after a shutdown request") {
    E2ETestClient.withSession(workspacePath, 10.seconds, ignoreEarlyExit = true) { session =>
      for {
        _ <- session.server.buildInitialize(initializeParamsNoCapabilities).asScala
        _ = session.server.onBuildInitialized()
        _ <- session.server.buildShutdown().asScala
        _ = session.server.onBuildExit()
        SessionResult(exitCode, _) <- session.serverClosed
      } yield {
        assert(exitCode === 0)
      }
    }
  }

  test("Server exits with 1 without a shutdown request") {
    E2ETestClient.withSession(workspacePath, 10.seconds, ignoreEarlyExit = true) { session =>
      session.server.onBuildExit()
      for {
        SessionResult(exitCode, _) <- session.serverClosed
      } yield {
        assert(exitCode === 1)
      }
    }
  }

  test("No build targets are returned if the client has no capabilities") {
    E2ETestClient.withSession(workspacePath, 10.seconds) { session =>
      withLifetime(initializeParamsNoCapabilities, session) { _ =>
        for {
          result <- session.server.workspaceBuildTargets().asScala
        } yield {
          assert(result.getTargets.isEmpty)
        }
      }
    }
  }

  test("Reload request works if is supported") {
    E2ETestClient.withSession(workspacePath, 10.seconds) { session =>
      withLifetime(initializeParamsFullCapabilities, session) { capabilities =>
        if (capabilities.getCanReload) {
          for {
            _ <- session.server.workspaceReload().asScala
          } yield ()
        } else {
          Future.successful(())
        }
      }
    }
  }

  test("Target sources list is empty if given no targets") {
    E2ETestClient.withSession(workspacePath, 10.seconds) { session =>
      withLifetime(initializeParamsFullCapabilities, session) { _ =>
        for {
          result <- session.server.buildTargetSources(new SourcesParams(List.empty.asJava)).asScala
        } yield {
          assert(result.getItems.isEmpty)
        }
      }
    }
  }

  test("Dependency sources list is empty if given no targets (if supported)") {
    E2ETestClient.withSession(workspacePath, 10.seconds) { session =>
      withLifetime(initializeParamsFullCapabilities, session) { capabilities =>
        if (capabilities.getDependencySourcesProvider) {
          for {
            result <- session.server
              .buildTargetDependencySources(new DependencySourcesParams(List.empty.asJava))
              .asScala
          } yield {
            assert(result.getItems.isEmpty)
          }
        } else {
          Future.successful(())
        }
      }
    }
  }

  test("Dependency modules list is empty if given no targets (if supported)") {
    E2ETestClient.withSession(workspacePath, 10.seconds) { session =>
      withLifetime(initializeParamsFullCapabilities, session) { capabilities =>
        if (capabilities.getDependencyModulesProvider) {
          for {
            result <- session.server
              .buildTargetDependencyModules(new DependencyModulesParams(List.empty.asJava))
              .asScala
          } yield {
            assert(result.getItems.isEmpty)
          }
        } else {
          Future.successful(())
        }
      }
    }
  }

  test("Resources list is empty if given no targets (if supported)") {
    E2ETestClient.withSession(workspacePath, 10.seconds) { session =>
      withLifetime(initializeParamsFullCapabilities, session) { capabilities =>
        if (capabilities.getResourcesProvider) {
          for {
            result <- session.server
              .buildTargetResources(new ResourcesParams(List.empty.asJava))
              .asScala
          } yield {
            assert(result.getItems.isEmpty)
          }
        } else {
          Future.successful(())
        }
      }
    }
  }

  test("Clean cache method works") {
    E2ETestClient.withSession(workspacePath, 10.seconds) { session =>
      withLifetime(initializeParamsFullCapabilities, session) { _ =>
        for {
          _ <- session.server.buildTargetCleanCache(new CleanCacheParams(List.empty.asJava)).asScala
        } yield ()
      }
    }
  }

}
