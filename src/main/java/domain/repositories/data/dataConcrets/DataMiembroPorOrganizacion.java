package domain.repositories.data.dataConcrets;

import db.EntidadPersistente;
import domain.entities.actores.organizaciones.Contacto;

import java.util.ArrayList;
import java.util.List;

public class DataMiembroPorOrganizacion {
    private static List<EntidadPersistente> miembrosPorOrganizacion = new ArrayList<>();

    public static List<EntidadPersistente> getList() {
        //Aca van los datos en concreto (guardarlos en la lista)

        return miembrosPorOrganizacion;
    }

    public static void addAll(List<EntidadPersistente> listClass) {
        miembrosPorOrganizacion.addAll(listClass);
    }
}
