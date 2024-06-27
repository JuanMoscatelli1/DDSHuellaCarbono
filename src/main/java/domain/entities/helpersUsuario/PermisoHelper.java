package domain.entities.helpersUsuario;

import domain.entities.usuarios.Rol;
import spark.Request;

public class PermisoHelper {

    public static Boolean usuarioTienePermiso(Request request, Rol rol){
        return UsuarioLogueadoHelper.usuarioLogueado(request).tienePermiso(rol);
    }
}
