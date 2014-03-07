import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

@RunWith(classOf[JUnitRunner])
class RubyGemsSpec extends Specification {
  implicit def specs2DurationToFiniteDuration(d : org.specs2.time.Duration): scala.concurrent.duration.FiniteDuration = {
    import scala.concurrent.duration._
    Duration(d.inMilliseconds, MILLISECONDS)
  }

  "#fetchRepositoryUrl" should {
    "correctly fetch url" in {
      providers.RubyGems.fetchRepositoryUrl("rails") must beRight("http://github.com/rails/rails").await(timeout = 3 seconds)
    }

    "fail on unknown module" in {
      providers.RubyGems.fetchRepositoryUrl("NoSuchModule::12345abc") must beLeft.await(timeout = 3 seconds)
    }
  }
}
