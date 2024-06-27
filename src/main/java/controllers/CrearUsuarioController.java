package controllers;

import domain.entities.usuarios.Rol;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

public class CrearUsuarioController {
    public ModelAndView pantallaDeRegistro(Request request, Response response) {
        return new ModelAndView(new HashMap<String, Object>(){{
            put("tiposDeUsuario", Arrays.stream(Rol.values()).map(Enum::name).collect(Collectors.toList()));
        }}, "index/crearUsuario.hbs");
    }
}