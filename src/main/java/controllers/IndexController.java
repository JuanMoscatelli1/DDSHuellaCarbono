package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class IndexController {
    public ModelAndView pantallaDeInicio(Request request, Response response) {
        return new ModelAndView(null, "index/index.hbs");
    }
}
