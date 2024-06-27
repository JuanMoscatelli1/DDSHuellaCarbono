package domain.repositories.data.dataConcrets;

import db.EntidadPersistente;

import java.util.ArrayList;
import java.util.List;

public class DataRol {
    private static List<EntidadPersistente> roles = new ArrayList<>();

    public static List<EntidadPersistente> getList() {
        //Aca van los datos en concreto (guardarlos en la lista)

        return roles;
    }

    public static void addAll(List<EntidadPersistente> listClass) {
        roles.addAll(listClass);
    }
}
