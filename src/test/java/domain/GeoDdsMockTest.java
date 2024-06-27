package domain;

import domain.entities.actores.miembros.Miembro;
import domain.entities.actores.organizaciones.Organizacion;
import domain.entities.actores.organizaciones.Sector;
import domain.entities.actores.sectores.SectorMunicipal;
import domain.entities.actores.solicitudes.Solicitud;
import domain.entities.trayectos.tramo.Tramo;
import domain.entities.trayectos.tramo.TramoTransportePrivado;
import domain.entities.trayectos.tramo.Trayecto;
import domain.entities.trayectos.vehiculosPrivados.TipoVehiculo;
import domain.entities.trayectos.vehiculosPrivados.TransportePrivado;
import domain.entities.ubicaciones.Localidad;
import domain.entities.ubicaciones.Direccion;
import domain.entities.services.geodds.ServicioDeUbicaciones;
import domain.entities.services.geodds.adapters.AdapterServicio;
import domain.entities.trayectos.tramo.Distancia;

import domain.entities.ubicaciones.Municipio;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static domain.entities.actores.organizaciones.TipoDeOrganizacion.*;
import static org.mockito.Mockito.*;

public class GeoDdsMockTest {
    private AdapterServicio adapterMock;
    private ServicioDeUbicaciones servicioGeodds;

    @Before
    public void init() {
        adapterMock = mock(AdapterServicio.class);
        servicioGeodds = ServicioDeUbicaciones.getInstancia();
        servicioGeodds.setAdapter(adapterMock);
    }

    @Test
    public void GeoDdsServiceProveeDistanciaTest() {
        Direccion direccion1 = new Direccion(new Localidad(), "Italia", "300");
        Direccion direccion2 = new Direccion(new Localidad(), "San Raimundo", "67");

        Distancia distancia = new Distancia(123456, "KM");

        when(this.adapterMock.distancia(direccion1, direccion2)).thenReturn(distancia);

        Assert.assertEquals(distancia,
                this.servicioGeodds.obtenerDistancia(direccion1, direccion2)
        );
    }

    @Test
    public void trayectos() {
        Municipio municipio = new Municipio();
        Localidad localidad = new Localidad();
        localidad.setMunicipio(municipio);

        Direccion direccion1 = new Direccion(localidad, "Italia", "300");
        Direccion direccion2 = new Direccion(localidad, "San Raimundo", "67");

        Distancia distancia = new Distancia(123456, "KM");

        when(this.adapterMock.distancia(direccion1, direccion2)).thenReturn(distancia);

        Assert.assertEquals(distancia,
                this.servicioGeodds.obtenerDistancia(direccion1, direccion2)
        );

        Miembro francoM, francoP;

        francoM = new Miembro("Franco", "Magne");
        francoM.setDireccion(direccion1);
        francoP = new Miembro("Franco", "Pasqualino");
        francoP.setDireccion(direccion2);

        Organizacion arcor;
        Sector ventas;

        arcor = new Organizacion("Arcor", EMPRESA, "S.A.");
        arcor.setDireccion(direccion1);

        ventas = new Sector("Ventas", arcor);

        francoM.solicitarVinculacion(new Solicitud(ventas));
        francoP.solicitarVinculacion(new Solicitud(ventas));

        Tramo tramo = new TramoTransportePrivado(
                new TransportePrivado(null, "PARTICULAR", "AUTOMOVIL", "GASOIL"),
                direccion1, direccion2
        );

        Trayecto trayecto = new Trayecto();
        trayecto.setOrganizacion(arcor);
        trayecto.agregarMiembro(francoM);

        tramo.agregarMiembro(francoM);
        tramo.agregarMiembro(francoP);

        trayecto.agregarTramo(tramo);

        Assert.assertEquals(trayecto.calcularDistancia().getValor(), 123456, 0.0);
        Assert.assertEquals(2, tramo.cantidadMiembros());
        Assert.assertEquals(123456, tramo.calcularDistancia().getValor(), 0.0);
        Assert.assertEquals(123456 * 0.4 / 2, tramo.calcularHuella(), 0.0);
    }
}
