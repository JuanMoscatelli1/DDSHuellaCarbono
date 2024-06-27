package controllers;

import domain.entities.usuarios.Usuario;
import domain.repositories.Repositorio;
import domain.repositories.factories.FactoryRepositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.Optional;

import static domain.entities.usuarios.Rol.*;

public class LoginController {
    private Repositorio<Usuario> repoUsuario;

    public LoginController() {
        repoUsuario = FactoryRepositorio.get(Usuario.class);
    }

    public ModelAndView pantallaDeLogin(Request request, Response response) {
        return new ModelAndView(null, "index/login.hbs");
    }

    public Response login(Request request, Response response) {
        try {
            List<Usuario> usuarios = repoUsuario.buscarTodos();
            Optional<Usuario> optionalUsuario = usuarios.stream().filter(u ->
                    u.getNombreDeUsuario().equals(request.queryParams("nombre_de_usuario")) &&
                    u.getContrasenia().equals(request.queryParams("contrasenia")))
                    .findFirst();

            if(optionalUsuario.isPresent()) {
                Usuario usuario = optionalUsuario.get();

                request.session(true);
                request.session().attribute("id", usuario.getId());

                if(usuario.tienePermiso(MIEMBRO)) response.redirect("/miembro/perfil");
                if(usuario.tienePermiso(ORGANIZACION)) response.redirect("/organizacion/miOrganizacion");
                if(usuario.tienePermiso(AGENTE_SECTORIAL)) response.redirect("/agente/guia");
                if(usuario.tienePermiso(ADMINISTRADOR)) response.redirect("/admin/menu");
            }
            else response.redirect("/login");
        }
        catch (Exception ex) {
            response.redirect("/login");
        }
        return response;
    }

    public Response logout(Request request, Response response) {
        request.session().invalidate();
        response.redirect("/login");
        return response;
    }

    public ModelAndView prohibido(Request request, Response response) {
        return new ModelAndView(null, "prohibido.hbs");
    }
}
