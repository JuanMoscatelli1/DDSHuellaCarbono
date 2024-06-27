package domain;

import db.EntityManagerHelper;
import domain.entities.actores.miembros.Miembro;
import domain.entities.actores.miembros.TipoDeDocumento;
import domain.entities.actores.organizaciones.Contacto;
import domain.entities.actores.organizaciones.Organizacion;
import domain.entities.actores.organizaciones.Sector;
import domain.entities.huellaDeCarbono.FactorDeEmision;
import domain.entities.importacionDeDatos.consumos.TipoDeConsumo;
import domain.entities.ubicaciones.Direccion;
import domain.entities.ubicaciones.Localidad;
import domain.entities.ubicaciones.Municipio;
import domain.entities.ubicaciones.Provincia;
import domain.entities.trayectos.transportePublico.Parada;
import domain.entities.trayectos.transportePublico.TransportePublico;
import domain.entities.usuarios.Rol;
import domain.entities.usuarios.Usuario;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import java.util.ArrayList;
import java.util.List;

import static domain.entities.actores.organizaciones.TipoDeOrganizacion.*;
import static org.junit.Assert.assertNotNull;

public class PersistenciaTest extends AbstractPersistenceTest implements WithGlobalEntityManager {
    @Test
    public void contextUp() {
        assertNotNull(entityManager());
    }

    @Test
    public void contextUpWithTransaction() {
        withTransaction(() -> {});
    }

    @Test
    public void init() {
        // Provincias

        Provincia
                buenosAires, catamarca, chaco, chubut, cordoba,
                corrientes, caba, entreRios, formosa, jujuy, laPampa, laRioja, mendoza, misiones,
                neuquen, rioNegro, salta, sanJuan, sanLuis, santaCruz, santaFe, santiago,
                tierraDelFuego, tucuman;

        buenosAires = new Provincia(168, "BUENOS AIRES");
        catamarca = new Provincia(169, "CATAMARCA");
        chaco = new Provincia(170, "CHACO");
        chubut = new Provincia(171, "CHUBUT");
        cordoba = new Provincia(172, "CORDOBA");
        corrientes = new Provincia(173,"CORRIENTES");
        caba = new Provincia(174, "CIUDAD DE BUENOS AIRES");
        entreRios = new Provincia(175, "ENTRE RIOS");
        formosa = new Provincia(176, "FORMOSA");
        jujuy = new Provincia(177, "JUJUY");
        laPampa = new Provincia(178, "LA PAMPA");
        laRioja = new Provincia(179, "MENDOZA");
        mendoza = new Provincia(180, "MENDOZA");
        misiones = new Provincia(181, "MISIONES");
        neuquen = new Provincia(182, "NEUQUEN");
        rioNegro = new Provincia(183, "RIO NEGRO");
        salta = new Provincia(184, "SALTA");
        sanJuan = new Provincia(185, "SAN JUAN");
        sanLuis = new Provincia(186, "SAN LUIS");
        santaCruz = new Provincia(187, "SANTA CRUZ");
        santaFe = new Provincia(188, "SANTA FE");
        santiago = new Provincia(189, "SANTIAGO DEL ESTERO");
        tierraDelFuego = new Provincia(190, "TIERRA DEL FUEGO");
        tucuman = new Provincia(191, "TUCUMAN");

        List<Provincia> provincias = new ArrayList<Provincia>() {{
            add(buenosAires); add(catamarca); add(chaco); add(chubut); add(cordoba);
            add(corrientes); add(caba); add(entreRios); add(formosa); add(jujuy);
            add(laPampa); add(laRioja); add(mendoza); add(misiones); add(neuquen);
            add(rioNegro); add(salta); add(sanJuan); add(sanLuis); add(santaCruz);
            add(santaFe); add(santiago); add(tierraDelFuego); add(tucuman);
        }};

        // Municipios

        Municipio
                muniCABA, laPlata, ezeiza, escobar, olavarria,
                rosario, caseros, sanLorenzo,
                cbaCapital, calamuchita, rioCuarto, alberti, adBrown, avellaneda,
                bahiaBlanca, balcarce, colon, varela
        ;

        muniCABA = new Municipio(241, "CIUDAD DE BUENOS AIRES");
        muniCABA.setProvincia(caba);

        laPlata = new Municipio(458, "LA PLATA");
        laPlata.setProvincia(buenosAires);
        ezeiza = new Municipio(368, "EZEIZA");
        ezeiza.setProvincia(buenosAires);
        escobar = new Municipio(365, "ESCOBAR");
        escobar.setProvincia(buenosAires);
        olavarria = new Municipio(434, "OLAVARRIA");
        olavarria.setProvincia(buenosAires);
        alberti = new Municipio(332, "ALBERTI");
        alberti.setProvincia(buenosAires);
        adBrown = new Municipio(333, "ALMIRANTE BROWN");
        adBrown.setProvincia(buenosAires);
        avellaneda = new Municipio(335, "AVELLANEDA");
        avellaneda.setProvincia(buenosAires);
        bahiaBlanca = new Municipio(338, "BAHIA BLANCA");
        bahiaBlanca.setProvincia(buenosAires);
        balcarce = new Municipio(339, "BALCARCE");
        balcarce.setProvincia(buenosAires);
        colon = new Municipio(357, "COLON");
        colon.setProvincia(buenosAires);
        varela = new Municipio(369, "FLORENCIO VARELA");
        varela.setProvincia(buenosAires);

        rosario = new Municipio(297, "ROSARIO");
        rosario.setProvincia(santaFe);
        caseros = new Municipio(287, "CASEROS");
        caseros.setProvincia(santaFe);
        sanLorenzo = new Municipio(302, "SAN LORENZO");
        sanLorenzo.setProvincia(santaFe);

        cbaCapital = new Municipio(261, "CORDOBA");
        cbaCapital.setProvincia(cordoba);
        calamuchita = new Municipio(260, "CALAMUCHITA");
        calamuchita.setProvincia(cordoba);
        rioCuarto = new Municipio(273, "RIO CUARTO");
        rioCuarto.setProvincia(cordoba);

        List<Municipio> municipios = new ArrayList<Municipio>() {{
            add(muniCABA); add(laPlata); add(ezeiza); add(escobar); add(olavarria);
            add(rosario); add(caseros); add(sanLorenzo); add(cbaCapital);
            add(calamuchita); add(rioCuarto);
        }};

        // Localidades

        Localidad
                almagro, belgrano, chacarita, monserrat,
                villaElisa, abasto, locLaPlata,
                funes, locRosario, alvear,
                cordobaCapital
        ;

        almagro = new Localidad(5334, "ALMAGRO",1213);
        almagro.setMunicipio(muniCABA);
        belgrano = new Localidad(5337, "BELGRANO", 1428);
        belgrano.setMunicipio(muniCABA);
        chacarita = new Localidad(5340, "CHACARITA", 1427);
        chacarita.setMunicipio(muniCABA);
        monserrat = new Localidad(5351, "MONSERRAT", 1074);
        monserrat.setMunicipio(muniCABA);

        villaElisa = new Localidad(603, "VILLA ELISA", 1894);
        villaElisa.setMunicipio(laPlata);
        abasto = new Localidad(585, "ABASTO", 1900);
        abasto.setMunicipio(laPlata);
        locLaPlata = new Localidad(594, "LA PLATA", 1900);
        locLaPlata.setMunicipio(laPlata);

        funes = new Localidad(3041, "FUNES", 2132);
        funes.setMunicipio(rosario);
        locRosario = new Localidad(3050, "ROSARIO", 2000);
        locRosario.setMunicipio(rosario);
        alvear = new Localidad(3034, "ALVEAR", 2126);
        alvear.setMunicipio(rosario);

        cordobaCapital = new Localidad(2404, "CORDOBA CAPITAL", 5000);
        cordobaCapital.setMunicipio(cbaCapital);

        List<Localidad> localidades = new ArrayList<Localidad>() {{
            add(almagro); add(belgrano); add(chacarita); add(monserrat); add(villaElisa);
            add(abasto); add(locLaPlata); add(funes); add(locRosario); add(alvear);
            add(cordobaCapital);
        }};

        // Organizaciones

        Organizacion arcor, galicia, ministerioJusticia, ml, archivo;
        Sector admin, rrhh, ventas, produccion, contabilidad, direccion, investigacion, estrategia;

        arcor = new Organizacion("Arcor", EMPRESA, "S.A.");
        arcor.setDireccion(new Direccion(
                funes,
                "MAIPU",
                "145"
        ));
        galicia = new Organizacion("Banco Galicia", EMPRESA, "S.A.");
        galicia.setDireccion(new Direccion(
                cordobaCapital,
                "PUEYRREDON",
                "8423"
        ));
        ministerioJusticia = new Organizacion("Ministerio de justicia de la nación", GUBERNAMENTAL, "Ministerio");
        ministerioJusticia.setDireccion(new Direccion(
                almagro,
                "CORDOBA",
                "6456"
        ));
        ml = new Organizacion("Mercado libre", EMPRESA, "S.R.L");
        ml.setDireccion(new Direccion(
                villaElisa,
                "SANTA FE",
                "792"
        ));
        archivo = new Organizacion("Archivo General de la Nación", INSTITUCION, "Dependencia");
        archivo.setDireccion(new Direccion(
                monserrat,
                "MONTEVIDEO",
                "1654"
        ));

        ventas = new Sector("Ventas", arcor);
        produccion = new Sector("Producción", arcor);
        direccion = new Sector("Dirección", galicia);
        admin = new Sector("Administración", galicia);
        investigacion = new Sector("Investigación", ministerioJusticia);
        estrategia = new Sector("Estrategia", ml);
        contabilidad = new Sector("Contabilidad", ml);
        rrhh = new Sector("Recursos humanos", archivo);

        // Contactos

        arcor.agregarContactos(
                new Contacto("Agostina", "Gómez", "agomez@gmail.com", "54", "118889999"),
                new Contacto("Federico", "Fernández", "ffernandez@gmail.com", "54", "118889999"),
                new Contacto("Joaquín", "López", "jlopez@gmail.com", "54", "118889999"),
                new Contacto("Cecilia", "Acuña", "agomez@gmail.com", "54", "118889999")
        );

        galicia.agregarContactos(
                new Contacto("Leonardo", "Gentili", "agentili@gmail.com", "54", "118889999"),
                new Contacto("Martín", "Falcone", "mfalcone@gmail.com", "54", "118889999"),
                new Contacto("Olivia", "Ludo", "oludo@gmail.com", "54", "118889999"),
                new Contacto("Josefina", "Álvarez", "jalvarez@gmail.com", "54", "118889999")
        );

        ministerioJusticia.agregarContactos(
                new Contacto("Catalina", "Gómez", "cgomez@gmail.com", "54", "118889999"),
                new Contacto("Martín", "Fernández", "mfernandez@gmail.com", "54", "118889999"),
                new Contacto("Delfina", "López", "dlopez@gmail.com", "54", "118889999"),
                new Contacto("Valentino", "Armani", "varmani@gmail.com", "54", "118889999")
        );

        ml.agregarContactos(
                new Contacto("Florencia", "Gómez", "fgomez@gmail.com", "54", "118889999"),
                new Contacto("Mariano", "Fernández", "mfernandez@gmail.com", "54", "118889999"),
                new Contacto("Antonella", "López", "alopez@gmail.com", "54", "118889999"),
                new Contacto("Ezequiel", "Acuña", "eacuna@gmail.com", "54", "118889999")
        );

        archivo.agregarContactos(
                new Contacto("Benjamín", "García", "bgarcia@gmail.com", "54", "118889999"),
                new Contacto("Matías", "Fiorio", "mfiorio@gmail.com", "54", "118889999"),
                new Contacto("Belén", "Lautaro", "blautaro@gmail.com", "54", "118889999"),
                new Contacto("Martina", "Agüero", "maguero@gmail.com", "54", "118889999")
        );

        List<Organizacion> organizaciones = new ArrayList<Organizacion>() {{
            add(arcor);
            add(galicia);
            add(ministerioJusticia);
            add(ml);
            add(archivo);
        }};

        // Miembros

        Miembro francoP, francoM, juan, ignacio, agustina;

        francoM = new Miembro("Franco", "Magne");
        francoM.setDireccion(new Direccion(
                locRosario,
                "25 DE MAYO",
                "5456"
        ));
        francoP = new Miembro("Franco", "Pasqualino");
        francoP.setDireccion(new Direccion(
                cordobaCapital,
                "24 DE SEPTIEMBRE",
                "687"
        ));
        juan = new Miembro("Juan", "Moscatelli");
        juan.setDireccion(new Direccion(
                chacarita,
                "JORGE NEWBERY",
                "988"
        ));
        ignacio = new Miembro("Ignacio", "Bisio");
        ignacio.setDireccion(new Direccion(
                locLaPlata,
                "58",
                "687"
        ));
        agustina = new Miembro("Agustina", "Razanov");
        agustina.setDireccion(new Direccion(
                belgrano,
                "O'HIGGINS",
                "756"
        ));

        List<Miembro> miembros = new ArrayList<Miembro>() {{
            add(francoM);
            add(francoP);
            add(juan);
            add(ignacio);
            add(agustina);
        }};

        miembros.forEach(m -> {
            m.setTipoDeDocumento(TipoDeDocumento.DNI);
            m.setNumeroDocumento(12345678);
        });

        // Usuarios

        francoM.setUsuario(new Usuario("francoM","123456", Rol.MIEMBRO));
        francoP.setUsuario(new Usuario("francoP","123456", Rol.MIEMBRO));
        juan.setUsuario(new Usuario("juan","123456", Rol.MIEMBRO));
        ignacio.setUsuario(new Usuario("ignacio","123456", Rol.MIEMBRO));
        agustina.setUsuario(new Usuario("agustina","123456", Rol.MIEMBRO));

        arcor.setUsuario(new Usuario("arcor","123456", Rol.ORGANIZACION));
        galicia.setUsuario(new Usuario("galicia","123456", Rol.ORGANIZACION));
        ministerioJusticia.setUsuario(new Usuario("ministerioJusticia","123456", Rol.ORGANIZACION));
        ml.setUsuario(new Usuario("ml","123456", Rol.ORGANIZACION));
        archivo.setUsuario(new Usuario("archivo","123456", Rol.ORGANIZACION));

        buenosAires.getSectorProvincial().setUsuario(new Usuario("buenosAires", "123456", Rol.AGENTE_SECTORIAL));
        caba.getSectorProvincial().setUsuario(new Usuario("caba", "123456", Rol.AGENTE_SECTORIAL));
        santaFe.getSectorProvincial().setUsuario(new Usuario("santaFe", "123456", Rol.AGENTE_SECTORIAL));
        cordoba.getSectorProvincial().setUsuario(new Usuario("cordoba", "123456", Rol.AGENTE_SECTORIAL));

        muniCABA.getSectorMunicipal().setUsuario(new Usuario("muniCABA", "123456", Rol.AGENTE_SECTORIAL));
        laPlata.getSectorMunicipal().setUsuario(new Usuario("laPlata", "123456", Rol.AGENTE_SECTORIAL));
        ezeiza.getSectorMunicipal().setUsuario(new Usuario("ezeiza", "123456", Rol.AGENTE_SECTORIAL));
        escobar.getSectorMunicipal().setUsuario(new Usuario("escobar", "123456", Rol.AGENTE_SECTORIAL));
        olavarria.getSectorMunicipal().setUsuario(new Usuario("olavarria", "123456", Rol.AGENTE_SECTORIAL));
        rosario.getSectorMunicipal().setUsuario(new Usuario("rosario", "123456", Rol.AGENTE_SECTORIAL));
        caseros.getSectorMunicipal().setUsuario(new Usuario("caseros", "123456", Rol.AGENTE_SECTORIAL));
        sanLorenzo.getSectorMunicipal().setUsuario(new Usuario("sanLorenzo", "123456", Rol.AGENTE_SECTORIAL));
        cbaCapital.getSectorMunicipal().setUsuario(new Usuario("cbaCapital", "123456", Rol.AGENTE_SECTORIAL));
        calamuchita.getSectorMunicipal().setUsuario(new Usuario("calamuchita", "123456", Rol.AGENTE_SECTORIAL));
        rioCuarto.getSectorMunicipal().setUsuario(new Usuario("rioCuarto", "123456", Rol.AGENTE_SECTORIAL));
        alberti.getSectorMunicipal().setUsuario(new Usuario("alberti", "123456", Rol.AGENTE_SECTORIAL));
        adBrown.getSectorMunicipal().setUsuario(new Usuario("adBrown", "123456", Rol.AGENTE_SECTORIAL));
        avellaneda.getSectorMunicipal().setUsuario(new Usuario("avellaneda", "123456", Rol.AGENTE_SECTORIAL));

        // Transporte publico

        TransportePublico subteC, subteD, colectivo64A, colectivo114;

        subteC = new TransportePublico("SUBTE", "C",
                new ArrayList<Parada>() {{
                    add(new Parada("Retiro", 1));
                    add(new Parada("General San Martín", 2));
                    add(new Parada("Lavalle", 3));
                    add(new Parada("Diagonal norte", 4));
                    add(new Parada("Avenida de mayo", 5));
                    add(new Parada("Moreno", 6));
                    add(new Parada("Independencia", 7));
                    add(new Parada("San Juan", 8));
                    add(new Parada("Constitución", 9));
                }}
        );

        subteD = new TransportePublico("SUBTE", "D",
                new ArrayList<Parada>() {{
                    add(new Parada("Catedral", 1));
                    add(new Parada("Nueve de julio", 2));
                    add(new Parada("Tribunales", 3));
                    add(new Parada("Callao", 4));
                    add(new Parada("Facultad de medicina", 5));
                    add(new Parada("Pueyrredón", 6));
                    add(new Parada("Agüero", 7));
                    add(new Parada("Bulnes", 8));
                    add(new Parada("Scalabrini Ortiz", 9));
                    add(new Parada("Plaza Italia", 10));
                    add(new Parada("Palermo", 11));
                    add(new Parada("Ministro Carranza", 12));
                    add(new Parada("Olleros", 13));
                    add(new Parada("José Hernández", 14));
                    add(new Parada("Juramento", 15));
                    add(new Parada("Congreso de Tucumán", 16));
                }}
        );

        colectivo64A = new TransportePublico("COLECTIVO", "64A",
                new ArrayList<Parada>() {{
                    add(new Parada("Avenida de Mayo", 1));
                    add(new Parada("Avenida Rivadavia", 2));
                    add(new Parada("Avenida Pueyrredón", 3));
                    add(new Parada("Beruti", 4));
                    add(new Parada("Ecuador", 5));
                    add(new Parada("Avenida Santa Fe1", 6));
                    add(new Parada("Plaza Italia", 7));
                    add(new Parada("Avenida Santa Fe2", 8));
                    add(new Parada("Avenida Luis María Campos", 9));
                }}
        );

        colectivo114 = new TransportePublico("COLECTIVO", "114",
                new ArrayList<Parada>() {{
                    add(new Parada("UTN Campus", 11.5));
                    add(new Parada("UTN Medrano", 0));
                }}
        );

        List<TransportePublico> transportes = new ArrayList<TransportePublico>() {{
            add(subteC);
            add(subteD);
            add(colectivo64A);
            add(colectivo114);
        }};

        // Factores de emision

        List<FactorDeEmision> factoresDeEmision = new ArrayList<>();

        for(TipoDeConsumo tipoDeConsumo : TipoDeConsumo.values()) {
            if (
                    tipoDeConsumo != TipoDeConsumo.DISTANCIA &&
                            tipoDeConsumo != TipoDeConsumo.PESO &&
                            tipoDeConsumo != TipoDeConsumo.CATEGORIA &&
                            tipoDeConsumo != TipoDeConsumo.MEDIO_DE_TRANSPORTE
            ) factoresDeEmision.add(new FactorDeEmision(tipoDeConsumo, 0.1));
        }

        EntityManagerHelper.beginTransaction();

        provincias.forEach(EntityManagerHelper::persist);
        municipios.forEach(EntityManagerHelper::persist);
        localidades.forEach(EntityManagerHelper::persist);
        organizaciones.forEach(EntityManagerHelper::persist);
        miembros.forEach(EntityManagerHelper::persist);
        transportes.forEach(EntityManagerHelper::persist);
        factoresDeEmision.forEach(EntityManagerHelper::persist);

        EntityManagerHelper.commit();

        EntityManagerHelper.closeEntityManager();
        EntityManagerHelper.closeEntityManagerFactory();
    }
}
