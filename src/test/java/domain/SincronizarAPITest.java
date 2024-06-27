package domain;

import db.EntityManagerHelper;
import domain.entities.services.geodds.ServicioDeUbicaciones;
import domain.entities.services.geodds.adapters.GeoDdsServiceAdapter;
import domain.entities.ubicaciones.Municipio;
import domain.entities.ubicaciones.Pais;
import domain.entities.ubicaciones.Provincia;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SincronizarAPITest {
    @Test
    public void sincronizarAPI() {
        ServicioDeUbicaciones servicioDeUbicaciones = ServicioDeUbicaciones.getInstancia();
        servicioDeUbicaciones.setAdapter(new GeoDdsServiceAdapter());

        EntityManagerHelper.beginTransaction();

        for(Pais pais : servicioDeUbicaciones.paises())
            for(Provincia provincia : servicioDeUbicaciones.provinciasPorPais(pais.getId())) {
                provincia.setPais(pais);
                for(Municipio municipio : servicioDeUbicaciones.municipiosPorProvincia(provincia.getId())) {
                    municipio.setProvincia(provincia);
                    servicioDeUbicaciones.localidadesPorMunicipio(municipio.getId()).forEach(l -> {
                        l.setMunicipio(municipio);
                        EntityManagerHelper.persist(l);
                    });
                }
            }

        EntityManagerHelper.commit();
        EntityManagerHelper.closeEntityManager();
        EntityManagerHelper.closeEntityManagerFactory();

        List<?> nombreProvincias = EntityManagerHelper.getEntityManager().createQuery("select nombre from Provincia").getResultList();
        System.out.println(nombreProvincias);
        Assert.assertEquals(24, nombreProvincias.size());
    }
}
