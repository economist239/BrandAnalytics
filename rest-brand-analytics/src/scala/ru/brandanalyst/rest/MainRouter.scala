package ru.brandanalyst.rest

import ru.circumflex.web._
/**
 * @author daddy-bear
 *         Date: 05.05.12 - 7:44
 */

class MainRouter extends Router {
  get("/") = {
    ftl("/index.html.ftl")
  }
}


