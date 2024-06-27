package domain;

import domain.entities.actores.miembros.Miembro;
import domain.entities.services.geodds.ServicioDeUbicaciones;
import domain.entities.services.geodds.adapters.GeoDdsServiceAdapter;
import domain.entities.trayectos.tramo.Distancia;
import domain.entities.ubicaciones.Localidad;
import domain.entities.trayectos.tramo.TramoTransportePrivado;
import domain.entities.trayectos.vehiculosPrivados.TransportePrivado;
import domain.entities.trayectos.tramo.Tramo;
import domain.entities.ubicaciones.Direccion;
import org.junit.Before;
import org.junit.Test;

public class GeoDdsTest {
    private ServicioDeUbicaciones servicioGeodds;
    private Direccion direccionOrigen, direccionDestino;

    @Before
    public void init() {
        servicioGeodds = ServicioDeUbicaciones.getInstancia();
        servicioGeodds.setAdapter(new GeoDdsServiceAdapter());

        Localidad localidad1 = new Localidad(457, "SANTA CLARA DEL MAR", 7609);
        Localidad localidad2 = new Localidad(3279, "CARHUE", 6430);

        direccionOrigen = new Direccion(localidad1, "maipu", "100");
        direccionDestino = new Direccion(localidad2, "O'Higgins", "200");
    }

    @Test
    public void calcularDistancia() {
        Distancia distancia = servicioGeodds.obtenerDistancia(direccionOrigen, direccionDestino);
        System.out.println("Distancia: " + distancia.getValor() + " " + distancia.getUnidad());
    }

    @Test
    public void distanciaEnAuto() {
        Tramo tramo = new TramoTransportePrivado(
                new TransportePrivado(null, "PARTICULAR", "AUTOMOVIL", "GASOIL"),
                direccionOrigen, direccionDestino
        );
        tramo.agregarMiembro(new Miembro("Oscar", "Isacc"));
        System.out.printf("Huella de carbono automóvil: %.2f grCO2eq", tramo.calcularHuella());
    }
}
