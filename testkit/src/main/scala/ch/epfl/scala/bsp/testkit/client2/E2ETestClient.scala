package ch.epfl.scala.bsp.testkit.client2

import java.nio.file.Path
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.{Failure, Success}

object E2ETestClient {
  def withSession(workspace: Path, timeout: Duration, ignoreEarlyExit: Boolean = false)(
      test: Session => Future[Unit]
  )(implicit ec: ExecutionContext): Unit = {
    val session = new Session(workspace)
    val testResult = test(session)
    val serverClosed = session.serverClosed.transform {
      case Success(_) => Failure(new Error(s"Server exited early"))
      case failure    => failure
    }

    try {
      if (ignoreEarlyExit) {
        Await.result(testResult, timeout)
      } else {
        Await.result(Future.firstCompletedOf(Seq(testResult, serverClosed)), timeout)
      }
    } finally {
      session.close()
      session.serverClosed.foreach { case SessionResult(exitCode, stderr) =>
        println(s"Server exited with code $exitCode and stderr:\n$stderr")
      }
    }
  }

}
