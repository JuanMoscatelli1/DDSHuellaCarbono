package domain.entities.helpersUsuario;

import db.EntityManagerHelper;
import domain.entities.usuarios.Usuario;
import spark.Request;

public class UsuarioLogueadoHelper {

    public static Usuario usuarioLogueado(Request request){
        return EntityManagerHelper.getEntityManager()
                .find(Usuario.class, request.session().attribute("id"));
    }
}
