package stock

import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http.{Method, Request, Response}
import com.twitter.finatra.http.filters.CommonFilters
import com.twitter.finatra.http.{Controller, HttpServer}
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.util._

/**
 * Fetch and handle finance data from an API:
 *
 * - Display basic informations for a stock symbol
 * - Fetch several stocks in parallel
 * - Compute metrics on a set of stocks and display them in table
 *
 * Run `server` in the console.
 *
 * Docs:
 * - http://twitter.github.io/util/
 * - http://twitter.github.io/finatra/
 */
object StockServer extends HttpServer {

  override def configureHttp(router: HttpRouter) {
    router
    .filter[CommonFilters]
    .add(StockController)
  }

}

object StockController extends Controller {

  get("/") { r: Request =>
    response.ok.view("index.mustache", Map("version" -> "v0"))
  }

  get("/stocks/:symbol") { r: Request =>
    ???
  }

}

case class Stock(symbol: String, name: String, price: Double, change: Double)

object StockService {
  private val API = "download.finance.yahoo.com"

  private val httpClient = {
    Http.client
      .withStreaming(false)
      .newService(s"$API:80")
  }

  def fetchSymbol(symbol: String): Future[Stock] = ???

  // Data will look like =>
  // "CRTO","Criteo S.A.",39.83,+0.61
  def fetchData(symbol: String): Future[String] = {
    val request = Request(Method.Get,s"/d/quotes.csv?s=$symbol&f=snac1")
    request.host=API
    httpClient(request).flatMap { res =>
      if(res.status.code == 200) {
        Future.value(res.contentString)
      } else {
        Future.exception(new Exception(s"Invalid status code ${res.status}"))
      }
    }
  }

}
