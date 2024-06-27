package domain;

import domain.entities.actores.miembros.Miembro;
import domain.entities.actores.organizaciones.Organizacion;
import static domain.entities.actores.organizaciones.TipoDeOrganizacion.*;

import domain.entities.actores.organizaciones.Sector;
import domain.entities.actores.solicitudes.Solicitud;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class VinculacionTest {
    private Organizacion arcor, galicia, ministerioJusticia, ml, archivo;
    private Sector admin, rrhh, ventas, produccion, contabilidad, direccion, investigacion, estrategia;
    private Miembro francoP, francoM, juan, ignacio, agustina;

    @Before
    public void init() {
        arcor = new Organizacion("Arcor", EMPRESA, "S.A.");
        galicia = new Organizacion("Banco Galicia", EMPRESA, "S.A.");
        ministerioJusticia = new Organizacion("Ministerio de justicia, seguridad y derechos humanos de la nación", GUBERNAMENTAL, "Ministerio");
        ml = new Organizacion("Mercado libre", EMPRESA, "S.R.L");
        archivo = new Organizacion("Archivo General de la Nación", INSTITUCION, "Dependencia");

        ventas = new Sector("Ventas", arcor);
        produccion = new Sector("Producción", arcor);
        direccion = new Sector("Dirección", galicia);
        admin = new Sector("Administración", galicia);
        investigacion = new Sector("Investigación", ministerioJusticia);
        estrategia = new Sector("Estrategia", ml);
        contabilidad = new Sector("Contabilidad", ml);
        rrhh = new Sector("Recursos humanos", archivo);

        francoM = new Miembro("Franco", "Magne");
        francoP = new Miembro("Franco", "Pasqualino");
        juan = new Miembro("Juan", "Moscatelli");
        ignacio = new Miembro("Ignacio", "Bisio");
        agustina = new Miembro("Agustina", "Razanov");
    }

    @Test
    public void solicitudesPendientes() {
        Solicitud solContabilidad = new Solicitud(contabilidad);
        Solicitud solEstrategia = new Solicitud(estrategia);

        francoP.solicitarVinculacion(solContabilidad);
        francoM.solicitarVinculacion(solEstrategia);

        Assert.assertEquals(2, ml.cantidadSolicitudes());
    }

    @Test
    public void solicitudesAceptadas() {
        Solicitud solDireccion = new Solicitud(direccion);
        Solicitud solInvestigacion = new Solicitud(investigacion);

        juan.solicitarVinculacion(solDireccion);
        juan.solicitarVinculacion(solInvestigacion);

        galicia.aceptarVinculacion(solDireccion);
        ministerioJusticia.aceptarVinculacion(solInvestigacion);

        Assert.assertEquals(2, juan.cantidadOrganizaciones());
    }

    @Test
    public void miembrosSector() {
        Solicitud solRRHH1 = new Solicitud(rrhh);
        Solicitud solRRHH2 = new Solicitud(rrhh);

        agustina.solicitarVinculacion(solRRHH1);
        ignacio.solicitarVinculacion(solRRHH2);

        archivo.aceptarVinculacion(solRRHH1);
        archivo.aceptarVinculacion(solRRHH2);

        Assert.assertEquals(2, rrhh.cantidadDeMiembros());
    }
}
