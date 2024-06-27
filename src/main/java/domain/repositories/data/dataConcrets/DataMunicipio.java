package domain.repositories.data.dataConcrets;

import db.EntidadPersistente;

import java.util.ArrayList;
import java.util.List;

public class DataMunicipio {
    private static List<EntidadPersistente> municipios = new ArrayList<>();

    public static List<EntidadPersistente> getList() {
        //Aca van los datos en concreto (guardarlos en la lista)

        return municipios;
    }

    public static void addAll(List<EntidadPersistente> listClass) {
        municipios.addAll(listClass);
    }
}
