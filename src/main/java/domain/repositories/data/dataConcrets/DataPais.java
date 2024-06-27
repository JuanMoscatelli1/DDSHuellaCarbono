package domain.repositories.data.dataConcrets;

import domain.entities.ubicaciones.Pais;

import java.util.ArrayList;
import java.util.List;

public class DataPais {
    private static List<Pais> paises = new ArrayList<>();

    public static List<Pais> getList() {
        Pais pais1 = new Pais();
        pais1.setNombre("Argentina");
        pais1.setId(1);
        paises.add(pais1);
        return paises;
    }

    public static void addAll(List<Pais> listClass) {
        paises.addAll(listClass);
    }
}
