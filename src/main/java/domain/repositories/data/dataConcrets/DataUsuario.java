package domain.repositories.data.dataConcrets;

import db.EntidadPersistente;

import java.util.ArrayList;
import java.util.List;

public class DataUsuario {
    private static List<EntidadPersistente> usuarios = new ArrayList<>();

    public static List<EntidadPersistente> getList() {
        //Aca van los datos en concreto (guardarlos en la lista)

        return usuarios;
    }

    public static void addAll(List<EntidadPersistente> listClass) {
        usuarios.addAll(listClass);
    }
}
