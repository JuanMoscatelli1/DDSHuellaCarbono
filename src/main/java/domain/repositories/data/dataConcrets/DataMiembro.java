package domain.repositories.data.dataConcrets;

import db.EntidadPersistente;
import domain.entities.actores.organizaciones.Contacto;

import java.util.ArrayList;
import java.util.List;

public class DataMiembro {
    private static List<EntidadPersistente> miembros = new ArrayList<>();

    public static List<EntidadPersistente> getList() {
        //Aca van los datos en concreto (guardarlos en la lista)

        return miembros;
    }

    public static void addAll(List<EntidadPersistente> listClass) {
        miembros.addAll(listClass);
    }
}
