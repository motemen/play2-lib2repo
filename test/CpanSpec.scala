import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

@RunWith(classOf[JUnitRunner])
class CpanSpec extends Specification {
  implicit def specs2DurationToFiniteDuration(d : org.specs2.time.Duration): scala.concurrent.duration.FiniteDuration = {
    import scala.concurrent.duration._
    Duration(d.inMilliseconds, MILLISECONDS)
  }

  "#fetchRepositoryUrl" should {
    "correctly fetch url" in {
      providers.Cpan.fetchRepositoryUrl("Wight::Node") must equalTo(Some("git://github.com/motemen/Wight")).await(timeout = 3 seconds)
    }

    "fail on unknown module" in {
      providers.Cpan.fetchRepositoryUrl("NoSuchModule::12345abc") must beNone.await(timeout = 3 seconds)
    }
  }
}
