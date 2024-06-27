package domain.repositories.data.dataConcrets;

import db.EntidadPersistente;

import java.util.ArrayList;
import java.util.List;

public class DataActor {
    private static List<EntidadPersistente> actores = new ArrayList<>();

    public static List<EntidadPersistente> getList() {
        //Aca van los datos en concreto (guardarlos en la lista)

        return actores;
    }
    public static void addAll(List<EntidadPersistente> listClass) {
        actores.addAll(listClass);
    }

}
