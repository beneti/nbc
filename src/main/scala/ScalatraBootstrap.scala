import br.com.beneti.nubank._
import org.scalatra._
import javax.servlet.ServletContext
import br.com.beneti.nubank.api.Centrality

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(new Centrality, "/api/centrality/*")
  }
}
