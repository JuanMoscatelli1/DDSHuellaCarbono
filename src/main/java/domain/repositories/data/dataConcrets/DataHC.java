package domain.repositories.data.dataConcrets;

import db.EntidadPersistente;

import java.util.ArrayList;
import java.util.List;

public class DataHC {
    private static List<EntidadPersistente> huellas = new ArrayList<>();

    public static List<EntidadPersistente> getList() {
        //Aca van los datos en concreto (guardarlos en la lista)

        return huellas;
    }

    public static void addAll(List<EntidadPersistente> listClass) {
        huellas.addAll(listClass);
    }
}
