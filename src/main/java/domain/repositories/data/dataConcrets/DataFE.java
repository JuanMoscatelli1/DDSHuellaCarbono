package domain.repositories.data.dataConcrets;

import db.EntidadPersistente;

import java.util.ArrayList;
import java.util.List;

public class DataFE {
    private static List<EntidadPersistente> factores = new ArrayList<>();

    public static List<EntidadPersistente> getList() {
        //Aca van los datos en concreto (guardarlos en la lista)

        return factores;
    }

    public static void addAll(List<EntidadPersistente> listClass) {
        factores.addAll(listClass);
    }
}
