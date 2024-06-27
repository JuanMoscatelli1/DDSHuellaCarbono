package controllers;

import domain.entities.actores.miembros.Miembro;
import domain.entities.actores.organizaciones.Organizacion;
import domain.entities.actores.organizaciones.Sector;
import domain.entities.actores.solicitudes.Solicitud;
import domain.entities.trayectos.tramo.Trayecto;
import domain.entities.trayectos.transportePublico.Parada;
import domain.entities.trayectos.transportePublico.TipoDeTransportePublico;
import domain.entities.trayectos.transportePublico.TransportePublico;
import domain.entities.trayectos.vehiculosPrivados.TipoCombustible;
import domain.entities.trayectos.vehiculosPrivados.TipoVehiculo;
import domain.entities.trayectos.vehiculosPrivados.TransportePrivado;
import domain.entities.trayectos.tramo.Tramo;
import domain.entities.trayectos.tramo.TramoTransportePrivado;
import domain.entities.trayectos.tramo.TramoTransportePublico;
import domain.entities.ubicaciones.Direccion;
import domain.entities.ubicaciones.Localidad;
import domain.entities.ubicaciones.Municipio;
import domain.repositories.Repositorio;
import domain.repositories.factories.FactoryRepositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class MiembroController {
    private Repositorio<Miembro> repoMiembro;
    private Repositorio<Organizacion> repoOrganizacion;
    private Repositorio<Sector> repoSector;
    private Repositorio<Localidad> repoLocalidad;
    private Repositorio<TransportePublico> repoTP;
    private Repositorio<Parada> repoParadas;
    private Trayecto trayecto;
    private Organizacion organizacion;
    private Repositorio<Tramo> repoTramo;

    public MiembroController(){
        this.repoMiembro = FactoryRepositorio.get(Miembro.class);
        this.repoOrganizacion = FactoryRepositorio.get(Organizacion.class);
        this.repoSector = FactoryRepositorio.get(Sector.class);
        this.repoLocalidad = FactoryRepositorio.get(Localidad.class);
        this.repoTP = FactoryRepositorio.get(TransportePublico.class);
        this.repoParadas = FactoryRepositorio.get(Parada.class);
        this.repoTramo = FactoryRepositorio.get(Tramo.class);
    }

    public Miembro buscarMiembro(Request request) {
        return repoMiembro.buscar("usuario", request.session().attribute("id"));
    }

    public Integer getId(Request request, String queryParam) {
        return new Integer(request.queryParams(queryParam));
    }

    public ModelAndView registrarTrayecto(Request request, Response response) {
        return new ModelAndView(new HashMap<String, Object>() {{
            put("organizaciones", buscarMiembro(request).convertirADTO().getOrganizaciones());
        }}, "miembros/registrarTrayecto.hbs");
    }

    public ModelAndView registrarTramo(Request request, Response response) {
        if(organizacion == null)
            organizacion = repoOrganizacion.buscar(new Integer(request.queryParams("organizacion")));

        if(trayecto == null) {
            trayecto = new Trayecto();
            trayecto.setOrganizacion(organizacion);
        }

        Map<String, Object> map = new HashMap<>();

        map.put("transportes", new ArrayList<String>(){{
            add("Vehículo particular");
            add("Servicio contratado");
            add("Transporte público");
            add("Bicicleta o a pie");
        }});

        Municipio municipio = buscarMiembro(request).getDireccion().getMunicipio();
        List<Localidad> localidades = repoLocalidad.buscarTodos("municipio", municipio.getId());
        map.put("localidades", localidades);

        map.put("tipoVehiculo", Arrays.stream(TipoVehiculo.values()).map(Enum::toString).collect(Collectors.toList()));
        map.put("tipoCombustible", Arrays.stream(TipoCombustible.values()).map(Enum::toString).collect(Collectors.toList()));
        map.put("tipoTP", Arrays.stream(TipoDeTransportePublico.values()).map(Enum::toString).collect(Collectors.toList()));
        map.put("transportesPublicos",
                repoTP.buscarTodos().stream()
                        .map(TransportePublico::convertirADTO)
                        .collect(Collectors.toList())
        );

        List<Miembro> miembros = organizacion.getMiembros();
        miembros.remove(buscarMiembro(request));

        map.put("miembros", miembros.stream().map(Miembro::convertirADTO).collect(Collectors.toList()));

        return new ModelAndView(map, "miembros/registrarTramo.hbs");
    }

    public Response agregarTramo(Request request, Response response) {
        Miembro miembro = buscarMiembro(request);
        Tramo tramo;

        if(request.queryParams("transporte").equals("2")) {
            tramo = new TramoTransportePublico(
                    repoTP.buscar(new Integer(request.queryParams("transporte_publico"))),
                    repoParadas.buscar(new Integer(request.queryParams("parada_origen"))),
                    repoParadas.buscar(new Integer(request.queryParams("parada_destino")))
            );
            tramo.agregarMiembro(miembro);
            trayecto.agregarTramo(tramo);
            repoTramo.agregar(tramo);
        }

        else {
            Localidad localidadOrigen, localidadDestino;
            Direccion direccionOrigen, direccionDestino;
            TransportePrivado transportePrivado = null;
            String vehiculo = null;

            switch (request.queryParams("transporte")) {
                case "0":
                    vehiculo = "auto";
                    transportePrivado = new TransportePrivado(
                            null,
                            "PARTICULAR",
                            request.queryParams("tipo_vehiculo"),
                            request.queryParams("tipo_combustible")
                    );
                    break;

                case "1":
                    vehiculo = "servicio";
                    transportePrivado = new TransportePrivado(
                            request.queryParams("servicio_contratado"),
                            "SERVICIO_CONTRATADO",
                            "AUTOMOVIL",
                            null
                    );
                    break;

                case "3":
                    vehiculo = "bici";
                    transportePrivado = new TransportePrivado(null, "BICICLETA_O_PIE", "BICICLETA", null);
                    break;
            }

            localidadOrigen = repoLocalidad.buscar(
                    getId(request, "localidad_origen_" + vehiculo)
            );

            localidadDestino = repoLocalidad.buscar(
                    getId(request, "localidad_destino_" + vehiculo)
            );

            direccionOrigen = new Direccion(
                    localidadOrigen,
                    request.queryParams("calle_origen_" + vehiculo),
                    request.queryParams("altura_origen_" + vehiculo)
            );

            direccionDestino = new Direccion(
                    localidadDestino,
                    request.queryParams("calle_destino_" + vehiculo),
                    request.queryParams("altura_destino_" + vehiculo)
            );

            tramo = new TramoTransportePrivado(transportePrivado, direccionOrigen, direccionDestino);
            tramo.agregarMiembro(miembro);
            trayecto.agregarTramo(tramo);
            repoTramo.agregar(tramo);

            String esCompartido = request.queryParams("confirmar_tramo_comp_SC");

            if (esCompartido != null && esCompartido.equals("1")) {
                Miembro miembro2 = repoMiembro.buscar(new Integer(request.queryParams("miembro_compartido_" + vehiculo)));
                miembro2.agregarTramo(tramo);
                repoMiembro.modificar(miembro2);
            }
        }
        response.redirect("/miembro/registrarTramo");
        return response;
    }

    public Response agregarTrayecto(Request request, Response response) {
        Miembro miembro = buscarMiembro(request);
        miembro.agregarTramo(trayecto);
        repoTramo.agregar(trayecto);
        repoTramo.modificar(trayecto);
        repoOrganizacion.modificar(trayecto.getOrganizacion());
        trayecto = null;
        organizacion = null;
        response.redirect("/miembro/registrarTrayecto");
        return response;
    }


    public ModelAndView calcularHuella(Request request, Response response) {
        return new ModelAndView(null, "miembros/huellaCalculador.hbs");
    }

    public ModelAndView vistaHuella(Request request, Response response) {
        Miembro miembro = buscarMiembro(request);
        HashMap<String, Object> map = new HashMap<>();
        Integer anio = null, mes = null;

        switch(request.queryParams("tipo_huella")){
            case "Mensual":
                mes = new Integer(request.queryParams("mes"));
            case "Anual":
                anio = new Integer(request.queryParams("anio"));
        }

        List<Tramo.TramoDTO> tramos = miembro.convertirADTO().getTramos(LocalDate.of(anio, mes, 1));
        List<Tramo.TramoDTO> trayectos = tramos.stream().filter(t -> t.getVehiculo() == null).collect(Collectors.toList());

        map.put("huella", miembro.getCalculadorHuella().calcularHuella(anio, mes));
        map.put("trayectos", trayectos);
        map.put("tramos", trayectos.stream().flatMap(t -> t.getTramos().stream()).collect(Collectors.toList()));

        return new ModelAndView(map, "miembros/huellaVista.hbs");
    }

    public ModelAndView vincularOrganizacion(Request request, Response response) {
        List<Organizacion.OrganizacionDTO> organizaciones = repoOrganizacion.buscarTodos().stream()
                .map(Organizacion::convertirADTO).collect(Collectors.toList());

        buscarMiembro(request).convertirADTO().getOrganizaciones().forEach(organizaciones::remove);

        Map<String, Object> map = new HashMap<String, Object>() {{
            put("organizaciones", organizaciones);
        }};

        return new ModelAndView(map, "miembros/organizacionVinculacion.hbs");
    }

   public Response vincularOrganizacionSeleccionada(Request request, Response response) {
        Miembro miembro = buscarMiembro(request);
        Organizacion org = repoOrganizacion.buscar(new Integer(request.queryParams("organizacion")));
        Sector sector = repoSector.buscar(new Integer(request.queryParams("sector")));

        miembro.solicitarVinculacion(new Solicitud(sector));
        repoOrganizacion.modificar(org);

        response.redirect("/miembro/vincularOrganizacion");
        return response;
    }

    public ModelAndView miPerfil(Request request, Response response) {
        Miembro miembro = buscarMiembro(request);

        HashMap<String, Object> map = new HashMap<>();
        map.put("miembro", miembro.convertirADTO());

        HashMap<Organizacion.OrganizacionDTO, Sector.SectorDTO> organizaciones = new HashMap<>();
        miembro.convertirADTO().getOrganizaciones().forEach(o -> organizaciones.put(o, o.getSector(miembro)));
        map.put("organizaciones", organizaciones);

        return new ModelAndView(map, "miembros/perfil.hbs");
    }

    public ModelAndView miOrganizacion(Request request, Response response) {
        Miembro.MiembroDTO miembro = buscarMiembro(request).convertirADTO();
        Organizacion organizacion = repoOrganizacion.buscar(new Integer(request.params("id")));

        HashMap<String, Object> map = new HashMap<>();
        List<Tramo.TramoDTO> trayectos = miembro
                .getTramos(organizacion).stream()
                .filter(t -> t.getVehiculo() == null)
                .collect(Collectors.toList());
        List<Tramo.TramoDTO> tramos = miembro.getTramos(organizacion).stream().filter(t -> t.getVehiculo() != null).collect(Collectors.toList());


        map.put("trayectos", trayectos);
        map.put("tramos", tramos);
        map.put("organizacion", organizacion.convertirADTO());

        return new ModelAndView(map, "miembros/organizacionVista.hbs");
    }

    public ModelAndView mostrarProhibido(Request request, Response response){
        return new ModelAndView(null, "miembros/prohibido.hbs");
    }
}