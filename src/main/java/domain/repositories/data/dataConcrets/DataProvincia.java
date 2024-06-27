package domain.repositories.data.dataConcrets;

import db.EntidadPersistente;

import java.util.ArrayList;
import java.util.List;

public class DataProvincia {
    private static List<EntidadPersistente> provincias = new ArrayList<>();

    public static List<EntidadPersistente> getList() {
        //Aca van los datos en concreto (guardarlos en la lista)

        return provincias;
    }

    public static void addAll(List<EntidadPersistente> listClass) {
        provincias.addAll(listClass);
    }
}
