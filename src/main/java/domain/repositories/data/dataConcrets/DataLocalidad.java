package domain.repositories.data.dataConcrets;

import db.EntidadPersistente;

import java.util.ArrayList;
import java.util.List;

public class DataLocalidad {
    private static List<EntidadPersistente> localidades = new ArrayList<>();

    public static List<EntidadPersistente> getList() {
        //Aca van los datos en concreto (guardarlos en la lista)

        return localidades;
    }

    public static void addAll(List<EntidadPersistente> listClass) {
        localidades.addAll(listClass);
    }
}
