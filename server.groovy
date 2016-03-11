@Grab(group='io.vertx', module='vertx-lang-groovy', version='3.2.1')
@Grab(group='io.vertx', module='vertx-core', version='3.2.1')
@Grab(group='io.vertx', module='vertx-web', version='3.2.1')
@GrabExclude('org.codehaus.groovy:groovy-all')

import io.vertx.groovy.ext.web.Router
import io.vertx.groovy.core.Vertx

def vertx = Vertx.vertx()
def server = vertx.createHttpServer()
def router = Router.router(vertx)

def lastRequest = 0
def firstRequest = 0

router.route().blockingHandler({ routingContext ->

  def response = routingContext.response()
  response.putHeader("content-type", "text/plain")

  def now = System.currentTimeMillis()
  if (now - lastRequest > 5000) {
  	firstRequest = now
  }
  lastRequest = now

  if (now - firstRequest > 7500 && now - firstRequest < 22500) { 
  	Thread.sleep(14950);
  }

  Thread.sleep(50)

  response.end("Hello World from Vert.x-Web!")
})


println "Up and running"

server.requestHandler(router.&accept).listen(8080)
